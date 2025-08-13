package livinglab.cse_back.menu.dto;

import livinglab.cse_back.menu.entity.Menu;
import lombok.Getter;

@Getter
public class MenuDto {
    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;
    private String category;

    // Menu 엔티티를 받아서 DTO로 변환해주는 생성자
    public MenuDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.imageUrl = menu.getImageUrl();
        this.category = menu.getCategory();
    }
}

