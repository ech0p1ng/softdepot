package ru.softdepot.core.models;

public class DegreeOfBelonging {
    private int id;
    private int programId;
    private int tagId;
    private float degreeOfBelongingValue;

    public DegreeOfBelonging(int id, int programId, int tagId, float degreeOfBelongingValue) {
        this.id = id;
        this.programId = programId;
        this.tagId = tagId;
        this.setDegreeOfBelongingValue(degreeOfBelongingValue);
    }

//    public DegreeOfBelonging(int programId, int tagId, float degreeOfBelongingValue) {
//        this.programId = programId;
//        this.tagId = tagId;
//        this.setDegreeOfBelongingValue(degreeOfBelongingValue);
//    }

    public DegreeOfBelonging(int id) {
        this.id = id;
    }

    public DegreeOfBelonging(){}

    public int getId() {
        return id;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public float getDegreeOfBelongingValue() {
        return degreeOfBelongingValue;
    }

    public void setDegreeOfBelongingValue(float degreeValue) {
        if (degreeValue >= 0 && degreeValue <= 10) {
            this.degreeOfBelongingValue = degreeValue;
        }
    }
}
