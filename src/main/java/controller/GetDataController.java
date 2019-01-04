package controller;

import dto.HomeData;
import dto.Result;
import entity.TeaClasss;
import entity.TokenValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.GetDataService;
import service.UserService;
import utils.tokenUtils;

import java.util.ArrayList;

@Controller
public class GetDataController {


    @Autowired
    GetDataService getDataService;

    @Autowired
    UserService userService;

    @GetMapping("/gethomedata")
    @ResponseBody
    public Result<HomeData> getHomeData(@RequestParam("token") String token){

        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        String tokenInDb = userService.getTokenByUID(uuid);

        if (tokenInDb.equals(token)){
            return new Result<HomeData>(true,getDataService.getHomeData());
        }else {
            return new Result<>(false,"登录过期或者错误，请重新登录");
        }





    }

    @GetMapping("/getmoredata")
    @ResponseBody
    public Result<ArrayList<TeaClasss>> getMoreData(){





        return new Result<ArrayList<TeaClasss>>(true,getDataService.getMoreClass());


    }
}
