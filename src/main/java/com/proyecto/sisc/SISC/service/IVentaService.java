package com.proyecto.sisc.SISC.service;

import java.util.List;
import java.util.Optional;

import com.proyecto.sisc.SISC.model.Usuario;
import com.proyecto.sisc.SISC.model.Venta;

public interface IVentaService {
	
	List<Venta>findAll();
	Optional<Venta> findById(Integer id);
	Venta save(Venta venta);
	String generarNumeroDeVenta();
	List<Venta> findByUsuario (Usuario usuario);

}
