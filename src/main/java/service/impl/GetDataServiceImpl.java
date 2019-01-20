package service.impl;

import dao.GetDataDao;
import dto.*;
import entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.GetDataService;

import java.util.ArrayList;
@Service
public class GetDataServiceImpl implements GetDataService {

    @Autowired
    GetDataDao getDataDao;
    @Override
    public HomeData getHomeData(String uuid) {
        ArrayList<Banner> banners = getDataDao.getBanners();
        ArrayList<TeaClasss> teaClassses = getDataDao.getClasses();
        for (int i = 0;i < teaClassses.size();i ++){
            teaClassses.get(i).setSelectstucount(Integer.toString(getDataDao.getClassView(teaClassses.get(i).getSelectId())));
            if (getDataDao.isUserBuy(teaClassses.get(i).getSelectId(),uuid) == 0){
                teaClassses.get(i).setIsbuy(true);
            }else {
                teaClassses.get(i).setIsbuy(false);
            }
        }
        ArrayList<User> teachers = getDataDao.getTeacherInUser();
        HomeData homeData = new HomeData();
        homeData.setBanners(banners);
        homeData.setTeaClasses(teaClassses);
        homeData.setTeachers(teachers);
        return homeData;
    }

    @Override
    public ArrayList<TeaClasss> getMoreClass(int currentPage,String uuid) {
        ArrayList<TeaClasss> teaClassses = getDataDao.getMoreClass(currentPage);
        for (int i = 0;i < teaClassses.size();i ++){
            teaClassses.get(i).setSelectstucount(Integer.toString(getDataDao.getClassView(teaClassses.get(i).getSelectId())));
            if (getDataDao.isUserBuy(teaClassses.get(i).getSelectId(),uuid) == 0){
                teaClassses.get(i).setIsbuy(true);
            }else {
                teaClassses.get(i).setIsbuy(false);
            }
        }
        return teaClassses;
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
    public ArrayList<QAData> getMoreQAData(int currentPage,String userId) {

        ArrayList<QAData> qaMoreData = getDataDao.getMoreQAData(currentPage);
        for (int i = 0;i < qaMoreData.size();i ++){
            qaMoreData.get(i).setQa_like(Integer.toString(getDataDao.getQALike(qaMoreData.get(i).getQa_id())));
            qaMoreData.get(i).setQa_comment(Integer.toString(getDataDao.getQAComment(qaMoreData.get(i).getQa_id())));
            if (getDataDao.isLike(userId,qaMoreData.get(i).getQa_id()) == 0){
                qaMoreData.get(i).setLiked(true);
            }else {
                qaMoreData.get(i).setLiked(false);
            }

        }
        return qaMoreData;
    }

    @Override
    public QADataDetail getQADetail(String qaId,String uuid) {

        QAData qaData = getDataDao.getQADetail(qaId);

        qaData.setQa_like(Integer.toString(getDataDao.getQALike(qaId)));
        qaData.setQa_comment(Integer.toString(getDataDao.getQAComment(qaId)));
        ArrayList<Comment> comments = getDataDao.getComment(qaId);
        if (getDataDao.isLike(uuid,qaId) == 0){
            qaData.setLiked(true);
        }else {
            qaData.setLiked(false);
        }
        for (int i = 0;i < comments.size();i ++){
            comments.get(i).setComment_q_like(Integer.toString(getDataDao.getCommentLike(comments.get(i).getComment_q_id())));
            if (getDataDao.isCommentLike(uuid,comments.get(i).getComment_q_id()) == 0){
                comments.get(i).setComment_liked(true);
            }else {
                comments.get(i).setComment_liked(false);
            }
            ArrayList<Reply> replies = getDataDao.getReply(comments.get(i).getComment_q_id());
            for (int j = 0;j < replies.size();j ++){
                replies.get(j).setReply_like(Integer.toString(getDataDao.getReplyLike(replies.get(j).getReply_id())));
                if (getDataDao.isReplyLike(uuid,replies.get(j).getReply_id()) == 0){
                    replies.get(j).setReply_liked(true);
                }else {
                    replies.get(j).setReply_liked(false);
                }
            }
            comments.get(i).setReplies(replies);
        }
        QADataDetail qaDataDetail = new QADataDetail();
        qaDataDetail.setQaData(qaData);
        qaDataDetail.setComments(comments);
        return qaDataDetail;
    }

    @Override
    public CommentDetail getCommentDetail(String commentId,String uuid) {

        Comment comment = getDataDao.getSingleComment(commentId);
        comment.setComment_q_like(Integer.toString(getDataDao.getCommentLike(commentId)));
        if (getDataDao.isCommentLike(uuid,commentId) == 0){
            comment.setComment_liked(true);
        }else {
            comment.setComment_liked(false);
        }
        ArrayList<Reply> replie = getDataDao.getReply(commentId);
        for (int i = 0;i < replie.size();i ++){
            replie.get(i).setReply_like(Integer.toString(getDataDao.getReplyLike(replie.get(i).getReply_id())));
            if (getDataDao.isReplyLike(uuid,replie.get(i).getReply_id()) == 0){
                replie.get(i).setReply_liked(true);
            }else {
                replie.get(i).setReply_liked(false);
            }
        }
        CommentDetail commentDetail = new CommentDetail();
        commentDetail.setComment(comment);
        commentDetail.setReplies(replie);
        return commentDetail;
    }

    @Override
    public ClassDetail getClassDetail(String uuid, String classId) {
        TeaClasss teaClasss = getDataDao.getClassDetail(classId);
        if (getDataDao.isUserBuy(classId,uuid) == 0){
            teaClasss.setIsbuy(true);
            teaClasss.setSelectprice("0.00");
        }else {
            teaClasss.setIsbuy(false);
        }

        if (getDataDao.isFav(uuid,classId) == 0){
            teaClasss.setIsfav(true);
        }else {
            teaClasss.setIsfav(false);
        }
        ArrayList<ClassDetailList> classDetailList = getDataDao.getClassList(classId);
        ClassDetail classDetail = new ClassDetail();
        classDetail.setTeaClasss(teaClasss);
        classDetail.setClassDetailLists(classDetailList);
        return classDetail;
    }

    @Override
    public VideoDetail getVideoDetail(String uuid, String classId, String classdId) {
        ClassDetailList classDetailList = getDataDao.getClassVideo(classId,classdId);
        classDetailList.setClassd_like(Integer.toString(getDataDao.getFavCount(classId)));
        classDetailList.setClassd_view(Integer.toString(getDataDao.getClassdView(classdId)));

        ArrayList<String> classdIds = getDataDao.getClassdIds(classId);

        ArrayList<Comment> comments = getDataDao.getComment(classdId);

        for (int i = 0;i < comments.size();i ++){
            comments.get(i).setComment_q_like(Integer.toString(getDataDao.getCommentLike(comments.get(i).getComment_q_id())));
            if (getDataDao.isCommentLike(uuid,comments.get(i).getComment_q_id()) == 0){
                comments.get(i).setComment_liked(true);
            }else {
                comments.get(i).setComment_liked(false);
            }
            ArrayList<Reply> replies = getDataDao.getReply(comments.get(i).getComment_q_id());
            for (int j = 0;j < replies.size();j ++){
                replies.get(j).setReply_like(Integer.toString(getDataDao.getReplyLike(replies.get(j).getReply_id())));
                if (getDataDao.isReplyLike(uuid,replies.get(j).getReply_id()) == 0){
                    replies.get(j).setReply_liked(true);
                }else {
                    replies.get(j).setReply_liked(false);
                }
            }
            comments.get(i).setReplies(replies);
        }

        VideoDetail videoDetail = new VideoDetail();
        videoDetail.setClassDetailList(classDetailList);
        videoDetail.setComments(comments);
        if (getDataDao.isUserBuy(classId,uuid) == 0){
            videoDetail.setBuy(true);
        }else {
            videoDetail.setBuy(false);
        }

        if (getDataDao.getClassPrice(classId).equals("0.00")){
            videoDetail.setFree(true);
        }else {
            videoDetail.setFree(false);
        }

        videoDetail.setClassd_id(classdIds);


        return videoDetail;
    }

    @Override
    public TeacherClass getTeaClass(String uuid, String teaId) {
        ArrayList<TeaClasss> teaClass = getDataDao.getClassByTea(teaId);
        for (int i = 0;i < teaClass.size();i ++){
            teaClass.get(i).setSelectstucount(Integer.toString(getDataDao.getClassView(teaClass.get(i).getSelectId())));
            if (getDataDao.isUserBuy(teaClass.get(i).getSelectId(),uuid) == 0){
                teaClass.get(i).setIsbuy(true);
            }else {
                teaClass.get(i).setIsbuy(false);
            }
        }
        User teachers = getDataDao.getTeaInfo(teaId);
        TeacherClass teacherClass = new TeacherClass();
        teacherClass.setTeacher(teachers);
        teacherClass.setTeaClassses(teaClass);
        return teacherClass;
    }

    @Override
    public TypeClass getClassByType(String uuid, String type) {
        ArrayList<TeaClasss> teaClass = getDataDao.getClassByType(type);
        for (int i = 0;i < teaClass.size();i ++){
            teaClass.get(i).setSelectstucount(Integer.toString(getDataDao.getClassView(teaClass.get(i).getSelectId())));
            if (getDataDao.isUserBuy(teaClass.get(i).getSelectId(),uuid) == 0){
                teaClass.get(i).setIsbuy(true);
            }else {
                teaClass.get(i).setIsbuy(false);
            }
        }
        String count = Integer.toString(getDataDao.getTypeClassCount(type));

        TypeClass typeClass = new TypeClass();
        typeClass.setTypeClassCount(count);
        typeClass.setTypeClass(teaClass);
        return typeClass;
    }

    @Override
    public TeaClasss getClassInfo(String classId) {

        return getDataDao.getClassInfo(classId);
    }

}
