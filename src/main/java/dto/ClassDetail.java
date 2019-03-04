package dto;

import entity.ClassDetailList;
import entity.SelectClass;

import java.util.ArrayList;

public class ClassDetail {
    private SelectClass selectClass;
    private ArrayList<ClassDetailList> classDetailLists;

    public ArrayList<ClassDetailList> getClassDetailLists() {
        return classDetailLists;
    }

    public void setClassDetailLists(ArrayList<ClassDetailList> classDetailLists) {
        this.classDetailLists = classDetailLists;
    }

    public SelectClass getSelectClass() {
        return selectClass;
    }

    public void setSelectClass(SelectClass selectClass) {
        this.selectClass = selectClass;
    }
}
