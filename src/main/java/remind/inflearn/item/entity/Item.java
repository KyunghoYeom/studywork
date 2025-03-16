package remind.inflearn.item.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import remind.inflearn.common.BaseEntity;
import remind.inflearn.common.exception.NotEnoughStockException;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;

    private Integer price;

    private Integer stockQuantity;

    public Item(String name, Integer price, Integer stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    //비즈니스 로직//
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity){
        int newQ = this.stockQuantity-quantity;
        if(newQ < 0){
            throw new NotEnoughStockException("재고 수량은 음수일 수 없습니다");

        }
        this.stockQuantity = newQ;
    }

    public void updateCommonFields(String name, Integer price, Integer stockQuantity) {
        if (name != null) this.name = name;
        if (price != null) this.price = price;
        if (stockQuantity != null) this.stockQuantity = stockQuantity;
    }
}
