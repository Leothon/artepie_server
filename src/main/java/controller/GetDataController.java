package controller;

import dto.*;
import entity.*;
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
    public Result<ArrayList<SelectClass>> getMoreData(@RequestParam("currentpage") int currentPage, @RequestParam("token") String token){



        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        return new Result<ArrayList<SelectClass>>(true,getDataService.getMoreClass(currentPage,uuid));


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
    public Result<ArrayList<QAData>> getQuestionDataById(@RequestParam("userid") String userId){





        return new Result<ArrayList<QAData>>(true,getDataService.getQADataById(userId));

    }


    @GetMapping("/getmorequestionbyid")
    @ResponseBody
    public Result<ArrayList<QAData>> getMoreQuestionDataById(@RequestParam("currentpage") int currentPage,@RequestParam("userid") String userId){


        return new Result<ArrayList<QAData>>(true,getDataService.getMoreQADataById(currentPage,userId));

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

    @GetMapping("/getclassbyuserid")
    @ResponseBody
    public Result<ArrayList<SelectClass>> getClassByUserId(@RequestParam("userid") String userId){


        return new Result<>(true,getDataService.getSelectClassByUserId(userId));
    }

    @GetMapping("/getclassbyclassid")
    @ResponseBody
    public Result<SelectClass> getTeaClass(@RequestParam("classid") String classId){


        return new Result<>(true,getDataService.getClassInfo(classId));
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
    public Result<SelectClass> getBuyClassInfo(@RequestParam("classid") String classId){


        return new Result<>(true,getDataService.getClassInfo(classId));
    }


    @GetMapping("/isqqregister")
    @ResponseBody
    public Result<String> isQQRegister(@RequestParam("accesstoken") String accessToken){


        return new Result<>(true,getDataService.isQQRegister(accessToken));
    }


    @GetMapping("/getfavclassbyuid")
    @ResponseBody
    public Result<ArrayList<SelectClass>> getFavClassByUid(@RequestParam("token") String token){
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
    @GetMapping("/getmorearticledata")
    @ResponseBody
    public Result<ArrayList<Article>> getMoreArticleData(@RequestParam("token") String token,@RequestParam("currentpage") int currentPage){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        return new Result<>(true,getDataService.getMoreArticleData(uuid,currentPage));
    }
    @GetMapping("/getarticledatabyid")
    @ResponseBody
    public Result<ArrayList<Article>> getArticleDataById(@RequestParam("userid") String userId){

        return new Result<>(true,getDataService.getArticleDataById(userId));
    }
    @GetMapping("/getmorearticledatabyid")
    @ResponseBody
    public Result<ArrayList<Article>> getMoreArticleDataById(@RequestParam("userid") String userId,@RequestParam("currentpage") int currentPage){


        return new Result<>(true,getDataService.getMoreArticleDataById(userId,currentPage));
    }

    @GetMapping("/getarticledetail")
    @ResponseBody
    public Result<Article> getArticleDetail(@RequestParam("token") String token,@RequestParam("articleid") String articleId){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();
        return new Result<>(true,getDataService.getArticleDetail(uuid,articleId));

    }


    @GetMapping("/ishasnotice")
    @ResponseBody
    public Result<String> isHasNotice(@RequestParam("token") String token){
        String uuid = tokenUtils.ValidToken(token).getUid();

        int result = getDataService.isHasNotice(uuid);
        if (result == 0){
            return new Result<>(true,"empty");
        }else {
            return new Result<>(true,"notice");
        }
    }

    @GetMapping("/getnoticeinfo")
    @ResponseBody
    public Result<ArrayList<NoticeInfo>> getNoticeInfo(@RequestParam("token") String token){
        String uuid = tokenUtils.ValidToken(token).getUid();

        return new Result<>(true,getDataService.getNoticeInfo(uuid));
    }

    @GetMapping("/getauthinfo")
    @ResponseBody
    public Result<ArrayList<AuthInfo>> getAuthInfo(@RequestParam("token") String token){
        String uuid = tokenUtils.ValidToken(token).getUid();
        return new Result<>(true,getDataService.getAuthInfo(uuid));
    }

    @GetMapping("/searchresult")
    @ResponseBody
    public Result<SearchResult> searchClass(@RequestParam("keyword") String keyword,@RequestParam("token") String token){

        String uuid = tokenUtils.ValidToken(token).getUid();

        SearchResult searchResult = new SearchResult();
        searchResult.setSelectClasses(getDataService.searchClassByKeyword(keyword,uuid));
        searchResult.setQaData(getDataService.searchQAByKeyword(keyword,uuid));
        searchResult.setUsers(getDataService.searchUserByKeyword(keyword,uuid));
        searchResult.setArticles(getDataService.searchArticleByKeyword(keyword,uuid));

        return new Result<>(true,searchResult);

    }


}
