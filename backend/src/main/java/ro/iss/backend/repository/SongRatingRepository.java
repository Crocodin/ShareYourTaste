package ro.iss.backend.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.iss.backend.domain.SongRating;
import ro.iss.backend.domain.SongRatingId;

import java.util.List;
import java.util.Optional;

@Primary
public interface SongRatingRepository extends JpaRepository<SongRating, Integer> {

    Optional<SongRating> findById(SongRatingId id);

    @Query("SELECT AVG(r.rating) FROM SongRating r WHERE r.song.songId = :songId")
    Double findAverageRatingBySongId(@Param("songId") Integer songId);

    @Query("SELECT sr FROM SongRating sr JOIN FETCH sr.song s JOIN FETCH s.artist JOIN FETCH s.album WHERE sr.user.userId = :userId ORDER BY sr.createdAt desc")
    List<SongRating> findByUser_UserId(@Param("userId") Integer userId);
}
