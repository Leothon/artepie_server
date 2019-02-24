package service.impl;

import dao.GetDataDao;
import dao.SendDataDao;
import entity.TokenValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.SendDataService;
import utils.commonUtils;
import utils.tokenUtils;

@Service
public class SendDataServiceImpl implements SendDataService {

    @Autowired
    SendDataDao sendDataDao;


    @Autowired
    GetDataDao getDataDao;
    @Override
    public void insertQAData(String qaId, String userId, String qaContent, String qaVideo, String qa_time, String qaAudio,String qaVideoCover) {

        sendDataDao.insertQa(qaId,userId,qaContent,qaVideo,qa_time,qaAudio,qaVideoCover);
    }

    @Override
    public void addLikeQa(String userId, String qaId) {
        String qaLikeId = "qalike" + commonUtils.createUUID();
        if (getDataDao.isLike(userId,qaId) != 0){
            sendDataDao.addLikeQaWithUser(qaLikeId,qaId,userId);
        }

    }

    @Override
    public void removeLikeQa(String userId, String qaId) {
        sendDataDao.removeLikeQaWithUser(qaId,userId);
    }

    @Override
    public void addLikeComment(String userId, String commentId) {

        String commentLikeId = "commentlike" + commonUtils.createUUID();
        if (getDataDao.isCommentLike(userId,commentId) != 0){
            sendDataDao.addLikeCommentWithUser(commentLikeId,commentId,userId);
        }

    }

    @Override
    public void removeLikeComment(String userId, String commentId) {
        sendDataDao.removeLikeCommentWithUser(commentId,userId);
    }

    @Override
    public void addLikeReply(String userId, String replyId) {
        String commentReplyId = "replylike" + commonUtils.createUUID();
        if (getDataDao.isReplyLike(userId,replyId) != 0){
            sendDataDao.addLikeReplyWithUser(commentReplyId,replyId,userId);
        }
    }

    @Override
    public void removeLikeReply(String userId, String replyId) {
        sendDataDao.removeLikeReplyWithUser(replyId,userId);
    }

    @Override
    public void sendQaComment(String qaId, String uuid, String content, String sendTime) {
        String commentQId = "comq" + commonUtils.createUUID();
        sendDataDao.insertQaComment(commentQId,qaId,uuid,content,sendTime);
    }

    @Override
    public void sendReply(String replyRId, String replyComment, String replyUserId, String replyToUserId, String replyTime) {
        String replyId = "repq" + commonUtils.createUUID();
        sendDataDao.insertReply(replyId,replyRId,replyComment,replyUserId,replyToUserId,replyTime);
    }

    @Override
    public void deleteComment(String commentId, String userId) {
        if (getDataDao.getCommentReplyCount(commentId) == 0){
            sendDataDao.deleteComment(commentId,userId);
        }else {
            sendDataDao.updateComment(commentId,userId);
        }
    }

    @Override
    public void deleteReply(String replyId, String userId) {
        sendDataDao.deleteReply(replyId,userId);
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
        sendDataDao.sendRe(qaId,uuid,content,time,qaReId);
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
}
