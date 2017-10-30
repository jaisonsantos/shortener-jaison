package com.bemobi.shortenerweb.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Jaison
 */
public interface ShortenLinkRepository extends CrudRepository<ShortenLink, Long> {

    ShortenLink findOneByOriginLink(String originLink);

    ShortenLink findOneByLinkId(String linkId);
}
