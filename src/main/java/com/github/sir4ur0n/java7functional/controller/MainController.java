package com.github.sir4ur0n.java7functional.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class MainController {

  @ApiIgnore
  @GetMapping("")
  public ModelAndView redirectToSwaggerUI(ModelMap model) {
    return new ModelAndView("redirect:/swagger-ui.html", model);
  }

}
