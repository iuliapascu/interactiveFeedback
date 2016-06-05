package com.ifeed.controller;

import com.ifeed.model.dto.UserAnswerDTO;
import com.ifeed.service.UserAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by ipascu on 24.05.2016.
 */
@Controller
@RequestMapping(value = "/userAnswers")
public class UserAnswerController {

    private UserAnswerService userAnswerService;

    @Autowired
    public UserAnswerController(UserAnswerService userAnswerService) {
        this.userAnswerService = userAnswerService;
    }

    @RequestMapping(value="/list", method = RequestMethod.GET)
    @ResponseBody
    public List<UserAnswerDTO> listAllUserAnswers(@RequestParam(value = "courseEventId", required = true) final Long courseEventId,
                                                  @RequestParam(value = "questionId", required = true) final Long questionId) {
        List<UserAnswerDTO> answers = userAnswerService.getAllUserAnswers(courseEventId, questionId);

        return answers;
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    @ResponseBody
    public UserAnswerDTO saveAnswer(@RequestParam(value = "text", required = true) final String text,
                                    @RequestParam(value = "eventId", required = true) final Long eventId,
                                    @RequestParam(value = "questionId", required = true) final Long questionId) {

        return userAnswerService.save(new UserAnswerDTO(text, eventId, questionId));
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    @ResponseBody
    public UserAnswerDTO removeUserAnswer(@RequestParam(value = "id", required = true) final Long id) {
        UserAnswerDTO removedAnswer = userAnswerService.find(id);
        userAnswerService.remove(id);
        return removedAnswer;
    }
}
