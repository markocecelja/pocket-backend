package com.mcecelja.pocket.repositories.user;

import com.mcecelja.pocket.domain.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	@EntityGraph("user_graph")
	Optional<User> findUserById(Long userId);
}
