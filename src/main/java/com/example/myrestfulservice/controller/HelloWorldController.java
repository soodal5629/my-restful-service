package com.example.myrestfulservice.controller;

import com.example.myrestfulservice.bean.HelloWorldBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        // 스프링 부트가 json 형태로 리턴
        return new HelloWorldBean("Hello World");
    }

    @GetMapping("/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBeanPathVariable(@PathVariable String name) {
        // 스프링 부트가 json 형태로 리턴
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }
}
