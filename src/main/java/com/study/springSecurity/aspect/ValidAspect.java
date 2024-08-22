package com.study.springSecurity.aspect;


import com.study.springSecurity.dto.request.ReqSignupDto;
import com.study.springSecurity.exception.ValidException;
import com.study.springSecurity.service.SignupService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Slf4j
@Aspect
@Component
public class ValidAspect {

    @Autowired
    private SignupService signupService;

    @Pointcut("@annotation(com.study.springSecurity.aspect.annotation.ValidAop)")
    private void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();      // signup 메소드들 안에 들어있는 getArgs 를 들고 args에 들어와라.

        BeanPropertyBindingResult bindingResult = null;     // bindingResult가 null이므로 안에 값을 먼저 넣어주고
        // BeanProperty는  BindingResult 라는 녀석. valid 어노테이션이 바인딩, 생성된 객체가 BeanPropertyBindingResult 가 매개변수로 가져오는 것이다.
        for (Object arg : args) {                           // 아래 반복문에서 나머지 dto
            System.out.println(arg.getClass().getSimpleName()); // sout 결과가 BeanPropertyBindingResult. 즉, ??

            if (arg.getClass() == BeanPropertyBindingResult.class) {    // arg 가 BeanPropertyBindingResult 인 녀석들을 가져와서
                bindingResult = (BeanPropertyBindingResult) arg;        // bindingResult 에 다운캐스팅 해서 담아준다.
                break;
            }
        }

        switch (proceedingJoinPoint.getSignature().getName()) {     // signup 이라는 '메소드'를 기준으로 동작하고 있고, getName이라는 것은 이 메소드(signup)를 뜻함.
            case "signup":  // signup 일 때 동작시켜라. 그러면 다른 작업을 수행할 때 필요없는 반복요청을 보내지 않는다.
                validSignupDto(args, bindingResult);
                break;
        }


//        for (FieldError fieldError : bindingResult.getFieldErrors()) {  // error가 있는 정규식이 있다면 이 반복이 돈다.
//            System.out.println(fieldError.getField());
//            System.out.println(fieldError.getDefaultMessage());
//        }

        // 유효성 검사는 클라이언트가 잘못해서 나는 에러기 때문에 400번대 에러가 난다. 하지만 여기서 나는 에러는 500번이 나오므로
        // 예외 지점을 어떠한 예외를 관찰하고 있다가 그 예외가 터지면 가져오는 작업을 할 것.
        if (bindingResult.hasErrors()) {
            throw new ValidException("유효성 검사 오류", bindingResult.getFieldErrors());
        }

        return proceedingJoinPoint.proceed();       // 매개변수가 잘 들어왔는지 확인하는 메서드. 따라서 전처리에만 사용하므로 후처리는 필요없으니 리턴에 바로 들어가도 된다.
    }

    private void validSignupDto(Object[] args, BeanPropertyBindingResult bindingResult) {
        for (Object arg : args) {
            if (arg.getClass() == ReqSignupDto.class) {     // dto 인지 확인. 해당 dto 가 있을때만 동작하자.
                ReqSignupDto dto = (ReqSignupDto) arg;      // dto로 다운캐스팅 한 후에 에러 체크
                if (!dto.getPassword().equals(dto.getCheckPassword())) {
                    FieldError fieldError = new FieldError("checkPassword", "checkPassword", "비밀번호가 일치하지 않습니다.");
                    bindingResult.addError(fieldError);
                }
                if (signupService.isDuplicatedUsername(dto.getUsername())) {
                    FieldError fieldError = new FieldError("username", "username", "이미 존재하는 사용자 이름입니다.");
                    bindingResult.addError(fieldError);
                }
                break;
            }
        }
    }
}
