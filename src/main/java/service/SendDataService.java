package service;

public interface SendDataService {

    void insertQAData(String qaId,String userId,String qaContent,String qaVideo,String qa_time,String qaAudio);

    void addLikeQa(String userId,String qaId);
    void removeLikeQa(String userId,String qaId);
    void addLikeComment(String userId,String commentId);
    void removeLikeComment(String userId,String commentId);
    void addLikeReply(String userId,String replyId);
    void removeLikeReply(String userId,String replyId);

    void sendQaComment(String qaId,String uuid,String content,String sendTime);

    void sendReply(String replyRId,String replyComment,String replyUserId,String replyToUserId,String replyTime);

    void deleteComment(String commentId,String userId);

    void deleteReply(String replyId,String userId);
}
