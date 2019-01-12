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

    private Map<String,String> phonecode = new HashMap<>();
    @GetMapping("/verifyphone")
    @ResponseBody
    public Result<VerifyCode> verificationPhone(@RequestParam("phonenumber") String phonenumber){


        //commonUtils.sendVerifyCode(phonenumber,commonUtils.getVerifyCode());


        //TODO 测试阶段直接通过服务器发送验证码

        String code = String.valueOf(commonUtils.getVerifyCode());
        phonecode.put(phonenumber,code);
        //发送验证码后10分钟内有效
        new Timer().schedule(new TimerTask() {
            public void run() {
                phonecode.remove(phonenumber);
            }
        }, 10*60*1000);

        VerifyCode verifyCode = new VerifyCode();
        verifyCode.setCode(code);
        return new Result<VerifyCode>(true,verifyCode);


    }


    @GetMapping("/usephonelogin")
    @ResponseBody
    public Result<User> usePhoneLogin(@RequestParam("phonenumber") String phonenumber, @RequestParam("verifycode") String verifycode){

        //查询数据库，手机号是否已注册，如果未注册，则生成token，和手机号一块插入数据库，并返回token
        //如果已经注册，则查找数据库，获取token，并返回

        if (phonecode.containsKey(phonenumber)){

            if (verifycode.equals(phonecode.get(phonenumber))){

                Token tokeninfo = null;
                if (!userService.phoneExits(phonenumber)){
                    //未注册
                    String uuid = "5699" + commonUtils.createUUID();
                    String token = tokenUtils.getToken(uuid);
                    tokeninfo = new Token();
                    tokeninfo.setInfo("注册成功");
                    tokeninfo.setToken(token);
                    String registerTime = commonUtils.getTime();
                    userService.register(uuid,phonenumber,token,"用户" + uuid,registerTime);

                    return new Result<User>(true,userService.getUserInfoById(uuid));
                }else {
                    //已注册

                    String token = userService.returnTokenByPhone(phonenumber);
                    TokenValid tokenValid  = tokenUtils.ValidToken(token);
                    String uuid = tokenValid.getUid();
                    return new Result<User>(true,userService.getUserInfoById(uuid));

                }
            }else {
                return new Result<>(false,"验证码错误！");
            }


        }else {
            //验证失败
            return new Result<>(false,"无效验证码，请重新获取！");
        }




    }


    @GetMapping("/getuserinfo")
    @ResponseBody
    public Result<User> getUserInfo(@RequestParam("token") String token){


        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();
        return new Result<User>(true,userService.getUserInfoById(uuid));
    }


    @PostMapping("/uploadfile")
    @ResponseBody
    public Result<String> updateUserInfo(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {




        String fileName = file.getOriginalFilename();

        File targetFile=new File("/resource",fileName);

        if(!targetFile.exists()){

            targetFile.mkdirs();

        }

        file.transferTo(targetFile);

        return new Result<>(true,fileName);

    }

    @PostMapping("/updateuserinfo")
    @ResponseBody
    public Result<String> updateUserInfo(@RequestBody User user){


        userService.updateInfo(user);

        return new Result<>(true,"success");
    }

}
