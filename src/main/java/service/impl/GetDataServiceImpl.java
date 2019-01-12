package service.impl;

import dao.GetDataDao;
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
    public ArrayList<QAData> getQAData() {
        return getDataDao.getQAData();
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

}
