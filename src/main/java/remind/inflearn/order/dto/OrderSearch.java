package remind.inflearn.order.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import remind.inflearn.order.entity.OrderStatus;
@Data
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;


}
