package livinglab.cse_back.order.entity;

import jakarta.persistence.*;
import livinglab.cse_back.menu.entity.Menu;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;   // 어떤 메뉴를 주문했는지

    @Column(nullable = false)
    private Integer price;   // 주문 금액 (메뉴 가격)

    @Column(nullable = false)
    private Integer quantity;   // 주문 수량

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
