package dto;

import entity.TeaClasss;

import java.util.ArrayList;

public class TypeClass {
    private String typeClassCount;
    private ArrayList<TeaClasss> typeClass;

    public ArrayList<TeaClasss> getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(ArrayList<TeaClasss> typeClass) {
        this.typeClass = typeClass;
    }

    public String getTypeClassCount() {
        return typeClassCount;
    }

    public void setTypeClassCount(String typeClassCount) {
        this.typeClassCount = typeClassCount;
    }


}
