package ro.iss.backend.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.iss.backend.domain.Artist;

@Primary
public interface ArtistRepository extends JpaRepository<Artist, Integer> {
}
