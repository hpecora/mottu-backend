package com.mottu.mototracker.repository;

import com.mottu.mototracker.model.Localizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long> {

    // navega pela associação: Localizacao.moto.id
    List<Localizacao> findByMoto_Id(Long motoId);
}
