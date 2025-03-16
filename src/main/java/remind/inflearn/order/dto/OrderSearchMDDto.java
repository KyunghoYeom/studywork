package remind.inflearn.order.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EnumExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.StringPath;
import lombok.Data;
import remind.inflearn.common.Address;
import remind.inflearn.common.QAddress;
import remind.inflearn.order.entity.Order;
import remind.inflearn.order.entity.OrderStatus;

import java.time.LocalDateTime;

@Data
public class OrderSearchMDDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private Address address;



    @QueryProjection
    public OrderSearchMDDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus status, Address address){
        this.orderId = orderId;
        this.name =name;
        this.orderDate = orderDate;
        this.status = status;
        this.address = address;

    }


}
