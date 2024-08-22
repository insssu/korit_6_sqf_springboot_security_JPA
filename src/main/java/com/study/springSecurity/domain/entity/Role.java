package com.study.springSecurity.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Role {     // 권한.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;    // ROLE_USER, ROLE_ADMIN, ROLE_MANAGER 식으로 들어옴
}

// 하나의 유저가 유저의 권한들 즉, 사용자의 권한을 가질 수 있고, admin, manager 의 권한을 가질 수 있도록 리스트 형태로 가져와야 한다.
// 이 권한을 가지고 있어야지만 요청을 날릴 수 있도록 한다.
// List<User> 의 users 이므로 이 리스트에 있는 유저마다 권한을 가지고 있게 됨. 하지만 List는 모든 경우의 수를 가져오기 때문에 중복이 된다.
// 중복을 제거하기 위해서는 Set 을 통해 중복제거를 해야 한다.