package com.proyecto.sisc.SISC.service;

import java.util.List;
import java.util.Optional;


import com.proyecto.sisc.SISC.model.Usuario;

public interface IUsuarioService {
	Optional<Usuario> findById(Integer id);
	Usuario save (Usuario usuario);
	Optional<Usuario> findByEmail(String email);
	List<Usuario>findAll();
}
