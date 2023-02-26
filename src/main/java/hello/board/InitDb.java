package hello.board;

import hello.board.entity.comment.Comment;
import hello.board.entity.member.Member;
import hello.board.entity.member.MemberRole;
import hello.board.entity.post.Post;
import hello.board.service.CommentService;
import hello.board.service.MemberService;
import hello.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;


@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final MemberService memberService;
        private final PostService postService;
        private final CommentService commentService;

        public void dbInit1() {
            Member userA = memberService.joinMember(new Member("안태호", 20, "AnID", "AnPW", MemberRole.ADMIN));
            reflect();
            Post post1 = new Post("\"안산은 이게 일상이야\"", "대충 안산 이야기");
            Post writePost1 = postService.writePost(userA.getId(), post1);
            reflect();
            Comment comment1 = commentService.writeComment(writePost1.getId(), userA.getName(), "안산식 ㄷㄷ 역시 안산의 이병건");
            reflect();
            String status = commentService.likeComment(comment1.getId(), userA.getId());
            System.out.println("status = " + status);
            reflect();
        }

        public void dbInit2() {
            for (int i = 0; i < 100; i++) {
                makeNormalMember(i);
            }

        }

        private void makeNormalMember(int i) {
            Member userB = memberService.joinMember(new Member("장대영" + (i+1), 30, "JangID" + (i+1), "JangPW" + (i+1), MemberRole.USER));
            reflect();
            Post post2 = new Post("\"55도발 왜 하냐구\"", "대충 55도발 이야기");
            Post writePost2 = postService.writePost(userB.getId(), post2);
            reflect();
            Comment comment2 = commentService.writeComment(writePost2.getId(), userB.getName(), "역시 개청자들 대가리를 한땀한땀 깨놔야 하는데");
            reflect();
            String status = commentService.likeComment(comment2.getId(), userB.getId());
            System.out.println("status = " + status);
            reflect();
        }

        private void reflect() {
            em.flush();
            em.clear();
        }

    }
}

