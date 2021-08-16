package com.project.ImageUploader.entity;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @Column(name="id", unique=true, nullable=false)
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "filename")
    private String filename;

    @Column(name = "thumbnail")
    private String thumbnail;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "Images{" +
                "id=" + id +
                ", filename='" + filename + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
