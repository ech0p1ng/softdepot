package ru.softdepot.core.models;

public class Tag {
    private int id;
    private String name;
    private float degreeOfBelonging;
    private int programId;

    public Tag(int id, String name, float degreeOfBelonging, int programId) {
        this.id = id;
        this.name = name;
        this.degreeOfBelonging = degreeOfBelonging;
        this.programId = programId;
    }

    public Tag(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDegreeOfBelonging() {
        return degreeOfBelonging;
    }

    public void setDegreeOfBelonging(float degreeOfBelonging) {
        this.degreeOfBelonging = degreeOfBelonging;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }
}
