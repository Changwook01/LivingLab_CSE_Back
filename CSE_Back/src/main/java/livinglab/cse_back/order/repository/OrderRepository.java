package livinglab.cse_back.order.repository;

import livinglab.cse_back.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 오늘 주문 수
    @Query("SELECT COUNT(o) FROM Order o WHERE CAST(o.createdAt AS date) = :today")
    long countTodayOrders(@Param("today") LocalDate today);

    // 오늘 총 매출
    @Query("SELECT COALESCE(SUM(o.price * o.quantity), 0) FROM Order o WHERE CAST(o.createdAt AS date) = :today")
    Integer sumTodayRevenue(@Param("today") LocalDate today);

    // 오늘 가장 많이 팔린 메뉴 이름
    @Query("SELECT o.menu.name FROM Order o WHERE CAST(o.createdAt AS date) = :today GROUP BY o.menu.name ORDER BY COUNT(o) DESC")
    String findTopMenuToday(@Param("today") LocalDate today);

}
