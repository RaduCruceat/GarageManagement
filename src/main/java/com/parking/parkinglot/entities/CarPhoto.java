package com.parking.parkinglot.entities;

import jakarta.persistence.*;

@Entity
public class CarPhoto {
    @Id
    @GeneratedValue
    Long id;
    String filename;
    String fileType;
    byte[] fileContent;
    @OneToOne
    Car car;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileType() {
        return fileType;
    }
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getFileContent() {
        return fileContent;
    }
    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public Car getCar() {
        return car;
    }
    public void setCar(Car car) {
        this.car = car;
    }
}
