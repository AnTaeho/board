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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    //홈 화면 메서드
    @GetMapping("/home")
    public ResponseEntity homeLoginV3ArgumentResolver(@Login Member loginMember, Model model) {

        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }


    //모든 게시글 화면 메서드
    @GetMapping("/home/posts")
    public ResponseEntity<Page<PostResDto>> findAllPost(@RequestParam("page") int page) {

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("id").descending());
        Page<PostResDto> posts = postService.findAllPost(pageRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(posts);
    }
}
