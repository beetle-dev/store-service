package com.cafe.storeservice.dto.store;

import com.cafe.storeservice.dto.SearchDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StoreSearchDto extends SearchDto {
    private String name;
    private String address;
    private String phone;
    private String email;
    private Boolean active;
}
