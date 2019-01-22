package dto;

import entity.TeaClasss;

import java.util.ArrayList;

public class BagPageData {
    private StudyLine studyLine;

    private ArrayList<TeaClasss> teaClassses;

    private ArrayList<TeaClasss> fineClasses;

    public ArrayList<TeaClasss> getTeaClassses() {
        return teaClassses;
    }

    public void setTeaClassses(ArrayList<TeaClasss> teaClassses) {
        this.teaClassses = teaClassses;
    }

    public ArrayList<TeaClasss> getFineClasses() {
        return fineClasses;
    }

    public void setFineClasses(ArrayList<TeaClasss> fineClasses) {
        this.fineClasses = fineClasses;
    }

    public StudyLine getStudyLine() {
        return studyLine;
    }

    public void setStudyLine(StudyLine studyLine) {
        this.studyLine = studyLine;
    }

}
