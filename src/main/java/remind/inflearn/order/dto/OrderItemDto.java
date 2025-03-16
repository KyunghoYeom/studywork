package remind.inflearn.order.dto;

import lombok.Data;
import remind.inflearn.order.entity.OrderItem;

@Data
public class OrderItemDto {
    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemDto(OrderItem orderItem){
        this.itemName = orderItem.getItem().getName();
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();

    }
}
