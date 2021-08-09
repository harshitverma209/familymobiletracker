package com.hyg.awesome.familymobiletracker.feature.Models;

public class MemberModel {
    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getlUpdate() {
        return lUpdate;
    }

    public void setlUpdate(String lUpdate) {
        this.lUpdate = lUpdate;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    String memberName,lUpdate;
    int photo;
}
