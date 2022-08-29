package com.ll.exam.qsl.user.repository;

import com.ll.exam.qsl.user.entity.SiteUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    // 본격적으로 Query DSL 시작
    // 그 전에 JPAQueryFactory Bean에 등록해주고 시작
    @Override
    public SiteUser getQslUser(Long id) {
        //아래 주석들은 윗 query문을 아래처럼 쓸 수 있다는 예시

        /*
        SELECT *
        FROM site_user
        WHERE id = 1
        */

        /*
        return jpaQueryFactory
                .select(QSiteUser.siteUser)
                .from(QSiteUser.siteUser)
                .where(QSiteUser.siteUser.id.eq(1L))
                .fetch();
         */

        return null;
    }
}
