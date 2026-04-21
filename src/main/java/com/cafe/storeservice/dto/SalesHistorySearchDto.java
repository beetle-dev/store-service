package com.cafe.storeservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SalesHistorySearchDto extends SearchDto {
    @NotNull
    private LocalDateTime from;
    @NotNull
    private LocalDateTime to;
}
