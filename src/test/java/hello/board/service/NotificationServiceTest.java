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
import hello.board.domain.notification.entity.Notification;
import hello.board.domain.notification.repository.NotificationRepository;
import hello.board.domain.notification.service.NotificationService;
import hello.board.domain.post.entity.Post;
import hello.board.domain.post.service.PostService;
import hello.board.exception.CustomNotFoundException;
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

    //알람 전체 조회
    @Test
    @Transactional
    void findAllNotification() {
        //given
        Notification notification = new Notification();
        Notification notification2 = new Notification();
        Notification notification3 = new Notification();

        //when
        notificationRepository.save(notification);
        notificationRepository.save(notification2);
        notificationRepository.save(notification3);

        //then
        List<NotificationResDto> all = notificationService.findAll();

        assertThat(all.size()).isEqualTo(3);
    }

    //회원의 알람 조회
    @Test
    @Transactional
    void findAllByMemberTest() {
        //given
        Member member = memberService.findMember(1L);
        Notification notification = notificationRepository.save(new Notification());
        Notification notification2 = notificationRepository.save(new Notification());
        Notification notification3 = notificationRepository.save(new Notification());

        //when
        member.addNotification(notification);
        member.addNotification(notification2);
        member.addNotification(notification3);

        //then
        List<Notification> allByMember = notificationService.findAllByMember(member);
        assertThat(allByMember.size()).isEqualTo(3);

        for (Notification notification1 : allByMember) {
            assertThat(notification1.getNotifiedMember()).isEqualTo(member);
        }
    }

    //알람 수정
    @Test
    @Transactional
    void updateNotificationTest() {
        //given
        Notification notification = notificationRepository.save(new Notification());
        NotificationUpdateReqDto updateReqDto = new NotificationUpdateReqDto("new-content");

        //when
        NotificationUpdateResDto updateResDto = notificationService.updateNotification(notification.getId(), updateReqDto);

        //then
        assertThat(updateResDto.getContent()).isEqualTo(updateReqDto.getContent());
    }

    //알람 삭제
    @Test
    @Transactional
    void deleteNotificationTest() {
        //given
        Notification notification = notificationRepository.save(new Notification());

        //when
        notificationService.deleteNotification(notification.getId());

        //then
        assertThatThrownBy(() -> notificationService.findById(notification.getId()))
                .isInstanceOf(CustomNotFoundException.class);
    }

    //게시글 작성시 알람
    @Test
    @Transactional
    void followMemberPostNotificationTest() {
        //given
        Member fromMember = memberService.findMember(1L);
        Member toMember = memberService.findMember(5L);
        memberService.followMember(toMember.getId(), fromMember);

        //when
        PostWriteResDto writeResDto = postService.writePost(toMember, new PostWriteReqDto("title", "content"));

        //then
        List<Notification> allByMember = notificationService.findAllByMember(toMember);
        for (Notification notification : allByMember) {
            assertThat(notification.getNotifiedMember()).isEqualTo(fromMember);
            assertThat(notification.getWriter()).isEqualTo(toMember.getName());
            assertThat(notification.getContent()).isEqualTo(writeResDto.getTitle());
        }
    }

    //댓글 작성시 알람
    @Test
    @Transactional
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

    //댓글 좋아요시 알람
    @Test
    @Transactional
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
            assertThat(notification.getOwnerComment()).isEqualTo(likedComment);
        }
    }

}
