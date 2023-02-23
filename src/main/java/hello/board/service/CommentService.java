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

    @Transactional
    public Comment writeComment(Long postId, Long memberId, String content) {
        Member findMember = memberRepository.findById(memberId).get();
        Comment writtenComment = new Comment(findMember.getName(), content);
        writtenComment.setPost(postRepository.findById(postId).get());
        return writtenComment;
    }

    @Transactional
    public void updateComment(Long commentId, String content) {
        Comment findComment = commentRepository.findById(commentId).get();
        findComment.setContent(content);
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

}
