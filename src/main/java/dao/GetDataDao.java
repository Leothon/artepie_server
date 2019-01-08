package dao;

import dto.QAData;
import entity.Banner;
import entity.TeaClasss;

import java.util.ArrayList;

public interface GetDataDao {

    ArrayList<Banner> getBanners();
    ArrayList<TeaClasss> getClasses();
    ArrayList<TeaClasss> getMoreClass(int currentPage);
    ArrayList<QAData> getQAData();

    ArrayList<QAData> getMoreQAData(int currentPage);
}
