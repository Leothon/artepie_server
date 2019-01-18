package dto;

import entity.ClassDetailList;
import entity.TeaClasss;

import java.util.ArrayList;

public class ClassDetail {
    private TeaClasss teaClasss;
    private ArrayList<ClassDetailList> classDetailLists;

    public ArrayList<ClassDetailList> getClassDetailLists() {
        return classDetailLists;
    }

    public void setClassDetailLists(ArrayList<ClassDetailList> classDetailLists) {
        this.classDetailLists = classDetailLists;
    }

    public TeaClasss getTeaClasss() {
        return teaClasss;
    }

    public void setTeaClasss(TeaClasss teaClasss) {
        this.teaClasss = teaClasss;
    }
}
