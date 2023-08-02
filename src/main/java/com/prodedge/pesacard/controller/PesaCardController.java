package com.prodedge.pesacard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prodedge.pesacard.model.PesaCard;
import com.prodedge.pesacard.repository.PesaCardRepository;
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
}
