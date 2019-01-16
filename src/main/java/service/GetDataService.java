package service;

import dto.CommentDetail;
import dto.HomeData;
import dto.QAData;
import dto.QADataDetail;
import entity.TeaClasss;

import java.util.ArrayList;


public interface GetDataService {
    HomeData getHomeData();

    ArrayList<TeaClasss> getMoreClass(int currentPage);

    ArrayList<QAData> getQAData(String uuid);

    ArrayList<QAData> getMoreQAData(int currentPage,String uuid);

    QADataDetail getQADetail(String qaId,String uuid);

    CommentDetail getCommentDetail(String commentId,String uuid);
}
