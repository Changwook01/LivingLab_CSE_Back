package livinglab.cse_back.menu.repository;

import livinglab.cse_back.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
