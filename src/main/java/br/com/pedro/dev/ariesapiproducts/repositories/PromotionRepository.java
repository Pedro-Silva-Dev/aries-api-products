package br.com.pedro.dev.ariesapiproducts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import br.com.pedro.dev.ariesapiproducts.models.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long>, QuerydslPredicateExecutor<Promotion> {
    
}
