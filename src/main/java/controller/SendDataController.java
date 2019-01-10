package controller;

import dto.QAData;
import dto.Result;
import dto.SendQAData;
import entity.TokenValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.SendDataService;
import service.UserService;
import utils.commonUtils;
import utils.tokenUtils;

import java.util.ArrayList;

@Controller
public class SendDataController {

    @Autowired
    UserService userService;

    @Autowired
    SendDataService sendDataService;

    @PostMapping("/sendqadata")
    @ResponseBody
    public Result<String> sendQAData(@RequestBody SendQAData sendQAData){

        String token = sendQAData.getToken();
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        String tokenInDb = userService.getTokenByUID(uuid);

        if (tokenInDb.equals(token)){

            String time = commonUtils.getTime();
            String qaId = "qa" + commonUtils.createUUID();

            sendDataService.insertQAData(qaId,uuid,sendQAData.getQa_content(),sendQAData.getQa_video(),time,sendQAData.getQa_audio());
            return new Result<>(true,"发送成功");
        }else {
            return new Result<>(false,"登录过期或者错误，请重新登录");
        }
    }
}
