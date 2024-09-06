package com.squarecross.photoalbum.dto;

import java.util.List;
import java.util.Date;

public class AlbumDto {
    private Long albumId;
    private String albumName;
    private Date createdAt;
    private int count;
    private List<String> thumbUrls;
    private String userId;  // 새로 추가된 필드

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getThumbUrls() {
        return thumbUrls;
    }

    public void setThumbUrls(List<String> thumbUrls) {
        this.thumbUrls = thumbUrls;
    }

    // 새로 추가된 userId의 getter와 setter
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}