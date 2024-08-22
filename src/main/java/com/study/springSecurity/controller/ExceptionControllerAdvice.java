package com.study.springSecurity.controller;

import com.study.springSecurity.exception.ValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;

@RestControllerAdvice   // 컴포넌트기 때문에 IOC 안에서 발생한 예외만 찾을 수 있음. 중요!
                        // 예를들어 Dto 안에서 예외가 발생하면 가져오지 못하고, 컴포넌트로 등록하지 않은 것에 대한 예외 또한 가져오지 못함.
public class ExceptionControllerAdvice {

    @ExceptionHandler(ValidException.class)     // GetMapping, PostMapping 같은 거라고 생각하면 됨. 이 예외가 터지면 아래 코드로 이동.
    public ResponseEntity<?> validException(ValidException e) {    // 생성된 매개변수를 validException 에 넣고
        return ResponseEntity.badRequest().body(e.getFieldErrors());   // 응답이 여기서 일어나게 됨. 즉, 예외들이 발생하면 대신 응답해서 정확한 에러를 찾을 수 있음
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> usernameNotFoundException(UsernameNotFoundException e) {    // 생성된 매개변수를 validException 에 넣고
        return ResponseEntity.badRequest().body(Set.of(new FieldError("authentication", "authentication", e.getMessage())));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsException(BadCredentialsException e) {        // 오류가 터질 때 log가 찍혀서 확인하기 편함
        return ResponseEntity.badRequest().body(Set.of(new FieldError("authentication", "authentication", e.getMessage())));
    }
}

