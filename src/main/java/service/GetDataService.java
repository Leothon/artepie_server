package service;

import dto.*;
import entity.TeaClasss;

import java.util.ArrayList;


public interface GetDataService {
    HomeData getHomeData(String uuid);

    ArrayList<TeaClasss> getMoreClass(int currentPage,String uuid);

    ArrayList<QAData> getQAData(String uuid);

    ArrayList<QAData> getMoreQAData(int currentPage,String uuid);

    QADataDetail getQADetail(String qaId,String uuid);

    CommentDetail getCommentDetail(String commentId,String uuid);

    ClassDetail getClassDetail(String uuid,String classId );

    VideoDetail getVideoDetail(String uuid,String classId,String classdId);

    TeacherClass getTeaClass(String uuid,String teaId);
    TypeClass getClassByType(String uuid,String type);

    TeaClasss getClassInfo(String classId);
}
