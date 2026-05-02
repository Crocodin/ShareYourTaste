package ro.iss.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ro.iss.backend.service.AlbumService;

@Controller
@RequiredArgsConstructor
public class AlbumController {
    protected final AlbumService albumService;
}
