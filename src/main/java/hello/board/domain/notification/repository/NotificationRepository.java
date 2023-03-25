package hello.board.domain.notification.repository;

import hello.board.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select n from Notification n where n.notifiedMember.id = :memberId")
    List<Notification> findAllByMemberId(@Param("memberId") Long memberId);
}
