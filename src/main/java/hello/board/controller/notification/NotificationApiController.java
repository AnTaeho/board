package hello.board.controller.notification;

import hello.board.controller.notification.dto.req.NotificationUpdateReqDto;
import hello.board.controller.notification.dto.req.NotificationWriteDto;
import hello.board.controller.notification.dto.res.NotificationResDto;
import hello.board.controller.notification.dto.res.NotificationUpdateResDto;
import hello.board.domain.member.entity.Member;
import hello.board.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static hello.board.controller.member.session.SessionConst.LOGIN_MEMBER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationApiController {

    private final NotificationService notificationService;

    @PostMapping("/notice")
    public ResponseEntity<NotificationResDto> createNotification(HttpServletRequest request, @RequestBody NotificationWriteDto writeDto) {
        Member loginMember = findLoginMember(request);
        NotificationResDto notificationResDto = notificationService.makeNotice(writeDto, loginMember.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(notificationResDto);
    }

    @PatchMapping("/edit/{noticeId}")
    public ResponseEntity<NotificationUpdateResDto> updateNotification(@PathVariable Long noticeId, @RequestBody NotificationUpdateReqDto updateReqDto) {
        NotificationUpdateResDto notificationUpdateResDto = notificationService.updateNotification(noticeId, updateReqDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(notificationUpdateResDto);

    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<NotificationResDto> findSingleNotification(@PathVariable Long noticeId) {
        NotificationResDto findNotice = notificationService.findById(noticeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(findNotice);
    }

    @GetMapping("/all")
    public ResponseEntity<List<NotificationResDto>> findAllNotification() {
        List<NotificationResDto> allNotifications = notificationService.findAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allNotifications);
    }

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long noticeId) {
        notificationService.deleteNotification(noticeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("notification delete");
    }

    private Member findLoginMember(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Member) session.getAttribute(LOGIN_MEMBER);
    }
}
