package com.couchcoding.oola.controller;

import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.validation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TestController {

    @PostMapping("/member")
    public String memberException(@Valid Member memberDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new ParameterBadRequestException(result);
        }
        return  "ok";
    }

    @GetMapping("/exception/{code}")
    public String exceptionTest(@PathVariable String code) {
        switch (code) {

            case "1":
                throw new CommentNotFoundException();
            case "2":
                throw new LoginForbiddenException();
            case "3":
                throw new MemberExistException();
            case "4":
                throw new MemberNotFoundException();
            case "5":
                throw new MemberUnAuthorizedException();
            case "6":
                throw new StudyExistException();
            case "7":
                throw new StudyNotFoundException();
            case "8":
                throw new StudySearchNotFoundException();
            case "9":
                throw new URLNotFoundException();
            default:
              break;
        }
        return "ok";
    }
}
