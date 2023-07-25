package com.fundatec.cevaja.usuario.repository;

import com.fundatec.cevaja.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Transactional
    @Modifying
    @Query(value = """
            UPDATE cevaja.usuario u
            SET u.nome = :nome,
                u.sobrenome = :sobrenome
            WHERE u.id_usuario = :idUsuario
            """, nativeQuery = true)
    void editaUsuarioById(@Nullable @Param("nome") String nome, @Nullable @Param("sobrenome") String sobrenome,
                          @Param("idUsuario") Integer idUsuario);

    @Query("select u from usuario u where u.username = ?1")
    Optional<Usuario> buscaUsuarioPorUsername(String username);


}
