package dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

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

    void removeViewHis(@Param("uuid") String uuid, @Param("classdId") String classdId);

    void uploadArticle(@Param("articleId") String articleId,@Param("articleAuthorId") String articleAuthorId,@Param("articleTime") String articleTime,@Param("articleContent") String articleContent,@Param("articleImg") String articleImg,@Param("articleTitle") String articleTitle);

    void sendRe(@Param("qaId") String qaId,@Param("qaUserId") String uuid,@Param("qaContent") String content,@Param("qaTime") String qaTime,@Param("qaReId") String qaReId);

    void noticeInfo(@Param("noticeId") String noticeId,@Param("noticeFromUserId") String noticeFromUserId,@Param("noticeToUserId") String noticeToUserId,@Param("noticeContent") String noticeContent,@Param("noticeType") String noticeType,@Param("noticeTargetId") String noticeTargetId,@Param("noticeStatus") int noticeStatus,@Param("noticeTime") String noticeTime);

    void noticeVisible(@Param("noticeId") String noticeId,@Param("noticeToUserId") String noticeToUserId);

    void noticeVisibleAll(String uuid);
    void deleteNoticeInfo(@Param("noticeTargetId") String noticeTargetId,@Param("noticeFromUserId") String noticeFromUserId,@Param("noticeType") String noticeType);

    void sendAuthInfo(@Param("authId") String authId,@Param("authUserId") String authUserId,@Param("authImg") String authImg,@Param("authInfo") String authInfo,@Param("authTime") String authTime);



    void insertFeedBackInfo(@Param("feedbackId") String feedbackId,@Param("userId") String userId,@Param("feedbackContent") String feedBackContent,@Param("feedbackTime") String feedbackTime);
}
