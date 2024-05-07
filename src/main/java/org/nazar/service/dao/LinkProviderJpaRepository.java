package org.nazar.service.dao;

import java.util.Optional;
import org.nazar.service.entities.LinkProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkProviderJpaRepository extends JpaRepository<LinkProvider, Long> {
    Optional<LinkProvider> findByName(String name);
}
