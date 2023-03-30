package hello.board.controller.home;

import hello.board.argumentresolver.Login;
import hello.board.controller.post.dto.res.PostResDto;
import hello.board.domain.member.entity.Member;
import hello.board.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    @GetMapping("/home")
    public ResponseEntity<Page<PostResDto>> showHomePageWithAllPost(@Login @ModelAttribute final Member loginMember, @RequestParam("page") final int page) {
        final PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("id").descending());
        final Page<PostResDto> posts = postService.findAllPostedPost(pageRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(posts);
    }
}
