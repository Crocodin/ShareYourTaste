package ro.iss.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ro.iss.backend.service.ArtistService;

@Controller
@RequiredArgsConstructor
public class ArtistController {
    protected final ArtistService artistService;
}
