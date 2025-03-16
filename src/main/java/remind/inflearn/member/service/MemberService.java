package remind.inflearn.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import remind.inflearn.member.dto.MemberSimpleDto;
import remind.inflearn.member.dto.UpdateMemberRequestDto;
import remind.inflearn.member.dto.UpdateMemberResponseDto;
import remind.inflearn.member.entity.Member;
import remind.inflearn.member.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        Member saveMember = memberRepository.save(member);
        return saveMember.getId();


    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new RuntimeException("중복된 회원입니다.");
                });
    }

    @Transactional
    public UpdateMemberResponseDto updateMember(Long memberId, UpdateMemberRequestDto dto){
        Member uMember = memberRepository.findById(memberId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 회원 아이디입니다")
        );
        dto.name().ifPresent(uMember::updateName);
        dto.address().ifPresent(uMember::updateAddress);
        return UpdateMemberResponseDto.from(memberId);
    }
    public List<MemberSimpleDto> findMembers(){
        List<Member> members = memberRepository.findAll();
        return members.stream().map(
                MemberSimpleDto::new
        ).toList();
    }

    public Member findOne(Long memberId){
        return memberRepository.findById(memberId).orElseThrow(
                ()->new RuntimeException("존재하지 않는 회원 아이디입니다")
        );
    }
}
