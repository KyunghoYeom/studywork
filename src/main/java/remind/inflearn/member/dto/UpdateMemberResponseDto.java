package remind.inflearn.member.dto;

public record UpdateMemberResponseDto(
        Long id
) {
    public static UpdateMemberResponseDto from(Long id){
        return new UpdateMemberResponseDto(id);
    }
}

