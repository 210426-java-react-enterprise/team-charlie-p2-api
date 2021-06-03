package com.revature.pantry.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/api/docs")
public class ApiDocController {

    @GetMapping
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public RedirectView redirectToApiDocumentation() {
        return new RedirectView("/swagger-ui/index.html");
    }

}
