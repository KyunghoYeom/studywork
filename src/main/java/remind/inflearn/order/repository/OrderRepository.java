package remind.inflearn.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import remind.inflearn.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {


    @EntityGraph(attributePaths = {"member", "delivery"})
    @Query("select o from Order o")
    Page<Order> findAllWithMD(Pageable pageable);

}
