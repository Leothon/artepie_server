package service.impl;

import dao.GetDataDao;
import dao.SendDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.SendDataService;
import utils.commonUtils;

@Service
public class SendDataServiceImpl implements SendDataService {

    @Autowired
    SendDataDao sendDataDao;


    @Autowired
    GetDataDao getDataDao;
    @Override
    public void insertQAData(String qaId, String userId, String qaContent, String qaVideo, String qa_time, String qaAudio) {

        sendDataDao.insertQa(qaId,userId,qaContent,qaVideo,qa_time,qaAudio);
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
}
