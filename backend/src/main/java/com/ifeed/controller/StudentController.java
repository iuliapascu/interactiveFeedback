package com.ifeed.controller;

import com.ifeed.model.dto.UserAnswerDTO;
import com.ifeed.service.UserAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ipascu on 24.05.2016.
 */
@Controller
@RequestMapping(value = "/students")
public class StudentController {

    private UserAnswerService userAnswerService;

    @Autowired
    public StudentController(UserAnswerService userAnswerService) {
        this.userAnswerService = userAnswerService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    @ResponseBody
    public UserAnswerDTO saveAnswer(@RequestParam(value = "id", required = false) final Long id,
                                    @RequestParam(value = "version", required = false) final Integer version,
                                    @RequestParam(value = "text", required = true) final String text,
                                    @RequestParam(value = "questionId", required = true) final Long questionId,
                                    @RequestParam(value = "courseEventId", required = true) final Long courseEventId) {

        return userAnswerService.save(new UserAnswerDTO(id, version, text, questionId, courseEventId));
    }

}
