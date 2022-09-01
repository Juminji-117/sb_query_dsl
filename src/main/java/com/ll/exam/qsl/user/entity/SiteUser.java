package com.ll.exam.qsl.user.entity;

import com.ll.exam.qsl.interestkeyword.entity.InterestKeyword;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


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

    // @Builder를 사용한 경우 build()시 설정하지 않으면 0 / null / false가 됨
    // 즉 빌더를 통해 만든 InterestKeyword 객체의 Set 필드가 null로 초기화되지 않고 설정해두었던 값으로 유지하게 하려면
    // @Build.Default 붙여줘야 됨
    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<InterestKeyword> interestKeywords = new HashSet<>();

    public void addInterestKeywordContent(String keywordContent) {
        interestKeywords.add(new InterestKeyword(keywordContent));
    }

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<SiteUser> followers = new HashSet<>();


    public void follow(SiteUser following) {
        following.getFollowers().add(this);
    }

}
