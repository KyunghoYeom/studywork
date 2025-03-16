package remind.inflearn.order.dto;

import lombok.Data;
import remind.inflearn.common.Address;
import remind.inflearn.order.entity.Order;
import remind.inflearn.order.entity.OrderStatus;

import java.time.LocalDateTime;

@Data
public class SimpleOrderDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private Address address;

    public SimpleOrderDto(Order order){
        this.orderId = order.getId();
        this.name = order.getMember().getName();
        this.orderDate = order.getOrderDate();
        this.status = order.getOrderStatus();
        this.address = order.getDelivery().getAddress();

    }
}
