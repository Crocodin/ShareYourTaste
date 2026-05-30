package ro.iss.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.iss.backend.domain.Song;
import ro.iss.backend.domain.SongRating;
import ro.iss.backend.domain.SongRatingId;
import ro.iss.backend.dto.SongDTO;
import ro.iss.backend.mapper.SongMapper;
import ro.iss.backend.repository.SongRatingRepository;
import ro.iss.backend.repository.SongRepository;
import ro.iss.backend.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SongService {
    protected final SongRepository songRepository;
    protected final SongRatingRepository songRatingRepository;
    protected final UserRepository userRepository;
    private final SongMapper songMapper;

    @Cacheable(value = "song_page_by_name", key = "'song - ' + #name + ', '+ #pageable.pageNumber + ', ' + #pageable.pageSize")
    public Page<SongDTO> findByNameOrArtist(String name, Pageable pageable) {
        log.debug("Request to get songs by name: {} and page: {}", name, pageable);
        return songRepository.searchByNameOrArtist(name, pageable).map(songMapper::toDTO);
    }

    @Cacheable(value = "song_page_by_name", key = "'song - ' + #name + ', '+ #pageable.pageNumber + ', ' + #pageable.pageSize")
    public Page<SongDTO> findByNameOrArtist(String name, Pageable pageable, int userId) {
        log.debug("Request to get songs by name: {} and page: {} and userId: {}", name, pageable, userId);
        return songRepository.searchByNameOrArtist(name, pageable).map(song -> {
            SongDTO DTO = songMapper.toDTO(song);
            songRatingRepository.findById(new SongRatingId(userId, song.getSongId())).ifPresent(rating -> {
                DTO.setUserRating(rating.getRating());
            });
            return DTO;
        });
    }

    @CacheEvict(value = "song_page_by_name", allEntries = true)
    public void rateSong(Integer songId, Integer userId, Integer rating) {
        log.debug("Request to rate song: {}", songId);
        SongRatingId ratingId = new SongRatingId(userId, songId);

        SongRating songRating = songRatingRepository.findById(ratingId).orElse(new SongRating());

        log.debug("Saving rating for song: {}", songRating);
        songRatingRepository.save(
                SongRating.builder()
                        .id(ratingId)
                        .user(userRepository.getReferenceById(userId))
                        .song(songRepository.getReferenceById(songId))
                        .rating(rating)
                        .build()
        );

        log.debug("Recalculated rating average for song with the ID: {}", songId);
        Double avg = songRatingRepository.findAverageRatingBySongId(songId);
        Song song = songRepository.getReferenceById(songId);
        song.setSongRating(avg);
        songRepository.save(song);
    }

    public SongDTO findById(int songId, int userId) {
        log.debug("Request to get song: {} for user: {}", songId, userId);
        Song song = songRepository.getReferenceById(songId);
        SongDTO songDTO = songMapper.toDTO(song);
        songRatingRepository.findById(new SongRatingId(userId, songId)).ifPresent(rating -> {
            songDTO.setUserRating(rating.getRating());
        });
        return songDTO;
    }

    public SongDTO findById(int songId) {
        log.debug("Request to get song: {}", songId);
        return songMapper.toDTO(songRepository.getReferenceById(songId));
    }

    public List<SongDTO> findFirstFiveByUserId(int userId) {
        log.debug("Request to get song for user: {}", userId);
        return songRatingRepository.findByUser_UserId(userId).stream().map(songRating -> {
            SongDTO dto = songMapper.toDTO(songRating.getSong());
            dto.setUserRating(songRating.getRating());
            return dto;
        }).limit(5).toList();
    }
}
