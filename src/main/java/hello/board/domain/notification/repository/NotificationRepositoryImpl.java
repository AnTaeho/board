package hello.board.domain.notification.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.board.domain.member.entity.Member;
import hello.board.domain.notification.entity.Notification;

import javax.persistence.EntityManager;
import java.util.List;

import static hello.board.domain.notification.entity.QNotification.*;

public class NotificationRepositoryImpl implements NotificationRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public NotificationRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Notification> findAllNotificationByMember(Member member) {
        return queryFactory
                .selectFrom(notification)
                .where(notification.notifiedMember.id.eq(member.getId()))
                .fetch();
    }
}
