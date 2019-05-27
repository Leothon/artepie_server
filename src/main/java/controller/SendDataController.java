package controller;

import dto.Result;
import dto.SendQAData;
import entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.GetDataService;
import service.SendDataService;
import service.UserService;
import utils.commonUtils;
import utils.tokenUtils;
@Controller
public class SendDataController {

    @Autowired
    UserService userService;

    @Autowired
    SendDataService sendDataService;

    @Autowired
    GetDataService getDataService;

    @PostMapping("/sendqadata")
    @ResponseBody
    public Result<String> sendQAData(@RequestBody SendQAData sendQAData){

        String token = sendQAData.getToken();
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        String tokenInDb = userService.getTokenByUID(uuid);
        System.out.println(tokenInDb);
        if (tokenInDb.equals(token)){

            String time = commonUtils.getTime();
            String qaId = "qa" + commonUtils.createUUID();

            sendDataService.insertQAData(qaId,uuid,sendQAData.getQa_content(),sendQAData.getQa_video(),time,sendQAData.getQa_audio(),sendQAData.getQa_video_cover());

            return new Result<>(true,"发送成功");
        }else {
            return new Result<>(false,"登录过期或者错误，请重新登录");
        }
    }

    @PostMapping("/addlikeqa")
    @ResponseBody
    public Result<String> likeQa(@RequestParam("token") String token,@RequestParam("qaid") String qaId){

        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        sendDataService.addLikeQa(uuid,qaId);
        return new Result<>(true,"success");
    }

    @PostMapping("/removelikeqa")
    @ResponseBody
    public Result<String> removelikeQa(@RequestParam("token") String token,@RequestParam("qaid") String qaId){

        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        sendDataService.removeLikeQa(uuid,qaId);

        return new Result<>(true,"success");
    }


    @PostMapping("/addlikecomment")
    @ResponseBody
    public Result<String> likeComment(@RequestParam("token") String token,@RequestParam("commentid") String commentId){

        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        sendDataService.addLikeComment(uuid,commentId);
        return new Result<>(true,"success");
    }

    @PostMapping("/removelikecomment")
    @ResponseBody
    public Result<String> removelikeComment(@RequestParam("token") String token,@RequestParam("commentid") String commentid){

        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        sendDataService.removeLikeComment(uuid,commentid);

        return new Result<>(true,"success");
    }

    @PostMapping("/addlikereply")
    @ResponseBody
    public Result<String> likeReply(@RequestParam("token") String token,@RequestParam("replyid") String replyId){

        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        sendDataService.addLikeReply(uuid,replyId);
        return new Result<>(true,"success");
    }

    @PostMapping("/removelikereply")
    @ResponseBody
    public Result<String> removelikeReply(@RequestParam("token") String token,@RequestParam("replyid") String replyId){

        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        sendDataService.removeLikeReply(uuid,replyId);

        return new Result<>(true,"success");
    }


    @PostMapping("/addlikearticle")
    @ResponseBody
    public Result<String> likeArticle(@RequestParam("token") String token,@RequestParam("articleid") String articleId){

        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        sendDataService.addLikeArticle(uuid,articleId);
        return new Result<>(true,"success");
    }

    @PostMapping("/removelikearticle")
    @ResponseBody
    public Result<String> removelikeArticle(@RequestParam("token") String token,@RequestParam("articleid") String articleId){

        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        sendDataService.removeLikeArticle(uuid,articleId);

        return new Result<>(true,"success");
    }



    @PostMapping("/sendqacomment")
    @ResponseBody
    public Result<String> sendQaComment(@RequestParam("qaid") String qaId,@RequestParam("token") String token,@RequestParam("content") String content){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();
        String time = commonUtils.getTime();
        sendDataService.sendQaComment(qaId,uuid,content,time);
        return new Result<>(true,"成功");
    }

    @PostMapping("/sendreply")
    @ResponseBody
    public Result<String> sendReply(@RequestParam("commentid") String commentid,@RequestParam("token") String token,@RequestParam("touserid") String toUserId,@RequestParam("content") String content){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();
        String time = commonUtils.getTime();
        sendDataService.sendReply(commentid,content,uuid,toUserId,time);
        return new Result<>(true,"成功");
    }

