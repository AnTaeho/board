//package hello.board;
//
//import hello.board.controller.comment.dto.req.CommentWriteDto;
//import hello.board.controller.comment.dto.res.CommentResDto;
//import hello.board.controller.member.dto.req.MemberRegisterReqDto;
//import hello.board.controller.member.dto.res.MemberRegisterResDto;
//import hello.board.controller.member.session.SessionConst;
//import hello.board.controller.post.dto.req.PostWriteReqDto;
//import hello.board.controller.post.dto.res.PostWriteResDto;
//import hello.board.domain.comment.service.CommentService;
//import hello.board.domain.member.entity.Member;
//import hello.board.domain.member.entity.MemberRole;
//import hello.board.domain.member.repository.member.MemberRepository;
//import hello.board.domain.member.service.MemberService;
//import hello.board.domain.post.service.PostService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.PostConstruct;
//import javax.persistence.EntityManager;
//import javax.servlet.http.HttpServletRequest;
//import java.util.Optional;
//
//
//@Component
//@RequiredArgsConstructor
//public class InitDb {
//
//    private final InitService initService;
//
//    @PostConstruct
//    public void init() {
//        initService.dbInit1();
//        initService.dbInit2();
//    }
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class InitService {
//
//        private final EntityManager em;
//        private final MemberRepository memberRepository;
//        private final MemberService memberService;
//        private final PostService postService;
//        private final CommentService commentService;
//
//        public void dbInit1() {
//            MemberRegisterResDto userA = memberService.joinMember(new MemberRegisterReqDto("안태호", 20, "AnID", "AnPW", MemberRole.ADMIN));
//            reflect();
//
//            Member member = memberRepository.findById(userA.getId()).get();
//
//            PostWriteResDto writePost1 = postService.writePost(member, new PostWriteReqDto("\"안산은 이게 일상이야\"", "대충 안산 이야기"));
//            reflect();
//            CommentResDto comment1 = commentService.writeComment(writePost1.getId(), member, new CommentWriteDto("안산식 ㄷㄷ 역시 안산의 이병건"));
//            reflect();
//            CommentResDto comment3 = commentService.writeComment(writePost1.getId(), member, new CommentWriteDto("안산식 ㄷㄷ 역시 안산의 이병건"));
//            reflect();
//            commentService.likeComment(comment1.getId(), userA.getId());
//            reflect();
//
//            PostWriteResDto writePost2 = postService.writePost(member, new PostWriteReqDto("\"안산은 이게 일상이야2\"", "대충 안산 이야기2"));
//            reflect();
//            CommentResDto comment2 = commentService.writeComment(writePost2.getId(), member, new CommentWriteDto("안산식 ㄷㄷ 역시 안산의 이병건2"));
//            reflect();
//            commentService.likeComment(comment2.getId(), userA.getId());
//            reflect();
//        }
//
//        public void dbInit2() {
//            for (int i = 0; i < 2; i++) {
//                makeNormalMember(i);
//            }
//
//        }
//
//        private void makeNormalMember(int i) {
//            MemberRegisterResDto userB = memberService.joinMember(new MemberRegisterReqDto("장대영" + (i+1), 30, "JangID" + (i+1), "JangPW" + (i+1), MemberRole.USER));
//            reflect();
//
//            Member member = memberRepository.findById(userB.getId()).get();
//            PostWriteResDto writePost2 = postService.writePost(member, new PostWriteReqDto("\"55도발 왜 하냐구\"", "대충 55도발 이야기"));
//            reflect();
//            CommentResDto comment2 = commentService.writeComment(writePost2.getId(), member, new CommentWriteDto("역시 개청자들 대가리를 한땀한땀 깨놔야 하는데"));
//            reflect();
//            commentService.likeComment(comment2.getId(), userB.getId());
//            reflect();
//        }
//
//        private void reflect() {
//            em.flush();
//            em.clear();
//        }
//
//    }
//}
//
