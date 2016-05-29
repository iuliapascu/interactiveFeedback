package com.ifeed.controller;

import com.ifeed.model.dto.CourseEventDTO;
import com.ifeed.model.dto.CourseEventQuestionDTO;
import com.ifeed.service.CourseEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by ipascu on 19.05.2016.
 */
@Controller
@RequestMapping(value = "/events")
public class CourseEventController {

    private final CourseEventService courseEventService;

    @Autowired
    public CourseEventController(CourseEventService courseEventService) {
        this.courseEventService = courseEventService;
    }

    @RequestMapping(value="/list", method = RequestMethod.GET)
    @ResponseBody
    public List<CourseEventDTO> listAllCourseEvents(@RequestParam(value = "id", required = true) final Long id) {
        List<CourseEventDTO> courseEvents = courseEventService.getAllCourseEventsByCourseId(id);
        return courseEvents;
    }

    @RequestMapping(value="/eventQuestions", method = RequestMethod.GET)
    @ResponseBody
    public List<CourseEventQuestionDTO> getAllCourseEventQuestions(@RequestParam(value = "courseEventId", required = true) final Long courseEventId) {
        List<CourseEventQuestionDTO> courseEventQuestions = courseEventService.getAllCourseEventQuestions(courseEventId);
        return courseEventQuestions;
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    @ResponseBody
    public CourseEventDTO saveCourseEvent(@RequestParam(value = "id", required = false) final Long id,
                              @RequestParam(value = "version", required = false) final Integer version,
                              @RequestParam(value = "name", required = false) final String name,
                              @RequestParam(value = "date", required = false) final String date,
                              @RequestParam(value = "courseId", required = false) final Long courseId,
                              @RequestParam(value = "questionList", required = false) final String questionListIds) {

        Date creationDate = null;
        /*try {
            creationDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        List<String> questionIds = Arrays.asList(questionListIds.split("\\s*,\\s*"));
        return courseEventService.save(new CourseEventDTO(id, version, name, creationDate, courseId), questionIds);
    }

    @RequestMapping(value = "/duplicate", method = RequestMethod.GET)
    @ResponseBody
    public CourseEventDTO duplicateCourseEvent(@RequestParam(value = "oldEventId", required = false) final Long oldEventId,
                                               @RequestParam(value = "name", required = false) final String name,
                                               @RequestParam(value = "courseId", required = false) final Long courseId) {

        return courseEventService.duplicate(oldEventId, name, courseId);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    @ResponseBody
    public CourseEventDTO removeCourseEvent(@RequestParam(value = "id", required = true) final Long id) {
        CourseEventDTO removedCourseEvent = courseEventService.find(id);
        courseEventService.remove(id);
        return removedCourseEvent;
    }

}
