package com.proyecto.sisc.SISC.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.sisc.SISC.model.Usuario;
import com.proyecto.sisc.SISC.model.Venta;
import com.proyecto.sisc.SISC.service.IUsuarioService;
import com.proyecto.sisc.SISC.service.IVentaService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IVentaService ventaService;
	
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@GetMapping("/registro")
	public String create() {
		return "usuario/registro";
	}

	@PostMapping("/save")
	public String save(Usuario usuario) {
		logger.info("Usuario registro: {}", usuario);
		usuario.setTipo("USER");
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		usuarioService.save(usuario);

		return "redirect:/";
	}

	@GetMapping("/login")
	public String Login() {
		return "usuario/login";
	}

	@GetMapping("/acceder")
	public String acceder(Usuario usuario, HttpSession session) {
		logger.info("Accesos : {}", usuario);

		Optional<Usuario> user = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));

		if (user.isPresent()) {
			session.setAttribute("idusuario", user.get().getId());
			if (user.get().getTipo().equals("ADMIN")) {
				return "redirect:/administrador";
			} else {
				return "redirect:/";
			}
		}else {
			logger.info("Usuario no existe");
		}

		return "redirect:/";
	}
	
	@GetMapping("/compras")
	public String obtenerCompra(Model model, HttpSession session) {
		model.addAttribute("sesion",session.getAttribute("idusuario"));
		
		Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		List<Venta> ventas= ventaService.findByUsuario(usuario);
		
		model.addAttribute("ventas",ventas);
		
		
		return "usuario/compras";
	}
	
	@GetMapping("/detalle/{id}")
	public String detalleCompra(@PathVariable Integer id, HttpSession session, Model model ) {
		logger.info("Id de la orden {}",id);
		Optional<Venta> venta = ventaService.findById(id);
		
		model.addAttribute("ventas", venta.get().getDetalle());
		
		//session
		model.addAttribute("sesion",session.getAttribute("idusuario"));
		
		return "usuario/detallecompra";
	}
	
	@GetMapping("/cerrar")
	public String cerrarSesion(HttpSession session) {
		
		session.removeAttribute("idusuario");
	
		return "redirect:/";
	}
}
