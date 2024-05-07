package org.nazar.service.dao;

import java.util.Optional;
import org.nazar.service.entities.ParsersLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParsersLinkJpaRepository extends JpaRepository<ParsersLink, Long> {
    Optional<ParsersLink> findByProviderName(String providerName);
}
