package dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import javax.print.DocFlavor;

public interface SendDataDao {



    void insertQa(@Param("qaId") String qaId, @Param("userId") String userId,@Param("qaContent") String qaContent,@Param("qaVideo") String qaVideo,@Param("qaTime") String qaTime,@Param("qaAudio") String qaAudio,@Param("qaVideoCover") String qaVideoCover);

    void addLikeQaWithUser(@Param("qaLikeId") String qaLikeId,@Param("qaLikeQaId") String qaLikeQaId,@Param("qaLikeUserId") String qaLikeUserId);
    void removeLikeQaWithUser(@Param("qaLikeQaId") String qaLikeQaId,@Param("qaLikeUserId") String qaLikeUserId);


    void deleteArticle(@Param("articleId") String articleId,@Param("uuid") String uuid);

    void deleteQa(@Param("qaId") String qaId,@Param("uuid") String uuid);

    void addLikeCommentWithUser(@Param("commentLikeId") String commentLikeId,@Param("commentLikeCommentId") String commentLikeCommentId,@Param("commentLikeUserId") String commentLikeUserId);

    void removeLikeCommentWithUser(@Param("commentLikeCommentId") String commentLikeCommentId,@Param("commentLikeUserId") String commentLikeUserId);
    void addLikeReplyWithUser(@Param("replyLikeId") String replyLikeId,@Param("replyLikeReplyId") String replyLikeReplyId,@Param("replyLikeUserId") String replyLikeUserId);

    void removeLikeReplyWithUser(@Param("replyLikeReplyId") String replyLikeReplyId,@Param("replyLikeUserId") String replyLikeUserId);


    void insertQaComment(@Param("commentQId") String commentQId,@Param("qaId") String qaId,@Param("uuid") String uuid,@Param("content") String content,@Param("sendTime") String sendTime);

    void insertReply(@Param("replyId") String replyId,@Param("replyRId") String replyRId,@Param("replyContent") String replyContent,@Param("replyUid") String replyUid,@Param("replyToUid") String replyToUid,@Param("replyTime") String replyTime);

    void deleteComment(@Param("commentQId") String commentQId,@Param("commentQUserId") String commentQUserId);

    void updateComment(@Param("commentQId") String commentQId,@Param("commentQUserId") String commentQUserId);


    void deleteReply(@Param("replyId") String replyId,@Param("replyUserId") String replyUserId);

    void insertFav(@Param("favId") String favId,@Param("uuid") String uuid,@Param("classId") String classId,@Param("favTime") String favTime);
    void deleteFav(@Param("uuid") String uuid,@Param("classId") String classId);

    void insertVideoView(@Param("classdViewId") String classdViewId,@Param("classdViewClassdId") String classdViewClassdId,@Param("classdViewUserId") String classdViewUserId,@Param("classdViewTime") String classdViewTime,@Param("classdViewClassId") String classdViewClassId);

    void insertVideoViewMore(@Param("classdViewClassdId") String classdViewClassdId,@Param("classdViewUserId") String classdViewUserId,@Param("classdViewTime") String classdViewTime,@Param("classdViewClassId") String classdViewClassId);

    void removeViewHis(@Param("uuid") String uuid, @Param("classdId") String classdId);

    void uploadArticle(@Param("articleId") String articleId,@Param("articleAuthorId") String articleAuthorId,@Param("articleTime") String articleTime,@Param("articleContent") String articleContent,@Param("articleImg") String articleImg,@Param("articleTitle") String articleTitle);

    void sendRe(@Param("qaId") String qaId,@Param("qaUserId") String uuid,@Param("qaContent") String content,@Param("qaTime") String qaTime,@Param("qaReId") String qaReId);

    void noticeInfo(@Param("noticeId") String noticeId,@Param("noticeFromUserId") String noticeFromUserId,@Param("noticeToUserId") String noticeToUserId,@Param("noticeContent") String noticeContent,@Param("noticeType") String noticeType,@Param("noticeTargetId") String noticeTargetId,@Param("noticeStatus") int noticeStatus,@Param("noticeTime") String noticeTime);

    void noticeVisible(@Param("noticeId") String noticeId,@Param("noticeToUserId") String noticeToUserId);

    void noticeVisibleAll(String uuid);
    void deleteNoticeInfo(@Param("noticeTargetId") String noticeTargetId,@Param("noticeFromUserId") String noticeFromUserId,@Param("noticeType") String noticeType);

