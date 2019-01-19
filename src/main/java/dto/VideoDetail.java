package dto;

import entity.ClassDetailList;
import entity.Comment;


import java.util.ArrayList;

public class VideoDetail {

    private ClassDetailList classDetailList;
    private ArrayList<String> classd_id;
    private boolean buy;

    private boolean free;

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    private ArrayList<Comment> comments;

    public ClassDetailList getClassDetailList() {
        return classDetailList;
    }

    public void setClassDetailList(ClassDetailList classDetailList) {
        this.classDetailList = classDetailList;
    }

    public ArrayList<String> getClassd_id() {
        return classd_id;
    }

    public void setClassd_id(ArrayList<String> classd_id) {
        this.classd_id = classd_id;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
