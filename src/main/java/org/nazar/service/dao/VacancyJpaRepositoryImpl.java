package org.nazar.service.dao;

import java.util.List;
import java.util.Optional;
import org.nazar.service.entities.LinkProvider;
import org.nazar.service.entities.ParsersLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "dao.type", havingValue = "jpa-repository")
public class VacancyJpaRepositoryImpl implements VacancyDao {

    private final LinkProviderJpaRepository linkProviderJpaRepository;

    private final ParsersLinkJpaRepository parsersLinkJpaRepository;

    private static final Logger logger = LoggerFactory.getLogger(VacancyJpaRepositoryImpl.class);


    public VacancyJpaRepositoryImpl(LinkProviderJpaRepository linkProviderJpaRepository,
                                    ParsersLinkJpaRepository parsersLinkJpaRepository) {
        this.linkProviderJpaRepository = linkProviderJpaRepository;
        this.parsersLinkJpaRepository = parsersLinkJpaRepository;
    }

    /**
     * Writes parsed links to the database.
     * If the resource identifier does not already exist, a new LinkProvider entity is created.
     *
     * @param parsedLinks list of parsed links to be stored.
     * @param resourceId identifier for the link provider.
     */
    @Override
    public void write(List<String> parsedLinks, String resourceId) {
        try {
            LinkProvider provider = linkProviderJpaRepository.findByName(resourceId)
                    .orElseGet(() -> {
                        LinkProvider newProvider = new LinkProvider();
                        newProvider.setName(resourceId);
                        return linkProviderJpaRepository.save(newProvider);
                    });

            ParsersLink parsersLink = new ParsersLink();
            parsersLink.setLink(parsedLinks.toString());
            parsersLink.setProvider(provider);
            parsersLinkJpaRepository.save(parsersLink);

            logger.info("Data was written successfully");
        } catch (Exception e) {
            logger.error("Cannot execute JPA repository operation", e);
        }
    }

    /**
     * Reads and returns the list of parsed links associated with a given resource identifier.
     *
     * @param resourceId the identifier for the link provider whose data is to be retrieved.
     * @return list of strings representing the parsed links; returns an empty list if none found or on error.
     */
    @Override
    public List<String> read(String resourceId) {
        try {
            Optional<ParsersLink> optionalLink = parsersLinkJpaRepository.findByProviderName(resourceId);

            return convertStringToList(optionalLink.map(ParsersLink::getLink).orElse(null));
        } catch (Exception e) {
            logger.error("Cannot execute JPA repository operation", e);
            return List.of();
        }
    }
}
