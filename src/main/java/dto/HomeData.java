package dto;

import entity.Banner;
import entity.TeaClasss;
import entity.Teacher;
import entity.User;

import java.util.ArrayList;

public class HomeData {

    public ArrayList<Banner> banners;
    public ArrayList<User> teachers;
    public ArrayList<TeaClasss> teaClasses;

    public ArrayList<Banner> getBanners() {
        return banners;
    }

    public void setBanners(ArrayList<Banner> banners) {
        this.banners = banners;
    }

    public ArrayList<TeaClasss> getTeaClasses() {
        return teaClasses;
    }

    public void setTeaClasses(ArrayList<TeaClasss> teaClasses) {
        this.teaClasses = teaClasses;
    }

    public ArrayList<User> getTeachers() {
        return teachers;
    }

    public void setTeachers(ArrayList<User> teachers) {
        this.teachers = teachers;
    }

}
