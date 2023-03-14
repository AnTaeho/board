package hello.board.service;

import hello.board.controller.comment.dto.req.CommentWriteDto;
import hello.board.controller.comment.dto.res.CommentResDto;
import hello.board.controller.notification.dto.req.NotificationUpdateReqDto;
import hello.board.controller.notification.dto.res.NotificationResDto;
import hello.board.controller.notification.dto.res.NotificationUpdateResDto;
import hello.board.controller.post.dto.req.PostWriteReqDto;
import hello.board.controller.post.dto.res.PostWriteResDto;
import hello.board.domain.comment.entity.Comment;
import hello.board.domain.comment.service.CommentService;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.service.MemberService;
import hello.board.domain.notification.entity.CommentNotification;
import hello.board.domain.notification.entity.Notification;
import hello.board.domain.notification.entity.PostNotification;
import hello.board.domain.notification.repository.NotificationRepository;
import hello.board.domain.notification.service.NotificationService;
import hello.board.domain.post.entity.Post;
import hello.board.domain.post.service.PostService;
import hello.board.exception.CustomNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class NotificationServiceTest {

    @Autowired private MemberService memberService;
    @Autowired private PostService postService;
    @Autowired private CommentService commentService;
    @Autowired private NotificationService notificationService;
    @Autowired private NotificationRepository notificationRepository;

    @Test
    @Transactional
    @DisplayName("전체 알람 조회 테스트")
    void findAllNotification() {
        //given
        Notification notification = new CommentNotification();
        Notification notification2 = new CommentNotification();
        Notification notification3 = new CommentNotification();

        //when
        notificationRepository.save(notification);
        notificationRepository.save(notification2);
        notificationRepository.save(notification3);

        //then
        List<NotificationResDto> all = notificationService.findAll();

        assertThat(all.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @DisplayName("알람 수정 테스트")
    void updateNotificationTest() {
        //given
        Notification notification = notificationRepository.save(new CommentNotification());
        NotificationUpdateReqDto updateReqDto = new NotificationUpdateReqDto("new-content");

        //when
        NotificationUpdateResDto updateResDto = notificationService.updateNotification(notification.getId(), updateReqDto);

        //then
        assertThat(updateResDto.getContent()).isEqualTo(updateReqDto.getContent());
    }

    @Test
    @Transactional
    @DisplayName("알람 삭제 테스트")
    void deleteNotificationTest() {
        //given
        Notification notification = notificationRepository.save(new CommentNotification());

        //when
        notificationService.deleteNotification(notification.getId());

        //then
        assertThatThrownBy(() -> notificationService.findById(notification.getId()))
                .isInstanceOf(CustomNotFoundException.class);
    }

    @Test
    @Transactional
    @DisplayName("게시글 작성시 작성자를 팔로우한 회원에게 알람 생성 테스트")
    void followMemberPostNotificationTest() {
        //given
        Member fromMember = memberService.findMember(1L);
        Member toMember = memberService.findMember(5L);
        memberService.followMember(toMember.getId(), fromMember);

        //when
        PostWriteResDto writeResDto = postService.writePost(toMember, new PostWriteReqDto("title", "content"));

        //then
        List<Notification> allByMember = notificationService.findAllByMember(toMember);
        for (Notification a : allByMember) {
            PostNotification notification = (PostNotification) a;
            assertThat(notification.getNotifiedMember()).isEqualTo(fromMember);
            assertThat(notification.getWriter()).isEqualTo(toMember.getName());
            assertThat(notification.getContent()).isEqualTo(writeResDto.getTitle());
        }
    }

    @Test
    @Transactional
    @DisplayName("댓글 작성시 해당 게시글의 작성자에게 알람 생성 테스트")
    void commentNotificationTest() {
        //given
        Post post = postService.findPost(2L);
        Member commentMember = memberService.findMember(5L);

        //when
        CommentResDto comment = commentService.writeComment(post.getId(), commentMember, new CommentWriteDto("content"));

        //then
        List<Notification> allByMember = notificationService.findAllByMember(post.getMember());
        for (Notification notification : allByMember) {
            assertThat(notification.getContent()).isEqualTo(comment.getContent());
            assertThat(notification.getWriter()).isEqualTo(comment.getWriter());
            assertThat(notification.getNotifiedMember().getId()).isEqualTo(post.getMember().getId());
        }
    }

    @Test
    @Transactional
    @DisplayName("댓글 좋아요시 해당 댓글 좋아요 주인에게 알람 생성 테스트")
    void commentLikeNotificationTest() {
        //given
        Long commentId = 3L;
        Long likeMemberId = 5L;
        Member likeMember = memberService.findMember(likeMemberId);

        //when
        commentService.likeComment(commentId, likeMemberId);
        Comment likedComment = commentService.findComment(3L);
        Member notifiedMember = likedComment.getPost().getMember();

        //then
        List<Notification> allByMember = notificationService.findAllByMember(notifiedMember);
        for (Notification notification : allByMember) {
            assertThat(notification.getContent()).isEqualTo("comment like");
            assertThat(notification.getWriter()).isEqualTo(likeMember.getName());
            assertThat(notification.getNotifiedMember().getId()).isEqualTo(notifiedMember.getId());
//            assertThat(notification.getOwnerComment()).isEqualTo(likedComment);
        }
    }

}
