package remind.inflearn.member.entity;


import jakarta.persistence.*;
import lombok.*;
import remind.inflearn.common.Address;
import remind.inflearn.common.BaseEntity;
import remind.inflearn.order.entity.Order;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    public void updateName(String name) {
        this.name = name;
    }

    public void updateAddress(Address address) {
        this.address = address;
    }
}
