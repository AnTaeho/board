package hello.board.service;

import hello.board.entity.member.Member;
import hello.board.entity.post.Post;
import hello.board.repository.MemberRepository;
import hello.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public Post findSinglePost(Long id) {
        return findPost(id);
    }

    public List<Post> findMemberPost(Long memberId) {
        Member findMember = findMember(memberId);
        return findMember.getPosts();
    }

    public List<Post> findAllPost() {
        return postRepository.findAll();
    }

    @Transactional
    public Post writePost(Long memberId, Post post) {
        Post writtenPost = postRepository.save(post);
        writtenPost.setMember(findMember(memberId));
        return writtenPost;
    }

    @Transactional
    public Post updatePost(Long id, Post updatePost) {
        Post findPost = findPost(id);
        findPost.updateInfo(updatePost.getTitle(), updatePost.getContent());
        return findPost;
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
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
