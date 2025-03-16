package remind.inflearn.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import remind.inflearn.item.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
