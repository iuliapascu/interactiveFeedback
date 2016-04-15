package com.ifeed.controller;

import com.ifeed.model.dto.UserDTO;
import com.ifeed.model.dto.UserEditDTO;
import com.ifeed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"", "/list"})
    public ModelAndView listAllUsers(ModelAndView modelAndView) {
        List<UserDTO> users = userService.loadAll();

        modelAndView.setViewName("user-list");
        modelAndView.getModelMap().addAttribute("users", users);
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView createUser(ModelAndView modelAndView) {
        return getUser(null, modelAndView);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView getUser(@PathVariable Long id, ModelAndView modelAndView) {
        UserDTO user = null;
        if (id == null) {
            user = new UserEditDTO();
        } else {
            user = new UserEditDTO(userService.find(id));
        }

        modelAndView.setViewName("user-edit");
        modelAndView.getModelMap().addAttribute("user", user);
        return modelAndView;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView saveUser(@ModelAttribute UserEditDTO user, ModelAndView modelAndView) {
        UserDTO savedUser = userService.save(user);
        modelAndView.getModelMap().addAttribute("savedUser", savedUser);
        return listAllUsers(modelAndView);
    }
}
