package com.proyecto.sisc.SISC.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.sisc.SISC.model.Usuario;
import com.proyecto.sisc.SISC.model.Venta;
import com.proyecto.sisc.SISC.repository.IVentaRepository;

@Service
public class VentaServiceImpl implements IVentaService {

	@Autowired
	private IVentaRepository ventaRepository;

	@Override
	public Venta save(Venta venta) {
		return ventaRepository.save(venta);
	}

	@Override
	public List<Venta> findAll() {
		return ventaRepository.findAll();
	}

	// INCREMENTO DE N°VENTA
	public String generarNumeroDeVenta() {
		int numero = 0;
		String numeroConcatenado = "";

		List<Venta> ventas = findAll();

		List<Integer> numeros = new ArrayList<Integer>();

		ventas.stream().forEach(p -> numeros.add(Integer.parseInt(p.getNumero())));

		if (ventas.isEmpty()) {
			numero = 1;
		} else {
			numero = numeros.stream().max(Integer::compare).get();
			numero++;
		}

		if (numero < 10) { // LA CADENA EMPEZARIA CON 0000001 Y AUMENTA DE ++
			numeroConcatenado = "000000000" + String.valueOf(numero);
		} else if (numero < 100) {
			numeroConcatenado = "00000000" + String.valueOf(numero);

		} else if (numero < 1000) {
			numeroConcatenado = "0000000" + String.valueOf(numero);

		} else if (numero < 10000) {
			numeroConcatenado = "000000" + String.valueOf(numero);

		} else if (numero < 100000) {
			numeroConcatenado = "00000" + String.valueOf(numero);

		}

		return numeroConcatenado;
	}

	@Override
	public List<Venta> findByUsuario(Usuario usuario) {
		return ventaRepository.findByUsuario(usuario);
	}

	@Override
	public Optional<Venta> findById(Integer id) {
		return ventaRepository.findById(id);
	}

}
