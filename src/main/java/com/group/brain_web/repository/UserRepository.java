package com.group.brain_web.repository;

import com.group.brain_web.models.Course;
import com.group.brain_web.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    boolean existsUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email=?1")
    User resetPasswordByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.enable = TRUE WHERE a.email = ?1")
    int enableUser(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.enable = FALSE WHERE a.userId = ?1")
    int disableUserByAdmin(int id);

    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.enable = TRUE WHERE a.userId = ?1")
    int enableUserByAdmin(int id);

    @Query("SELECT u FROM User u WHERE u.userName LIKE %:searchTerm% OR u.email LIKE %:searchTerm%")
    List<User> search(@Param("searchTerm") String searchTerm);
}
