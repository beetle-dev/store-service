package com.cafe.storeservice.dto.order;

import com.cafe.storeservice.domain.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderUpdateReqDto {
    private Status status;
}
