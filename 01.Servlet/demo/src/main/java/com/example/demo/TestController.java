package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "테스트 메소드입니다.";
    }

    @GetMapping(value = "/index", produces = "text/html; charset=utf-8")
    public String test2() {
        return """
                <h1>가장 큰 타이틀 입니다..</h1>
                <h2>조금 작은 타이틀 입니다..</h2>
                """;
    }
}
