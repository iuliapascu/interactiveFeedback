package com.ifeed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Iulia-Anamaria Pascu on 2/11/2016.
 */
@Controller
public class SecurityController {
    @RequestMapping(value = {"/", "/login"})
    public String login() {
        return "login";
    }
}
