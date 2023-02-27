package hello.board.controller.home;

import hello.board.controller.post.dto.res.PostResDto;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.service.MemberService;
import hello.board.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminHomeController {

    private final MemberService memberService;
    private final PostService postService;

    @GetMapping
    public String adminHome() {
        return "home/adminHome";
    }

    //멤버 전체 조회 화면 메서드
    @GetMapping("/members")
    public String findAll(Model model, @RequestParam("page") int page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Member> members = memberService.findAll(pageRequest);
        model.addAttribute("members", members);
        return "members/members";
    }

    //모든 게시글 화면 메서드
    @GetMapping("/posts")
    public String findAllPost(Model model, @RequestParam("page") int page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        List<PostResDto> posts = findAllPost(pageRequest);
        model.addAttribute("posts", posts);
        return "posts/posts";
    }

    private List<PostResDto> findAllPost(Pageable pageable) {
        return postService.findAllPost(pageable)
                .stream()
                .map(PostResDto::new)
                .collect(Collectors.toList());
    }
}
