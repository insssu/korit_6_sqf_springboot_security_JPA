package com.study.springSecurity.exception;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

public class ValidException extends RuntimeException {  // 런타임 익셉션 생성할 때 에러를 넣어주겠다.

    @Getter // 가져오기만 하고 setter가 없으니 수정은 안됨
    private List<FieldError> fieldErrors;   // 예외객체를 하나 생성해서 담아 둠.

    public ValidException(String message, List<FieldError> fieldErrors) {   // 생성될 때 List 통째로 들고와서 넣어주겠다.
        super(message);
        this.fieldErrors = fieldErrors;
    }
}
