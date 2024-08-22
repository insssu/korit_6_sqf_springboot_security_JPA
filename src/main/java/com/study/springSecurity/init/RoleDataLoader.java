package com.study.springSecurity.init;

import com.study.springSecurity.domain.entity.Role;
import com.study.springSecurity.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component      // 프로그램 실행되면 자동으로 실행되는 Runner
public class RoleDataLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

//    public void test2() throws Exception{
//        test(new String[] {"a"});
//    }
//
//    public void test(String[] args) throws Exception {
//        run("a", "b", "c");
//    }

//    public void test() throws Exception{
//        run("a", "b", "c");
//    }

    @Override
    public void run(String... args) throws Exception {      // 'String...'은 매개변수를 갯수 상관없이 모두 받겠다는 것. 대신 배열로 들어오므로 원하는 값은 뽑아서 써야함
        // role 테이블에 ROLE_USER 라는 이름이 없으면 ~ TRUE
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            roleRepository.save(Role.builder().name("ROLE_USER").build());
        }
        if (roleRepository.findByName("ROLE_MANAGER").isEmpty()) {
            roleRepository.save(Role.builder().name("ROLE_MANAGER").build());
        }
        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            roleRepository.save(Role.builder().name("ROLE_ADMIN").build());
        }
    }
}
