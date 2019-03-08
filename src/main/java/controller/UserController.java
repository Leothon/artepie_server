package controller;

import dto.Result;
import dto.Token;
import dto.VerifyCode;
import entity.TokenValid;
import entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.UserService;
import utils.commonUtils;
import utils.tokenUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    private Map<String, String> phonecode = new HashMap<>();

    @GetMapping("/verifyphone")
    @ResponseBody
    public Result<VerifyCode> verificationPhone(@RequestParam("phonenumber") String phonenumber) {


        //commonUtils.sendVerifyCode(phonenumber,commonUtils.getVerifyCode());


        //TODO 测试阶段直接通过服务器发送验证码

        String code = String.valueOf(commonUtils.getVerifyCode());
        phonecode.put(phonenumber, code);
        //发送验证码后10分钟内有效
        new Timer().schedule(new TimerTask() {
            public void run() {
                phonecode.remove(phonenumber);
            }
        }, 10 * 60 * 1000);

        VerifyCode verifyCode = new VerifyCode();
        verifyCode.setCode(code);
        return new Result<VerifyCode>(true, verifyCode);


    }

    @GetMapping("/usephonelogin")
    @ResponseBody
    public Result<User> usePhoneLogin(@RequestParam("phonenumber") String phonenumber, @RequestParam("verifycode") String verifycode) {

        //查询数据库，手机号是否已注册，如果未注册，则生成token，和手机号一块插入数据库，并返回token
        //如果已经注册，则查找数据库，获取token，并返回

        if (phonecode.containsKey(phonenumber)) {

            if (verifycode.equals(phonecode.get(phonenumber))) {

                Token tokeninfo = null;
                if (!userService.phoneExits(phonenumber)) {
                    //未注册
                    String uuid = "5699" + commonUtils.createUUID();
                    String token = tokenUtils.getToken(uuid);
                    tokeninfo = new Token();
                    tokeninfo.setInfo("注册成功");
                    tokeninfo.setToken(token);
                    String registerTime = commonUtils.getTime();
                    userService.register(uuid, phonenumber, token, "用户" + uuid, registerTime);

                    return new Result<User>(true, userService.getUserInfoById(uuid));
                } else {
                    //已注册

                    String token = userService.returnTokenByPhone(phonenumber);
                    TokenValid tokenValid = tokenUtils.ValidToken(token);
                    String uuid = tokenValid.getUid();
                    return new Result<User>(true, userService.getUserInfoById(uuid));

                }
            } else {
                return new Result<>(false, "验证码错误！");
            }


        } else {
            //验证失败
            return new Result<>(false, "无效验证码，请重新获取！");
        }


    }


    @GetMapping("/usepasswordlogin")
    @ResponseBody
    public Result<User> usePasswordLogin(@RequestParam("phonenumber") String phoneNumber,@RequestParam("password") String password){
        String passwordInDB = userService.getPasswordByPhoneNumber(phoneNumber);
        if (passwordInDB.equals("")){
            return new Result<>(false,"您尚未设置密码，请使用验证码登录");
        }else if (passwordInDB.equals(password)){
            return new Result<>(true, userService.getUserInfoById(tokenUtils.ValidToken(userService.returnTokenByPhone(phoneNumber)).getUid()));
        }else {
            return new Result<>(false,"密码错误！");
        }
    }


    @GetMapping("/getuserinfo")
    @ResponseBody
    public Result<User> getUserInfo(@RequestParam("token") String token) {


        TokenValid tokenValid = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();
        return new Result<User>(true, userService.getUserInfoById(uuid));
    }

    @GetMapping("/getuserinfobyid")
    @ResponseBody
    public Result<User> getUserInfoById(@RequestParam("userid") String userId) {



        return new Result<User>(true, userService.getUserInfoById(userId));
    }


    @PostMapping("/uploadfile")
    @ResponseBody
    public Result<String> updateUserInfo(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {


        String fileName = file.getOriginalFilename();

        File targetFile = new File("/resource", fileName);

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
        userService.qqRegister(uuid, user.getUser_icon(), user.getUser_name(), user.getUser_sex(), registerTime, token, user.getTencent_token());

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
        userService.weChatRegister(uuid, user.getUser_icon(), user.getUser_name(), user.getUser_sex(), user.getUser_address(),registerTime, token, user.getTencent_token());

        return new Result<User>(true, userService.getUserInfoById(uuid));

    }


    @GetMapping("/getuserinfobyqq")
    @ResponseBody
    public Result<User> getUserInfoByQQ(@RequestParam("accesstoken") String accessToken) {
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
    public Result<String> bindPhoneNumber(@RequestParam("token") String token, @RequestParam("phonenumber") String phoneNumber,@RequestParam("code") String code) {
        String uuid = tokenUtils.ValidToken(token).getUid();
        if (phonecode.containsKey(phoneNumber)) {

            if (code.equals(phonecode.get(phoneNumber))) {
                if (userService.phoneExits(phoneNumber)) {
                    if (!userService.getUserIdByPhoneNumber(phoneNumber).equals(uuid)) {
                        userService.insertPhoneNumber(userService.getUserIdByPhoneNumber(phoneNumber), "");
                    }
                }
                userService.insertPhoneNumber(uuid, phoneNumber);
                return new Result<>(true, "绑定成功");

            } else {
                return new Result<>(false, "验证码错误！");
            }


        } else {
            //验证失败
            return new Result<>(false, "无效验证码，请重新获取！");
        }

    }


    @PostMapping("/setpassword")
    @ResponseBody
    public Result<String> setPassword(@RequestParam("token") String token,@RequestParam("password") String password){
        String uuid = tokenUtils.ValidToken(token).getUid();
        userService.insertPassword(uuid,password);
        return new Result<>(true,"设置密码成功");
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
}
