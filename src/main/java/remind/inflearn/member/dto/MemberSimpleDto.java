package remind.inflearn.member.dto;

import lombok.Data;
import remind.inflearn.common.Address;
import remind.inflearn.member.entity.Member;

@Data
public class MemberSimpleDto {
    private String name;
    private Address address;

    public MemberSimpleDto(Member member){
        this.name = member.getName();
        this.address = member.getAddress();
    }
}
