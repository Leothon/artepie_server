package service;

import dto.HomeData;
import entity.TeaClasss;

import java.util.ArrayList;


public interface GetDataService {
    HomeData getHomeData();

    ArrayList<TeaClasss> getMoreClass();
}
