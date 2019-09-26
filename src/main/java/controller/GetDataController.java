package controller;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import com.vdurmont.emoji.EmojiParser;
import dao.GetDataDao;
import dao.SendDataDao;
import dao.UserDao;
import dto.*;
import entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.GetDataService;
import service.UserService;
import utils.*;

import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class GetDataController {


    @Autowired
    GetDataService getDataService;

    @Autowired
    UserService userService;

    @Autowired
    SendDataDao sendDataDao;
    @Autowired
    GetDataDao getDataDao;

    @Autowired
    UserDao userDao;

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


    @GetMapping("/getmoreqadetail")
    @ResponseBody
    public Result<ArrayList<Comment>> getMoreQADetail(@RequestParam("token") String token,@RequestParam("qaid") String qaId,@RequestParam("currentpage") int currentPage){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        String tokenInDb = userService.getTokenByUID(uuid);

        if (tokenInDb.equals(token)){

            ArrayList<Comment> comments = getDataDao.getMoreComment(qaId,currentPage);
            for (int i = 0;i < comments.size();i ++){

                comments.get(i).setComment_q_content(EmojiParser.parseToUnicode(comments.get(i).getComment_q_content()));
                comments.get(i).setComment_q_like(Integer.toString(getDataDao.getCommentLike(comments.get(i).getComment_q_id())));
                if (getDataDao.isCommentLike(uuid,comments.get(i).getComment_q_id()) == 0){
                    comments.get(i).setComment_liked(true);
                }else {
                    comments.get(i).setComment_liked(false);
                }

                if (Integer.parseInt(comments.get(i).getComment_q_like()) >= 5){
                    if (Integer.parseInt(comments.get(i).getComment_q_like()) > Integer.parseInt(comments.get(0).getComment_q_like())){
                        Comment temp  = comments.get(i);
                        comments.remove(i);
                        comments.add(0,temp);
                    }
                }
                ArrayList<Reply> replies = getDataDao.getReply(comments.get(i).getComment_q_id());
                for (int j = 0;j < replies.size();j ++){
                    replies.get(j).setReply_comment(EmojiParser.parseToUnicode(replies.get(j).getReply_comment()));
                    replies.get(j).setReply_like(Integer.toString(getDataDao.getReplyLike(replies.get(j).getReply_id())));
                    if (getDataDao.isReplyLike(uuid,replies.get(j).getReply_id()) == 0){
                        replies.get(j).setReply_liked(true);
                    }else {
                        replies.get(j).setReply_liked(false);
                    }
                }
                comments.get(i).setReplies(replies);
            }
            return new Result<ArrayList<Comment>>(true,comments);
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

    @GetMapping("/getmorecommentdetail")
    @ResponseBody
    public Result<ArrayList<Reply>> getMoreCommentDetail(@RequestParam("commentid") String commentId,@RequestParam("token") String token,@RequestParam("currentpage") int currentPage){

        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();
        ArrayList<Reply> replie = getDataDao.getMoreReply(commentId,currentPage);
        for (int i = 0;i < replie.size();i ++){
            replie.get(i).setReply_comment(EmojiParser.parseToUnicode(replie.get(i).getReply_comment()));
            replie.get(i).setReply_like(Integer.toString(getDataDao.getReplyLike(replie.get(i).getReply_id())));
            if (getDataDao.isReplyLike(uuid,replie.get(i).getReply_id()) == 0){
                replie.get(i).setReply_liked(true);
            }else {
                replie.get(i).setReply_liked(false);
            }
        }
        return new Result<ArrayList<Reply>>(true,replie);
    }


    @GetMapping("/getclassdetail")
    @ResponseBody
    public Result<ClassDetail> getClassDetail(@RequestParam("token") String token,@RequestParam("classid") String classId){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        return new Result<ClassDetail>(true,getDataService.getClassDetail(uuid,classId));

    }


    @GetMapping("/getmoreclassdetail")
    @ResponseBody
    public Result<ArrayList<ClassDetailList>> getMoreClassDetail(@RequestParam("token") String token,@RequestParam("classid") String classId,@RequestParam("currentpage") int currentPage){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();
        ArrayList<ClassDetailList> classDetailList = getDataDao.getMoreClassList(classId,currentPage);
        return new Result<ArrayList<ClassDetailList>>(true,classDetailList);

    }



    @GetMapping("/getclassvideo")
    @ResponseBody
    public  Result<VideoDetail> getClassVideo(@RequestParam("token") String token,@RequestParam("classdid") String classdId,@RequestParam("classid") String classId){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();
        return new Result<>(true,getDataService.getVideoDetail(uuid,classId,classdId));
    }

    @GetMapping("/getmoreclassvideo")
    @ResponseBody
    public  Result<ArrayList<Comment>> getMoreClassVideo(@RequestParam("token") String token,@RequestParam("classdid") String classdId,@RequestParam("currentpage") int currentPage){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();
        ArrayList<Comment> comments = getDataDao.getMoreComment(classdId,currentPage);

        for (int i = 0;i < comments.size();i ++){
            comments.get(i).setComment_q_content(comments.get(i).getComment_q_content());
            comments.get(i).setComment_q_like(Integer.toString(getDataDao.getCommentLike(comments.get(i).getComment_q_id())));
            if (getDataDao.isCommentLike(uuid,comments.get(i).getComment_q_id()) == 0){
                comments.get(i).setComment_liked(true);
            }else {
                comments.get(i).setComment_liked(false);
            }
            ArrayList<Reply> replies = getDataDao.getReply(comments.get(i).getComment_q_id());
            for (int j = 0;j < replies.size();j ++){
                replies.get(j).setReply_comment(replies.get(j).getReply_comment());
                replies.get(j).setReply_like(Integer.toString(getDataDao.getReplyLike(replies.get(j).getReply_id())));
                if (getDataDao.isReplyLike(uuid,replies.get(j).getReply_id()) == 0){
                    replies.get(j).setReply_liked(true);
                }else {
                    replies.get(j).setReply_liked(false);
                }
            }
            comments.get(i).setReplies(replies);
        }
        return new Result<ArrayList<Comment>>(true,comments);
    }




    @GetMapping("/getteaclass")
    @ResponseBody
    public Result<TeacherClass> getTeaClass(@RequestParam("token") String token,@RequestParam("teaid") String teaId){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        return new Result<>(true,getDataService.getTeaClass(uuid,teaId));
    }

    @GetMapping("/getmoreteaclass")
    @ResponseBody
    public Result<ArrayList<SelectClass>> getMoreTeaClass(@RequestParam("token") String token,@RequestParam("teaid") String teaId,@RequestParam("currentpage") int currentPage){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();
        ArrayList<SelectClass> teaClass = getDataDao.getMoreClassByTea(teaId,currentPage);
        for (int i = 0;i < teaClass.size();i ++){
            //teaClass.get(i).setSelectstucount(Integer.toString(getDataDao.getClassView(teaClass.get(i).getSelectId())));
            if (getDataDao.isUserBuy(teaClass.get(i).getSelectId(),uuid) == 0){
                teaClass.get(i).setIsbuy(true);
            }else {
                teaClass.get(i).setIsbuy(false);
            }
        }
        return new Result<ArrayList<SelectClass>>(true,teaClass);
    }



    @GetMapping("/getclassbyuserid")
    @ResponseBody
    public Result<ArrayList<SelectClass>> getClassByUserId(@RequestParam("userid") String userId){

        return new Result<>(true,getDataService.getSelectClassByUserId(userId));
    }

    @GetMapping("/getmoreclassbyuserid")
    @ResponseBody
    public Result<ArrayList<SelectClass>> getClassByUserId(@RequestParam("userid") String userId,@RequestParam("currentpage") int currentPage){

        ArrayList<SelectClass> selectClasses = getDataDao.getMoreClassByTea(userId,currentPage);
        for (int i = 0;i < selectClasses.size();i ++){
            //selectClasses.get(i).setSelectstucount(Integer.toString(getDataDao.getClassView(selectClasses.get(i).getSelectId())));
            if (getDataDao.isUserBuy(selectClasses.get(i).getSelectId(),userId) == 0){
                selectClasses.get(i).setIsbuy(true);
            }else {
                selectClasses.get(i).setIsbuy(false);
            }
        }
        return new Result<>(true,selectClasses);
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

    @GetMapping("/getmoreclassbytype")
    @ResponseBody
    public Result<ArrayList<SelectClass>> getMoreClassByType(@RequestParam("token") String token,@RequestParam("type") String type,@RequestParam("currentpage") int currentPage){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();
        String decodeType = "";
        try{
            decodeType = URLDecoder.decode(type,"utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }

        ArrayList<SelectClass> teaClass = getDataDao.getMoreClassByType(decodeType,currentPage);

        for (int i = 0;i < teaClass.size();i ++){
            //teaClass.get(i).setSelectstucount(Integer.toString(getDataDao.getClassView(teaClass.get(i).getSelectId())));
            if (getDataDao.isUserBuy(teaClass.get(i).getSelectId(),uuid) == 0){
                teaClass.get(i).setIsbuy(true);
            }else {
                teaClass.get(i).setIsbuy(false);
            }
        }
        return new Result<>(true,teaClass);
    }

    @GetMapping("/getbuyclassinfo")
    @ResponseBody
    public Result<SelectClass> getBuyClassInfo(@RequestParam("classid") String classId){


        return new Result<>(true,getDataService.getClassInfo(classId));
    }


    @GetMapping("/isqqwechatregister")
    @ResponseBody
    public Result<String> isQQOrWeChatRegister(@RequestParam("accesstoken") String accessToken){


        return new Result<>(true,getDataService.isQQOrWeChatRegister(accessToken));
    }


    @GetMapping("/getfavclassbyuid")
    @ResponseBody
    public Result<ArrayList<SelectClass>> getFavClassByUid(@RequestParam("token") String token){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        return new Result<>(true,getDataDao.getFavClassByUid(uuid));
    }

    @GetMapping("/getmorefavclassbyuid")
    @ResponseBody
    public Result<ArrayList<SelectClass>> getMoreFavClassByUid(@RequestParam("token") String token,@RequestParam("currentpage") int currentPage){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();
        return new Result<>(true,getDataDao.getMoreFavClassByUid(uuid,currentPage));
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


    @GetMapping("/getmorenoticeinfo")
    @ResponseBody
    public Result<ArrayList<NoticeInfo>> getMoreNoticeInfo(@RequestParam("current") int page,@RequestParam("token") String token){
        String uuid = tokenUtils.ValidToken(token).getUid();

        ArrayList<NoticeInfo> noticeInfos = getDataDao.getMoreNoticeInfo(page,uuid);
        for (int i = 0;i < noticeInfos.size();i ++){
            noticeInfos.get(i).setNoticeContent(EmojiParser.parseToUnicode(noticeInfos.get(i).getNoticeContent()));
            User user = getDataDao.getUserInfoInGet(noticeInfos.get(i).getNoticeFromUserId());
            if (user != null){
                noticeInfos.get(i).setUserName(user.getUser_name());
                noticeInfos.get(i).setUserIcon(user.getUser_icon());
            }

        }
        return new Result<>(true,noticeInfos);
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


    @GetMapping("/")
    public String visitHomePage(){
        return "index";
    }



    @GetMapping("/getlittlehome")
    @ResponseBody
    public Result<ArrayList<SelectClass>> getLittleHome(@RequestParam("token") String token){



        String uuid = tokenUtils.ValidToken(token).getUid();

        return new Result<ArrayList<SelectClass>>(true,getDataService.getLittleHome(uuid));



    }


    @GetMapping("/getinform")
    @ResponseBody
    public Result<Inform> getInform(@RequestParam("token") String token){



        String uuid = tokenUtils.ValidToken(token).getUid();

        return new Result<Inform>(true,getDataService.getInform(uuid));



    }

    @GetMapping("/getupdate")
    @ResponseBody
    public Result<Update> getUpdate(@RequestParam("token") String token){



        String uuid = tokenUtils.ValidToken(token).getUid();


        String startId = "start" + commonUtils.createUUID();
        sendDataDao.insertStartTime(startId,commonUtils.getTime(),uuid);
        return new Result<Update>(true,getDataService.getUpdate(uuid));



    }


    @GetMapping("/getauthimg")
    @ResponseBody
    public Result<String> getAuthImg(@RequestParam("userid") String userId){

        return new Result(true,getDataService.getAuthImg(userId));
    }

    @GetMapping("/authuser")
    @ResponseBody
    public Result<String> authUser(@RequestParam("userid") String userId,@RequestParam("userrole") String userRole,@RequestParam("usertype") int type){


        getDataService.authUser(userRole,userId,type);
        return new Result(true,"成功");
    }

    @GetMapping("/getarticlecomment")
    @ResponseBody
    public Result<ArrayList<ArticleComment>> getArticleComment(@RequestParam("articleid") String articleId){



        return new Result(true,getDataService.getArticleComment(articleId));
    }
    @GetMapping("/getarticlecommentmore")
    @ResponseBody
    public Result<ArrayList<ArticleComment>> getArticleComment(@RequestParam("articleid") String articleId,@RequestParam("currentpage") int currentPage){



        return new Result(true,getDataService.getArticleCommentMore(articleId,currentPage));
    }

    @GetMapping("/getbuyclass")
    @ResponseBody
    public Result<ArrayList<SelectClass>> getBuyClass(@RequestParam("token") String token){

        String uuid = tokenUtils.ValidToken(token).getUid();

        return new Result(true,getDataDao.getBuyClassByUid(uuid));
    }


    @GetMapping("/getmorebuyclass")
    @ResponseBody
    public Result<ArrayList<SelectClass>> getMoreBuyClass(@RequestParam("token") String token,@RequestParam("currentpage") int currentPage){

        String uuid = tokenUtils.ValidToken(token).getUid();

        return new Result(true,getDataDao.getMoreBuyClassByUid(uuid,currentPage));
    }

    @GetMapping("/getbills")
    @ResponseBody
    public Result<ArrayList<Bill>> getBills(@RequestParam("token") String token){

        String uuid = tokenUtils.ValidToken(token).getUid();
        ArrayList<Bill> outBill = getDataDao.getOutBill(uuid);
        ArrayList<Bill> inBill = getDataDao.getInBill(uuid);

        for (int i = 0;i < outBill.size();i ++){
            outBill.get(i).setOutIn(0);
        }
        for (int j = 0;j < inBill.size();j ++){
            inBill.get(j).setOutIn(1);
            inBill.get(j).setCount(commonUtils.computeAuthorPrice(inBill.get(j).getCount()));
            outBill.add(inBill.get(j));
        }
        Collections.sort(outBill, new SortClass());
        return new Result(true,outBill);
    }

    @GetMapping("/getmorebills")
    @ResponseBody
    public Result<ArrayList<Bill>> getMoreBills(@RequestParam("token") String token,@RequestParam("currentpage") int currentPage){

        String uuid = tokenUtils.ValidToken(token).getUid();
        ArrayList<Bill> outBill = getDataDao.getMoreOutBill(uuid,currentPage);
        ArrayList<Bill> inBill = getDataDao.getMoreInBill(uuid,currentPage);

        for (int i = 0;i < outBill.size();i ++){
            outBill.get(i).setOutIn(0);
        }
        for (int j = 0;j < inBill.size();j ++){
            inBill.get(j).setOutIn(1);
            inBill.get(j).setCount(commonUtils.computeAuthorPrice(inBill.get(j).getCount()));
            outBill.add(inBill.get(j));
        }
        Collections.sort(outBill, new SortClass());
        return new Result(true,outBill);
    }

    @GetMapping("/getorderhis")
    @ResponseBody
    public Result<ArrayList<OrderHis>> getOrderHis(@RequestParam("token") String token){


        String uuid = tokenUtils.ValidToken(token).getUid();


        return new Result(true,getDataDao.getOrderHis(uuid));
    }

    @GetMapping("/getmoreorderhis")
    @ResponseBody
    public Result<ArrayList<OrderHis>> getMoreOrderHis(@RequestParam("token") String token,@RequestParam("currentpage") int currentPage){


        String uuid = tokenUtils.ValidToken(token).getUid();


        return new Result(true,getDataDao.getMoreOrderHis(uuid,currentPage));
    }


    @GetMapping("/getcashtotal")
    @ResponseBody
    public Result<String> getcashtotal(@RequestParam("token") String token){


        String uuid = tokenUtils.ValidToken(token).getUid();

        if (userDao.getUserInfo(uuid).getUser_balance() == null){
            return new Result(true,"0");
        }
        Float fifteenTotal = 0f;
        ArrayList<Bill> bills = getDataDao.getInfifteen(uuid);
        for (int i = 0;i < bills.size();i ++){
            bills.get(i).setCount(commonUtils.computeAuthorPrice(bills.get(i).getCount()));
            fifteenTotal += Float.valueOf(bills.get(i).getCount());
            System.out.println("每笔账单" + fifteenTotal);
        }
        System.out.println("近期现金" + fifteenTotal);

        Float couldTotal = Float.valueOf(userDao.getUserInfo(uuid).getUser_balance()) - fifteenTotal;
        System.out.println("可提现金" + couldTotal);

        DecimalFormat df = new DecimalFormat("#.00");
        return new Result(true,df.format(couldTotal));
    }


    @GetMapping("/ishaspsd")
    @ResponseBody
    public Result<String> isHasPsd(@RequestParam("token") String token){

        String uuid = tokenUtils.ValidToken(token).getUid();
        if (getDataDao.isHasPsd(uuid) == 0){
            return new Result(true,"true");
        }else {
            return new Result(true,"false");
        }

    }


    @GetMapping("/batchregister")
    @ResponseBody
    public Result<String> batchRegister(@RequestParam("path") String path){


        List<RegisterEntity> registerEntities = MoreUtils.getExcel(path);

        for (int i = 0;i < registerEntities.size();i ++){

            String username = registerEntities.get(i).getUsername();
            String userphone = registerEntities.get(i).getUserPhone();
            if (!userService.phoneExits(userphone)){
                String uuid = "5699" + commonUtils.createUUID();
                String token = tokenUtils.getToken(uuid);
                userService.insertFalseUser(uuid,username,token,userphone);
            }
        }
        return new Result<>(true,"完事儿");
    }


    @GetMapping("/getsplash")
    @ResponseBody
    public Result<SplashInfo> getSplash(@RequestParam("type") String type){


        return new Result<>(true,getDataDao.getSplashUrl());
    }

    @GetMapping("/getcustomshow")
    @ResponseBody
    public Result<ArrayList<CustomShow>> getCustomShow(@RequestParam("currentpage") int currentPage){


        return new Result<>(true,getDataDao.getCustomShow(currentPage));
    }

}
