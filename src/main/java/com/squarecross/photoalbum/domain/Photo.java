package com.squarecross.photoalbum.domain;

import jakarta.persistence.*;
import jakarta.persistence.UniqueConstraint;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

@Entity
@Table(name="photo", schema = "photo_album",uniqueConstraints = {@UniqueConstraint(columnNames="album_id")})
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "photo_id",unique = true,nullable = false)
    private  Long photoId;

    @Column(name="file_name",unique = true,nullable = true)
    private String fileName;

    @Column(name = "thumb_url",unique = true,nullable = true)
    private String thumbUrl;

    @Column(name = "original_url",unique = true,nullable = true)
    private String originalUrl;

    @Column(name = "file_size",unique = true,nullable = true)
    private int fileSize;

    @Column(name = "uploaded_at",unique = true,nullable = true)
    @CreationTimestamp
    private Date uploadedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="album_id")
    private Album album;

    public Photo(){};

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Date uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
