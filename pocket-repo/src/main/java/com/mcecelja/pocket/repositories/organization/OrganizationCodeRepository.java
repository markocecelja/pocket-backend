package com.mcecelja.pocket.repositories.organization;

import com.mcecelja.pocket.domain.organization.OrganizationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationCodeRepository extends JpaRepository<OrganizationCode, Long> {

	boolean existsByValue(String value);
}
