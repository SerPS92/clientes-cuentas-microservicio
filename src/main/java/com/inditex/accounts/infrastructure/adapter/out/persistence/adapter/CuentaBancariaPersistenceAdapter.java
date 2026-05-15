package com.inditex.accounts.infrastructure.adapter.out.persistence.adapter;

import com.inditex.accounts.application.port.out.CuentaBancariaRepositoryPort;
import com.inditex.accounts.domain.model.CuentaBancaria;
import com.inditex.accounts.infrastructure.adapter.out.persistence.repository.CuentaBancariaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CuentaBancariaPersistenceAdapter implements CuentaBancariaRepositoryPort {

    private final CuentaBancariaJpaRepository cuentaBancariaJpaRepository;

    @Override
    public CuentaBancaria save(CuentaBancaria cuentaBancaria) {
        return cuentaBancariaJpaRepository.save(cuentaBancaria);
    }

    @Override
    public Optional<CuentaBancaria> findById(Long id) {
        return cuentaBancariaJpaRepository.findById(id);
    }
}
