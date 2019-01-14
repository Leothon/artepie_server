package service.impl;

import dao.GetDataDao;
import dto.CommentDetail;
import dto.HomeData;
import dto.QAData;
import dto.QADataDetail;
import entity.Banner;
import entity.Comment;
import entity.Reply;
import entity.TeaClasss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.GetDataService;

import java.util.ArrayList;
@Service
public class GetDataServiceImpl implements GetDataService {

    @Autowired
    GetDataDao getDataDao;
    @Override
    public HomeData getHomeData() {
        ArrayList<Banner> banners = getDataDao.getBanners();
        ArrayList<TeaClasss> teaClassses = getDataDao.getClasses();
        HomeData homeData = new HomeData();
        homeData.setBanners(banners);
        homeData.setTeaClasses(teaClassses);
        return homeData;
    }

    @Override
    public ArrayList<TeaClasss> getMoreClass(int currentPage) {
        return getDataDao.getMoreClass(currentPage);
    }

    @Override
    public ArrayList<QAData> getQAData(String uuid) {

        ArrayList<QAData> qaData = getDataDao.getQAData();
        for (int i = 0;i < qaData.size();i ++){
            qaData.get(i).setQa_like(Integer.toString(getDataDao.getQALike(qaData.get(i).getQa_id())));
            qaData.get(i).setQa_comment(Integer.toString(getDataDao.getQAComment(qaData.get(i).getQa_id())));
            if (getDataDao.isLike(uuid,qaData.get(i).getQa_id()) == 0){
                qaData.get(i).setLiked(true);
            }else {
                qaData.get(i).setLiked(false);
            }

        }
        return qaData;
    }

    @Override
    public ArrayList<QAData> getMoreQAData(int currentPage) {
        return getDataDao.getMoreQAData(currentPage);
    }

    @Override
    public QADataDetail getQADetail(String qaId) {

        QAData qaData = getDataDao.getQADetail(qaId);

        ArrayList<Comment> comments = getDataDao.getComment(qaId);
        for (int i = 0;i < comments.size();i ++){
            ArrayList<Reply> replies = getDataDao.getReply(comments.get(i).getComment_q_id());
            comments.get(i).setReplies(replies);
        }
        QADataDetail qaDataDetail = new QADataDetail();
        qaDataDetail.setQaData(qaData);
        qaDataDetail.setComments(comments);
        return qaDataDetail;
    }

    @Override
    public CommentDetail getCommentDetail(String commentId) {

        Comment comment = getDataDao.getSingleComment(commentId);
        ArrayList<Reply> replies = getDataDao.getReply(commentId);
        CommentDetail commentDetail = new CommentDetail();
        commentDetail.setComment(comment);
        commentDetail.setReplies(replies);
        return commentDetail;
    }

}
