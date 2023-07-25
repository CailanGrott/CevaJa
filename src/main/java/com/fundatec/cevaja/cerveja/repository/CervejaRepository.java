package com.fundatec.cevaja.cerveja.repository;

import com.fundatec.cevaja.cerveja.model.Cerveja;
import com.fundatec.cevaja.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CervejaRepository extends JpaRepository<Cerveja, Integer> {
    @Query("select c from Cerveja c where c.tipoCerveja = ?1")
    Optional<Cerveja> buscaTipoCerveja(String tipoCerveja);
    @Transactional
    @Modifying
    @Query(value = """
            UPDATE cevaja.cerveja c
            SET c.tipo_cerveja = :tipoCerveja,
                c.valor = :valor
            WHERE id_cerveja = :idCerveja
            """, nativeQuery = true)
    void editaCervejaPorId(@Nullable @Param("tipoCerveja") String tipoCerveja, @Nullable @Param("valor") BigDecimal valor,
                           @Param("idCerveja") Integer idCerveja);
}
