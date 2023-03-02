package hello.board.domain.post.service;

import hello.board.controller.post.dto.req.PostUpdateReqDto;
import hello.board.controller.post.dto.req.PostWriteReqDto;
import hello.board.controller.post.dto.res.PostResDto;
import hello.board.controller.post.dto.res.PostUpdateResDto;
import hello.board.controller.post.dto.res.PostWriteResDto;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.repository.MemberRepository;
import hello.board.domain.post.entity.Post;
import hello.board.domain.post.repository.PostRepository;
import hello.board.exception.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostResDto findSinglePost(Long id) {
        return new PostResDto(findPost(id));
    }

    public List<PostResDto> findMemberPost(Long memberId) {
        Member findMember = findMember(memberId);
        return findMember.getPosts()
                .stream()
                .map(PostResDto::new)
                .collect(Collectors.toList());
    }

    public Page<PostResDto> findAllPost(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostResDto::new);
    }

    @Transactional
    public PostWriteResDto writePost(Long memberId, PostWriteReqDto postWriteReqDto) {
        return new PostWriteResDto(savePost(memberId, postWriteReqDto));
    }

    private Post savePost(Long memberId, PostWriteReqDto postWriteReqDto) {
        return postRepository.save(createPost(memberId, postWriteReqDto));
    }

    private Post createPost(Long memberId, PostWriteReqDto postWriteReqDto) {
        return Post.builder()
                .title(postWriteReqDto.getTitle())
                .content(postWriteReqDto.getContent())
                .member(findMember(memberId))
                .build();
    }

    @Transactional
    public PostUpdateResDto updatePost(Long id, PostUpdateReqDto postUpdateReqDto) {
        Post findPost = findPost(id);
        findPost.updateInfo(postUpdateReqDto);
        return new PostUpdateResDto(findPost);
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    private Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> {
                    throw new CustomNotFoundException(String.format("id=%s not found",postId));
                });
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    throw new CustomNotFoundException(String.format("id=%s not found",memberId));
                });
    }
}
