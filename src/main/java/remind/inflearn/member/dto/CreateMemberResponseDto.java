package remind.inflearn.member.dto;

public record CreateMemberResponseDto(
        Long id
) {
    public static CreateMemberResponseDto from(Long id){
        return new CreateMemberResponseDto(id);
    }
}
