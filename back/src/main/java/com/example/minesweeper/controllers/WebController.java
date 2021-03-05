package com.example.minesweeper.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {
  @ResponseBody
  @RequestMapping("/")
  public ModelAndView indexWeb() {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("index.html");
    return mav;
  }
}
