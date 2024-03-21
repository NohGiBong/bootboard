package com.busanit.entity;

import com.busanit.constant.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="member")
@Getter
@Setter
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // 로그인 시 아이디로 이메일 사용
    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    // Role 값을 String 값으로 저장
    @Enumerated(EnumType.STRING)
    private Role role;

    // 소셜 로그인 여부
    private boolean social;



}
