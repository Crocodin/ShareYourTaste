package ro.iss.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.iss.backend.domain.User;
import ro.iss.backend.exception.FailedAuthentication;
import ro.iss.backend.repository.UserRepository;

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
}
