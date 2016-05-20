package com.ifeed.service;

import com.ifeed.mapper.TopicMapper;
import com.ifeed.model.Topic;
import com.ifeed.model.dto.TopicDTO;
import com.ifeed.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Iulia-Anamaria Pascu on 3/29/2016.
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class TopicServiceImpl implements TopicService{

    private final TopicRepository topicRepository;
    private final TopicMapper mapper;

    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository, TopicMapper topicMapper) {
        this.topicRepository = topicRepository;
        this.mapper = topicMapper;
    }

    @Override
    public List<TopicDTO> getAllCourseTopics(Long courseId) {
        return mapper.map(topicRepository.findCourseTopicsOrderedByTitle(courseId));
    }

    @Override
    public List<TopicDTO> getTopicsForIds(List<Long> ids) {
        return mapper.map(topicRepository.findAll(ids));
    }

    @Override
    public TopicDTO find(long id) {
        Topic topic = topicRepository.findOne(id);
        if (topic == null) {
            return null;
        }
        return mapper.map(topic);
    }

    @Override
    public TopicDTO save(TopicDTO topic) {
        Topic entity = null;
        if (topic.getId() != null) {
            entity = topicRepository.findOne(topic.getId());
        }

        if (entity == null) {
            entity = new Topic();
        }
        mapper.map(topic, entity);

        Topic savedTopic = topicRepository.save(entity);

        return mapper.map(savedTopic);
    }

    @Override
    public void remove(Long topicId) {
        if (topicId != null) {
            topicRepository.delete(topicId);
        }
    }

}

