package ro.iss.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ro.iss.backend.domain.User;
import ro.iss.backend.dto.RegisterDTO;
import ro.iss.backend.dto.UpdateUserDTO;
import ro.iss.backend.exception.FailedAuthentication;
import ro.iss.backend.repository.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    protected final UserRepository userRepository;

    public User authenticate(String username, String password) throws FailedAuthentication {
        log.debug("authenticate for user {}", username);
        User user = userRepository.findUserByUsernameAndPassword(username, password);
        if (user == null) {
            log.warn("User {} not found", username);
            throw new FailedAuthentication("username or password is incorrect");
        }
        log.debug("authenticated for  user {}", username);
        return user;
    }

    public User findUserById(Integer userId) {
        log.debug("findUserById for user {}", userId);
        return userRepository.findUserByUserId(userId).orElseThrow();
    }

    @Transactional
    public String updateProfilePictureUrl(MultipartFile newAvatar, int userId) throws IOException {
        String filename = userId + "_" + newAvatar.getOriginalFilename();
        Path savePath = Paths.get("/src/main/resources/static/avatars/" + filename);
        log.debug("updateProfilePictureUrl for user {} at {}", userId, filename);

        Files.write(savePath, newAvatar.getBytes());
        userRepository.setUserProfilePictureUrl("/avatars/" + filename, userId);

        return "/avatars/" + filename;
    }

    public boolean isFollowing(Integer followerId, Integer followedId) {
        log.debug("user {} if following {}?",  followerId, followedId);
        return userRepository.isFollowing(followerId, followedId);
    }

    @Transactional
    public void follow(Integer followerId, Integer followedId) {
        log.debug("user {} wants to follow {}", followerId,  followedId);
        if (!userRepository.isFollowing(followerId, followedId)) {
            userRepository.addFollower(followerId, followedId);
        }
    }

    @Transactional
    public void unfollow(Integer followerId, Integer followedId) {
        log.debug("user {} wants to unfollow {}", followerId, followedId);
        userRepository.removeFollower(followerId, followedId);
    }

    @Transactional
    public void updateUserProfile(Integer userId, UpdateUserDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setSpotifyLink(dto.getSpotifyLink());
        user.setInstagramLink(dto.getInstagramLink());
        user.setFacebookLink(dto.getFacebookLink());
        user.setProfilePictureUrl(dto.getProfilePictureUrl());

        userRepository.save(user);
    }

    public void register(RegisterDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username already taken");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        userRepository.save(user);
    }
}
