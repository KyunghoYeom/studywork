package remind.inflearn.order.dto;

import lombok.Data;
import remind.inflearn.common.Address;
import remind.inflearn.order.entity.Order;
import remind.inflearn.order.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems;

    public OrderDto(Order order){
        this.orderId = order.getId();
        this.name = order.getMember().getName();
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getOrderStatus();
        this.address = order.getDelivery().getAddress();
        this.orderItems = order.getOrderItems().stream().
                map(oi->new OrderItemDto(oi))
                .collect(Collectors.toList());

    }


}
