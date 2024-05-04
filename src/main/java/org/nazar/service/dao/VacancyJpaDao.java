package org.nazar.service.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.nazar.service.entities.LinkProvider;
import org.nazar.service.entities.ParsersLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@ConditionalOnProperty(name = "vacancydao.type", havingValue = "jpa")
public class VacancyJpaDao implements VacancyDao {
    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger = LoggerFactory.getLogger(VacancyJpaDao.class);

    /**
     * Writes parsed links to database
     * @param parsedLinks links from site
     * @param resourceId id of parsed resource
     */
    @Override
    @Transactional
    public void write(List<String> parsedLinks, String resourceId) {
        try {
            LinkProvider provider = entityManager.createQuery("SELECT lp FROM LinkProvider lp WHERE lp.name = :name", LinkProvider.class)
                    .setParameter("name", resourceId)
                    .getResultList()
                    .stream()
                    .findFirst()
                    .orElse(null);

            if (provider == null) {
                provider = new LinkProvider();
                provider.setName(resourceId);
                entityManager.persist(provider);
            }

            ParsersLink parsersLink = new ParsersLink();
            parsersLink.setLink(parsedLinks.toString());
            parsersLink.setProvider(provider);
            entityManager.persist(parsersLink);

            logger.info("Data was written successfully");
        } catch (Exception e) {
            logger.error("Cannot execute JPA operation", e);
        }
    }

    /**
     * Read parsed links from database
     * @param resourceId id of parsed resource
     * @return list of links
     */
    @Override
    public List<String> read(String resourceId) {
        try {
            TypedQuery<String> query = entityManager.createQuery(
                    "SELECT pl.link FROM ParsersLink pl WHERE pl.provider.id = (SELECT lp.id FROM LinkProvider lp WHERE lp.name = :name)", String.class);
            query.setParameter("name", resourceId);
            return convertStringToList(query.getSingleResult());
        } catch (NoResultException e) {
            logger.info("No results found for resource: " + resourceId);
            return List.of();
        } catch (Exception e) {
            logger.error("Cannot execute JPA operation", e);
            return List.of();
        }
    }
}
