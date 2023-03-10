package hello.board.domain.notification.repository;

import hello.board.domain.member.entity.Member;
import hello.board.domain.notification.entity.Notification;

import java.util.List;

public interface NotificationRepositoryCustom {

    List<Notification> findAllNotificationByMember(Member member);

}
