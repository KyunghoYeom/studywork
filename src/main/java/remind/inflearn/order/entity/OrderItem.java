package remind.inflearn.order.entity;

import jakarta.persistence.*;
import lombok.*;
import remind.inflearn.common.BaseEntity;
import remind.inflearn.item.entity.Item;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = OrderItem.builder().build();
        orderItem.detOrderPrice(orderPrice);
        orderItem.detCount(count);
        orderItem.detItem(item);
        return orderItem;


    }

    public void detOrderPrice(int orderPrice){
        this.orderPrice = orderPrice;
    }
    public void detCount(int count){
        this.count = count;
    }
    public void detItem(Item item){
        this.item = item;
        this.item.removeStock(this.count);
    }

    public void addOrder(Order order){
        this.order = order;
    }

    //비즈니스 로직
    public void cancel(){
        this.item.addStock(count);
    }

    public int getTotalPrice(){
        return this.orderPrice * this.count;
    }



}
