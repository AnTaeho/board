package hello.board.domain.post.repository;

import hello.board.controller.post.dto.req.PostSearchCondition;
import hello.board.controller.post.dto.res.PostResDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostRepositoryCustom {

    Slice<PostResDto> search(PostSearchCondition condition, Pageable pageable);

    Slice<PostResDto> findAllPostedPost(Pageable pageable);

    Slice<PostResDto> findAllAllWaitingPost(Pageable pageable);
}
