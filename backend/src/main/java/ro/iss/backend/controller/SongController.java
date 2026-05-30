package ro.iss.backend.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ro.iss.backend.domain.User;
import ro.iss.backend.dto.CommentDTO;
import ro.iss.backend.dto.RatingDTO;
import ro.iss.backend.dto.SongDTO;
import ro.iss.backend.exception.FailedAuthentication;
import ro.iss.backend.service.CommentService;
import ro.iss.backend.service.SongService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/songs")
public class SongController {
    protected final SongService songService;
    protected final CommentService commentService;

    @GetMapping("/search")
    public Page<SongDTO> findByName(@RequestParam String name, Pageable pageable, HttpSession session) {
        log.debug("Request to get songs by name : {} and page: {}", name, pageable);
        User user = ((User) session.getAttribute("user"));
        if (user == null || user.getUserId() == -1) {
            log.warn("User is not authenticated");
            return songService.findByNameOrArtist(name, pageable);
        }
        return songService.findByNameOrArtist(name, pageable, user.getUserId());
    }

    @PostMapping("/{id}/rate")
    public void rateSong(@PathVariable Integer id, @RequestBody RatingDTO ratingDTO, HttpSession session) {
        log.debug("Request to rate song : {}", id);
        User user = (User) session.getAttribute("user");
        if (user == null || user.getUserId() == -1) {
            log.warn("user is not authenticated");
            throw new FailedAuthentication("Can't rate song without authentification");
        }
        songService.rateSong(id, user.getUserId(), ratingDTO.getRating());
    }

    @GetMapping("/{id}")
    public SongDTO getSongById(@PathVariable Integer id, HttpSession session) {
        log.debug("Request to get song : {}", id);
        User user = ((User) session.getAttribute("user"));
        if (user == null || user.getUserId() == -1) {
            return songService.findById(id);
        }
        return songService.findById(id, user.getUserId());
    }

    @GetMapping("/{id}/comments")
    public Page<CommentDTO> findBySong(@PathVariable Integer id, Pageable pageable) {
        log.debug("Request to get comment for song by id : {}", id);
        return commentService.findBySong(id, pageable);
    }

    @PostMapping("/{id}/comment")
    public void commentOnSong(@PathVariable Integer id, @RequestBody CommentDTO commentDTO, HttpSession session) {
        log.debug("Request to comment on song with id : {}", id);
        User user = ((User) session.getAttribute("user"));
        if (user == null || user.getUserId() == -1) {
            log.warn("User is not authenticated");
            throw new FailedAuthentication("Can't comment on song without authentification");
        }
        commentService.commentOnSong(id, commentDTO, user.getUserId());
    }

    @GetMapping("/{userId}/activities")
    public List<SongDTO> findUserRecentActivity(@PathVariable Integer userId) {
        log.debug("Request to get recent activity for song with id : {}", userId);
        return songService.findFirstFiveByUserId(userId);
    }
}
