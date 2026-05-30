package ro.iss.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.iss.backend.domain.Comment;
import ro.iss.backend.domain.Song;
import ro.iss.backend.dto.CommentDTO;
import ro.iss.backend.mapper.CommentMapper;
import ro.iss.backend.repository.CommentRepository;
import ro.iss.backend.repository.SongRepository;
import ro.iss.backend.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    protected final CommentRepository commentRepository;
    protected final SongRepository songRepository;
    protected final CommentMapper commentMapper;
    protected final UserRepository userRepository;

    public Page<CommentDTO> findBySong(int songId, Pageable pageable) {
        log.debug("Request to get comments by songId {}", songId);
        Song song = this.songRepository.getReferenceById(songId);
        return commentRepository.findBySong(song, pageable).map(commentMapper::toDto);
    }

    public void commentOnSong(int songId, CommentDTO commentDTO, int userId) {
        log.debug("Request to save comment on songId {}", songId);
        commentRepository.save(Comment.builder()
                .user(userRepository.getReferenceById(userId))
                .song(songRepository.getReferenceById(songId))
                .content(commentDTO.getContent())
                .build()
        );
    }

    public List<CommentDTO> findFirstFiveByUser(int userId) {
        log.debug("Request to get comments by userId {}", userId);
        return commentRepository.findByUser_UserId(userId)
                .stream().map(commentMapper::toDto).limit(5).toList();
    }
}
