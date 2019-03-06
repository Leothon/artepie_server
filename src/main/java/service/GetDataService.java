package service;

import dto.*;
import entity.*;

import java.util.ArrayList;


public interface GetDataService {
    HomeData getHomeData(String uuid);

    ArrayList<SelectClass> getMoreClass(int currentPage, String uuid);

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

    SelectClass getClassInfo(String classId);

    String isQQRegister(String accessToken);

    ArrayList<SelectClass> getFavClassByUid(String uuid);

    ArrayList<ClassDetailList> getClassViewById(String uuid);

    BagPageData getBagPageData(String uuid);

    ArticleData getArticleData(String uuid);
    ArrayList<Article> getMoreArticleData(String uuid,int currentPage);
    ArrayList<Article> getArticleDataById(String uuid);
    ArrayList<Article> getMoreArticleDataById(String uuid,int currentPage);

    Article getArticleDetail(String uuid,String articleId);

    int isHasNotice(String uuid);

    ArrayList<NoticeInfo> getNoticeInfo(String uuid);

    ArrayList<AuthInfo> getAuthInfo(String uuid);

    ArrayList<SelectClass> searchClassByKeyword(String keyword, String uuid);
    ArrayList<QAData> searchQAByKeyword(String keyword,String uuid);
    ArrayList<User> searchUserByKeyword(String keyword,String uuid);
    ArrayList<Article> searchArticleByKeyword(String keyword,String uuid);

    ArrayList<SelectClass> getSelectClassByUserId(String userId);



}
