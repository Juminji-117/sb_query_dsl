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
public class InterestKeyword {
    @Id
    private String content;

    // HashSet을 이용하기 위해 equals()와 hashCode() 오버라이드(재정의)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InterestKeyword)) return false;

        InterestKeyword that = (InterestKeyword) o;

        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}