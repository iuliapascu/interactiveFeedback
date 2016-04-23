package com.ifeed.model;

/**
 * Created by Iulia-Anamaria Pascu on 3/29/2016.
 */

import com.ifeed.model.base.IdentifiableEntity;

import javax.persistence.*;

@Entity
public class Topic extends IdentifiableEntity {

    @Column(unique = true, nullable = false, length = 100)
    private String title;

    @Column(unique = true, nullable = false)
    private Integer position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    public Topic() {
    }

    public Topic(String title, Integer position, Course course) {
        this.title = title;
        this.position = position;
        this.course = course;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}

