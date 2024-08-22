package com.study.springSecurity.controller;

import com.study.springSecurity.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController     // 넌 왜 붙이니? A:
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public ResponseEntity<?> get() {
//        System.out.println("get메소드 호출");            // 부가 기능 이라고 가정하고
//
        System.out.println(testService.aopTest());      // 핵심 기능
        testService.aopTest2("황인수", 29);
        testService.aopTest3("010-9584-6142", "부산 사하구");
//
//        System.out.println("get메소드 리턴되기 직전");    // 부가 기능
        return ResponseEntity.ok("확인");
    }
}
