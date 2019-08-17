package com.stackroute.loginwithjwt.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController

public class UserRequiredInformation {

    @RequestMapping({ "/message" })
    public String firstPage() {
        return "Hii you are getting mesage";
    }
}
