package dao;

import dto.QAData;
import dto.QADataDetail;
import entity.Banner;
import entity.Comment;
import entity.Reply;
import entity.TeaClasss;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface GetDataDao {

    ArrayList<Banner> getBanners();
    ArrayList<TeaClasss> getClasses();
    ArrayList<TeaClasss> getMoreClass(int currentPage);
    ArrayList<QAData> getQAData();

    ArrayList<QAData> getMoreQAData(int currentPage);


    QAData getQADetail(String qaId);
    ArrayList<Comment> getComment(String qaId);
    ArrayList<Reply> getReply(String commentId);


    Comment getSingleComment(String commentId);

    int getQALike(String qaId);
    int getQAComment(String qaId);

    int isLike(@Param("userId") String userId, @Param("qaId") String qaId);

    int getCommentLike(String commentId);

    int isCommentLike(@Param("userId") String userId,@Param("commentId") String commentId);

    int getReplyLike(String replyId);

    int isReplyLike(@Param("userId") String userId,@Param("replyId") String replyId);

    int getCommentReplyCount(String commentId);
}
