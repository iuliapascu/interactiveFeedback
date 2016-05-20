package com.ifeed.model;

import com.ifeed.model.base.IdentifiableEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Iulia-Anamaria Pascu on 19.05.2016.
 */
@Entity
@Table(name="course_event")
public class CourseEvent extends IdentifiableEntity {

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 10)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    public CourseEvent() {}

    public CourseEvent(String name, Date date, Course course) {
        this.name = name;
        this.date = date;
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
