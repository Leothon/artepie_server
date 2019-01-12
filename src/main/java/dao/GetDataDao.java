package dao;

import dto.QAData;
import dto.QADataDetail;
import entity.Banner;
import entity.Comment;
import entity.Reply;
import entity.TeaClasss;

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

}
