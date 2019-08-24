package dao;

import dto.Inform;
import dto.QAData;
import dto.Update;
import dto.createArticle;
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
    ArrayList<Comment> getMoreComment(@Param("qaId")String qaId,@Param("currentPage") int currentPage);
    ArrayList<Reply> getReply(String commentId);
    ArrayList<Reply> getMoreReply(@Param("commentId") String commentId,@Param("currentPage") int currentPage);

    Comment getSingleComment(String commentId);

    int getQALike(String qaId);
    int getQAComment(String qaId);

    int isLike(@Param("userId") String userId, @Param("qaId") String qaId);

    int getCommentLike(String commentId);

    int isCommentLike(@Param("userId") String userId,@Param("commentId") String commentId);

    int getReplyLike(String replyId);

    int isReplyLike(@Param("userId") String userId,@Param("replyId") String replyId);

    int isArticleLike(@Param("userId") String userId,@Param("articleId") String articleId);

    int getArticleLike(String articleId);

    int getArticleCommentCount(String articleId);

    int getCommentReplyCount(String commentId);

    SelectClass getClassDetail(String classId);


    int isFav(@Param("userId") String userId,@Param("classId") String classId);

    ArrayList<ClassDetailList> getClassList(String classId);
    ArrayList<ClassDetailList> getMoreClassList(@Param("classId") String classId,@Param("currentPage") int currentPage);


    ClassDetailList getClassVideo(@Param("classId") String classId,@Param("classdId") String classdId);


    ArrayList<String> getClassdIds(String classId);

    String getClassPrice(String classId);

    int getFavCount(String classId);

    ArrayList<SelectClass> getClassByTea(String teaId);
    ArrayList<SelectClass> getMoreClassByTea(@Param("teaId") String teaId,@Param("currentPage") int currentPage);

    ArrayList<SelectClass> getClassByMyself(String teaId);

    User getTeaInfo(String teaId);

    ArrayList<SelectClass> getClassByType(String type);
    ArrayList<SelectClass> getMoreClassByType(@Param("type") String type,@Param("currentPage") int currentPage);

    int getTypeClassCount(String type);

    SelectClass getClassInfo(String classId);

    int isQQOrWeChatRegister(String accessToken);

    ArrayList<SelectClass> getFavClassByUid(String uuid);
    ArrayList<SelectClass> getMoreFavClassByUid(@Param("uuid") String uuid,@Param("currentPage") int currentPage);

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
    ArrayList<NoticeInfo> getMoreNoticeInfo(@Param("page") int page,@Param("userId") String userId);

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

    Inform getInform(String uuid);

    Update getUpdate(String uuid);
    User getUserInfoInGet(String userId);
    String getAuthImg(String userId);

    void authUserz(@Param("userRole") String userRole,@Param("userId") String userId);
    void authUsero(@Param("userRole") String userRole,@Param("userId") String userId);

    void authUserto(String userId);

    int getArticleVisionCount(String articleId);
    void insertArticleView(@Param("articleViewId") String articleViewId,@Param("articleViewArticleId") String articleViewArticleId,@Param("articleViewUserId") String articleViewUserId,@Param("articleViewTime") String articleViewTime);


    ArrayList<ArticleComment> getArticleComment(String articleId);
    ArrayList<ArticleComment> getArticleCommentMore(@Param("articleId") String articleId,@Param("currentPage") int currentPage);

    ArrayList<createArticle> getArticleInfo();

    int getQaView(String qaId);

    ArrayList<String> getClassdids(String classid);

    void insertArticleViewCount(String articleid);

    Orders getOrders(String tradeNumber);


    String getAuthorIdByClassId(String classId);



    ArrayList<Fav> getUserIdByClassIdInFav(String classId);



    ArrayList<SelectClass> getBuyClassByUid(String uuid);
    ArrayList<SelectClass> getMoreBuyClassByUid(@Param("uuid") String uuid,@Param("currentPage") int currentPage);

    ArrayList<Bill> getOutBill(String uuid);
    ArrayList<Bill> getMoreOutBill(@Param("uuid") String uuid,@Param("currentPage") int currentPage);


    ArrayList<Bill> getInBill(String uuid);
    ArrayList<Bill> getMoreInBill(@Param("uuid") String uuid,@Param("currentPage") int currentPage);

    ArrayList<Bill> getInfifteen(String uuid);

    ArrayList<OrderHis> getOrderHis(String uuid);
    ArrayList<OrderHis> getMoreOrderHis(@Param("uuid") String uuid,@Param("currentPage") int currentPage);

    int isHasPsd(String uuid);

    String getPsd(String uuid);

    String getAuthorIdByArticleId(String articleId);
    String getTitleByArticleId(String articleId);

}
