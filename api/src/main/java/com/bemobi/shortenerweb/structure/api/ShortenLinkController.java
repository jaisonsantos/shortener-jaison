package com.bemobi.shortenerweb.structure.api;

import com.bemobi.shortenerweb.EndPoints;
import com.bemobi.shortenerweb.domain.ShortenLink;
import com.bemobi.shortenerweb.structure.service.ShortenLinkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jaison
 * @apiNote  Classe que lida com as solicitações de HTTP recebidas.
 */
@RestController
@RequestMapping(value = EndPoints.SHORTEN_URL_API)
public class ShortenLinkController {

    private final ShortenLinkService shortenLinkService;

    public ShortenLinkController(ShortenLinkService shortenLinkService) {
        this.shortenLinkService = shortenLinkService;
    }

    @RequestMapping(value = "/{linkId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ShortenLink> findOneByLinkId(@PathVariable String linkId) {
        return new ResponseEntity<ShortenLink>(shortenLinkService.findOneByLinkId(linkId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<ShortenLink> save(@RequestBody ShortenLink link) {
        return new ResponseEntity<ShortenLink>(shortenLinkService.create(link), HttpStatus.CREATED);
    }
}
