package com.study.springSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity  // 우리가 만든 SecurityConfig 를 적용시키겠다.
@Configuration      // Configuration : IOC container 에 bean 으로 생성되어 들어감
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 추상클래스 안에 추상메서드가 있냐 없냐에 따라 두가지로 나뉜다. 어떻게 알 수 있는지? SecurityConfig 에 빨간줄이 뜨면 아직 구현이 안된 것이 있다는 뜻
    // 오버라이드 : 부모에게서 상속받은 메서드들을 자식에게서 다시 재정의 (자료형(type)을 바꿀 수는 없지만 매개변수명은 바꿀 수 있음).

    @Bean   // BCrypt 는 이미 생성된 컴포넌트이므로 내가 임의로 생성해서 만들고 싶을 때, Bean 등록을 한 후에 재사용 가능
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();     // new 로 새로운 객체 생성해서 메소드 이름으로 IoC에 등록이 됨. 그러면 컴포넌트로 등록하고 사용 할 수 있겠지.
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);      // 상위 클래스(WebSecurityConfigurerAdapter)의 메서드(configure)를 호출해라. http 라는 매개변수를 부모가 가진 매개변(http)수 그대로.
        http.formLogin().disable();
        http.httpBasic().disable();
        http.csrf().disable();
        // csrf : 위조 방지 스티커, 주로 서버사이드 랜더링때 사용. 그러면 서버가 랜더링 할 때, 내가 랜더링 한 페이지야 하는 것
        // 지금은 session 을 안쓰니깐 disable을 걸어둘 뿐.
//        http.sessionManagement().disable();
        // 스프링 시큐리티가 세션을 생성하지도 않고 기존의 세션을 사용하지도 않겠다는 뜻
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 스프링 시큐리티가 세션을 생성하지 않겠다. 기존의 세션을 완전히 사용하지 않겠다는 뜻은 아님. 세션 자체는 살려두고 시큐리티(인증절차) 안에서만 사용하지 않는 것.
        // JWT 등의 토큰 인증방식을 사용할 때 설정하는 것. 토큰을 쓰니 세션을 쓸 필요가 없다.
        http.cors();  // CorsOrigin : 서버가 서로 다를 때 연결시켜 주는 것.

        http.authorizeRequests()
                .antMatchers("/auth/**", "/h2-console/**", "/test/**")                // 문자열을 가지고 주소를 선택하는 것
                .permitAll()                            // auth 를 포함한 모두에게 열린 권한 / 회원가입, 로그인 등
                .anyRequest()                           // 나머지 모든 요청들은
                .authenticated()                       // 인증을 거쳐라
                .and()
                .headers()
                .frameOptions()
                .disable();
    }
}

// 서버의 키가 있어야 다른 내용들을 열어볼 수 있다. 애초에 모든 요청들이 막혀있으면 키를 달라고 하는 요청조차 날리지 못함
// (antMatchers, permitAll) 의 역할은 들어갈 수 있게 키를 달라는 요청은 열어둬라.
//