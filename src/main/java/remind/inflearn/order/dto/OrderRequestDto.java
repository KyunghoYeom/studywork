package remind.inflearn.order.dto;

import lombok.Builder;
import lombok.Data;
import remind.inflearn.common.Address;

import java.util.List;

@Data
@Builder
public class OrderRequestDto {
    private Long memberId;
    private Address address;
    private List<OrderItemRequestDto> orderItems;

}
