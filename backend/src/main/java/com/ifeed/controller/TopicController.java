package com.ifeed.controller;

import com.ifeed.model.dto.TopicDTO;
import com.ifeed.model.dto.TopicQuestionDTO;
import com.ifeed.service.TopicQuestionService;
import com.ifeed.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Iulia-Anamaria Pascu on 3/29/2016.
 */
@Controller
@RequestMapping(value = "/topics")
public class TopicController {
    private TopicService topicService;
    private TopicQuestionService topicQuestionService;

    @Autowired
    public TopicController(TopicService topicService, TopicQuestionService topicQuestionService) {
        this.topicService = topicService;
        this.topicQuestionService = topicQuestionService;
    }

    @RequestMapping(value="/list", method = RequestMethod.GET)
    @ResponseBody
    public List<TopicDTO> listAllTopics(@RequestParam(value = "id", required = true) final Long id) {
        List<TopicDTO> topics = topicService.getAllCourseTopics(id);

        for (TopicDTO dto : topics) {
            dto.setQuestions(topicQuestionService.getAllTopicQuestions(dto.getId()));
        }

        return topics;
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    @ResponseBody
    public TopicDTO saveTopic(@RequestParam(value = "id", required = false) final Long id,
                                @RequestParam(value = "version", required = false) final Integer version,
                                @RequestParam(value = "title", required = false) final String title,
                                @RequestParam(value = "position", required = false) final Integer position,
                                @RequestParam(value = "courseId", required = false) final Long courseId) {

        return topicService.save(new TopicDTO(id, version, title, position, courseId));
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    @ResponseBody
    public TopicDTO removeTopic(@RequestParam(value = "id", required = true) final Long id) {
        TopicDTO removedTopic = topicService.find(id);
        topicService.remove(id);
        return removedTopic;
    }

    @RequestMapping(value="/questionTopics", method = RequestMethod.GET)
    @ResponseBody
    public List<TopicDTO> listAllQuestionTopics(@RequestParam(value = "questionId", required = true) final Long questionId) {
        List<TopicDTO> questionTopics = topicQuestionService.getAllQuestionTopics(questionId);

        return questionTopics;
    }

    @RequestMapping(value = "/assignTopicQuestion", method = RequestMethod.GET)
    @ResponseBody
    public TopicQuestionDTO assignTopicQuestion(@RequestParam(value = "topicId", required = true) final Long topicId,
                                              @RequestParam(value = "questionId", required = true) final Long questionId) {

        return topicQuestionService.save(new TopicQuestionDTO(topicId, questionId));
    }

    @RequestMapping(value = "/unassignTopicQuestion", method = RequestMethod.GET)
    @ResponseBody
    public TopicQuestionDTO unassignTopicQuestion(@RequestParam(value = "topicId", required = true) final Long topicId,
                                                @RequestParam(value = "questionId", required = true) final Long questionId) {
        TopicQuestionDTO removedTopic = topicQuestionService.find(topicId, questionId);
        topicQuestionService.remove(topicId, questionId);
        return removedTopic;
    }

}

