package ro.iss.backend.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.iss.backend.domain.Comment;
import ro.iss.backend.domain.Song;

import java.util.List;

@Primary
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Page<Comment> findBySong(Song song, Pageable pageable);

    @Query("SELECT c FROM Comment c JOIN FETCH c.song s JOIN FETCH c.user u WHERE c.user.userId = :userId ORDER BY c.commentId DESC")
    List<Comment> findByUser_UserId(Integer userId);
}
