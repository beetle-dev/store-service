package com.cafe.storeservice.dto.order;

import com.cafe.storeservice.dto.SearchDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderSearchDto extends SearchDto {
    LocalDateTime orderStartDate;
    LocalDateTime orderEndDate;
}
