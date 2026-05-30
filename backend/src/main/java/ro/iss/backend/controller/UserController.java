package ro.iss.backend.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.iss.backend.domain.User;
import ro.iss.backend.dto.*;
import ro.iss.backend.mapper.UserMapper;
import ro.iss.backend.service.CommentService;
import ro.iss.backend.service.UserService;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    protected final UserService userService;
    private final UserMapper userMapper;
    private final CommentService commentService;

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

    @GetMapping("{user_id}")
    public User findUserById(@PathVariable Integer user_id) {
        return userService.findUserById(user_id);
    }

    @PostMapping("/update-avatar")
    public String setUserProfile(@RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            log.debug("updating the avatar of the user {}", user);
            return userService.updateProfilePictureUrl(file, user.getUserId());
        } else {
            log.debug("can't set profile for user, session doesn't have user attribute");
            throw new AuthenticationException("user not logged in");
        }
    }

    @GetMapping("{id}/is-following")
    public boolean isFollowing(@PathVariable Integer id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        return userService.isFollowing(user.getUserId(), id);
    }

    @PostMapping("/{id}/follow")
    public void follow(@PathVariable Integer id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        userService.follow(user.getUserId(), id);
    }

    @DeleteMapping("/{id}/follow")
    public void unfollow(@PathVariable Integer id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        userService.unfollow(user.getUserId(), id);
    }

    @GetMapping("/{userId}/comments")
    public List<CommentDTO> findUserComments(@PathVariable Integer userId) {
        log.debug("Request to get recent activity for comments with id : {}", userId);
        return commentService.findFirstFiveByUser(userId);
    }

    @PutMapping("/{userId}/update")
    public void updateUserProfile(@PathVariable Integer userId, @RequestBody UpdateUserDTO dto, HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");

        if (!sessionUser.getUserId().equals(userId)) {
            throw new RuntimeException("You can only update your own profile");
        }
        userService.updateUserProfile(userId, dto);
        // update session with new data so it stays in sync
        sessionUser.setSpotifyLink(dto.getSpotifyLink());
        sessionUser.setInstagramLink(dto.getInstagramLink());
        sessionUser.setFacebookLink(dto.getFacebookLink());
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterDTO dto) {
        userService.register(dto);
    }
}
