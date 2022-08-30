package com.ll.exam.qsl.user.entity;

import lombok.*;

import javax.persistence.*;

// 빌더는 내부적으로 전체인자생성자를 호출
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    public void addInterestKeywordContent(String keywordContent) {

    }
}
