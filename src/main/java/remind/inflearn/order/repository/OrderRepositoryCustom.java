package remind.inflearn.order.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import remind.inflearn.order.dto.OrderSearch;
import remind.inflearn.order.dto.OrderSearchMDDto;

import java.util.List;

public interface OrderRepositoryCustom {
    List<OrderSearchMDDto> search(OrderSearch orderSearch);
    //Page<OrderSearchMDDto> searchPageSimple(OrderSearch orderSearch, Pageable pageable);

    Page<OrderSearchMDDto> searchPageComplex(OrderSearch orderSearch, Pageable pageable);
}
