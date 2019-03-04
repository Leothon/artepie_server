package dto;

import entity.SelectClass;

import java.util.ArrayList;

public class TypeClass {
    private String typeClassCount;
    private ArrayList<SelectClass> typeClass;

    public ArrayList<SelectClass> getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(ArrayList<SelectClass> typeClass) {
        this.typeClass = typeClass;
    }

    public String getTypeClassCount() {
        return typeClassCount;
    }

    public void setTypeClassCount(String typeClassCount) {
        this.typeClassCount = typeClassCount;
    }


}
