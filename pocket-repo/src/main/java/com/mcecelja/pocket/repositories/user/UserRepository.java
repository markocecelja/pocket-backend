package com.mcecelja.pocket.repositories.user;

import com.mcecelja.pocket.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
