package controller;

import dto.*;
import entity.Article;
import entity.ClassDetailList;
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

import java.net.URLDecoder;
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
            return new Result<HomeData>(true,getDataService.getHomeData(uuid));
        }else {
            return new Result<>(false,"登录过期或者错误，请重新登录");
        }





    }

    @GetMapping("/getmoredata")
    @ResponseBody
    public Result<ArrayList<TeaClasss>> getMoreData(@RequestParam("currentpage") int currentPage,@RequestParam("token") String token){



        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        return new Result<ArrayList<TeaClasss>>(true,getDataService.getMoreClass(currentPage,uuid));


    }


    @GetMapping("/getquestion")
    @ResponseBody
    public Result<ArrayList<QAData>> getQuestionData(@RequestParam("token") String token){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        String tokenInDb = userService.getTokenByUID(uuid);

        if (tokenInDb.equals(token)){
            return new Result<ArrayList<QAData>>(true,getDataService.getQAData(uuid));
        }else {
            return new Result<>(false,"登录过期或者错误，请重新登录");
        }
    }


    @GetMapping("/getmorequestion")
    @ResponseBody
    public Result<ArrayList<QAData>> getMoreQuestionData(@RequestParam("currentpage") int currentPage,@RequestParam("token") String token){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        return new Result<ArrayList<QAData>>(true,getDataService.getMoreQAData(currentPage,uuid));

    }

    @GetMapping("/getquestionbyid")
    @ResponseBody
    public Result<ArrayList<QAData>> getQuestionDataById(@RequestParam("token") String token){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        String tokenInDb = userService.getTokenByUID(uuid);

        if (tokenInDb.equals(token)){
            return new Result<ArrayList<QAData>>(true,getDataService.getQADataById(uuid));
        }else {
            return new Result<>(false,"登录过期或者错误，请重新登录");
        }
    }


    @GetMapping("/getmorequestionbyid")
    @ResponseBody
    public Result<ArrayList<QAData>> getMoreQuestionDataById(@RequestParam("currentpage") int currentPage,@RequestParam("token") String token){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        return new Result<ArrayList<QAData>>(true,getDataService.getMoreQADataById(currentPage,uuid));

    }


    @GetMapping("/getqadetail")
    @ResponseBody
    public Result<QADataDetail> getQADetail(@RequestParam("token") String token,@RequestParam("qaid") String qaId){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        String tokenInDb = userService.getTokenByUID(uuid);

        if (tokenInDb.equals(token)){
            return new Result<QADataDetail>(true,getDataService.getQADetail(qaId,uuid));
        }else {
            return new Result<>(false,"登录过期或者错误，请重新登录");
        }
    }

    @GetMapping("/getqainfo")
    @ResponseBody
    public Result<QAData> getQA(@RequestParam("token") String token,@RequestParam("qaid") String qaId){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        String tokenInDb = userService.getTokenByUID(uuid);

        if (tokenInDb.equals(token)){
            return new Result<QAData>(true,getDataService.getQA(qaId,uuid));
        }else {
            return new Result<>(false,"登录过期或者错误，请重新登录");
        }
    }


    @GetMapping("/getcommentdetail")
    @ResponseBody
    public Result<CommentDetail> getCommentDetail(@RequestParam("commentid") String commentId,@RequestParam("token") String token){

        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();
        return new Result<CommentDetail>(true,getDataService.getCommentDetail(commentId,uuid));
    }


    @GetMapping("/getclassdetail")
    @ResponseBody
    public Result<ClassDetail> getClassDetail(@RequestParam("token") String token,@RequestParam("classid") String classId){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();



        return new Result<ClassDetail>(true,getDataService.getClassDetail(uuid,classId));

    }

    @GetMapping("/getclassvideo")
    @ResponseBody
    public  Result<VideoDetail> getClassVideo(@RequestParam("token") String token,@RequestParam("classdid") String classdId,@RequestParam("classid") String classId){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();
        return new Result<>(true,getDataService.getVideoDetail(uuid,classId,classdId));
    }


    @GetMapping("/getteaclass")
    @ResponseBody
    public Result<TeacherClass> getTeaClass(@RequestParam("token") String token,@RequestParam("teaid") String teaId){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        return new Result<>(true,getDataService.getTeaClass(uuid,teaId));
    }

    @GetMapping("/getclassbytype")
    @ResponseBody
    public Result<TypeClass> getClassByType(@RequestParam("token") String token,@RequestParam("type") String type){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();
        String decodeType = "";
        try{
            decodeType = URLDecoder.decode(type,"utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Result<>(true,getDataService.getClassByType(uuid,decodeType));
    }

    @GetMapping("/getbuyclassinfo")
    @ResponseBody
    public Result<TeaClasss> getBuyClassInfo(@RequestParam("classid") String classId){


        return new Result<>(true,getDataService.getClassInfo(classId));
    }


    @GetMapping("/isqqregister")
    @ResponseBody
    public Result<String> isQQRegister(@RequestParam("accesstoken") String accessToken){


        return new Result<>(true,getDataService.isQQRegister(accessToken));
    }


    @GetMapping("/getfavclassbyuid")
    @ResponseBody
    public Result<ArrayList<TeaClasss>> getFavClassByUid(@RequestParam("token") String token){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        return new Result<>(true,getDataService.getFavClassByUid(uuid));
    }

    @GetMapping("/getclassviewhis")
    @ResponseBody
    public Result<ArrayList<ClassDetailList>> getClassViewHis(@RequestParam("token") String token){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        return new Result<>(true,getDataService.getClassViewById(uuid));
    }


    @GetMapping("/getbagpagedata")
    @ResponseBody
    public Result<BagPageData> getBagPageData(@RequestParam("token") String token){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();


        return new Result<>(true,getDataService.getBagPageData(uuid));
    }

    @GetMapping("/getarticledata")
    @ResponseBody
    public Result<ArticleData> getArticleData(@RequestParam("token") String token){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        return new Result<>(true,getDataService.getArticleData(uuid));
    }

    @GetMapping("/getarticledetail")
    @ResponseBody
    public Result<Article> getArticleDetail(@RequestParam("token") String token,@RequestParam("articleid") String articleId){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();
        return new Result<>(true,getDataService.getArticleDetail(uuid,articleId));

    }
}
