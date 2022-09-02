package com.ll.exam.qsl.interestkeyword.entity;

import com.ll.exam.qsl.user.entity.SiteUser;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// equals :  두 객체의 내용이 같은지, 동등성(equality) 를 비교하는 연산자
// hashCode : 두 객체가 같은 객체인지, 동일성(identity) 를 비교하는 연산자
// 롬복에서 제공하는 equals와 hashcode 자동 생성해주는 어노테이션
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
// @IdClass : 복합키 매핑 위한 어노테이션
@IdClass(InterestKeywordId.class)
public class InterestKeyword {

    @Id
    @ManyToOne
    @EqualsAndHashCode.Include
    private SiteUser user;
    @Id

    // 이 속성 값(content)를 기준으로 두 객체가 같은지 비교
    @EqualsAndHashCode.Include
    private String content;



}