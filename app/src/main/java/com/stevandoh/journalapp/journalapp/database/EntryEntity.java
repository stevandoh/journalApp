package com.stevandoh.journalapp.journalapp.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "entries")
public class EntryEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Ignore
    public EntryEntity() {
    }

    public EntryEntity(int id, Date date, String content) {
        this.id = id;
        this.date = date;
        this.content = content;
    }

    @Ignore
    public EntryEntity(Date date, String content) {
        this.date = date;
        this.content = content;
    }

    @Override
    public String toString() {
        return "EntryEntity{" +
                "id=" + id +
                ", date=" + date +
                ", content='" + content + '\'' +
                '}';
    }
}
