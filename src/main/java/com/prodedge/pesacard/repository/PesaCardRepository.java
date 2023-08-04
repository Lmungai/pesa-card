package com.prodedge.pesacard.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.prodedge.pesacard.model.PesaCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface PesaCardRepository extends CrudRepository<PesaCard, Long>, PagingAndSortingRepository<PesaCard, Long> {
    PesaCard findByIdAndOwner(Long id, String owner);

    Page<PesaCard> findByOwner(String owner, PageRequest amount);

    boolean existsByIdAndOwner(Long id, String owner);
}
