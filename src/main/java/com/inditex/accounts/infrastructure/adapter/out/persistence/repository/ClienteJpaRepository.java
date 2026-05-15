package com.inditex.accounts.infrastructure.adapter.out.persistence.repository;

import com.inditex.accounts.domain.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ClienteJpaRepository extends JpaRepository<Cliente, String> {

    @Query("SELECT c FROM Cliente c WHERE c.fechaNacimiento <= :fechaLimite")
    Page<Cliente> findClientesMayoresDeEdad(@Param("fechaLimite") LocalDate fechaLimite, Pageable pageable);

    @Query("""
        SELECT c
        FROM Cliente c
        JOIN c.cuentas cb
        GROUP BY c
        HAVING SUM(cb.total) > :cantidad
        """)
    Page<Cliente> findClientesConTotalCuentasSuperiorA(@Param("cantidad") BigDecimal cantidad, Pageable pageable);
}
