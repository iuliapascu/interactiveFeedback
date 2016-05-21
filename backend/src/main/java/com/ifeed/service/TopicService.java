package com.ifeed.service;

import com.ifeed.exception.EntityInUseException;
import com.ifeed.model.dto.TopicDTO;

import java.util.List;

/**
 * Created by Iulia-Anamaria Pascu on 3/29/2016.
 */
public interface TopicService {

    TopicDTO find(long id);

    List<TopicDTO> getAllCourseTopics(Long courseId);

    TopicDTO save(TopicDTO topic);

    void remove(Long topicId) throws EntityInUseException;

    List<TopicDTO> getTopicsByQuestionId(Long id);
}
