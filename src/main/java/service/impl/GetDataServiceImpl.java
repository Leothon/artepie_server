package service.impl;

import com.vdurmont.emoji.EmojiParser;
import dao.GetDataDao;
import dao.UserDao;
import dto.*;
import entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.GetDataService;
import utils.commonUtils;

import java.util.ArrayList;
@Service
public class GetDataServiceImpl implements GetDataService {

    @Autowired
    GetDataDao getDataDao;

//    @Autowired
//    UserDao userDao;

    @Override
    public HomeData getHomeData(String uuid) {
        ArrayList<Banner> banners = getDataDao.getBanners();
        ArrayList<SelectClass> selectClasses = getDataDao.getClasses();

        for (int i = 0; i < selectClasses.size(); i ++){
            selectClasses.get(i).setSelectstucount(Integer.toString(getDataDao.getClassView(selectClasses.get(i).getSelectId())));
            if (getDataDao.isUserBuy(selectClasses.get(i).getSelectId(),uuid) == 0){
                selectClasses.get(i).setIsbuy(true);
            }else {
                selectClasses.get(i).setIsbuy(false);
            }
        }
        ArrayList<User> teachers = getDataDao.getTeacherInUser();
        HomeData homeData = new HomeData();
        homeData.setBanners(banners);
        homeData.setTeaClasses(selectClasses);
        homeData.setTeachers(teachers);
        return homeData;
    }

    @Override
    public ArrayList<SelectClass> getMoreClass(int currentPage, String uuid) {
        ArrayList<SelectClass> selectClasses = getDataDao.getMoreClass(currentPage);
        for (int i = 0; i < selectClasses.size(); i ++){
            selectClasses.get(i).setSelectstucount(Integer.toString(getDataDao.getClassView(selectClasses.get(i).getSelectId())));
            if (getDataDao.isUserBuy(selectClasses.get(i).getSelectId(),uuid) == 0){
                selectClasses.get(i).setIsbuy(true);
            }else {
                selectClasses.get(i).setIsbuy(false);
            }
        }
        return selectClasses;
    }

    @Override
    public ArrayList<QAData> getQAData(String uuid) {

        ArrayList<QAData> qaData = getDataDao.getQAData();

//        QAData qaDataTop = getDataDao.getTopQA("qa1000000500878171");
//        qaDataTop.setQa_like(Integer.toString(getDataDao.getQALike(qaDataTop.getQa_id())));
//        qaDataTop.setQa_comment(Integer.toString(getDataDao.getQAComment(qaDataTop.getQa_id())));
//        if (getDataDao.isLike(uuid,qaDataTop.getQa_id()) == 0){
//            qaDataTop.setLiked(true);
//        }else {
//            qaDataTop.setLiked(false);
//        }
        for (int i = 0;i < qaData.size();i ++){

            qaData.get(i).setQa_content(EmojiParser.parseToUnicode(qaData.get(i).getQa_content()));
            String reId = qaData.get(i).getQa_re_id();

            if (reId != null){
                QAData qaDataSingle = getDataDao.getQADetail(reId);
                if (qaDataSingle != null){
                    qaDataSingle.setQa_like(Integer.toString(getDataDao.getQALike(qaDataSingle.getQa_id())));
                    qaDataSingle.setQa_comment(Integer.toString(getDataDao.getQAComment(qaDataSingle.getQa_id())));
                }else {
                    qaDataSingle = new QAData();
                    qaDataSingle.setQa_id(reId);
                    qaDataSingle.setQa_like("0");
                    qaDataSingle.setQa_comment("0");
                    qaDataSingle.setUser_name("");
                    qaDataSingle.setUser_name("已删除");
                    qaDataSingle.setQa_content("该内容已被作者删除");
                }
                qaDataSingle.setQa_content(EmojiParser.parseToUnicode(qaDataSingle.getQa_content()));
                qaData.get(i).setQaData(qaDataSingle);


            }


            qaData.get(i).setQa_like(Integer.toString(getDataDao.getQALike(qaData.get(i).getQa_id())));
            qaData.get(i).setQa_comment(Integer.toString(getDataDao.getQAComment(qaData.get(i).getQa_id())));
            if (getDataDao.isLike(uuid,qaData.get(i).getQa_id()) == 0){
                qaData.get(i).setLiked(true);
            }else {
                qaData.get(i).setLiked(false);
            }

        }
        //qaData.add(0,qaDataTop);
        return qaData;
    }

