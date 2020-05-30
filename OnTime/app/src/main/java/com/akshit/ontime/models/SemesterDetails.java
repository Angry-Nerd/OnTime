package com.akshit.ontime.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.akshit.ontime.room.converters.Converter;

import lombok.ToString;


@Entity(tableName = "semester_details")
@TypeConverters({Converter.class})
@ToString
public class SemesterDetails implements Parcelable {
    @Ignore
    public static final Creator<SemesterDetails> CREATOR = new Creator<SemesterDetails>() {
        @Override
        public SemesterDetails createFromParcel(Parcel in) {
            return new SemesterDetails(in);
        }

        @Override
        public SemesterDetails[] newArray(int size) {
            return new SemesterDetails[size];
        }
    };
    /**
     * The semester number.
     */
    @PrimaryKey
    @ColumnInfo(name = "semester_number")
    private int semesterNumber;

    /**
     * Url for the logo of the image for semester tile..
     */
    @ColumnInfo(name = "logo_url")
    private String logoUrl;

    protected SemesterDetails(Parcel in) {
        semesterNumber = in.readInt();
        logoUrl = in.readString();
    }

    public SemesterDetails() {

    }

    public int getSemesterNumber() {
        return semesterNumber;
    }

    public void setSemesterNumber(int semesterNumber) {
        this.semesterNumber = semesterNumber;
    }


    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @Override
    @Ignore
    public int describeContents() {
        return 0;
    }

    @Override
    @Ignore
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(semesterNumber);
        dest.writeString(logoUrl);
    }
}
