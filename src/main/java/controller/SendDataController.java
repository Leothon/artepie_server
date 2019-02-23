package controller;

import dto.QAData;
import dto.Result;
import dto.SendQAData;
import entity.Article;
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
}
