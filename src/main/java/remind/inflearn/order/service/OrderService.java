package remind.inflearn.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import remind.inflearn.common.Address;
import remind.inflearn.delivery.Delivery;
import remind.inflearn.delivery.DeliveryStatus;
import remind.inflearn.item.entity.Item;
import remind.inflearn.item.repository.ItemRepository;
import remind.inflearn.member.entity.Member;
import remind.inflearn.member.repository.MemberRepository;

import remind.inflearn.order.dto.*;
import remind.inflearn.order.entity.Order;
import remind.inflearn.order.entity.OrderItem;
import remind.inflearn.order.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(OrderRequestDto orderRequestDto){
        Member member = memberRepository.findById(orderRequestDto.getMemberId()).orElseThrow(
                () -> new RuntimeException("멤버를 찾을 수 없습니다")
        );

        Address address = (orderRequestDto.getAddress() != null) ? orderRequestDto.getAddress() : member.getAddress();

        Delivery delivery = Delivery.builder().address(address).deliveryStatus(DeliveryStatus.READY)
                .build();
        List<OrderItem>orderItems = new ArrayList<>();
        for(OrderItemRequestDto itemDto :orderRequestDto.getOrderItems()){
            Item item = itemRepository.findById(itemDto.getItemId()).orElseThrow(
                    () -> new RuntimeException("아이템을 찾을 수 없습니다")
            );
            orderItems.add(OrderItem.createOrderItem(item, item.getPrice(), itemDto.getCount()));
        }
        Order order = Order.createOrder(member, delivery,  orderItems.toArray(new OrderItem[0]));
        orderRepository.save(order);
        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new RuntimeException("해당 아이디의 주문을 찾을 수 없습니다")
        );
        order.cancel();
    }

    /*
    public List<Order>findOrders(OrderSearch orderSearch){
        return orderRepository.findAll(orderSearch);
    }

     */

    public Page<SimpleOrderDto>findAllWithMD(Pageable pageable){
        Page<Order> orderLists = orderRepository.findAllWithMD(pageable);
        return orderLists.map(SimpleOrderDto::new);

    }

    public Page<OrderDto>findAllWithMDI(Pageable pageable){
        Page<Order> orderLists = orderRepository.findAllWithMD(pageable);
        return orderLists.map(OrderDto::new);

    }

    public List<OrderSearchMDDto>search(OrderSearch orderSearch){
        return orderRepository.search(orderSearch);

    }

    public Page<OrderSearchMDDto>searchWithPaging(OrderSearch orderSearch, Pageable pageable){
        return orderRepository.searchPageComplex(orderSearch, pageable);

    }





}
