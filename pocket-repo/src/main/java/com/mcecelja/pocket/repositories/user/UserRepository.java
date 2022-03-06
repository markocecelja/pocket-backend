package com.mcecelja.pocket.repositories.user;

import com.mcecelja.pocket.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findUserById(Long userId);
}
