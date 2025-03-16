package remind.inflearn.item.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("A")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Album extends Item{
    private String artist;
    private String etc;
    public Album(String name, Integer price, Integer stockQuantity, String artist, String etc) {
        super(name, price, stockQuantity);
        this.artist = artist;
        this.etc = etc;
    }

    public void updateAlbumFields(String artist, String etc) {
        if (artist != null) this.artist = artist;
        if (etc != null) this.etc = etc;
    }
}
