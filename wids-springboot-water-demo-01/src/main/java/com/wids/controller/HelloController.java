package com.wids.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    /**
     * @GetMapping将HTTP GET请求映射到特定的方法。例如：将浏览器的/hello请求映射到hello()方法
     * @return
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hello Git";
    }
}
