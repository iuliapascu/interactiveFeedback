package com.ifeed.controller;

import com.ifeed.exception.EntityInUseException;
import com.ifeed.model.dto.QuestionDTO;
import com.ifeed.model.enums.QuestionType;
import com.ifeed.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "/questions")
public class QuestionController {
    private QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping(value="/list", method = RequestMethod.GET)
    @ResponseBody
    public List<QuestionDTO> listAllQuestions() {
        List<QuestionDTO> questions = questionService.getAllQuestions();

        return questions;
    }

    @RequestMapping(value="/find", method = RequestMethod.GET)
    @ResponseBody
    public QuestionDTO findQuestion(@RequestParam(value="id", required = true) final Long id) {
        return questionService.find(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    @ResponseBody
    public QuestionDTO saveQuestion(@RequestParam(value = "id", required = false) final Long id,
                                @RequestParam(value = "version", required = false) final Integer version,
                                @RequestParam(value = "title", required = true) final String title,
                                @RequestParam(value = "requirement", required = true) final String requirement,
                                @RequestParam(value = "questionType", required = true) final QuestionType questionType,
                                @RequestParam(value = "goodKeywords", required = false) final String goodKeywords,
                                @RequestParam(value = "badKeywords", required = false) final String badKeywords) {

        return questionService.save(new QuestionDTO(id, version, title, requirement, questionType, goodKeywords, badKeywords));
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity removeQuestion(@RequestParam(value = "id", required = true) final Long id) {
        QuestionDTO removedQuestion = questionService.find(id);
        try {
            questionService.remove(id);
        } catch (EntityInUseException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return new ResponseEntity<>(removedQuestion ,HttpStatus.OK);
    }

}
