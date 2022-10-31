package com.proyecto.sisc.SISC.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.proyecto.sisc.SISC.model.Producto;

public interface ProductoService {
    public Producto save(@Valid Producto producto);
	public Optional<Producto> get(Integer id);
	public void update(Producto producto);
	public void delete(Integer id);
	public List<Producto> findAll();
}
