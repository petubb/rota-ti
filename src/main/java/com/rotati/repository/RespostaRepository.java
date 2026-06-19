package com.rotati.repository;

import com.rotati.model.Resposta;
import com.rotati.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {

    List<Resposta> findByUsuario(Usuario usuario);
}
