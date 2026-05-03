package ro.iss.backend.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.iss.backend.domain.User;
import ro.iss.backend.dto.LoginDTO;
import ro.iss.backend.dto.UserDTO;
import ro.iss.backend.mapper.UserMapper;
import ro.iss.backend.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    protected final UserService userService;
    private final UserMapper userMapper;

    @CacheEvict(value = "song_page_by_name", allEntries = true)
    @PostMapping("/login")
    public UserDTO authenticateUser(@RequestBody LoginDTO loginDTO, HttpSession session) {
        User user = userService.authenticate(
                loginDTO.getUsername(),
                loginDTO.getPassword()
        );
        session.setAttribute("user", user);
        return userMapper.toDTO(user);
    }
}
