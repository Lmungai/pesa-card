package com.prodedge.pesacard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prodedge.pesacard.model.PesaCard;

@RestController
@RequestMapping("/pesacards")
public class PesaCardController {

    @GetMapping("/{requestedId}")
    public ResponseEntity<PesaCard> findById(@PathVariable Long requestedId) {

        if (requestedId.equals(99L)) {
            PesaCard pesaCard = new PesaCard(99L, 123.45);
            return ResponseEntity.ok(pesaCard);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
