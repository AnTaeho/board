//package hello.board;
//
//import hello.board.entity.comment.Comment;
//import hello.board.entity.Member;
//import hello.board.entity.post.Post;
//import hello.board.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.PostConstruct;
//import javax.persistence.EntityManager;
//
///**
// * 종 주문 2개
// * * userA
// * 	 * JPA1 BOOK
// * 	 * JPA2 BOOK
// * * userB
// * 	 * SPRING1 BOOK
// * 	 * SPRING2 BOOK
// */
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
//
//        public void dbInit1() {
//            Member member1 = createMember("UserA", 20);
//            em.persist(member1);
//
//            Post post1 = createPost("Title1", "content1");
//            post1.setMember(member1);
//            em.persist(post1);
//
//            Post post2 = createPost("Title2", "content2");
//            post2.setMember(member1);
//            em.persist(post2);
//
//            Comment comment1 = new Comment("content11");
//            Comment comment2 = new Comment("content12");
//            comment1.setPost(post1);
//            comment1.setMember(member1);
//            comment2.setPost(post1);
//            comment2.setMember(member1);
//            Comment comment3 = new Comment("content13");
//            Comment comment4 = new Comment("content14");
//            comment3.setPost(post2);
//            comment3.setMember(member1);
//            comment4.setPost(post2);
//            comment4.setMember(member1);
//        }
//
//        public void dbInit2() {
//            Member member2 = createMember("UserB", 30);
//            em.persist(member2);
//
//            Post post3 = createPost("Title3", "content3");
//            post3.setMember(member2);
//            em.persist(post3);
//
//            Post post4 = createPost("Title4", "content4");
//            post4.setMember(member2);
//            em.persist(post4);
//
//            Comment comment5 = new Comment("content21");
//            Comment comment6 = new Comment("content22");
//            comment5.setPost(post3);
//            comment5.setMember(member2);
//            comment6.setPost(post3);
//            comment6.setMember(member2);
//            Comment comment7 = new Comment("content23");
//            Comment comment8 = new Comment("content24");
//            comment7.setPost(post4);
//            comment7.setMember(member2);
//            comment8.setPost(post4);
//            comment8.setMember(member2);
//        }
//
//        private Member createMember(String name, int age) {
//            return memberRepository.save(new Member(name, age));
//
//        }
//
//        private Post createPost(String title1, String content1) {
//            return new Post(title1, content1);
//        }
//    }
//}
//
