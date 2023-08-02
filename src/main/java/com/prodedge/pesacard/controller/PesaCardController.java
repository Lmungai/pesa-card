package com.prodedge.pesacard.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.prodedge.pesacard.model.PesaCard;
import com.prodedge.pesacard.repository.PesaCardRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pesacards")
public class PesaCardController {
    private PesaCardRepository pesaCardRepository;

    public PesaCardController(PesaCardRepository pesaCardRepository) {
        this.pesaCardRepository = pesaCardRepository;
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<PesaCard> findById(@PathVariable Long requestedId) {

        Optional<PesaCard> pesaCardOptional = pesaCardRepository.findById(requestedId);

        if (pesaCardOptional.isPresent()) {
            return ResponseEntity.ok(pesaCardOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    private ResponseEntity<Void> createCashCard(@RequestBody PesaCard newCashCardRequest, UriComponentsBuilder ucb) {
        PesaCard savedCashCard = pesaCardRepository.save(newCashCardRequest);
        URI locationOfNewCashCard = ucb
                .path("pesacards/{id}")
                .buildAndExpand(savedCashCard.id())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }

    // @PostMapping
    // private ResponseEntity<Void> createPesaCard(@RequestBody PesaCard
    // newPesaCardRequest, UriComponentsBuilder ucb) {
    // PesaCard savedPesaCard = pesaCardRepository.save(newPesaCardRequest);
    // URI locationOfNewPesaCard = ucb
    // .path("pesacards/{id}")
    // .buildAndExpand(savedPesaCard.id())
    // .toUri();
    // return ResponseEntity.created(locationOfNewPesaCard).build();
    // }

    @GetMapping
    public ResponseEntity<List<PesaCard>> findAll(Pageable pageable) {
        Page<PesaCard> page = pesaCardRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(), pageable.getSort()));
        return ResponseEntity.ok(page.getContent());
    }
}