    @PostMapping("/deleteqacomment")
    @ResponseBody
    public Result<String> deleteQaComment(@RequestParam("commentid") String commentId,@RequestParam("token") String token){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        sendDataService.deleteComment(commentId,uuid);
        return new Result<>(true,"删除成功");
    }
    @PostMapping("/deletereply")
    @ResponseBody
    public Result<String> deleteReply(@RequestParam("replyId") String replyId,@RequestParam("token") String token){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        sendDataService.deleteReply(replyId,uuid);
        return new Result<>(true,"删除成功");
    }

    @PostMapping("/favclass")
    @ResponseBody
    public Result<String> favClass(@RequestParam("token") String token,@RequestParam("classid") String classId){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();
        String favId = "fav" + commonUtils.createUUID();
        sendDataService.addFav(uuid,classId,favId);
        return new Result<>(true,"收藏成功");

    }

    @PostMapping("/unfavclass")
    @ResponseBody
    public Result<String> unFavClass(@RequestParam("token") String token,@RequestParam("classid") String classId){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        sendDataService.removeFav(uuid,classId);
        return new Result<>(true,"取消收藏");

    }

    @PostMapping("/addvideoview")
    @ResponseBody
    public Result<String> addVideoView(@RequestParam("token") String token,@RequestParam("classid") String classId,@RequestParam("classdid") String classdId){

        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();

        sendDataService.addVideoView(uuid,classdId,classId);
        return new Result<>(true,"成功");
    }

    @PostMapping("/removeclassviewhis")
    @ResponseBody
    public Result<String> removeClassViewHis(@RequestParam("token") String token,@RequestParam("classdid") String classdId){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();
        sendDataService.removeViewHis(uuid,classdId);
        return new Result<>(true,"成功");
    }

    @PostMapping("/uploadarticle")
    @ResponseBody
    public Result<String> uploadArticle(@RequestBody Article article){

        sendDataService.uploadArticle(article.getArticleTitle(),article.getArticleImg(),article.getArticleContent(),article.getArticleAuthorId());
        return new Result<>(true,"成功");
    }

    @PostMapping("/sendre")
    @ResponseBody
    public Result<String> SendRe(@RequestParam("token") String token,@RequestParam("content") String content,@RequestParam("qaid") String qaId){
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();
        sendDataService.sendRe(uuid,content,qaId);
        return new Result<>(true,"成功");
    }


    @PostMapping("/deletearticle")
    @ResponseBody
    public Result<String> deleteArticle(@RequestParam("token") String token,@RequestParam("articleid") String articleId){


        sendDataService.deleteArticle(token,articleId);

        return new Result<>(true,"删除成功");
    }
    @PostMapping("/deleteqa")
    @ResponseBody
    public Result<String> deleteQa(@RequestParam("token") String token,@RequestParam("qaid") String qaId){


        sendDataService.deleteQa(token,qaId);

        return new Result<>(true,"删除成功");
    }
    @PostMapping("/visiblenotice")
    @ResponseBody
    public Result<String> visibleNotice(@RequestParam("token") String token,@RequestParam("noticeid") String noticeId){


        String uuid = tokenUtils.ValidToken(token).getUid();
        sendDataService.visibleNotice(uuid,noticeId);

        return new Result<>(true,"已查阅该提醒");
    }

    @PostMapping("/visiblenoticeall")
    @ResponseBody
    public Result<String> visibleNoticeAll(@RequestParam("token") String token){


        String uuid = tokenUtils.ValidToken(token).getUid();
        sendDataService.visibleNoticeAll(uuid);

        return new Result<>(true,"已全部查阅提醒");
    }

    @PostMapping("/uploadauthinfo")
    @ResponseBody
    public Result<String> uploadAuthInfo(@RequestParam("token") String token,@RequestParam("authimg") String img,@RequestParam("authcontent") String content){
        String uuid = tokenUtils.ValidToken(token).getUid();

        sendDataService.sendAuthInfo(uuid,img,content);
        return new Result<>(true,"已上传认证信息，请等候审核");
    }




    @PostMapping("/sendfeedback")
    @ResponseBody
    public Result<String> sendFeedback(@RequestBody FeedbackInfo feedbackInfo){
        String userId = feedbackInfo.getUserId();
        String content = "反馈内容：" + feedbackInfo.getFeedbackContent() + "安卓版本" + feedbackInfo.getAndroidVersion() + "\nBuild Api" + feedbackInfo.getBuildApi() + "\n手机品牌" + feedbackInfo.getDeviceBrand() + "\n手机型号" + feedbackInfo.getDeviceModel() + "\n版本号" + feedbackInfo.getVersionCode();
        sendDataService.sendFeedBack(userId,content);
        return new Result<>(true,"我们已收到您的反馈，谢谢！");
    }

