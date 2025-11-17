package com.playlist.mi_playlist.Clases;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String url;

    private int likes;
    private boolean favorite;

    public Video() {
    }

    public Video(String title, String url) {
        this.title = title;
        this.url = url;
        this.likes = 0;
        this.favorite = false;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public int getLikes() {
        return likes;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
