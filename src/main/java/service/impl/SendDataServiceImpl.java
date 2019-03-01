package service.impl;

import com.vdurmont.emoji.EmojiParser;
import dao.GetDataDao;
import dao.SendDataDao;
import dao.UserDao;
import entity.TokenValid;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.SendDataService;
import service.UserService;
import utils.JpushUtils;
import utils.commonUtils;
import utils.tokenUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class SendDataServiceImpl implements SendDataService {

    @Autowired
    SendDataDao sendDataDao;


    @Autowired
    GetDataDao getDataDao;

    @Autowired
    UserDao userDao;
    @Override
    public void insertQAData(String qaId, String userId, String qaContent, String qaVideo, String qa_time, String qaAudio,String qaVideoCover) {

        String content = EmojiParser.parseToAliases(qaContent);
        sendDataDao.insertQa(qaId,userId,content,qaVideo,qa_time,qaAudio,qaVideoCover);
    }

    @Override
    public void addLikeQa(String userId, String qaId) {
        String qaLikeId = "qalike" + commonUtils.createUUID();


        if (getDataDao.isLike(userId,qaId) != 0){
            sendDataDao.addLikeQaWithUser(qaLikeId,qaId,userId);
            String authorId = getDataDao.getUserIdByQaId(qaId);
            if (!authorId.equals(userId)){
                Map<String, String> parm = new HashMap<String, String>();
                parm.put("id",authorId);
                User user = userDao.getUserInfo(userId);
                parm.put("msg",user.getUser_name() + "点赞了您的问答");
                JpushUtils.jpushAndroidByAlias(parm);
                String noticeId = "noticeId" + commonUtils.createUUID();
                sendDataDao.noticeInfo(noticeId,userId,authorId,"","qalike",qaId,0,commonUtils.getTime());
            }
        }


    }

    @Override
    public void removeLikeQa(String userId, String qaId) {
        sendDataDao.removeLikeQaWithUser(qaId,userId);
        sendDataDao.deleteNoticeInfo(qaId,userId,"qalike");
    }

    @Override
    public void addLikeComment(String userId, String commentId) {

        String commentLikeId = "commentlike" + commonUtils.createUUID();

        if (getDataDao.isCommentLike(userId,commentId) != 0){
            sendDataDao.addLikeCommentWithUser(commentLikeId,commentId,userId);
            String authorId = getDataDao.getUserIdByCommentId(commentId);
            if (!authorId.equals(userId)){
                Map<String, String> parm = new HashMap<String, String>();
                parm.put("id",authorId);
                User user = userDao.getUserInfo(userId);
                parm.put("msg",user.getUser_name() + "点赞了您的评论 ");
                JpushUtils.jpushAndroidByAlias(parm);
                String noticeId = "noticeId" + commonUtils.createUUID();
                sendDataDao.noticeInfo(noticeId,userId,authorId,"","commentlike",commentId,0,commonUtils.getTime());
            }
        }


    }

    @Override
    public void removeLikeComment(String userId, String commentId) {
        sendDataDao.removeLikeCommentWithUser(commentId,userId);
        sendDataDao.deleteNoticeInfo(commentId,userId,"commentlike");
    }

    @Override
    public void addLikeReply(String userId, String replyId) {
        String commentReplyId = "replylike" + commonUtils.createUUID();

        if (getDataDao.isReplyLike(userId,replyId) != 0){
            sendDataDao.addLikeReplyWithUser(commentReplyId,replyId,userId);
            String authorId = getDataDao.getUserIdByReplyId(replyId);
            if (!authorId.equals(userId)){
                Map<String, String> parm = new HashMap<String, String>();
                parm.put("id",authorId);
                User user = userDao.getUserInfo(userId);
                parm.put("msg",user.getUser_name() + "点赞了您的回复 ");
                JpushUtils.jpushAndroidByAlias(parm);
                String noticeId = "noticeId" + commonUtils.createUUID();
                sendDataDao.noticeInfo(noticeId,userId,authorId,"","replylike",getDataDao.getCommentIdByReplyId(replyId),0,commonUtils.getTime());
            }
        }

    }

    @Override
    public void removeLikeReply(String userId, String replyId) {
        sendDataDao.removeLikeReplyWithUser(replyId,userId);
        sendDataDao.deleteNoticeInfo(replyId,userId,"replylike");
    }

    @Override
    public void sendQaComment(String qaId, String uuid, String content, String sendTime) {
        String commentQId = "comq" + commonUtils.createUUID();
        String contentwithoutemoji = EmojiParser.parseToAliases(content);
        sendDataDao.insertQaComment(commentQId,qaId,uuid,contentwithoutemoji,sendTime);

        if (qaId.substring(0,2).equals("qa")){
            String authorId = getDataDao.getUserIdByQaId(qaId);
            if (!authorId.equals(uuid)){
                Map<String, String> parm = new HashMap<String, String>();
                parm.put("id",authorId);
                User user = userDao.getUserInfo(uuid);
                parm.put("msg",user.getUser_name() + "评论了您: " + contentwithoutemoji);
                JpushUtils.jpushAndroidByAlias(parm);
                String noticeId = "noticeId" + commonUtils.createUUID();
                sendDataDao.noticeInfo(noticeId,uuid,authorId,contentwithoutemoji,"qacomment",qaId,0,commonUtils.getTime());
            }
        }

    }

    @Override
    public void sendReply(String replyRId, String replyComment, String replyUserId, String replyToUserId, String replyTime) {
        String replyId = "repq" + commonUtils.createUUID();
        String contentwithoutemoji = EmojiParser.parseToAliases(replyComment);
        sendDataDao.insertReply(replyId,replyRId,contentwithoutemoji,replyUserId,replyToUserId,replyTime);

        //String authorId = getDataDao.getUserIdByReplyId(replyRId);
        if (!replyToUserId.equals(replyUserId)){
            Map<String, String> parm = new HashMap<String, String>();
            parm.put("id",replyToUserId);
            User user = userDao.getUserInfo(replyUserId);
            parm.put("msg",user.getUser_name() + "回复了您 : " + contentwithoutemoji);
            JpushUtils.jpushAndroidByAlias(parm);
            String noticeId = "noticeId" + commonUtils.createUUID();
            sendDataDao.noticeInfo(noticeId,replyUserId,replyToUserId,contentwithoutemoji,"replycomment",replyRId,0,commonUtils.getTime());
        }
    }

    @Override
    public void deleteComment(String commentId, String userId) {
        if (getDataDao.getCommentReplyCount(commentId) == 0){
            sendDataDao.deleteComment(commentId,userId);
        }else {
            sendDataDao.updateComment(commentId,userId);
        }
        sendDataDao.deleteNoticeInfo(commentId,userId,"qacomment");
    }

    @Override
    public void deleteReply(String replyId, String userId) {
        sendDataDao.deleteReply(replyId,userId);
        sendDataDao.deleteNoticeInfo(getDataDao.getCommentIdByReplyId(replyId),userId,"replycomment");
    }

    @Override
    public void addFav(String uuid, String classId, String favId) {
        String nowTime = commonUtils.getTime();
        if (getDataDao.isFav(uuid,classId) != 0){
            sendDataDao.insertFav(favId,uuid,classId,nowTime);
        }

    }

    @Override
    public void removeFav(String uuid, String classId) {
        sendDataDao.deleteFav(uuid,classId);
    }

    @Override
    public void addVideoView(String uuid, String classdId, String classId) {
        String nowTime = commonUtils.getTime();
        String viewId = "classview" + commonUtils.createUUID();

        sendDataDao.insertVideoView(viewId,classdId,uuid,nowTime,classId);
    }

    @Override
    public void removeViewHis(String uuid, String classdId) {
        sendDataDao.removeViewHis(uuid,classdId);
    }

    @Override
    public void uploadArticle(String title, String img, String content, String uid) {
        String time = commonUtils.getTime();
        String articleId = "article" + commonUtils.createUUID();
        sendDataDao.uploadArticle(articleId,uid,time,content,img,title);
    }

    @Override
    public void sendRe(String uuid, String content, String qaReId) {
        String time = commonUtils.getTime();
        String qaId = "qa" + commonUtils.createUUID();
        String contentwithoutemoji = EmojiParser.parseToAliases(content);
        sendDataDao.sendRe(qaId,uuid,contentwithoutemoji,time,qaReId);
    }

    @Override
    public void deleteArticle(String token, String articleId) {
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();
        sendDataDao.deleteArticle(articleId,uuid);
    }
    @Override
    public void deleteQa(String token, String qaId) {
        TokenValid tokenValid  = tokenUtils.ValidToken(token);
        String uuid = tokenValid.getUid();
        sendDataDao.deleteQa(qaId,uuid);
    }

    @Override
    public void visibleNotice(String uuid, String noticeId) {
        sendDataDao.noticeVisible(noticeId,uuid);
    }
    @Override
    public void visibleNoticeAll(String uuid) {
        sendDataDao.noticeVisibleAll(uuid);
    }

    @Override
    public void sendAuthInfo(String uuid, String img, String content) {
        String authId = "auth" + commonUtils.createUUID();
        String contentwithoutemoji = EmojiParser.parseToAliases(content);
        sendDataDao.sendAuthInfo(authId,uuid,img,contentwithoutemoji,commonUtils.getTime());
    }



    @Override
    public void sendFeedBack(String uuid, String content) {
        String feedbackId = "feed" + commonUtils.createUUID();
        String contentwithoutemoji = EmojiParser.parseToAliases(content);
        sendDataDao.insertFeedBackInfo(feedbackId,uuid,contentwithoutemoji,commonUtils.getTime());
    }
}
