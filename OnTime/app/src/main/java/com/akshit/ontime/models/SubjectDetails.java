package com.akshit.ontime.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import lombok.ToString;

@Entity(tableName = "subject_details", primaryKeys = {"semester_name", "name_of_subject"})
@ToString
public class SubjectDetails {

    @ColumnInfo(name = "semester_name")
    @NonNull
    private String semesterName;

    @ColumnInfo(name = "name_of_subject")
    @NonNull
    private String nameOfSubject;

    @ColumnInfo(name = "subject_logo_url")
    private String logoUrl;

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public String getNameOfSubject() {
        return nameOfSubject;
    }

    public void setNameOfSubject(String nameOfSubject) {
        this.nameOfSubject = nameOfSubject;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
