package remind.inflearn.order.entity;

import jakarta.persistence.*;
import lombok.*;
import remind.inflearn.common.BaseEntity;
import remind.inflearn.delivery.Delivery;
import remind.inflearn.delivery.DeliveryStatus;
import remind.inflearn.member.entity.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;


    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime orderDate;

    //연관관계 메서드//
    public void confirmMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void confirmOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.addOrder(this);
    }

    public void confirmDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.addOrder(this);
    }

    public void statusOrder(){
        this.orderStatus = OrderStatus.ORDER;

    }
    public void statusCancel(){
        this.orderStatus = OrderStatus.CANCEL;

    }

    private void makeOrderDate(){
        this.orderDate = LocalDateTime.now();
    }
    //생성 메서드//
    public static Order createOrder(Member member, Delivery delivery, OrderItem ... orderItems){
        Order order = Order.builder().build();
        order.confirmMember(member);
        order.confirmDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.confirmOrderItem(orderItem);
        }
        order.statusOrder();
        order.makeOrderDate();
        return order;
    }

    //비즈니스 로직//
    /**
     * 주문 취소
     */
    public void cancel(){
        if(delivery.getDeliveryStatus() == DeliveryStatus.COMP){
            throw new RuntimeException("이미 배송된 상품입니다");
        }
        this.statusCancel();
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    public int getTotalPrice(){
        int sum = 0;
        for (OrderItem orderItem : orderItems) {
            sum += orderItem.getTotalPrice();
        }
        return sum;
    }








}
