package ro.iss.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.iss.backend.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUsernameAndPassword(String username, String password);

    Optional<User> findUserByUserId(Integer userId);

    @Modifying
    @Query("UPDATE User SET profilePictureUrl = :pfu WHERE userId = :uId")
    void setUserProfilePictureUrl(@Param("pfu") String pfu, @Param("uId") Integer uId);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) > 0 FROM user_following WHERE follower = :follower AND followed = :following")
    boolean isFollowing(@Param("follower") Integer follower, @Param("following") Integer following);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM user_following WHERE follower = :follower AND followed = :following")
    void removeFollower(@Param("follower")  Integer follower, @Param("following") Integer following);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO user_following(follower, followed) VALUES (:follower, :following)")
    void addFollower(@Param("follower") Integer follower, @Param("following") Integer following);

    boolean existsByUsername(String username);
}
