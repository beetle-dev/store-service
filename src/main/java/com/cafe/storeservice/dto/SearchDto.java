package com.cafe.storeservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Setter
@Getter
public class SearchDto {

    @Min(0)
    private int page = 0;

    @Max(100)
    private int size = 20;

    private String sort = "createdAt";

    @Pattern(regexp = "^(ASC|DESC)$", message = "direction은 ASC 또는 DESC만 허용됩니다.")
    private String direction = "DESC";

    public static Pageable toPageable(SearchDto searchDto) {
        Sort sort = Sort.by(
                Sort.Direction.fromString(searchDto.getDirection()),
                searchDto.getSort()
        );

        return PageRequest.of(searchDto.getPage(), searchDto.getSize(), sort);
    }
}
