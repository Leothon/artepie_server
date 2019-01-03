package controller;

import dto.HomeData;
import dto.Result;
import entity.TeaClasss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.GetDataService;

import java.util.ArrayList;

@Controller
public class GetDataController {


    @Autowired
    GetDataService getDataService;


    @GetMapping("/gethomedata")
    @ResponseBody
    public Result<HomeData> getHomeData(){




        return new Result<HomeData>(true,getDataService.getHomeData());


    }

    @GetMapping("/getmoredata")
    @ResponseBody
    public Result<ArrayList<TeaClasss>> getMoreData(){





        return new Result<ArrayList<TeaClasss>>(true,getDataService.getMoreClass());


    }
}
