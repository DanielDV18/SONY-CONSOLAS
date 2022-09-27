package com.proyecto.sisc.SISC.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.sisc.SISC.model.Usuario;
import com.proyecto.sisc.SISC.model.Venta;
@Repository
public interface IVentaRepository extends JpaRepository<Venta, Integer> {
	List<Venta> findByUsuario (Usuario usuario);
}
