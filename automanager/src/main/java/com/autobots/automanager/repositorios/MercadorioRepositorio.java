package com.autobots.automanager.repositorios;

import com.autobots.automanager.entidades.Mercadoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MercadorioRepositorio extends JpaRepository<Mercadoria, Long>{
}
