package remind.inflearn.item.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("B")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Item{
    private String author;
    private String isbn;

    public Book(String name, Integer price, Integer stockQuantity, String author, String isbn) {
        super(name, price, stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }

    public void updateBookFields(String author, String isbn) {
        if (author != null) this.author = author;
        if (isbn != null) this.isbn = isbn;
    }
}
