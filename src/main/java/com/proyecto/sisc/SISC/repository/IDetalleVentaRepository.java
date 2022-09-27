package com.proyecto.sisc.SISC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.sisc.SISC.model.DetalleVenta;

@Repository
public interface IDetalleVentaRepository extends JpaRepository<DetalleVenta, Integer>{

}
