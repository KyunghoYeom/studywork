package remind.inflearn.member.dto;

import jakarta.validation.constraints.NotNull;
import remind.inflearn.common.Address;

public record CreateMemberRequestDto(
        @NotNull
        String name,
        @NotNull
        Address address
) {

}
