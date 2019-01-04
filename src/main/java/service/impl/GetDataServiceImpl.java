package service.impl;

import dao.GetDataDao;
import dto.HomeData;
import entity.Banner;
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
    public ArrayList<TeaClasss> getMoreClass() {
        return getDataDao.getMoreClass();
    }

}