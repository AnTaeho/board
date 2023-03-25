package hello.board.domain.member.service;

import hello.board.controller.member.dto.req.LoginFormDto;
import hello.board.controller.member.dto.req.MemberRegisterReqDto;
import hello.board.controller.member.dto.req.MemberUpdateReqDto;
import hello.board.controller.member.dto.res.AllMemberInfoDto;
import hello.board.controller.member.dto.res.MemberRegisterResDto;
import hello.board.controller.member.dto.res.MemberResDto;
import hello.board.controller.member.dto.res.MemberUpdateResDto;
import hello.board.domain.member.entity.Follow;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.repository.follow.FollowRepository;
import hello.board.domain.member.repository.member.MemberRepository;
import hello.board.exception.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    /**
     * 회원 가입 메서드
     * 가입 폼을 받아서 회원 엔티티를 생성하고 저장한다.
     * @return MemberRegisterResDto
     */
    @Transactional
    public MemberRegisterResDto joinMember(final MemberRegisterReqDto memberRegisterReqDto) {
        return new MemberRegisterResDto(saveMember(memberRegisterReqDto));
    }

    private Member saveMember(final MemberRegisterReqDto memberRegisterReqDto) {
        return memberRepository.save(Member.createMember(memberRegisterReqDto));
    }

    /**
     * 회원 찾기 메서드
     * 아이디 값을 받아서 회원을 찾는다.
     * @return MemberResDto
     */
    public MemberResDto findById(final Long memberId) {
        return new MemberResDto(findMember(memberId));
    }

    /**
     * 전체 회원 조회 메서드
     * 전체 회원 목록을 페이징해서 반환한다.
     * @return Page<MemberResDto>
     */
    public Page<MemberResDto> findAll(final Pageable pageable) {
        return memberRepository.findAll(pageable)
                .map(MemberResDto::new);
    }

    /**
     * 회원 정보 업데이트 메서드
     * 업데이트 폼과 회원 아이디 값을 받아서
     * 정보를 업데이트한다.
     * @return MemberUpdateResDto
     */
    @Transactional
    public MemberUpdateResDto updateMember(final Long memberId, final MemberUpdateReqDto MemberUpdateReqDto) {
        Member findMember = findMember(memberId);
        findMember.updateInfo(MemberUpdateReqDto);
        return new MemberUpdateResDto(findMember);
    }

    /**
     * 회원 삭제 메서드
     * 아이디 값을 받아서 회원을 삭제한다.
     */
    @Transactional
    public void deleteMember(final Long memberId) {
        memberRepository.deleteById(memberId);
    }

    /**
     * 로그인 메서드
     * 로그인 폼을 받아서 리포지토리에서 매칭되는 멤버를 찾는다.
     * 있다면 해당 멤버를 반환하고, 없다면 null을 반환한다.
     * @return Member
     */
    @Transactional
    public Member login(final LoginFormDto form) {
        return memberRepository.findByLoginId(form.getLoginId())
                .filter(m -> m.getPassword().equals(form.getPassword()))
                .orElse(null);
    }

    /**
     * 팔로우 메서드
     * 팔로우할 멤버의 아이디 값과 로그인 되어있는 멤버를 받아서
     * 팔로우 여부를 체크한 뒤 없다면 팔로우하고 있다면 팔로우를 취소한다.
     * @return String
     */
    @Transactional
    public String followMember(final Long memberId, Member fromMember) {
        final Member toMember = findMember(memberId);
        if (toMember.getId().equals(fromMember.getId())) {
            return "자기 자신은 팔로우 할 수 없습니다.";
        }
        if (followRepository.isAlreadyFollow(toMember, fromMember)) {
            followRepository.deleteByFromMemberAndToMember(fromMember, toMember);
            return "unfollow success";
        }
        followRepository.save(new Follow(fromMember, toMember));
        return "follow success";
    }

    /**
     * 회원의 모든 정보 조회 메서드
     * OneToMany 관계에서 페치 조인시 생기는 문제점을 확인하기 위해 구현한 메서드
     * @return AllMemberInfoDto
     */
    public AllMemberInfoDto findAllInfo(final Long memberId) {
        return new AllMemberInfoDto(findMemberWithAllInfo(memberId));
    }

    //공용 메서드
    public Member findMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    throw new CustomNotFoundException(String.format("id=%s not found",memberId));
                });
    }

    private Member findMemberWithAllInfo(final Long memberId) {
        return memberRepository.findMemberById(memberId)
                .orElseThrow(() -> {
                    throw new CustomNotFoundException(String.format("id=%s not found",memberId));
                });
    }
}
