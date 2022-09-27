package com.proyecto.sisc.SISC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.sisc.SISC.model.DetalleVenta;
import com.proyecto.sisc.SISC.repository.IDetalleVentaRepository;
@Service
public class DetalleVentaServiceImpl implements IDetalleVentaService {

	@Autowired
	private IDetalleVentaRepository detalleVentaRepository;
	
	@Override
	public DetalleVenta save(DetalleVenta detalleVenta) {
		return detalleVentaRepository.save(detalleVenta);
	}

}
