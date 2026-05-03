package ro.iss.backend.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ro.iss.backend.domain.User;
import ro.iss.backend.dto.RatingDTO;
import ro.iss.backend.dto.SongDTO;
import ro.iss.backend.exception.FailedAuthentication;
import ro.iss.backend.mapper.SongMapper;
import ro.iss.backend.service.SongService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/songs")
public class SongController {
    protected final SongService songService;

    @GetMapping("/search")
    public Page<SongDTO> findByName(@RequestParam String name, Pageable pageable, HttpSession session) {
        log.debug("Request to get songs by name : {} and page: {}", name, pageable);
        User user = ((User) session.getAttribute("user"));
        if (user == null || user.getUserId() == -1)
            return songService.findByNameOrArtist(name, pageable);
        return songService.findByNameOrArtist(name, pageable, user.getUserId());
    }

    @PostMapping("/{id}/rate")
    public void rateSong(@PathVariable Integer id, @RequestBody RatingDTO ratingDTO, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getUserId() == -1)
            throw new FailedAuthentication("Can't rate song without authentification");
        songService.rateSong(id, user.getUserId(), ratingDTO.getRating());
    }
}
