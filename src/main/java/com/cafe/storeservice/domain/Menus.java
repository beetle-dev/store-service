package com.cafe.storeservice.domain;

import com.cafe.storeservice.dto.MenuReqDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Menus extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private MenuCategories menuCategories;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 0)
    private BigDecimal price;

    @Column(precision = 10, scale = 0)
    private BigDecimal cost;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(length = 500)
    private String imageUrl;

    public void modified(MenuReqDto reqDto, String key) {
        if (reqDto.getPrice() != null) this.price = reqDto.getPrice();
        if (reqDto.getIsActive() != null) this.isActive = reqDto.getIsActive();
        if (StringUtils.hasText(key)) this.imageUrl = key;
    }
}
