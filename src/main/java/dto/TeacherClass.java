package dto;

import entity.TeaClasss;
import entity.User;

import java.util.ArrayList;

public class TeacherClass {
    private User teacher;

    private ArrayList<TeaClasss> teaClassses;

    public ArrayList<TeaClasss> getTeaClassses() {
        return teaClassses;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeaClassses(ArrayList<TeaClasss> teaClassses) {
        this.teaClassses = teaClassses;
    }
}
