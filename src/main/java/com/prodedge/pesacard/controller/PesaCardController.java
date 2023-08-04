package com.prodedge.pesacard.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.prodedge.pesacard.model.PesaCard;
import com.prodedge.pesacard.repository.PesaCardRepository;

import java.net.URI;
import java.util.List;
import java.security.Principal;

@RestController
@RequestMapping("/pesacards")
public class PesaCardController {
    private PesaCardRepository pesaCardRepository;

    public PesaCardController(PesaCardRepository pesaCardRepository) {
        this.pesaCardRepository = pesaCardRepository;
    }

    private PesaCard findPesaCard(Long requestedId, Principal principal) {
        return pesaCardRepository.findByIdAndOwner(requestedId, principal.getName());
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<PesaCard> findById(@PathVariable Long requestedId, Principal principal) {
        PesaCard pesaCard = findPesaCard(requestedId, principal);
        if (pesaCard != null) {
            return ResponseEntity.ok(pesaCard);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    private ResponseEntity<Void> createPesaCard(@RequestBody PesaCard newCashCardRequest, UriComponentsBuilder ucb,
            Principal principal) {

        PesaCard pesaCardWithOwner = new PesaCard(null, newCashCardRequest.amount(), principal.getName());
        PesaCard savedPesaCard = pesaCardRepository.save(pesaCardWithOwner);
        URI locationofNewPesaCard = ucb
                .path("pesacards/{id}")
                .buildAndExpand(savedPesaCard.id())
                .toUri();
        return ResponseEntity.created(locationofNewPesaCard).build();
    }

    @GetMapping
    public ResponseEntity<List<PesaCard>> findAll(Pageable pageable, Principal principal) {
        Page<PesaCard> page = pesaCardRepository.findByOwner(principal.getName(),
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(), pageable.getSort()));

        return ResponseEntity.ok(page.getContent());
    }

    @PutMapping("/{requestedId}")
    private ResponseEntity<Void> putCashCard(@PathVariable Long requestedId, @RequestBody PesaCard pesaCardUpdate,
            Principal principal) {
        PesaCard pesaCard = findPesaCard(requestedId, principal);
        if (pesaCard != null) {
            PesaCard updatedPesaCard = new PesaCard(pesaCard.id(), pesaCardUpdate.amount(), principal.getName());
            pesaCardRepository.save(updatedPesaCard);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
