package livinglab.cse_back.review.repository;

import livinglab.cse_back.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
