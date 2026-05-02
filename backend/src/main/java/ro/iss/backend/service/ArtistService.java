package ro.iss.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.iss.backend.repository.ArtistRepository;

@Service
@RequiredArgsConstructor
public class ArtistService {
    protected final ArtistRepository artistRepository;
}
