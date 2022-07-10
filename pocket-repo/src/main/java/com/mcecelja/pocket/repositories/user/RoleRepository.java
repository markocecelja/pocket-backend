package com.mcecelja.pocket.repositories.user;

import com.mcecelja.pocket.domain.user.codebook.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
