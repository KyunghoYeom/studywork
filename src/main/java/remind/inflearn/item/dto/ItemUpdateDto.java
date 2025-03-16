package remind.inflearn.item.dto;

import java.util.Optional;


public record ItemUpdateDto(
        Optional<String> name,
        Optional<Integer> price,
        Optional<Integer> stockQuantity,
        Optional<String> author,
        Optional<String> isbn,
        Optional<String> artist,
        Optional<String> etc,
        Optional<String> director,
        Optional<String> actor
) {}

