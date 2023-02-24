package hello.board.service;

import hello.board.entity.comment.Comment;
import hello.board.entity.Member;
import hello.board.entity.comment.CommentLike;
import hello.board.entity.post.Post;
import hello.board.repository.CommentLikeRepository;
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
    private final CommentLikeRepository commentLikeRepository;

    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    public List<Comment> findCommentsByPost(Long postId) {
        Post findPost = findPost(postId);
        return findPost.getComments();
    }

    @Transactional
    public Comment writeComment(Long postId, Long memberId, String content) {
        Member findMember = findMember(memberId);
        Comment newComment = new Comment(findMember.getName(), content);
        newComment.setPost(findPost(postId));
        return newComment;
    }

    @Transactional
    public void updateComment(Long commentId, String content) {
        Comment findComment = findComment(commentId);
        findComment.setContent(content);
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    @Transactional
    public void likeComment(Long commentId, Long memberId) {
        Member findMember = findMember(memberId);
        Comment findComment = findComment(commentId);

        commentLikeRepository.save(new CommentLike(findMember, findComment));
        //이후에 다시 likeComment 를 호출했을 때 자동으로 삭제되게끔 하는 로직 추가 해보실?
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("댓글이 이상해");
                });
    }

    private Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("게시글이 없어");
                });
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("회원이 없어");
                });
    }
}
