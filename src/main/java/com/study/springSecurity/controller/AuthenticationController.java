package com.study.springSecurity.controller;

import com.study.springSecurity.aspect.annotation.ParamsAop;
import com.study.springSecurity.aspect.annotation.ValidAop;
import com.study.springSecurity.dto.request.ReqSigninDto;
import com.study.springSecurity.dto.request.ReqSignupDto;
import com.study.springSecurity.service.SigninService;
import com.study.springSecurity.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private SignupService signupService;

    @Autowired
    private SigninService signinService;

    @ValidAop
    @ParamsAop
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody ReqSignupDto dto, BeanPropertyBindingResult bindingResult) {    // @Valid 어노테이션이 정규식(문자열만)을 확인해주는, 예외 확인하는 역할을 한다.
//        boolean isDuplicate = signupService.isDuplicatedUsername(dto.getUsername());    // 중복이 됐으면 true. field 오류이기 때문에 오류처리도 같이 해줘야 한다.
//        위 기능(isDuplicate)은 ValidAspect 에서 사용할 것
//        if (bindingResult.hasErrors()) {
//            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors());       // 어느 필드에 어떠한 에러가 있는지 던져 줘야한다.
//        }

        return ResponseEntity.created(null).body(signupService.signup(dto));
    }

    @ValidAop
    @PostMapping("/signin")
    public ResponseEntity<?> signin(
            @Valid @RequestBody ReqSigninDto dto,
            BeanPropertyBindingResult bindingResult) {
        signinService.signin(dto);
        return ResponseEntity.ok().body(null);
    }
}
// 핸들러 매핑에 가서 요청주소가 있는지, 있다면 받아오고, 받아 왔더니 dto 필요하고 binding result가 필요한 녀석이더라.
// 이 dto에 valid 어노테이션이 붙어있는데, dto 안에 들어가서 valid의 동작이 일어남. 그 전에 dto가 생성이 되어야 하는데,
// JSON 데이터가 들어왔으니 젝슨이라는 녀석이 변환해서 만들어줌. 디스패처 서블릿은 아직 핸들러 매핑에서 필요한 정보만 가져온 상태.
// 각각의 필드 안에 패턴이라는 어노테이션이 붙어있음. 이를 보고 valid 어노테이션이 일치하는지 하나씩 확인함.
// 이때, 에러가 있다면 field error를 생성하고 이 객체에 생성된 error를 생성하게 됨.
// dto , bindingResult(error 를 집어넣는 장소) 모두 생성이 되었고
// 이제 메소드를 호출함. 호출했더니 Aop가 걸려있네? 매개변수가 들어왔으니 아규먼트에 beanpropertybindingresult 로 생성이 된 것.
// 오브젝트로 생성되었기 때문에 다운캐스팅 해야 사용할 수 있음.
// class 가 dto 인 것들을 찾아서 다운캐스팅 한 뒤 dto 안에 들어있는 패스워드와 check 패스워드가 같지 않으면 수동으로 에러(field error)를 보내줌
// 그 후 bindingResult에 오류가 있는지 없는지 확인. 여기서 true 가 뜨면 validException 생성.
// 그러면 지금까지 오류났던 에러들을 담아주게 되는 것.
// validexception 이 생성되면 exceptionhandler가 기다리고 있기 때문에 무조건 오류이므로 리턴으로 badRequest를 뿌려주고, body에는
// 오류들을 뿌려줌. 이때, body에 들어온 에러들은 List로 구성되어 있기 때문에 배열에 에러 객체들로 채워지게 된다.