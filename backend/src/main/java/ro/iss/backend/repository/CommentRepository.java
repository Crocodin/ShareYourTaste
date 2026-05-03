package ro.iss.backend.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.iss.backend.domain.Comment;
import ro.iss.backend.domain.Song;

@Primary
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Page<Comment> findBySong(Song song, Pageable pageable);
}
