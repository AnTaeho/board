package hello.board.domain.notification.service;

import hello.board.controller.comment.dto.req.CommentWriteDto;
import hello.board.controller.comment.dto.res.CommentResDto;
import hello.board.controller.notification.dto.res.NotificationResDto;
import hello.board.controller.post.dto.req.PostWriteReqDto;
import hello.board.controller.post.dto.res.PostWriteResDto;
import hello.board.domain.comment.service.CommentService;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.service.MemberService;
import hello.board.domain.post.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class NotificationServiceTest {

    private final Long member1 = 1L;
    private final Long postId = 2L;
    private final Long commentId = 3L;
    private final Long member2 = 5L;

    private final Long post_notificationId = 15L;
    private final Long comment_notificationId = 18L;
    private final Long comment_like_notificationId = 17L;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private NotificationService notificationService;

    @Test
    void post_noticeTest() {
        Member fromMember = memberService.findMember(member1);
        memberService.followMember(member2, fromMember);

        Member postMember = memberService.findMember(member2);
        PostWriteResDto post = postService.writePost(postMember, new PostWriteReqDto("title", "content"));

        NotificationResDto postNotification = notificationService.findById(post_notificationId);

        assertThat(postNotification).isNotNull();
        assertThat(postNotification.getWriter()).isEqualTo(postMember.getName());
        assertThat(postNotification.getContent()).isEqualTo(post.getTitle());
    }

    //댓글 작성시 알람
    @Test
    void comment_noticeTest() {
        Member member2 = memberService.findMember(this.member2);
        CommentResDto newComment = commentService.writeComment(postId, member2, new CommentWriteDto("content"));

        NotificationResDto commentNotification = notificationService.findById(comment_notificationId);

        assertThat(commentNotification).isNotNull();
        assertThat(commentNotification.getContent()).isEqualTo(newComment.getContent());
        assertThat(commentNotification.getWriter()).isEqualTo(member2.getName());
    }

    //댓글 좋아요시 알람
    @Test
    void commentLike_noticeTest() {
        commentService.likeComment(commentId, member2);
        Member likeMember = memberService.findMember(member2);

        NotificationResDto commentLikeNotification = notificationService.findById(comment_like_notificationId);

        assertThat(commentLikeNotification).isNotNull();
        assertThat(commentLikeNotification.getWriter()).isEqualTo(likeMember.getName());
        assertThat(commentLikeNotification.getContent()).isEqualTo("comment like");

    }

    //알람 전체 조회
    @Test
    void findAll() {
        List<NotificationResDto> all = notificationService.findAll();

        assertThat(all.size()).isEqualTo(0);
    }

    //나의 알람 전제 조회

    //알람 삭제
    @Test
    void deleteNotification() {

    }
}