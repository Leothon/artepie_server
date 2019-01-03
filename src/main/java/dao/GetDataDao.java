package dao;

import entity.Banner;
import entity.TeaClasss;

import java.util.ArrayList;

public interface GetDataDao {

    ArrayList<Banner> getBanners();
    ArrayList<TeaClasss> getClasses();
    ArrayList<TeaClasss> getMoreClass();
}
