package hello.board.service;

import hello.board.entity.Member;
import hello.board.entity.Post;
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
        return postRepository.findById(id).get();
    }

    public List<Post> findMemberPost(Long memberId) {
        Member findMember = memberRepository.findById(memberId).get();
        return findMember.getPosts();
    }

    public List<Post> findAllPost() {
        return postRepository.findAll();
    }

    @Transactional
    public Post writePost(Long memberId, Post post) {
        Post writtenPost = postRepository.save(post);
        writtenPost.setMember(memberRepository.findById(memberId).get());
        return writtenPost;
    }

    @Transactional
    public void updatePost(Long id, Post updatePost) {
        Post findPost = postRepository.findById(id).get();
        findPost.setTitle(updatePost.getTitle());
        findPost.setContent(updatePost.getContent());
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }


}
