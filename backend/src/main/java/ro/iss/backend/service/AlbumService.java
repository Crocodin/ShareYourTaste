package ro.iss.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.iss.backend.domain.Album;
import ro.iss.backend.repository.AlbumRepository;

@Service
@RequiredArgsConstructor
public class AlbumService {
    protected final AlbumRepository albumRepository;

    @Cacheable(value = "album_page_by_name", key = "'album - ' + #name + ', '+ #pageable.pageNumber + ', ' + #pageable.pageSize")
    public Page<Album> findByName(String name, Pageable pageable) {
        return albumRepository.findByAlbumNameContainingIgnoreCase(name, pageable);
    }
}
