package dto;

import entity.ClassDetailList;
import entity.SelectClass;

import java.util.ArrayList;

public class ClassDetail {
    private SelectClass teaClasss;
    private ArrayList<ClassDetailList> classDetailLists;


    public ArrayList<ClassDetailList> getClassDetailLists() {
        return classDetailLists;
    }

    public void setClassDetailLists(ArrayList<ClassDetailList> classDetailLists) {
        this.classDetailLists = classDetailLists;
    }

    public SelectClass getTeaClasss() {
        return teaClasss;
    }

    public void setTeaClasss(SelectClass selectClass) {
        this.teaClasss = selectClass;
    }
}
