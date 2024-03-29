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
import hello.board.exception.badrequest.AlreadyJoinBadRequestException;
import hello.board.exception.badrequest.SelfFollowBadRequestException;
import hello.board.exception.notfound.NotMemberNotFoundException;
import hello.board.support.annotation.CreateTransactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    @CreateTransactional
    public MemberRegisterResDto joinMember(final MemberRegisterReqDto memberRegisterReqDto) {
        checkAlreadyJoin(memberRegisterReqDto.getLoginId());
        return new MemberRegisterResDto(saveMember(memberRegisterReqDto));
    }

    private void checkAlreadyJoin(String loginId) {
        if (memberRepository.existsByLoginId(loginId)){
            throw new AlreadyJoinBadRequestException();
        }
    }

    private Member saveMember(final MemberRegisterReqDto memberRegisterReqDto) {
        return memberRepository.save(Member.createMember(memberRegisterReqDto));
    }

    public MemberResDto findById(final Long memberId) {
        return new MemberResDto(memberRepository.findMember(memberId));
    }

    public Slice<MemberResDto> findAll(final Pageable pageable) {
        return memberRepository.findAll(pageable)
                .map(MemberResDto::new);
    }

    public double findFollowerAgeAverage(final Long memberId) {
        final Member toMember = memberRepository.findMember(memberId);
        final Optional<Double> average = followRepository.findAverageFollowerAge(toMember);
        return average.isEmpty() ? 0 : average.get();
    }

    @Transactional
    public MemberUpdateResDto updateMember(final Long memberId, final MemberUpdateReqDto memberUpdateReqDto) {
        checkAlreadyJoin(memberUpdateReqDto.getLoginId());
        Member findMember = memberRepository.findMember(memberId);
        findMember.updateInfo(memberUpdateReqDto);
        return new MemberUpdateResDto(findMember);
    }

    @Transactional
    public void deleteMember(final Long memberId) {
        memberRepository.deleteById(memberId);
    }

    @Transactional
    public Member login(final LoginFormDto form) {
        return memberRepository.findByLoginId(form.getLoginId())
                .filter(m -> m.getPassword().equals(form.getPassword()))
                .orElseThrow(NotMemberNotFoundException::new);
    }

    @CreateTransactional
    public String followMember(final Long memberId, Member fromMember) {
        final Member toMember = memberRepository.findMember(memberId);
        if (toMember.getId().equals(fromMember.getId())) {
            throw new SelfFollowBadRequestException();
        }
        if (followRepository.isAlreadyFollow(toMember, fromMember)) {
            followRepository.deleteByFromMemberAndToMember(fromMember, toMember);
            return "unfollow success";
        }
        followRepository.save(new Follow(fromMember, toMember));
        return "follow success";
    }

    public AllMemberInfoDto findAllInfo(final Long memberId) {
        return new AllMemberInfoDto(memberRepository.findMemberWithAllInfo(memberId));
    }

}