    @PostMapping("/createclass")
    @ResponseBody
    public Result<String> createClass(@RequestBody SelectClass selectClass){
        String classId = "class" + commonUtils.createUUID();
        sendDataService.createClassInfo(classId,selectClass.getSelectlisttitle(),selectClass.getSelectauthor(),selectClass.getSelectauthorid(),selectClass.getSelectprice(),selectClass.getSelectdesc(),selectClass.getSelectbackimg(),selectClass.getType(),selectClass.getSelectauthordes());

        return new Result<>(true,classId);
    }

    @PostMapping("/editclass")
    @ResponseBody
    public Result<String> editClass(@RequestBody SelectClass selectClass){
        sendDataService.editClassInfo(selectClass.getSelectId(),selectClass.getSelectlisttitle(),selectClass.getSelectprice(),selectClass.getSelectdesc(),selectClass.getSelectbackimg(),selectClass.getType());
        return new Result<>(true,selectClass.getSelectId());
    }

    @PostMapping("/uploadclass")
    @ResponseBody
    public Result<String> createClass(@RequestBody ClassDetailList classDetailList){
        String classdId = "classd" + commonUtils.createUUID();
        sendDataService.uploadClassDetail(classdId,classDetailList.getClassd_title(),classDetailList.getClass_classd_id(),classDetailList.getClassd_des(),classDetailList.getClassd_duration(),classDetailList.getClassd_video(),classDetailList.getClassd_video_cover());

        return new Result<>(true,"课程上传成功");
    }

    @PostMapping("/deleteclass")
    @ResponseBody
    public Result<String> deleteClass(@RequestParam("classid") String classId,@RequestParam("token") String token){
        String uuid = tokenUtils.ValidToken(token).getUid();

        sendDataService.deleteClass(uuid,classId);
        return new Result<>(true,"课程删除成功");
    }

    @PostMapping("/deleteclassdetail")
    @ResponseBody
    public Result<String> deleteClassDetail(@RequestParam("classdid") String classdId){

        sendDataService.deleteClassDetail(classdId);
        return new Result<>(true,"本节课程删除成功");
    }


    @PostMapping("/insertarticlecomment")
    @ResponseBody
    public Result<String> insertArticleComment(@RequestParam("artcomartid") String artComArtId,@RequestParam("token") String token,@RequestParam("artcom") String artCom){

        String uuid = tokenUtils.ValidToken(token).getUid();
        sendDataService.insertArticleComment(artComArtId,uuid,artCom);
        return new Result<>(true,"留言成功");
    }
    @PostMapping("/replyarticlecomment")
    @ResponseBody
    public Result<String> replyArticleComment(@RequestParam("artcomid") String artComId,@RequestParam("token") String token,@RequestParam("artcomreply") String artComReply){
        String uuid = tokenUtils.ValidToken(token).getUid();
        sendDataService.replyArticleComment(artComId,uuid,artComReply);
        return new Result<>(true,"回复成功");
    }
    @PostMapping("/deletearticlecomment")
    @ResponseBody
    public Result<String> deleteArticleComment(@RequestParam("artcomid") String artComId,@RequestParam("token") String token){
        String uuid = tokenUtils.ValidToken(token).getUid();
        sendDataService.deleteArticleComment(artComId,uuid);
        return new Result<>(true,"留言已删除");
    }
    @PostMapping("/deletereplyarticlecomment")
    @ResponseBody
    public Result<String> deleteReplyArticleComment(@RequestParam("artcomid") String artComId,@RequestParam("token") String token){
        String uuid = tokenUtils.ValidToken(token).getUid();
        sendDataService.deleteReplyArticleComment(artComId,uuid);
        return new Result<>(true,"回复已删除");
    }
    @PostMapping("/likeariclecomment")
    @ResponseBody
    public Result<String> likeArticleComment(@RequestParam("token") String token,@RequestParam("artcommentid") String artCommentId){
        String uuid = tokenUtils.ValidToken(token).getUid();
        sendDataService.likeArticleComment(uuid,artCommentId);
        return new Result<>(true,"已点赞");
    }
    @PostMapping("/removelikearticlecomment")
    @ResponseBody
    public Result<String> removeLikeArticleComment(@RequestParam("artcommentid") String artCommentId,@RequestParam("token") String token){
        String uuid = tokenUtils.ValidToken(token).getUid();
        sendDataService.removeLikeArticleComment(artCommentId,uuid);
        return new Result<>(true,"已取消");
    }
}
