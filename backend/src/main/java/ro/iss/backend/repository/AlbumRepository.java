package ro.iss.backend.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.iss.backend.domain.Album;

@Primary
public interface AlbumRepository extends JpaRepository<Album, Integer> {

    Page<Album> findByAlbumNameContainingIgnoreCase(String name, Pageable pageable);
}
