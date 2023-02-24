package hello.board.service;

import hello.board.entity.Member;
import hello.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member joinMember(String name, int age) {
        return memberRepository.save(new Member(name, age));
    }

    public Member findById(Long id) {
        return findMember(id);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Transactional
    public void updateMember(Long id, Member updateMember) {
        Member findMember = findMember(id);
        findMember.setName(updateMember.getName());
        findMember.setAge(updateMember.getAge());
    }

    @Transactional
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("회원이 없어");
                });
    }

}
