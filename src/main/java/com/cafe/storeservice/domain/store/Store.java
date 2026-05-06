package com.cafe.storeservice.domain.store;

import com.cafe.storeservice.domain.BaseEntity;
import com.cafe.storeservice.dto.store.ModifyStoreReqDto;
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

    private String email;

    private LocalTime openTime;
    private LocalTime closeTime;

    @Builder.Default
    @Column(nullable = false)
    private boolean active = true;

    public void modified(ModifyStoreReqDto reqDto) {
        if (StringUtils.hasText(reqDto.getName())) this.name = reqDto.getName();
        if (StringUtils.hasText(reqDto.getAddress())) this.address = reqDto.getAddress();
        if (StringUtils.hasText(reqDto.getPhone())) this.phone= reqDto.getPhone();
        if (StringUtils.hasText(reqDto.getEmail())) this.email= reqDto.getEmail();
        if (reqDto.getOpenTime() != null ) this.openTime = reqDto.getOpenTime();
        if (reqDto.getCloseTime() != null ) this.closeTime = reqDto.getCloseTime();
        this.active = reqDto.isActive();
    }
}
