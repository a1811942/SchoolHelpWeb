package com.example.demo.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ForumDoc {
    private String id;
    private String title;
    private String content;
    private String name;
    private Timestamp date;
    private String sex;

    public ForumDoc() {
    }
    public ForumDoc(ForumDoc forumDoc) {
        this.id= forumDoc.getId();
        this.title= forumDoc.getTitle();
        this.content= forumDoc.getContent();
        this.name= forumDoc.getName();
        this.date= forumDoc.getDate();
        this.sex= forumDoc.getSex();

    }
}