    void sendAuthInfo(@Param("authId") String authId,@Param("authUserId") String authUserId,@Param("authImg") String authImg,@Param("authInfo") String authInfo,@Param("authTime") String authTime);


    void addVideoViewCount(@Param("classdid") String classdid,@Param("classid") String classid);

    void insertFeedBackInfo(@Param("feedbackId") String feedbackId,@Param("userId") String userId,@Param("feedbackContent") String feedBackContent,@Param("feedbackTime") String feedbackTime);

    void createClassInfo(@Param("classId") String classId, @Param("classTitle") String classTitle, @Param("classAuthor") String classAuthor, @Param("classAuthorId") String classAuthorId, @Param("classsPrice") String classPrice, @Param("classDes") String classDes, @Param("classImg") String classImg, @Param("classType") String classType, @Param("classTime") String classTime);

    void editClassInfo(@Param("classId") String classId, @Param("classTitle") String classTitle, @Param("classsPrice") String classPrice, @Param("classDes") String classDes, @Param("classImg") String classImg, @Param("classType") String classType, @Param("classTime") String classTime,@Param("ser") int ser);

    void uploadClassDetail(@Param("classdId") String classdId,@Param("classdTitle") String classdTitle,@Param("classclassdId") String classclassdId,@Param("classdDes") String classdDes,@Param("classdDuration") String classdDuration,@Param("classdVideo") String classdVideo,@Param("classdCreateTime") String classdCreateTime,@Param("classdVideoCover") String classdVideoCover);


    void addDuration(@Param("classId") String classId,@Param("classDuration") String duration);

    void deleteClass(@Param("uuid") String uuid,@Param("classId") String classId);

    void deleteClassDetail(String classdId);


    void likeArticle(@Param("articleLikeId") String articleLikeId,@Param("userId") String userId,@Param("articleId") String articleId,@Param("articleLikeTime") String articleLikeTime);

    void removeLikeArticle(@Param("articleId") String articleId,@Param("userId") String userId);

    void insertArticleComment(@Param("artComId") String artComId,@Param("artComArtId") String artComArtId,@Param("artComUserId") String artComUserId,@Param("artCom") String artCom,@Param("artComTime") String artComTime);

    void replyArticleComment(@Param("artComId") String artComId, @Param("artComUserId") String artComUserId,@Param("artComReply") String artComReply);

    void deleteArticleComment(@Param("artComId") String artComId,@Param("artComUserId") String artComUserId);

    void deleteReplyArticleComment(@Param("artComId") String artComId, @Param("artComUserId") String artComUserId);

    void likeArticleComment(@Param("artComLikeId") String artComLikeId,@Param("userId") String userId,@Param("artCommentId") String artCommentId,@Param("artLikeTime") String artLikeTime);
    void removeLikeArticleComment(@Param("artCommentId") String artCommentId,@Param("userId") String userId);


    void addQaViewCount(String qaId);
    void addQaView(@Param("qaViewId") String qaViewId,@Param("qaViewUserId") String qaViewUserId,@Param("qaViewQaId") String qaViewQaId,@Param("qaViewTime") String qaViewTime);

    void insertOrders(@Param("orderId") String orderId,@Param("orderNumber") String orderNumber,@Param("orderUserId") String orderUserId,@Param("orderTime") String orderTime,@Param("orderClassId") String orderClassId,@Param("orderPirce") String orderPirce,@Param("orderDiscount") String orderDiscount,@Param("orderEndPrice") String orderEndPrice,@Param("orderStatus") String orderStatus);


    void updateTransaction(@Param("transactionId") String transactionId,@Param("payType") String payType,@Param("payStatus") String payStatus,@Param("bankType") String bankType,@Param("wechatNumber") String wechatNumber,@Param("endTime") String endTime);


    void insertClassBuyInfo(@Param("class_buy_id") String class_buy_id,@Param("class_buy_class_id") String class_buy_class_id,@Param("class_buy_user_id") String class_buy_user_id,@Param("buy_time") String buy_time);

    void updateUserBalance(@Param("userId") String userId,@Param("balance") String balance);

    void updateCoin(@Param("userId") String userId, @Param("artCoin") String artCoin);

    void insertPsd(@Param("psdId") String psdId,@Param("uuid") String uuid, @Param("psd") String psd);

    void insertStartTime(@Param("startId") String startId,@Param("createTime") String createTime, @Param("userId") String userId);

}
