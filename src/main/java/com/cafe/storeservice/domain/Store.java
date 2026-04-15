package com.cafe.storeservice.domain;

import com.cafe.storeservice.dto.StoreReqDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.time.LocalTime;

@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "stores")
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 200)
    private String address;

    @Column(length = 20)
    private String phone;

    private LocalTime openTime;
    private LocalTime closeTime;

    @Builder.Default
    @Column(nullable = false)
    private boolean isActive = true;

    public void modified(StoreReqDto reqDto) { // todo static 필요 없나?
        if (StringUtils.hasText(reqDto.getName())) this.name = reqDto.getName();
        if (StringUtils.hasText(reqDto.getAddress())) this.address = reqDto.getAddress();
        if (StringUtils.hasText(reqDto.getPhone())) this.phone= reqDto.getPhone();
        if (reqDto.getOpenTime() != null ) this.openTime = reqDto.getOpenTime();
        if (reqDto.getCloseTime() != null ) this.closeTime = reqDto.getCloseTime();
    }
}
