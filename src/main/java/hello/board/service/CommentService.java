package hello.board.service;

import hello.board.entity.Comment;
import hello.board.entity.Member;
import hello.board.entity.Post;
import hello.board.repository.CommentRepository;
import hello.board.repository.MemberRepository;
import hello.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    public List<Comment> findCommentsByPost(Long postId) {
        Post findPost = postRepository.findById(postId).get();
        return findPost.getComments();
    }

    //댓글과 포스트 멤버 사이의 관계가 복잡하다.
    //각 댓글의 멤버는 하나뿐은데 각 멤버는 여러 포스트/각 포스트에서
    //댓글을 여러개 작성할 수 있다.
    // -> 관계정리하고 로직을 다시 구현해야 한다.
    public Comment findCommentsByMember(Long memberId) {
        Member findMember = memberRepository.findById(memberId).get();
        return findMember.getComment();
    }

    //    단일 회원 단일 게시물 댓글 조회	GET	/comments/members/{member_id}/posts/{post_id}

    public Comment writeComment(Long postId, String content) {
        Comment writtenComment = new Comment(content);
        writtenComment.setPost(postRepository.findById(postId).get());
        return writtenComment;
    }

    public void updateComment(Long commentId, String content) {
        Comment findComment = commentRepository.findById(commentId).get();
        findComment.setContent(content);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

}
