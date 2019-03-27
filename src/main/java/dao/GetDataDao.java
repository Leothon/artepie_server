package dao;

import dto.QAData;
import entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface GetDataDao {

    ArrayList<Banner> getBanners();
    ArrayList<SelectClass> getClasses();
    ArrayList<SelectClass> getMoreClass(int currentPage);
    ArrayList<QAData> getQAData();
    QAData getTopQA(String topId);
    ArrayList<User> getTeacherInUser();
    int getClassView(String classId);
    int getClassdView(String classdId);
    int isUserBuy(@Param("classId") String classId, @Param("userId") String userId);
    ArrayList<QAData> getMoreQAData(int currentPage);
    ArrayList<QAData> getQADataById(String uuid);
    ArrayList<QAData> getMoreQADataById(@Param("currentPage") int currentPage,@Param("uuid") String uuid);

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

    SelectClass getClassDetail(String classId);


    int isFav(@Param("userId") String userId,@Param("classId") String classId);

    ArrayList<ClassDetailList> getClassList(String classId);


    ClassDetailList getClassVideo(@Param("classId") String classId,@Param("classdId") String classdId);


    ArrayList<String> getClassdIds(String classId);

    String getClassPrice(String classId);

    int getFavCount(String classId);

    ArrayList<SelectClass> getClassByTea(String teaId);

    ArrayList<SelectClass> getClassByMyself(String teaId);

    User getTeaInfo(String teaId);

    ArrayList<SelectClass> getClassByType(String type);

    int getTypeClassCount(String type);

    SelectClass getClassInfo(String classId);

    int isQQOrWeChatRegister(String accessToken);

    ArrayList<SelectClass> getFavClassByUid(String uuid);

    ArrayList<ClassDetailList> getViewHisById(String uuid);

    ArrayList<ClassDView> getStudyEveryday(@Param("uuid") String uuid,@Param("time") String time);

    ArrayList<SelectClass> getBuyClassByid(String uid);


    ArrayList<Banner> getArticleBanner();

    ArrayList<Article> getArticleList();
    ArrayList<Article> getMoreArticleList(int currentPage);

    ArrayList<Article> getArticleListById(String userId);
    ArrayList<Article> getMoreArticleListById(@Param("userId") String userId,@Param("currentPage") int currentPage);

    Article getArticleDetail(String articleId);

    String getUserIdByQaId(String qaId);

    String getUserIdByCommentId(String commentId);

    String getUserIdByReplyId(String replyId);


    ArrayList<NoticeInfo> getNoticeInfo(String userId);

    int isHasNotice(String userId);
    int isHasClassDetail(String classId);

    String getCommentIdByReplyId(String replyId);

    ArrayList<AuthInfo> getAuthInfo(String uuid);

    ArrayList<SelectClass> getClassDataByKeyword(String keyword);
    ArrayList<QAData> getQADataByKeyword(String keyword);
    ArrayList<User> getUserByKeyword(String keyword);
    ArrayList<Article> getArticleByKeyword(String keyword);

    String getUserNameByUserId(String userId);
    String getUserIdByClassId(String classId);

    String getClassAllDuration(String classId);

}
