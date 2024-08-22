package com.study.springSecurity.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity // Entity 를 다는 순간 User 클래스는 sql의 table이 된다
@Data
@Builder
public class User {

    @Id     // primary 를 대신 받는 Id 어노테이션
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // AI (auto increasment)
    private Long id;    // id 는 다른 변수명을 쓰지않고 id 로 고정하기로 약속
    @Column(unique = true, nullable = false)    // nullable : not null
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;

    // fetch : 엔터티를 조인했을 때 연관된 데이터를 언제 가져올지 결정(EAGER - 당장(데이터가 적어서 금방 가져올 수 있는 경우), LAZY - 나중에 데이터를 실제로 사용할 때(데이터가 많을 경우. 기본값임))
    // cascade : 외래키를 지울 때
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)     // 누가 부모냐에 따라, 누가 기준이냐에 따라 ManyToMany, OneToMany, ManyToOne, OneToOne 등을 붙임
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),            //
            inverseJoinColumns = @JoinColumn(name = "role_id")      // 외래키
    )
    private Set<Role> roles;    // List로 받게되면 모든 경우의 수들을 가져와서 중복이 됨. 따라서 중복제거를 하려면 Set<Role> 을 해줘야 한다. 다만 컬럼의 순서가 중요하다면 List 를 써주고
                                // List의 중복제거를 하려면 List를 불러온 후 따로 제거해 줘야 한다.

}
