/**
 * @file   UserItem.java
 * @brief  User items.
 * @date   01.08.2018
 * @autor  M.Gusev
 */
package com.example.john.users;

import java.io.Serializable;


@SuppressWarnings("serial")
public class UserItem implements Serializable {
    private String nameFirst_;          /* First name*/
    private String nameLast_;           /* Last name */
    private String location_;           /* Location */
    private String email_;              /* E-mail */
    private String phone_;              /* Phone */
    private String picLargeUrl_;        /* URL large picture */
    private String picThumbnailUrl_;    /* URL thumbnail picture */
    private String nation_;             /* Nations */

    /** Set first name */
    public void setNameFirst(String nameFirst) {
        this.nameFirst_ = nameFirst;
    }

    /** Get first name */
    public String getNameFirst() {
        return this.nameFirst_;
    }

    /** Set last name */
    public void setNameLast(String nameLast) {
        this.nameLast_ = nameLast;
    }

    /** get last name */
    public String getNameLast() {
        return this.nameLast_;
    }

    /** Set location */
    public void setLocation(String location) {
        this.location_ = location;
    }

    /** Get location */
    public String getLocation() {
        return this.location_;
    }

    /** Set user E-mail */
    public void setEmail(String email) {
        this.email_ = email;
    }

    /** Get user E-mail */
    public String getEmail() {
        return this.email_;
    }

    /** Set user phone */
    public void setPhone(String phone) {
        this.phone_ = phone;
    }

    /** Get user phone */
    public String getPhone() {
        return this.phone_;
    }

    /** Set Url large picture */
    public void setPicLargeUrl(String picLareUrl) {
        this.picLargeUrl_ = picLareUrl;
    }

    /** Get Url large picture */
    public String getPicLargeUrl() {
        return this.picLargeUrl_;
    }

    /** Set Url tumbnail picture */
    public void setPicThumbnailUrl(String picThumbnailUrl) {
        this.picThumbnailUrl_ = picThumbnailUrl;
    }

    /** Get Url tumbnail picture */
    public String getPicThumbnailUrl() {
        return this.picThumbnailUrl_;
    }

    /** Set nations */
    public void setNation(String nation) {
        this.nation_ = nation;
    }

    /** Get Nations */
    public String getNation() {
        return this.nation_;
    }

    /** Get to 'String' (Lact name + First name) */
    @Override
    public String toString() {
        return nameLast_ + ", " + nameFirst_;
    }
}