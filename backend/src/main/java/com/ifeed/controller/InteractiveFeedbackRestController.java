package com.ifeed.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Iulia-Anamaria Pascu on 3/23/2016.
 */
@RestController
@RequestMapping("/rest")
public class InteractiveFeedbackRestController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }
}
