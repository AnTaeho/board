package hello.board.domain.member.service;

import hello.board.controller.member.dto.req.MemberRegisterReqDto;
import hello.board.controller.member.dto.req.MemberUpdateReqDto;
import hello.board.controller.member.dto.res.MemberRegisterResDto;
import hello.board.controller.member.dto.res.MemberResDto;
import hello.board.controller.member.dto.res.MemberUpdateResDto;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.repository.MemberRepository;
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

    @Transactional
    public MemberRegisterResDto joinMember(MemberRegisterReqDto memberRegisterReqDto) {
        return new MemberRegisterResDto(saveMember(memberRegisterReqDto));
    }

    private Member saveMember(MemberRegisterReqDto memberRegisterReqDto) {
        return memberRepository.save(createMember(memberRegisterReqDto));
    }

    private Member createMember(MemberRegisterReqDto memberRegisterReqDto) {

        return Member.builder()
                .name(memberRegisterReqDto.getName())
                .age(memberRegisterReqDto.getAge())
                .loginId(memberRegisterReqDto.getLoginId())
                .password(memberRegisterReqDto.getPassword())
                .role(memberRegisterReqDto.getRole())
                .build();
    }

    public MemberResDto findById(Long id) {
        return new MemberResDto(findMember(id));
    }

    public Page<MemberResDto> findAll(Pageable pageable) {
        return memberRepository.findAll(pageable)
                .map(MemberResDto::new);
    }

    @Transactional
    public MemberUpdateResDto updateMember(Long id, MemberUpdateReqDto MemberUpdateReqDto) {
        Member findMember = findMember(id);
        findMember.updateInfo(MemberUpdateReqDto);
        return new MemberUpdateResDto(findMember);
    }

    @Transactional
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    @Transactional
    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    throw new CustomNotFoundException(String.format("id=%s not found",memberId));
                });
    }

}
