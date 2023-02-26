package hello.board.controller.home;

import hello.board.controller.dto.res.PostResDto;
import hello.board.entity.member.Member;
import hello.board.service.MemberService;
import hello.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    //현재 멤버 전체 조회하는 화면 미구현.
    @GetMapping("/members")
    public String findAll(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "members/members";
    }

    //모든 게시글 화면 메서드
    @GetMapping("/posts")
    public String findAllPost(Model model) {

        //모든 게시글을 찾아온다.
        List<PostResDto> posts = findAllPost();
        model.addAttribute("posts", posts);
        return "posts/posts";
    }

    private List<PostResDto> findAllPost() {
        return postService.findAllPost()
                .stream()
                .map(PostResDto::new)
                .collect(Collectors.toList());
    }
}
