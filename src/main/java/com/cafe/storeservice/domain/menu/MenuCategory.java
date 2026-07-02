package com.cafe.storeservice.domain.menu;

import com.cafe.storeservice.dto.menu.MenuCategoryModifyReqDto;
import com.cafe.storeservice.dto.menu.MenuCategoryReqDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Table(name = "menu_categories")
public class MenuCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Builder.Default
    @Column(nullable = false)
    private Integer sortOrder = 0;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isActive = true;

    public void modified(MenuCategoryModifyReqDto reqDto) {
        if (StringUtils.hasText(reqDto.getName())) this.name = reqDto.getName();
        if (reqDto.getSortOrder() != null) this.sortOrder = reqDto.getSortOrder();
        if (reqDto.getIsActive() != null) this.isActive = reqDto.getIsActive();
    }
}
