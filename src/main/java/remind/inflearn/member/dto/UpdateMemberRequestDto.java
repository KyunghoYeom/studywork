package remind.inflearn.member.dto;

import remind.inflearn.common.Address;

import java.util.Optional;

public record UpdateMemberRequestDto(Optional<String> name,
                                     Optional<Address> address) {
}
