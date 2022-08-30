package com.ll.exam.qsl.interestKeyword.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

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
public class InterestKeyword {
    @Id
    // 이 속성 값(content)를 기준으로 두 객체가 같은지 비교
    @EqualsAndHashCode.Include
    private String content;

}