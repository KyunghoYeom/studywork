package remind.inflearn.item.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("M")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie extends Item{
    private String director;
    private String actor;

    public Movie(String name, Integer price, Integer stockQuantity, String director, String actor) {
        super(name, price, stockQuantity);
        this.director = director;
        this.actor = actor;
    }

    public void updateMovieFields(String director, String actor) {
        if (director != null) this.director = director;
        if (actor != null) this.actor = actor;
    }
}
