package dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface SendDataDao {



    void insertQa(@Param("qaId") String qaId, @Param("userId") String userId,@Param("qaContent") String qaContent,@Param("qaVideo") String qaVideo,@Param("qaTime") String qaTime,@Param("qaAudio") String qaAudio);

    void addLikeQaWithUser(@Param("qaLikeId") String qaLikeId,@Param("qaLikeQaId") String qaLikeQaId,@Param("qaLikeUserId") String qaLikeUserId);
    void removeLikeQaWithUser(@Param("qaLikeQaId") String qaLikeQaId,@Param("qaLikeUserId") String qaLikeUserId);



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
}
