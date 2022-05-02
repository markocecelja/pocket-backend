package com.mcecelja.pocket.repositories.organization;

import com.mcecelja.pocket.domain.organization.OrganizationMember;
import com.mcecelja.pocket.domain.organization.primaryKey.OrganizationMemberPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationMemberRepository extends JpaRepository<OrganizationMember, OrganizationMemberPK> {

	Page<OrganizationMember> findAllByOrganizationMemberPK_OrganizationId(Long organizationId, Pageable pageable);
}
