package com.mcecelja.pocket.repositories.organization;

import com.mcecelja.pocket.domain.organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
