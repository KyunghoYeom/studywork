package remind.inflearn.order.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemRequestDto {
    private Long itemId;
    private int count;
}
