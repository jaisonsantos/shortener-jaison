package com.bemobi.shortenerweb.structure;

import com.bemobi.shortenerweb.domain.ShortenLink;
import com.bemobi.shortenerweb.structure.service.ShortenLinkService;
import com.bemobi.shortenerweb.support.ConcurrentUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jaison
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationTests {

    @Autowired
    private ShortenLinkService shortenLinkService;

    @Test
    public void integration() {
        Set<ShortenLink> links = new HashSet<>();
        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 10000).forEach(i -> executor.submit(() -> {
            ShortenLink entity = new ShortenLink();
            entity.setOriginLink(String.valueOf(i));
            ShortenLink link = shortenLinkService.create(entity);
            System.out.println(String.format("%s %s", Thread.currentThread(), link));
            links.add(link);
        }));

        ConcurrentUtils.stop(executor);
        assertThat(links.size()).isEqualTo(10000);
    }
}
