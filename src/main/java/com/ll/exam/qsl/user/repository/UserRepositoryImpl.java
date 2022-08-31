package com.ll.exam.qsl.user.repository;

import com.ll.exam.qsl.interestkeyword.entity.InterestKeyword;
import com.ll.exam.qsl.user.entity.QSiteUser;
import com.ll.exam.qsl.user.entity.SiteUser;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.LongSupplier;

import static com.ll.exam.qsl.interestkeyword.entity.QInterestKeyword.interestKeyword;
import static com.ll.exam.qsl.user.entity.QSiteUser.siteUser;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    // 본격적으로 Query DSL 시작
    // 그 전에 JPAQueryFactory Bean에 등록해주고 시작
    @Override
    public SiteUser getQslUser(Long id) {
    return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .where(siteUser.id.eq(id))
                .fetchOne();
    }

    @Override
    public long getQslCount() {
        return jpaQueryFactory
                .select(siteUser.count())
                .from(siteUser)
                .fetchOne();
    }

    @Override
    public SiteUser getQslUserOrderByIdAscOne() {
        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .orderBy(siteUser.id.asc())
                .fetchFirst();
                /*
                .limit(1)
                .fetchOne();
                 */
    }

    @Override
    public List<SiteUser> getQslUsersOrderByIdAsc() {
        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .orderBy(siteUser.id.asc())
                .fetch();
    }

    @Override
    public List<SiteUser> searchQsl(String kw) {
        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                // contains == like="%%"
                // 따라서 contains 쓸 때는 % 포함 X
                .where(siteUser.username.contains(kw).or(siteUser.email.contains(kw)))
                .orderBy(siteUser.id.desc())
                .fetch();
    }

    // 리스트를 페이징하여 가져오려면 리스트 가져오는 쿼리 하나, 페이지 가져오는 쿼리 하나 필요
    @Override
    public Page<SiteUser> searchQsl(String kw, Pageable pageable) {
        JPAQuery<SiteUser> usersQuery = jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .where(
                        siteUser.username.contains(kw)
                                .or(siteUser.email.contains(kw))
                )
                .offset(pageable.getOffset()) // 몇개를 건너 띄어야 하는지 LIMIT (1, ?)
                .limit(pageable.getPageSize()); // 한페이지에 몇개가 보여야 하는지 LIMIT ?, {1}

        for (Sort.Order o : pageable.getSort()) { // 페이지를 Sort에 의해 정렬
            PathBuilder pathBuilder = new PathBuilder(siteUser.getType(), siteUser.getMetadata());
            usersQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }

        List<SiteUser> users = usersQuery.fetch();

        // public static interface PageableExecutionUtils.TotalSupplier는 곧 제공이 끝날 인터페이스이므로 다른 구문으로 대체
        // 페이지 생성하기 위한 siteUser.count() 구하는 쿼리
        JPAQuery<Long> usersCountQuery = jpaQueryFactory
                .select(siteUser.count())
                .from(siteUser)
                .where(
                        siteUser.username.contains(kw)
                                .or(siteUser.email.contains(kw))
                );

        // 앞에 리스트와 달리 페이지는 따로 또 생성을 해줘야 됨
        return PageableExecutionUtils.getPage(users, pageable, usersCountQuery::fetchOne);
    }

    @Override
    public List<SiteUser> getQslUsersByInterestKeyword(String kw) {

        // 방법1
        /*
        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .where(siteUser.interestKeywords.contains(new InterestKeyword(kw)))
                .orderBy(siteUser.id.desc())
                .fetch();

         */

        //방법 2
        return jpaQueryFactory
                .selectFrom(siteUser)
                .innerJoin(siteUser.interestKeywords, interestKeyword) // 두번째 붙은 인자는 SQL의 AS와 같은 역할
                .where(
                        interestKeyword.content.eq(kw)
                )
                .fetch();
    }


}
