package com.study.springSecurity.dto.request;

import com.study.springSecurity.domain.entity.Role;
import com.study.springSecurity.domain.entity.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
public class ReqSignupDto {



    @Pattern(regexp = "[a-z0-9]{6,}$", message = "사용자 이름은 6자 이상의 영문, 숫자 조합이어야 합니다.")
    private String username;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[~!@#$%^&*?])[A-Za-z\\d~!@#$%^&*?]{8,16}$", message = "비밀번호는 8자 이상 16자 이하의 영대소문, 숫자, 특수문자(~!@#$%^&*?)를 포함해야 합니다.")
    private String password;
    private String checkPassword;
    @Pattern(regexp = "^[가-힣]+$", message = "이름은 한들이어야 합니다.")
    private String name;

    public User toEntity(BCryptPasswordEncoder passwordEncoder) {            // toEntity 만들기 전에 해야 할 것은
        return User.builder()           // username 중복체크
                .username(username)     // 정규식 적용
                .password(passwordEncoder.encode(password))     // 암호화 되어서 toEntity에 들어가야 함.
                .name(name)
                .build();
    }
}
