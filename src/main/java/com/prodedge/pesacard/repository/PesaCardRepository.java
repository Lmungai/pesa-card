package com.prodedge.pesacard.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.prodedge.pesacard.model.PesaCard;

public interface PesaCardRepository extends CrudRepository<PesaCard, Long>, PagingAndSortingRepository<PesaCard, Long> {

}
