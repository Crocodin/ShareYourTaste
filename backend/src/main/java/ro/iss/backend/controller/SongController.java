package ro.iss.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.iss.backend.dto.SongDTO;
import ro.iss.backend.mapper.SongMapper;
import ro.iss.backend.service.SongService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/songs")
public class SongController {
    protected final SongService songService;
    private final SongMapper songMapper;

    @GetMapping("/search")
    public Page<SongDTO> findByName(@RequestParam String name, Pageable pageable) {
        log.debug("Request to get songs by name : {} and page: {}", name, pageable);
        return songService.findByNameOrArtist(name, pageable).map(songMapper::toDTO);
    }
}
