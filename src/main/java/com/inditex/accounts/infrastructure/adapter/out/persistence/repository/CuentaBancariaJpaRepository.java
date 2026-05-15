package com.inditex.accounts.infrastructure.adapter.out.persistence.repository;

import com.inditex.accounts.domain.model.CuentaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaBancariaJpaRepository extends JpaRepository<CuentaBancaria, Long> {
}
