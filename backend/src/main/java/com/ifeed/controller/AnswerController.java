package com.ifeed.controller;

import com.ifeed.model.dto.AnswerDTO;
import com.ifeed.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by ipascu on 23.04.2016.
 */
@Controller
@RequestMapping(value = "/answers")
public class AnswerController {
    private AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @RequestMapping(value="/list", method = RequestMethod.GET)
    @ResponseBody
    public List<AnswerDTO> listAllAnswers(@RequestParam(value = "id", required = true) final Long id) {
        List<AnswerDTO> answers = answerService.getAllQuestionAnswers(id);

        return answers;
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    @ResponseBody
    public AnswerDTO saveAnswer(@RequestParam(value = "id", required = false) final Long id,
                               @RequestParam(value = "version", required = false) final Integer version,
                               @RequestParam(value = "text", required = true) final String text,
                               @RequestParam(value = "correct", required = false, defaultValue = "false") final Boolean isCorrect,
                               @RequestParam(value = "position", required = false, defaultValue = "0") final Integer position,
                               @RequestParam(value = "questionId", required = true) final Long questionId) {

        return answerService.save(new AnswerDTO(id, version, text, isCorrect, position, questionId));
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    @ResponseBody
    public AnswerDTO removeAnswer(@RequestParam(value = "id", required = true) final Long id) {
        AnswerDTO removedAnswer = answerService.find(id);
        answerService.remove(id);
        return removedAnswer;
    }

}