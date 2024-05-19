package ru.softdepot.core.models;

import java.time.OffsetDateTime;

public class Purchase {
    private int id;
    private OffsetDateTime dateTime;
    private int customerId;
    private int programId;

    public Purchase(int id, OffsetDateTime purchaseDate, int customer, int program) {
        this.id = id;
        this.dateTime = purchaseDate;
        this.customerId = customer;
        this.programId = program;
    }

    public Purchase() {

    }

    public Purchase(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(OffsetDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }
}
