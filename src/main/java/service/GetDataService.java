package service;

import com.mysql.cj.protocol.x.Notice;
import dto.*;
import entity.*;

import java.util.ArrayList;


public interface GetDataService {
    HomeData getHomeData(String uuid);

    ArrayList<TeaClasss> getMoreClass(int currentPage,String uuid);

    ArrayList<QAData> getQAData(String uuid);

    ArrayList<QAData> getMoreQAData(int currentPage,String uuid);

    ArrayList<QAData> getQADataById(String uuid);

    ArrayList<QAData> getMoreQADataById(int currentPage,String uuid);

    QADataDetail getQADetail(String qaId,String uuid);
    QAData getQA(String qaId,String uuid);
    CommentDetail getCommentDetail(String commentId,String uuid);

    ClassDetail getClassDetail(String uuid,String classId );

    VideoDetail getVideoDetail(String uuid,String classId,String classdId);

    TeacherClass getTeaClass(String uuid,String teaId);
    TypeClass getClassByType(String uuid,String type);

    TeaClasss getClassInfo(String classId);

    String isQQRegister(String accessToken);

    ArrayList<TeaClasss> getFavClassByUid(String uuid);

    ArrayList<ClassDetailList> getClassViewById(String uuid);

    BagPageData getBagPageData(String uuid);

    ArticleData getArticleData(String uuid);

    Article getArticleDetail(String uuid,String articleId);

    int isHasNotice(String uuid);

    ArrayList<NoticeInfo> getNoticeInfo(String uuid);

    ArrayList<AuthInfo> getAuthInfo(String uuid);

    ArrayList<TeaClasss> searchClassByKeyword(String keyword);
    ArrayList<QAData> searchQAByKeyword(String keyword);
    ArrayList<User> searchUserByKeyword(String keyword);
    ArrayList<Article> searchArticleByKeyword(String keyword);
}
