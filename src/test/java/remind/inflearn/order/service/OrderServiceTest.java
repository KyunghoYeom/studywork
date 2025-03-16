package remind.inflearn.order.service;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import remind.inflearn.common.Address;
import remind.inflearn.item.entity.Book;
import remind.inflearn.item.entity.Item;
import remind.inflearn.member.entity.Member;
import remind.inflearn.order.dto.OrderItemRequestDto;
import remind.inflearn.order.dto.OrderRequestDto;
import remind.inflearn.order.entity.Order;
import remind.inflearn.order.entity.OrderStatus;
import remind.inflearn.order.repository.OrderRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    EntityManager em;

    private Book createBook(Integer stockQuantity) {
        Book book = new Book("book1", 10000, stockQuantity, "luther", "11");
        em.persist(book);
        return book;
    }

    private Member createMember(Address address) {
        Member member = Member.builder().name("철수").address(address).build();
        em.persist(member);
        return member;
    }

    @Test
    void 주문_성공(){
        Address address = Address.builder().city("서울").street("도장").zipcode("10").build();

        Member member = createMember(address);

        Item item = createBook(100);

        int orderCount = 2;
        OrderRequestDto dto = OrderRequestDto.builder()
                .memberId(member.getId())
                .address(address)
                .orderItems(List.of(
                        OrderItemRequestDto.builder()
                                .itemId(item.getId())
                                .count(orderCount)
                                .build()
                ))
                .build();
        Long orderId = orderService.order(dto);

        Order findOrder = orderRepository.findById(orderId).orElseThrow();

        assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(findOrder.getMember().getName()).isEqualTo("철수");
        assertThat(findOrder.getOrderItems().get(0).getCount()).isEqualTo(2);
        assertThat(findOrder.getOrderItems().get(0).getItem().getStockQuantity()).isEqualTo(98);


    }



    @Test
    void 수량_초과_오류(){
        Address address = Address.builder().city("서울").street("도장").zipcode("10").build();

        Member member = createMember(address);

        Item item = createBook(1);

        int orderCount = 2;
        OrderRequestDto dto = OrderRequestDto.builder()
                .memberId(member.getId())
                .address(address)
                .orderItems(List.of(
                        OrderItemRequestDto.builder()
                                .itemId(item.getId())
                                .count(orderCount)
                                .build()
                ))
                .build();
        assertThatThrownBy(() -> orderService.order(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("재고 수량은 음수일 수 없습니다");


    }

    @Test
    void 주문_취소(){
        Address address = Address.builder().city("서울").street("도장").zipcode("10").build();

        Member member = createMember(address);

        Item item = createBook(100);

        int orderCount = 2;
        OrderRequestDto dto = OrderRequestDto.builder()
                .memberId(member.getId())
                .address(address)
                .orderItems(List.of(
                        OrderItemRequestDto.builder()
                                .itemId(item.getId())
                                .count(orderCount)
                                .build()
                ))
                .build();
        Long orderId = orderService.order(dto);
        orderService.cancelOrder(orderId);
        Order findOrder = orderRepository.findById(orderId).orElseThrow();
        assertThat(findOrder.getOrderItems().get(0).getItem().getStockQuantity()).isEqualTo(100);


    }


}