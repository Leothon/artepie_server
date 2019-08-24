package controller;

import dao.GetDataDao;
import dao.SendDataDao;
import dto.Result;
import dto.Token;
import entity.Bill;
import entity.TokenValid;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.UserService;
import utils.commonUtils;
import utils.tokenUtils;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    GetDataDao getDataDao;

    @Autowired
    SendDataDao sendDataDao;

    @GetMapping("/usephonelogin")
    @ResponseBody
    public Result<User> usePhoneLogin(@RequestParam("phonenumber") String phonenumber) {

        Token tokeninfo = null;
        if (!userService.phoneExits(phonenumber)) {
            //未注册
            String uuid = "5699" + commonUtils.createUUID();
            String token = tokenUtils.getToken(uuid);
            tokeninfo = new Token();
            tokeninfo.setInfo("注册成功");
            tokeninfo.setToken(token);
            String registerTime = commonUtils.getTime();
            userService.register(uuid, phonenumber, token, "用户" + commonUtils.getRandomString(8), registerTime);
            return new Result<User>(true, userService.getUserInfoById(uuid));
        } else {
            //已注册
            String token = userService.returnTokenByPhone(phonenumber);
            TokenValid tokenValid = tokenUtils.ValidToken(token);
            String uuid = tokenValid.getUid();
            if (tokenValid.isExpired()){
                String newtoken = tokenUtils.getToken(uuid);
                userService.updateToken(newtoken,uuid);
            }
            return new Result<User>(true, userService.getUserInfoById(uuid));

        }
    }


    @GetMapping("/usepasswordlogin")
    @ResponseBody
    public Result<User> usePasswordLogin(@RequestParam("phonenumber") String phoneNumber,@RequestParam("password") String password){

        if (!userService.phoneExits(phoneNumber)){
            return new Result<>(false,"该号码未注册，请使用短信验证码注册！");
        }else {
            String passwordInDB = userService.getPasswordByPhoneNumber(phoneNumber);
            if (passwordInDB.equals("")){
                return new Result<>(false,"您尚未设置密码，请使用验证码登录");
            }else if (passwordInDB.equals(password)){
                String token = userService.returnTokenByPhone(phoneNumber);
                TokenValid tokenValid = tokenUtils.ValidToken(token);
                String uuid = tokenValid.getUid();
                if (tokenValid.isExpired()){
                    String newtoken = tokenUtils.getToken(uuid);
                    userService.updateToken(newtoken,uuid);
                }
                return new Result<>(true, userService.getUserInfoById(tokenUtils.ValidToken(userService.returnTokenByPhone(phoneNumber)).getUid()));
            }else {
                return new Result<>(false,"密码错误！");
            }
        }

    }


    @GetMapping("/getuserinfo")
    @ResponseBody
    public Result<User> getUserInfo(@RequestParam("token") String token) {


        TokenValid tokenValid = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        ArrayList<Bill> inBill = getDataDao.getInBill(uuid);

        DecimalFormat df = new DecimalFormat("#.00");
        Float balance = 0.00f;
        for (int j = 0;j < inBill.size();j ++){
            inBill.get(j).setCount(commonUtils.computeAuthorPrice(inBill.get(j).getCount()));

            balance += Float.valueOf(inBill.get(j).getCount());
        }
        String endBalance = df.format(balance);

        sendDataDao.updateUserBalance(uuid, endBalance);
        return new Result<User>(true, userService.getUserInfoById(uuid));
    }

    @GetMapping("/getuserinfobyid")
    @ResponseBody
    public Result<User> getUserInfoById(@RequestParam("userid") String userId) {

        return new Result<User>(true, userService.getUserInfoById(userId));
    }


    @PostMapping("/uploadfile")
    @ResponseBody
    public Result<String> updateFile(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {


        String fileName = file.getOriginalFilename();

        File targetFile = new File("/image", fileName);

        if (!targetFile.exists()) {

            targetFile.mkdirs();

        }

        file.transferTo(targetFile);

        return new Result<>(true, fileName);

    }

    @PostMapping("/updateuserinfo")
    @ResponseBody
    public Result<String> updateUserInfo(@RequestBody User user) {


        userService.updateInfo(user);

        return new Result<>(true, "success");
    }


    @PostMapping("/qquserregister")
    @ResponseBody
    public Result<User> qqUserRegister(@RequestBody User user) {
        String uuid = "tenc" + commonUtils.createUUID();
        String token = tokenUtils.getToken(uuid);
        Token tokeninfo = new Token();
        tokeninfo.setInfo("注册成功");
        tokeninfo.setToken(token);
        String registerTime = commonUtils.getTime();
        String phone = user.getUser_phone() + "";
        userService.qqRegister(uuid, user.getUser_icon(), user.getUser_name(), user.getUser_sex(), registerTime, token, user.getTencent_token(),phone);

        return new Result<User>(true, userService.getUserInfoById(uuid));

    }

    @PostMapping("/wechatuserregister")
    @ResponseBody
    public Result<User> weChatUserRegister(@RequestBody User user) {
        String uuid = "wec" + commonUtils.createUUID();
        String token = tokenUtils.getToken(uuid);
        Token tokeninfo = new Token();
        tokeninfo.setInfo("注册成功");
        tokeninfo.setToken(token);
        String registerTime = commonUtils.getTime();
        String phone = user.getUser_phone() + "";
        userService.weChatRegister(uuid, user.getUser_icon(), user.getUser_name(), user.getUser_sex(), user.getUser_address(),registerTime, token, user.getTencent_token(),phone);

        return new Result<User>(true, userService.getUserInfoById(uuid));

    }


    @GetMapping("/getuserinfobyqq")
    @ResponseBody
    public Result<User> getUserInfoByQQ(@RequestParam("accesstoken") String accessToken) {
        String token = userService.getTokenByAccessToken(accessToken);
        System.out.println("令牌值" + token);
        TokenValid tokenValid = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();
        System.out.println("用户ID" + uuid);
        if (tokenValid.isExpired()){
            String newtoken = tokenUtils.getToken(uuid);
            System.out.println("新的令牌值" + newtoken);
            userService.updateToken(newtoken,uuid);
        }
        return new Result<>(true, userService.getUserInfoByQQ(accessToken));
    }


    @GetMapping("/isphoneexits")
    @ResponseBody
    public Result<String> isPhoneExits(@RequestParam("phone") String phoneNumber) {
        if (userService.phoneExits(phoneNumber)) {
            return new Result<>(true, "yes");
        } else {
            return new Result<>(true, "no");
        }

    }

    @PostMapping("/bindphone")
    @ResponseBody
    public Result<String> bindPhoneNumber(@RequestParam("token") String token, @RequestParam("phonenumber") String phoneNumber) {
        String uuid = tokenUtils.ValidToken(token).getUid();


                if (userService.phoneExits(phoneNumber)) {
                    if (!userService.getUserIdByPhoneNumber(phoneNumber).equals(uuid)) {
                        userService.insertPhoneNumber(userService.getUserIdByPhoneNumber(phoneNumber), "");
                    }
                }
                userService.insertPhoneNumber(uuid, phoneNumber);
                return new Result<>(true, "绑定成功");



    }


    @PostMapping("/setpassword")
    @ResponseBody
    public Result<String> setPassword(@RequestParam("token") String token,@RequestParam("password") String password){
        String uuid = tokenUtils.ValidToken(token).getUid();
        userService.insertPassword(uuid,password);
        User user = userService.getUserInfoById(uuid);
        if (!user.getUser_password().equals(password)){
            return new Result<>(false,"密码设置失败，请重新设置");
        }else {
            return new Result<>(true,"设置密码成功");
        }

    }

    @PostMapping("/changepassword")
    @ResponseBody
    public Result<String> changePassword(@RequestParam("token") String token,@RequestParam("oldpassword") String oldpPassword,@RequestParam("password") String password){
        String uuid = tokenUtils.ValidToken(token).getUid();
        User user = userService.getUserInfoById(uuid);
        if (user.getUser_password().equals(oldpPassword)){
            userService.insertPassword(uuid,password);
            return new Result<>(true,"密码修改成功");
        }else {
            return new Result<>(false,"原密码错误");
        }


    }


    @PostMapping("/createmoreuser")
    @ResponseBody
    public Result<String> createMoreUser(@RequestParam("username") String username,@RequestParam("userphone") String userPhone){
        if (!userService.phoneExits(userPhone)){
            String uuid = "5699" + commonUtils.createUUID();
            String token = tokenUtils.getToken(uuid);
            userService.insertFalseUser(uuid,username,token,userPhone);
            return new Result<>(true,"创建成功");
        }else {
            return new Result<>(false,"该号码已注册");
        }

    }


}
