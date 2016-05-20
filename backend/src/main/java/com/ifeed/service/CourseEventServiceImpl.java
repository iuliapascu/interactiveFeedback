package com.ifeed.service;

import com.ifeed.mapper.CourseEventMapper;
import com.ifeed.model.CourseEvent;
import com.ifeed.model.dto.CourseEventDTO;
import com.ifeed.model.dto.CourseEventQuestionDTO;
import com.ifeed.repository.CourseEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ipascu on 19.05.2016.
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class CourseEventServiceImpl implements CourseEventService{

    private final CourseEventRepository courseEventRepository;
    private final CourseEventMapper mapper;
    private final CourseEventQuestionService courseEventQuestionService;

    @Autowired
    public CourseEventServiceImpl(CourseEventRepository courseEventRepository, CourseEventMapper topicMapper,
                                  CourseEventQuestionService courseEventQuestionService) {
        this.courseEventRepository = courseEventRepository;
        this.mapper = topicMapper;
        this.courseEventQuestionService = courseEventQuestionService;
    }

    @Override
    public List<CourseEventDTO> getAllCourseEventsByCourseId(Long courseId) {
        List<CourseEventDTO> courseEvents = mapper.map(courseEventRepository.findCourseEventsByCourseId(courseId));
        //TODO: only fetch questions when an event is selected
        for (CourseEventDTO dto : courseEvents) {
            List<CourseEventQuestionDTO> courseEventQuestions = courseEventQuestionService.getAllCourseEventQuestionsByCourseEventId(dto.getId());
            dto.setQuestions(courseEventQuestions);
        }

        return courseEvents;
    }

    @Override
    public CourseEventDTO find(long id) {
        CourseEvent courseEvent = courseEventRepository.findOne(id);
        if (courseEvent == null) {
            return null;
        }
        return mapper.map(courseEvent);
    }

    @Override
    public CourseEventDTO save(CourseEventDTO courseEvent, List<String> questionIdStrings) {
        CourseEvent entity = null;
        if (courseEvent.getId() != null) {
            entity = courseEventRepository.findOne(courseEvent.getId());
        }

        if (entity == null) {
            entity = new CourseEvent();
        }
        mapper.map(courseEvent, entity);
        CourseEvent savedCourseEvent = courseEventRepository.save(entity);

        List<Long> questionIds = new ArrayList<>();
        for (String id : questionIdStrings) {
            questionIds.add(Long.parseLong(id));
        }
        courseEventQuestionService.addEntitiesWithIds(savedCourseEvent.getId(), questionIds);

        return mapper.map(savedCourseEvent);
    }

    @Override
    public void remove(Long courseEventId) {
        if (courseEventId != null) {
            courseEventQuestionService.removeAllForCourseEvent(courseEventId);
            courseEventRepository.delete(courseEventId);
        }
    }

}
