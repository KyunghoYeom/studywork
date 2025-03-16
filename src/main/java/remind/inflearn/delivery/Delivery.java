package remind.inflearn.delivery;

import jakarta.persistence.*;
import lombok.*;
import remind.inflearn.common.Address;
import remind.inflearn.common.BaseEntity;
import remind.inflearn.order.entity.Order;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Delivery extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    public void addOrder(Order order){
        this.order = order;
    }


}
