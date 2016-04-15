package com.ifeed.controller;

import com.ifeed.model.dto.CourseDTO;
import com.ifeed.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Iulia-Anamaria Pascu on 3/28/2016.
 */
@Controller
@RequestMapping(value = "/courses")
public class CourseController {
    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping(value="/list", method = RequestMethod.GET)
    @ResponseBody
    public List<CourseDTO> listAllCourses() {
        List<CourseDTO> courses = courseService.getAllCourses();

        return courses;
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    @ResponseBody
    public CourseDTO saveCourse(@RequestParam(value = "id", required = false) final Long id,
                           @RequestParam(value = "version", required = false) final Integer version,
                           @RequestParam(value = "name", required = false) final String name,
                           @RequestParam(value = "code", required = false) final String code) {

        return courseService.save(new CourseDTO(id, version, name, code));
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    @ResponseBody
    public CourseDTO removeCourse(@RequestParam(value = "id", required = true) final Long id) {
        CourseDTO removedCourse = courseService.find(id);
        courseService.remove(id);
        return removedCourse;
    }

}
