package remind.inflearn.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import remind.inflearn.member.dto.*;
import remind.inflearn.member.entity.Member;
import remind.inflearn.member.service.MemberService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public CreateMemberResponseDto login(@RequestBody @Valid CreateMemberRequestDto dto){
        Member member = Member.builder().name(dto.name()).address(dto.address()).build();
        return CreateMemberResponseDto.from(memberService.join(member));
    }

    @PostMapping("/{id}")
    public UpdateMemberResponseDto update(@PathVariable Long id, @RequestBody UpdateMemberRequestDto dto){
        return memberService.updateMember(id, dto);

    }

    @GetMapping
    public List<MemberSimpleDto> fetchMembers(){
        return memberService.findMembers();
    }


}
