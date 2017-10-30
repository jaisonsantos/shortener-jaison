package com.bemobi.shortenerweb.domain;

import com.bemobi.shortenerweb.AbstractDataJpaTest;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jaison
 */
public class ShortenLinkRepositoryTests extends AbstractDataJpaTest {

    @Autowired
    private ShortenLinkRepository shortenLinkRepository;

    @Test
    public void findAll() {
        Iterable<ShortenLink> links = shortenLinkRepository.findAll();
        assertThat(Lists.newArrayList(links).size()).isEqualTo(3);
    }

    @Test
    public void findOne() {
        ShortenLink entity = shortenLinkRepository.findOne(1L);
        assertThat(entity).isNotNull();
    }

    @Test
    public void findOneByOriginLink() {
        ShortenLink entity = shortenLinkRepository.findOneByOriginLink("https://twitter.com/bemobi_oficial/");
        assertThat(entity).isNotNull();
    }

    @Test
    public void findOneByLinkId() {
        ShortenLink entity = shortenLinkRepository.findOneByLinkId("aaaaaa");
        assertThat(entity).isNotNull();
    }

    @Test
    public void empty() {
        ShortenLink entity = shortenLinkRepository.findOneByLinkId("eeeeee");
        assertThat(entity).isNull();
    }

    @Test
    public void save() {
        ShortenLink entity = new ShortenLink();
        entity.setLinkId("ffffff");
        entity.setOriginLink("https://pt.linkedin.com/company/bemobi/");

        entity = shortenLinkRepository.save(entity);
        assertThat(shortenLinkRepository.exists(entity.getSequence())).isTrue();
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveWithLinkIdIsNull() {
        ShortenLink entity = new ShortenLink();
        entity.setLinkId(null);
        entity.setOriginLink("https://projects.spring.io/spring-boot/");

        shortenLinkRepository.save(entity);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveWithOriginLinkIsNull() {
        ShortenLink entity = new ShortenLink();
        entity.setLinkId("ffffff");
        entity.setOriginLink(null);

        shortenLinkRepository.save(entity);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveWithLinkIdIsTooLong() {
        ShortenLink entity = new ShortenLink();
        entity.setLinkId("fffffffff");
        entity.setOriginLink(null);

        assertThat(entity.getLinkId().length() > 8).isTrue();
        shortenLinkRepository.save(entity);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveWithDuplicatedOriginLink() {
        ShortenLink entity = new ShortenLink();
        entity.setLinkId("ffffff");
        entity.setOriginLink("https://www.facebook.com/bemobioficial/");

        shortenLinkRepository.save(entity);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveWithDuplicatedLinkId() {
        ShortenLink entity = new ShortenLink();
        entity.setLinkId("aaaaaa");
        entity.setOriginLink("https://projects.spring.io/spring-boot/");

        shortenLinkRepository.save(entity);
    }
}
