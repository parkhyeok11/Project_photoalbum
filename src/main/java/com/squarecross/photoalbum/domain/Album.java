package com.squarecross.photoalbum.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "album", schema = "photo_album", uniqueConstraints = {@UniqueConstraint(columnNames = "album_id")})
public class Album {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "album", cascade = CascadeType.ALL)
    private List<Photo> photos;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id", unique = true, nullable = false)
    private Long albumId;

    @Column(name = "album_name", unique = false, nullable = false)
    private String albumName;

    @Column(name = "created_at", unique = false, nullable = true)
    @CreationTimestamp
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Album() {}

    // Getters and setters

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}