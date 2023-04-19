package hello.board.domain.post.repository;

import hello.board.controller.post.dto.req.PostSearchCondition;
import hello.board.controller.post.dto.res.PostResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostRepositoryCustom {

    Page<PostResDto> search(PostSearchCondition condition, Pageable pageable);

    Page<PostResDto> findAllPostedPost(Pageable pageable);

    Page<PostResDto> findAllAllWaitingPost(Pageable pageable);

    Slice<PostResDto> searchSlice(PostSearchCondition condition, Pageable pageable);
}
