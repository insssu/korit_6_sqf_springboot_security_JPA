package com.study.springSecurity.service;

import com.study.springSecurity.domain.entity.User;
import com.study.springSecurity.dto.request.ReqSigninDto;
import com.study.springSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SigninService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void signin(ReqSigninDto dto) {
        // username 확인
        User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(       // id 는 맞았다
                () -> new UsernameNotFoundException("사용자 정보를 다시 입력하세요.")
        );
        // password 확인
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {          // 비밀번호가 틀렸다면 true
            throw new BadCredentialsException("사용자 정보를 다시 입력하세요.");
        }

        System.out.println("로그인 성공");
    }
}