    @Override
    public ArrayList<QAData> getMoreQAData(int currentPage,String userId) {

        ArrayList<QAData> qaMoreData = getDataDao.getMoreQAData(currentPage);
        for (int i = 0;i < qaMoreData.size();i ++){
            qaMoreData.get(i).setQa_content(EmojiParser.parseToUnicode(qaMoreData.get(i).getQa_content()));
            String reId = qaMoreData.get(i).getQa_re_id();


            if (reId != null){
                QAData qaDataSingle = getDataDao.getQADetail(reId);
                if (qaDataSingle != null){
                    qaDataSingle.setQa_like(Integer.toString(getDataDao.getQALike(qaDataSingle.getQa_id())));
                    qaDataSingle.setQa_comment(Integer.toString(getDataDao.getQAComment(qaDataSingle.getQa_id())));
                }else {
                    qaDataSingle = new QAData();
                    qaDataSingle.setQa_id(reId);
                    qaDataSingle.setQa_like("0");
                    qaDataSingle.setQa_comment("0");
                    qaDataSingle.setUser_name("");
                    qaDataSingle.setUser_name("已删除");
                    qaDataSingle.setQa_content("该内容已被作者删除");
                }
                qaDataSingle.setQa_content(EmojiParser.parseToUnicode(qaDataSingle.getQa_content()));
                qaMoreData.get(i).setQaData(qaDataSingle);
            }

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
    public ArrayList<QAData> getQADataById(String uuid) {

        ArrayList<QAData> qaData = getDataDao.getQADataById(uuid);
        for (int i = 0;i < qaData.size();i ++){
            qaData.get(i).setQa_content(EmojiParser.parseToUnicode(qaData.get(i).getQa_content()));
            String reId = qaData.get(i).getQa_re_id();

            if (reId != null){
                QAData qaDataSingle = getDataDao.getQADetail(reId);
                if (qaDataSingle != null){
                    qaDataSingle.setQa_like(Integer.toString(getDataDao.getQALike(qaDataSingle.getQa_id())));
                    qaDataSingle.setQa_comment(Integer.toString(getDataDao.getQAComment(qaDataSingle.getQa_id())));
                }else {
                    qaDataSingle = new QAData();
                    qaDataSingle.setQa_id(reId);
                    qaDataSingle.setQa_like("0");
                    qaDataSingle.setQa_comment("0");
                    qaDataSingle.setUser_name("");
                    qaDataSingle.setUser_name("已删除");
                    qaDataSingle.setQa_content("该内容已被作者删除");
                }
                qaDataSingle.setQa_content(EmojiParser.parseToUnicode(qaDataSingle.getQa_content()));
                qaData.get(i).setQaData(qaDataSingle);


            }

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
    public ArrayList<QAData> getMoreQADataById(int currentPage,String userId) {

        ArrayList<QAData> qaMoreData = getDataDao.getMoreQADataById(currentPage,userId);

        for (int i = 0;i < qaMoreData.size();i ++){
            qaMoreData.get(i).setQa_content(EmojiParser.parseToUnicode(qaMoreData.get(i).getQa_content()));
            String reId = qaMoreData.get(i).getQa_re_id();

            if (reId != null){
                QAData qaDataSingle = getDataDao.getQADetail(reId);
                if (qaDataSingle != null){
                    qaDataSingle.setQa_like(Integer.toString(getDataDao.getQALike(qaDataSingle.getQa_id())));
                    qaDataSingle.setQa_comment(Integer.toString(getDataDao.getQAComment(qaDataSingle.getQa_id())));
                }else {
                    qaDataSingle = new QAData();
                    qaDataSingle.setQa_id(reId);
                    qaDataSingle.setQa_like("0");
                    qaDataSingle.setQa_comment("0");
                    qaDataSingle.setUser_name("");
                    qaDataSingle.setUser_name("已删除");
                    qaDataSingle.setQa_content("该内容已被作者删除");
                }
                qaDataSingle.setQa_content(EmojiParser.parseToUnicode(qaDataSingle.getQa_content()));
                qaMoreData.get(i).setQaData(qaDataSingle);


            }
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
        String reId = qaData.getQa_re_id();
        qaData.setQa_content(EmojiParser.parseToUnicode(qaData.getQa_content()));

        if (reId != null){
            QAData qaDataSingle = getDataDao.getQADetail(reId);
            if (qaDataSingle != null){
                qaDataSingle.setQa_like(Integer.toString(getDataDao.getQALike(qaDataSingle.getQa_id())));
                qaDataSingle.setQa_comment(Integer.toString(getDataDao.getQAComment(qaDataSingle.getQa_id())));
            }else {
                qaDataSingle = new QAData();
                qaDataSingle.setQa_id(reId);
                qaDataSingle.setQa_like("0");
                qaDataSingle.setQa_comment("0");
                qaDataSingle.setUser_name("已删除");
                qaDataSingle.setQa_content("该内容已被作者删除");
            }
            qaDataSingle.setQa_content(EmojiParser.parseToUnicode(qaDataSingle.getQa_content()));
            qaData.setQaData(qaDataSingle);
        }

        qaData.setQa_like(Integer.toString(getDataDao.getQALike(qaId)));
        qaData.setQa_comment(Integer.toString(getDataDao.getQAComment(qaId)));
        ArrayList<Comment> comments = getDataDao.getComment(qaId);
        if (getDataDao.isLike(uuid,qaId) == 0){
            qaData.setLiked(true);
        }else {
            qaData.setLiked(false);
        }
        for (int i = 0;i < comments.size();i ++){

            comments.get(i).setComment_q_content(EmojiParser.parseToUnicode(comments.get(i).getComment_q_content()));
            comments.get(i).setComment_q_like(Integer.toString(getDataDao.getCommentLike(comments.get(i).getComment_q_id())));
            if (getDataDao.isCommentLike(uuid,comments.get(i).getComment_q_id()) == 0){
                comments.get(i).setComment_liked(true);
            }else {
                comments.get(i).setComment_liked(false);
            }

            if (Integer.parseInt(comments.get(i).getComment_q_like()) >= 5){
                if (Integer.parseInt(comments.get(i).getComment_q_like()) > Integer.parseInt(comments.get(0).getComment_q_like())){
                    Comment temp  = comments.get(i);
                    comments.remove(i);
                    comments.add(0,temp);
                }
            }
            ArrayList<Reply> replies = getDataDao.getReply(comments.get(i).getComment_q_id());
            for (int j = 0;j < replies.size();j ++){
                replies.get(j).setReply_comment(EmojiParser.parseToUnicode(replies.get(j).getReply_comment()));
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
    public QAData getQA(String qaId,String uuid) {

        QAData qaData = getDataDao.getQADetail(qaId);
        qaData.setQa_content(EmojiParser.parseToUnicode(qaData.getQa_content()));
        String reId = qaData.getQa_re_id();



        if (reId != null){
            QAData qaDataSingle = getDataDao.getQADetail(reId);

            if (qaDataSingle != null){
                qaDataSingle.setQa_like(Integer.toString(getDataDao.getQALike(qaDataSingle.getQa_id())));
                qaDataSingle.setQa_comment(Integer.toString(getDataDao.getQAComment(qaDataSingle.getQa_id())));
            }else {
                qaDataSingle = new QAData();
                qaDataSingle.setQa_id(reId);
                qaDataSingle.setQa_like("0");
                qaDataSingle.setQa_comment("0");
                qaDataSingle.setUser_name("");
                qaDataSingle.setUser_name("已删除");
                qaDataSingle.setQa_content("该内容已被作者删除");
            }
            qaDataSingle.setQa_content(EmojiParser.parseToUnicode(qaDataSingle.getQa_content()));

            qaData.setQaData(qaDataSingle);
        }

        qaData.setQa_like(Integer.toString(getDataDao.getQALike(qaId)));
        qaData.setQa_comment(Integer.toString(getDataDao.getQAComment(qaId)));

        return qaData;
    }

    @Override
    public CommentDetail getCommentDetail(String commentId,String uuid) {

        Comment comment = getDataDao.getSingleComment(commentId);
        comment.setComment_q_content(EmojiParser.parseToUnicode(comment.getComment_q_content()));
        comment.setComment_q_like(Integer.toString(getDataDao.getCommentLike(commentId)));
        if (getDataDao.isCommentLike(uuid,commentId) == 0){
            comment.setComment_liked(true);
        }else {
            comment.setComment_liked(false);
        }
        ArrayList<Reply> replie = getDataDao.getReply(commentId);
        for (int i = 0;i < replie.size();i ++){
            replie.get(i).setReply_comment(EmojiParser.parseToUnicode(replie.get(i).getReply_comment()));
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
        SelectClass selectClass = getDataDao.getClassDetail(classId);
        if (getDataDao.isUserBuy(classId,uuid) == 0){
            selectClass.setIsbuy(true);
            selectClass.setSelectprice("0.00");
        }else {
            selectClass.setIsbuy(false);
        }

        if (getDataDao.isFav(uuid,classId) == 0){
            selectClass.setIsfav(true);
        }else {
            selectClass.setIsfav(false);
        }
        ArrayList<ClassDetailList> classDetailList = getDataDao.getClassList(classId);
        ClassDetail classDetail = new ClassDetail();
        classDetail.setTeaClasss(selectClass);
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
            comments.get(i).setComment_q_content(comments.get(i).getComment_q_content());
            comments.get(i).setComment_q_like(Integer.toString(getDataDao.getCommentLike(comments.get(i).getComment_q_id())));
            if (getDataDao.isCommentLike(uuid,comments.get(i).getComment_q_id()) == 0){
                comments.get(i).setComment_liked(true);
            }else {
                comments.get(i).setComment_liked(false);
            }
            ArrayList<Reply> replies = getDataDao.getReply(comments.get(i).getComment_q_id());
            for (int j = 0;j < replies.size();j ++){
                replies.get(j).setReply_comment(replies.get(j).getReply_comment());
                replies.get(j).setReply_like(Integer.toString(getDataDao.getReplyLike(replies.get(j).getReply_id())));
                if (getDataDao.isReplyLike(uuid,replies.get(j).getReply_id()) == 0){
                    replies.get(j).setReply_liked(true);
                }else {
                    replies.get(j).setReply_liked(false);
                }
            }
            comments.get(i).setReplies(replies);
        }

        String authorId = getDataDao.getUserIdByClassId(classDetailList.getClass_classd_id());
        User user = getDataDao.getUserInfoInGet(authorId);
        VideoDetail videoDetail = new VideoDetail();
        videoDetail.setClassDetailList(classDetailList);
        videoDetail.setComments(comments);
        videoDetail.setAuthorIcon(user.getUser_icon());
        videoDetail.setAuthorId(authorId);
        videoDetail.setAuthorName(user.getUser_name());
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
        ArrayList<SelectClass> teaClass = getDataDao.getClassByTea(teaId);
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
        ArrayList<SelectClass> teaClass = getDataDao.getClassByType(type);

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
    public SelectClass getClassInfo(String classId) {

        return getDataDao.getClassInfo(classId);
    }

    @Override
    public String isQQOrWeChatRegister(String accessToken) {

        return Integer.toString(getDataDao.isQQOrWeChatRegister(accessToken));
    }

    @Override
    public ArrayList<SelectClass> getFavClassByUid(String uuid) {

        return getDataDao.getFavClassByUid(uuid);
    }

    @Override
    public ArrayList<ClassDetailList> getClassViewById(String uuid) {

        return getDataDao.getViewHisById(uuid);
    }

    @Override
    public BagPageData getBagPageData(String uuid) {

        ArrayList<ClassDView> classDViews = getDataDao.getStudyEveryday(uuid, commonUtils.getTime());
        StudyLine studyLine = new StudyLine();
        int[] days = {0,0,0,0,0,0,0};
        ArrayList<String> day = new ArrayList<>();
        for (int i = 0;i < classDViews.size();i ++){

            switch (commonUtils.getTimeRange(commonUtils.getTime(),classDViews.get(i).getClassd_view_time())){
                case "0":
                    days[6] ++;
                    break;
                case "1":
                    days[5] ++;
                    break;
                case "2":
                    days[4] ++;
                    break;
                case "3":
                    days[3] ++;
                    break;
                case "4":
                    days[2] ++;
                    break;
                case "5":
                    days[1] ++;
                    break;
                case "6":
                    days[0] ++;
                    break;
                default:
                    break;
            }
        }
        studyLine.setClassCount(classDViews.size());

        for (int j = 0;j < days.length;j ++){
            day.add(Integer.toString(days[j]));
        }

        studyLine.setClassCountDat(day);
        ArrayList<SelectClass> buyClasses = getDataDao.getBuyClassByid(uuid);
        for (int i = 0;i < buyClasses.size();i ++){
            buyClasses.get(i).setSelectstucount(Integer.toString(getDataDao.getClassView(buyClasses.get(i).getSelectId())));
            if (getDataDao.isUserBuy(buyClasses.get(i).getSelectId(),uuid) == 0){
                buyClasses.get(i).setIsbuy(true);
            }else {
                buyClasses.get(i).setIsbuy(false);
            }
        }
        ArrayList<SelectClass> fineClasses = getDataDao.getClasses();

        for (int i = 0;i < fineClasses.size();i ++){
            fineClasses.get(i).setSelectstucount(Integer.toString(getDataDao.getClassView(fineClasses.get(i).getSelectId())));
            if (getDataDao.isUserBuy(fineClasses.get(i).getSelectId(),uuid) == 0){
                fineClasses.get(i).setIsbuy(true);
            }else {
                fineClasses.get(i).setIsbuy(false);
            }
        }

        BagPageData bagPageData = new BagPageData();
        bagPageData.setStudyLine(studyLine);
        bagPageData.setSelectClasses(buyClasses);
        bagPageData.setFineClasses(fineClasses);

        return bagPageData;
    }

    @Override
    public ArticleData getArticleData(String uuid) {

        ArrayList<Banner> banners = getDataDao.getArticleBanner();
        ArrayList<Article> articles = getDataDao.getArticleList();

        for (int i = 0;i < articles.size();i++){
            articles.get(i).setArticleVisionCount(String.valueOf(getDataDao.getArticleVisionCount(articles.get(i).getArticleId())));
            articles.get(i).setLikeCount(String.valueOf(getDataDao.getArticleLike(articles.get(i).getArticleId())));
        }
        ArticleData articleData = new ArticleData();
        articleData.setArticles(articles);
        articleData.setBanners(banners);
        return articleData;
    }
    @Override
    public ArrayList<Article> getMoreArticleData(String uuid,int currentPage) {


        ArrayList<Article> articles = getDataDao.getMoreArticleList(currentPage);
        for (int i = 0;i < articles.size();i++){
            articles.get(i).setArticleVisionCount(String.valueOf(getDataDao.getArticleVisionCount(articles.get(i).getArticleId())));
            articles.get(i).setLikeCount(String.valueOf(getDataDao.getArticleLike(articles.get(i).getArticleId())));
        }
        return articles;
    }
    @Override
    public ArrayList<Article> getArticleDataById(String uuid) {


        ArrayList<Article> articles = getDataDao.getArticleListById(uuid);
        for (int i = 0;i < articles.size();i++){
            articles.get(i).setArticleVisionCount(String.valueOf(getDataDao.getArticleVisionCount(articles.get(i).getArticleId())));
            articles.get(i).setLikeCount(String.valueOf(getDataDao.getArticleLike(articles.get(i).getArticleId())));
        }
        return articles;
    }
    @Override
    public ArrayList<Article> getMoreArticleDataById(String uuid,int currentPage) {


        ArrayList<Article> articles = getDataDao.getMoreArticleListById(uuid,currentPage);
        for (int i = 0;i < articles.size();i++){
            articles.get(i).setArticleVisionCount(String.valueOf(getDataDao.getArticleVisionCount(articles.get(i).getArticleId())));
            articles.get(i).setLikeCount(String.valueOf(getDataDao.getArticleLike(articles.get(i).getArticleId())));
        }
        return articles;
    }

    @Override
    public Article getArticleDetail(String uuid, String articleId) {

        Article article = getDataDao.getArticleDetail(articleId);
        if (getDataDao.isArticleLike(uuid,articleId) == 0){
            article.setLike(true);
        }else {
            article.setLike(false);
        }
        article.setLikeCount(String.valueOf(getDataDao.getArticleLike(articleId)));
        article.setCommentCount(String.valueOf(getDataDao.getArticleCommentCount(articleId)));
        String viewId = "articleview" + commonUtils.createUUID();
        getDataDao.insertArticleView(viewId,articleId,uuid,commonUtils.getTime());
        return article;
    }

    @Override
    public int isHasNotice(String uuid) {

        return getDataDao.isHasNotice(uuid);
    }

    @Override
    public ArrayList<NoticeInfo> getNoticeInfo(String uuid) {
        ArrayList<NoticeInfo> noticeInfos = getDataDao.getNoticeInfo(uuid);
        for (int i = 0;i < noticeInfos.size();i ++){
            noticeInfos.get(i).setNoticeContent(EmojiParser.parseToUnicode(noticeInfos.get(i).getNoticeContent()));
            User user = getDataDao.getUserInfoInGet(noticeInfos.get(i).getNoticeFromUserId());
            if (user != null){
                noticeInfos.get(i).setUserName(user.getUser_name());
                noticeInfos.get(i).setUserIcon(user.getUser_icon());
            }

        }
        return noticeInfos;
    }

    @Override
    public ArrayList<AuthInfo> getAuthInfo(String uuid) {

        return getDataDao.getAuthInfo(uuid);
    }

    @Override
    public ArrayList<SelectClass> searchClassByKeyword(String keyword, String uuid) {

        ArrayList<SelectClass> selectClasses = getDataDao.getClassDataByKeyword(keyword);
        for (int i = 0; i < selectClasses.size(); i ++){
            selectClasses.get(i).setSelectstucount(Integer.toString(getDataDao.getClassView(selectClasses.get(i).getSelectId())));
            if (getDataDao.isUserBuy(selectClasses.get(i).getSelectId(),uuid) == 0){
                selectClasses.get(i).setIsbuy(true);
            }else {
                selectClasses.get(i).setIsbuy(false);
            }
        }

        return getDataDao.getClassDataByKeyword(keyword);
    }

    @Override
    public ArrayList<QAData> searchQAByKeyword(String keyword,String uuid) {

        ArrayList<QAData> qaData = getDataDao.getQADataByKeyword(keyword);
        for (int i = 0;i < qaData.size();i ++){

            qaData.get(i).setQa_content(EmojiParser.parseToUnicode(qaData.get(i).getQa_content()));
            String reId = qaData.get(i).getQa_re_id();

            if (reId != null){
                QAData qaDataSingle = getDataDao.getQADetail(reId);
                if (qaDataSingle != null){
                    qaDataSingle.setQa_like(Integer.toString(getDataDao.getQALike(qaDataSingle.getQa_id())));
                    qaDataSingle.setQa_comment(Integer.toString(getDataDao.getQAComment(qaDataSingle.getQa_id())));
                }else {
                    qaDataSingle = new QAData();
                    qaDataSingle.setQa_id(reId);
                    qaDataSingle.setQa_like("0");
                    qaDataSingle.setQa_comment("0");
                    qaDataSingle.setUser_name("");
                    qaDataSingle.setUser_name("已删除");
                    qaDataSingle.setQa_content("该内容已被作者删除");
                }
                qaDataSingle.setQa_content(EmojiParser.parseToUnicode(qaDataSingle.getQa_content()));
                qaData.get(i).setQaData(qaDataSingle);


            }


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
    public ArrayList<User> searchUserByKeyword(String keyword,String uuid) {
        return getDataDao.getUserByKeyword(keyword);
    }

    @Override
    public ArrayList<Article> searchArticleByKeyword(String keyword,String uuid) {
        return getDataDao.getArticleByKeyword(keyword);
    }

    @Override
    public ArrayList<SelectClass> getSelectClassByUserId(String userId) {
        ArrayList<SelectClass> selectClasses = getDataDao.getClassByTea(userId);
        for (int i = 0;i < selectClasses.size();i ++){
            selectClasses.get(i).setSelectstucount(Integer.toString(getDataDao.getClassView(selectClasses.get(i).getSelectId())));
            if (getDataDao.isUserBuy(selectClasses.get(i).getSelectId(),userId) == 0){
                selectClasses.get(i).setIsbuy(true);
            }else {
                selectClasses.get(i).setIsbuy(false);
            }
        }
        return selectClasses;
    }

    @Override
    public ArrayList<SelectClass> getLittleHome(String uuid) {
        ArrayList<SelectClass> selectClasses = getDataDao.getClasses();

        for (int i = 0; i < selectClasses.size(); i ++){
            selectClasses.get(i).setSelectstucount(Integer.toString(getDataDao.getClassView(selectClasses.get(i).getSelectId())));
            if (getDataDao.isUserBuy(selectClasses.get(i).getSelectId(),uuid) == 0){
                selectClasses.get(i).setIsbuy(true);
            }else {
                selectClasses.get(i).setIsbuy(false);
            }
        }

        return selectClasses;
    }

    @Override
    public Inform getInform(String uuid) {

        return getDataDao.getInform(uuid);
    }


    @Override
    public Update getUpdate(String uuid) {

        return getDataDao.getUpdate(uuid);
    }

    @Override
    public String getAuthImg(String userId) {
        return getDataDao.getAuthImg(userId);
    }

    @Override
    public void authUser(String userRole, String userId, int userType) {
        if (userType == 1){
            getDataDao.authUsero(userRole,userId);
        }else {
            getDataDao.authUserz(userRole,userId);
        }
        getDataDao.authUserto(userId);
    }

    @Override
    public ArrayList<ArticleComment> getArticleComment(String articleId) {
        return getDataDao.getArticleComment(articleId);
    }

    @Override
    public ArrayList<ArticleComment> getArticleCommentMore(String articleId, int currentPage) {
        return getDataDao.getArticleCommentMore(articleId,currentPage);
    }
}
