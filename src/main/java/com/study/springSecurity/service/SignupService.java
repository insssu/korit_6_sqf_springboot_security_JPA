package com.study.springSecurity.service;

import com.study.springSecurity.aspect.annotation.TimeAop;
import com.study.springSecurity.domain.entity.Role;
import com.study.springSecurity.domain.entity.User;
import com.study.springSecurity.dto.request.ReqSignupDto;
import com.study.springSecurity.repository.RoleRepository;
import com.study.springSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SignupService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @TimeAop
    public User signup(ReqSignupDto dto) {
        User user = dto.toEntity(passwordEncoder);
        Role role = roleRepository.findByName("ROLE_USER").orElseGet(       // ElseGet : True가 아니면 Get 해라 & return 타입은 Supplier(return만 있는 메소드)
                () -> roleRepository.save(Role.builder().name("ROLE_USER").build())
        );
        user.setRoles(Set.of(role));
        return userRepository.save(user);    // save 의 return 이 Entity 가 된다. 그러면 userRepository 도 entity
    }

    public boolean isDuplicatedUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

}
