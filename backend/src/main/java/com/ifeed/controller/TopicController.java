package com.ifeed.controller;

import com.ifeed.exception.EntityInUseException;
import com.ifeed.model.dto.TopicDTO;
import com.ifeed.model.dto.TopicQuestionDTO;
import com.ifeed.service.QuestionService;
import com.ifeed.service.TopicQuestionService;
import com.ifeed.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Iulia-Anamaria Pascu on 3/29/2016.
 */
@Controller
@RequestMapping(value = "/topics")
public class TopicController {
    private TopicService topicService;
    private QuestionService questionService;
    private TopicQuestionService topicQuestionService;

    @Autowired
    public TopicController(TopicService topicService, QuestionService questionService, TopicQuestionService topicQuestionService) {
        this.topicService = topicService;
        this.questionService = questionService;
        this.topicQuestionService = topicQuestionService;
    }

    @RequestMapping(value="/list", method = RequestMethod.GET)
    @ResponseBody
    public List<TopicDTO> listAllTopics(@RequestParam(value = "id", required = true) final Long id) {
        List<TopicDTO> topics = topicService.getAllCourseTopics(id);

        for (TopicDTO dto : topics) {
            dto.setQuestions(questionService.getQuestionsByTopicId(dto.getId()));
        }

        return topics;
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    @ResponseBody
    public TopicDTO saveTopic(@ModelAttribute TopicDTO topic) {

        return topicService.save(topic);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity removeTopic(@RequestParam(value = "id", required = true) final Long id) {
        TopicDTO removedTopic = topicService.find(id);
        try {
            topicService.remove(id);
        } catch (EntityInUseException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return new ResponseEntity<>(removedTopic ,HttpStatus.OK);
    }

    @RequestMapping(value="/questionTopics", method = RequestMethod.GET)
    @ResponseBody
    public List<TopicDTO> listAllQuestionTopics(@RequestParam(value = "questionId", required = true) final Long questionId) {
        List<TopicDTO> questionTopics = topicService.getTopicsByQuestionId(questionId);

        return questionTopics;
    }

    @RequestMapping(value = "/assign", method = RequestMethod.GET)
    @ResponseBody
    public TopicQuestionDTO assign(@RequestParam(value = "topicId", required = true) final Long topicId,
                                   @RequestParam(value = "questionId", required = true) final Long questionId) {

        return topicQuestionService.save(new TopicQuestionDTO(topicId, questionId));
    }

    @RequestMapping(value = "/unassign", method = RequestMethod.GET)
    @ResponseBody
    public TopicQuestionDTO unassign(@RequestParam(value = "topicId", required = true) final Long topicId,
                                     @RequestParam(value = "questionId", required = true) final Long questionId) {
        TopicQuestionDTO removedTopic = topicQuestionService.find(topicId, questionId);
        topicQuestionService.remove(topicId, questionId);
        return removedTopic;
    }

}

