package service;

import dto.HomeData;
import dto.QAData;
import entity.TeaClasss;

import java.util.ArrayList;


public interface GetDataService {
    HomeData getHomeData();

    ArrayList<TeaClasss> getMoreClass(int currentPage);

    ArrayList<QAData> getQAData();

    ArrayList<QAData> getMoreQAData(int currentPage);
}
