package remind.inflearn.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import remind.inflearn.item.dto.ItemUpdateDto;
import remind.inflearn.item.entity.Album;
import remind.inflearn.item.entity.Book;
import remind.inflearn.item.entity.Item;
import remind.inflearn.item.entity.Movie;
import remind.inflearn.item.repository.ItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;
    @Transactional
    public Long saveItem(Item item){
        Item saveItem = itemRepository.save(item);
        return saveItem.getId();
    }

    @Transactional
    public Long updateItem(Long itemId, ItemUpdateDto dto){
        Item updateItem = itemRepository.findById(itemId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 아이템입니다")
        );

        updateItem.updateCommonFields(
                dto.name().orElse(null),
                dto.price().orElse(null),
                dto.stockQuantity().orElse(null)
        );


        if (updateItem instanceof Book book) {
            book.updateBookFields(dto.author().orElse(null), dto.isbn().orElse(null));
        } else if (updateItem instanceof Album album) {
            album.updateAlbumFields(dto.artist().orElse(null), dto.etc().orElse(null));
        } else if (updateItem instanceof Movie movie) {
            movie.updateMovieFields(dto.director().orElse(null), dto.actor().orElse(null));
        }

        return updateItem.getId();


    }
    public List<Item> findAll(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId)
    {
        return itemRepository.findById(itemId).orElseThrow(
                ()->new RuntimeException("존재하지 않는 아이템 아이디입니다")
        );
    }




}
