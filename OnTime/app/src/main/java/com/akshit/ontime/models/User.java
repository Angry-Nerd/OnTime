package com.akshit.ontime.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Parcelable {
    protected String email;
    protected String userName;
    protected String displayName;
    protected String department;
    protected int age;
    protected int level;
    protected long phoneNumber;
    protected String photoUrl;
    protected long createdOn;
    protected long modifiedOn;
    protected long dateOfJoining;
    protected int troubleTickets;
    protected List<String> assignedToMe;
    protected List<String> assignedByMe;
    private String group;
    private String college;
    private List<String> permissions;
    private String token;
    private List<String> subscribedTopics;
    private String stream;
//    private int applicationStatus;
    private Map<String, Object> otherProperties;


    protected User(Parcel in) {
        email = in.readString();
        userName = in.readString();
        displayName = in.readString();
        department = in.readString();
        age = in.readInt();
        level = in.readInt();
        phoneNumber = in.readLong();
        photoUrl = in.readString();
        createdOn = in.readLong();
        modifiedOn = in.readLong();
        dateOfJoining = in.readLong();
        troubleTickets = in.readInt();
        assignedToMe = in.createStringArrayList();
        assignedByMe = in.createStringArrayList();
        group = in.readString();
        college = in.readString();
        permissions = in.createStringArrayList();
        token = in.readString();
        subscribedTopics = in.createStringArrayList();
        stream = in.readString();
//        applicationStatus = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(userName);
        dest.writeString(displayName);
        dest.writeString(department);
        dest.writeInt(age);
        dest.writeInt(level);
        dest.writeLong(phoneNumber);
        dest.writeString(photoUrl);
        dest.writeLong(createdOn);
        dest.writeLong(modifiedOn);
        dest.writeLong(dateOfJoining);
        dest.writeInt(troubleTickets);
        dest.writeStringList(assignedToMe);
        dest.writeStringList(assignedByMe);
        dest.writeString(group);
        dest.writeString(college);
        dest.writeStringList(permissions);
        dest.writeString(token);
        dest.writeStringList(subscribedTopics);
        dest.writeString(stream);
//        dest.writeInt(applicationStatus);
    }
}
