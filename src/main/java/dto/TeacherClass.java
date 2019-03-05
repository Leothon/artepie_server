package dto;

import entity.SelectClass;
import entity.User;

import java.util.ArrayList;

public class TeacherClass {
    private User teacher;

    private ArrayList<SelectClass> teaClassses;

    public ArrayList<SelectClass> getTeaClassses() {
        return teaClassses;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeaClassses(ArrayList<SelectClass> selectClasses) {
        this.teaClassses = selectClasses;
    }
}
