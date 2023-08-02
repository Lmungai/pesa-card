package com.prodedge.pesacard.model;

import org.springframework.data.annotation.Id;

public record PesaCard(@Id Long id, Double amount) {

}
