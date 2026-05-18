package com.cafe.storeservice.domain.menu;

import com.cafe.storeservice.domain.BaseEntity;
import com.cafe.storeservice.dto.menu.MenuModifyReqDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Table(name = "menus")
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private MenuCategory menuCategory;

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

    public void modified(MenuModifyReqDto reqDto, MenuCategory menuCategory, String key) {
        if (menuCategory != null) this.menuCategory = menuCategory;
        if (StringUtils.hasText(reqDto.getName())) this.name = reqDto.getName();
        if (reqDto.getPrice() != null) this.price = reqDto.getPrice();
        if (StringUtils.hasText(reqDto.getDescription())) this.description = reqDto.getDescription();
        if (reqDto.getCost() != null) this.cost = reqDto.getCost();
        if (StringUtils.hasText(key)) this.imageUrl = key;
        if (reqDto.getIsActive() != null) this.isActive = reqDto.getIsActive();
    }
}
