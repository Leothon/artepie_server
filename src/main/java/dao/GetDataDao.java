package dao;

import dto.QAData;
import dto.QADataDetail;
import entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

public interface GetDataDao {

    ArrayList<Banner> getBanners();
    ArrayList<TeaClasss> getClasses();
    ArrayList<TeaClasss> getMoreClass(int currentPage);
    ArrayList<QAData> getQAData();
    int getClassView(String classId);
    int getClassdView(String classdId);
    int isUserBuy(@Param("classId") String classId, @Param("userId") String userId);
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

    TeaClasss getClassDetail(String classId);

    int isFav(@Param("userId") String userId,@Param("classId") String classId);

    ArrayList<ClassDetailList> getClassList(String classId);

    ClassDetailList getClassVideo(@Param("classId") String classId,@Param("classdId") String classdId);

    ArrayList<String> getClassdIds(String classId);

    String getClassPrice(String classId);

    int getFavCount(String classId);
}
