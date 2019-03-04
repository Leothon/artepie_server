package service;

public interface SendDataService {

    void insertQAData(String qaId,String userId,String qaContent,String qaVideo,String qa_time,String qaAudio,String qaVideoCover);

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

    void addFav(String uuid,String classId,String favId);

    void removeFav(String uuid,String classId);
    void addVideoView(String uuid,String classdId,String classId);

    void removeViewHis(String uuid,String classdId);

    void uploadArticle(String title,String img,String content,String uid);

    void sendRe(String uuid,String content,String qaReId);

    void deleteArticle(String token,String articleId);

    void deleteQa(String token,String qaId);

    void visibleNotice(String uuid,String noticeId);
    void visibleNoticeAll(String uuid);

    void sendAuthInfo(String uuid,String img,String content);


    void sendFeedBack(String uuid, String content);

    void createClassInfo(String classTitle,String classAuthor,String classAuthorId, String classPrice, String classDes,String classImg, String classType, String classAuthorDes);

}
