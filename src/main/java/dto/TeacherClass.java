package dto;

import entity.SelectClass;
import entity.User;

import java.util.ArrayList;

public class TeacherClass {
    private User teacher;

    private ArrayList<SelectClass> selectClasses;

    public ArrayList<SelectClass> getSelectClasses() {
        return selectClasses;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setSelectClasses(ArrayList<SelectClass> selectClasses) {
        this.selectClasses = selectClasses;
    }
}
