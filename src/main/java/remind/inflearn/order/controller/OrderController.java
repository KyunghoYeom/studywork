package remind.inflearn.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import remind.inflearn.order.dto.OrderDto;
import remind.inflearn.order.dto.OrderSearch;
import remind.inflearn.order.dto.OrderSearchMDDto;
import remind.inflearn.order.dto.SimpleOrderDto;
import remind.inflearn.order.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @GetMapping("/simple/page")
    public Page<SimpleOrderDto> fetchSimpleOrdersWithPaging(Pageable pageable){
        return orderService.findAllWithMD(pageable);
    }

    @GetMapping("/page")
    public Page<OrderDto> fetchOrdersWithPaging(Pageable pageable){
        return orderService.findAllWithMDI(pageable);
    }

    @GetMapping("/search")
    public List<OrderSearchMDDto>searchOrders(OrderSearch orderSearch){
        return orderService.search(orderSearch);
    }

    @GetMapping("/search/page")
    public Page<OrderSearchMDDto>searchOrdersWithPaging(OrderSearch orderSearch,  Pageable pageable){
        return orderService.searchWithPaging(orderSearch, pageable);

    }


}
