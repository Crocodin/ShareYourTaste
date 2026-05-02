package ro.iss.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.iss.backend.domain.Song;
import ro.iss.backend.repository.SongRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class SongService {
    protected final SongRepository songRepository;

    @Cacheable(value = "song_page_by_name", key = "'song - ' + #name + ', '+ #pageable.pageNumber + ', ' + #pageable.pageSize")
    public Page<Song> findByNameOrArtist(String name, Pageable pageable) {
        log.debug("Request to get songs by name : {} and page: {}", name, pageable);
        return songRepository.searchByNameOrArtist(name, pageable);
    }
}
