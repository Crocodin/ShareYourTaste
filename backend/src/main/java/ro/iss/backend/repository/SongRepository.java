package ro.iss.backend.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.iss.backend.domain.Song;

@Primary
public interface SongRepository extends JpaRepository<Song, Integer> {

    Page<Song> findBySongNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT s FROM Song s JOIN s.artist a WHERE UPPER(s.songName) LIKE UPPER(CONCAT('%', :name, '%')) OR UPPER(a.artistName) LIKE UPPER(CONCAT('%', :name, '%'))")
    Page<Song> searchByNameOrArtist(@Param("name") String name, Pageable pageable);
}
