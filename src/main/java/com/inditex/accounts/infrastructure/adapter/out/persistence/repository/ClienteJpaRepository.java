package com.inditex.accounts.infrastructure.adapter.out.persistence.repository;

import com.inditex.accounts.domain.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ClienteJpaRepository extends JpaRepository<Cliente, String> {

    @Query("""
        SELECT c
        FROM Cliente c
        LEFT JOIN FETCH c.cuentas
        WHERE c.dni = :dni
        """)
    Optional<Cliente> findByDniWithCuentas(@Param("dni") String dni);

    @Query("""
        SELECT DISTINCT c
        FROM Cliente c
        LEFT JOIN FETCH c.cuentas
        WHERE c.dni IN :dnis
        """)
    List<Cliente> findAllWithCuentasByDniIn(@Param("dnis") List<String> dnis);

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
