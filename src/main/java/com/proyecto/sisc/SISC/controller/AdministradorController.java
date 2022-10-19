package com.proyecto.sisc.SISC.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.proyecto.sisc.SISC.model.Producto;
import com.proyecto.sisc.SISC.model.Venta;
import com.proyecto.sisc.SISC.service.IUsuarioService;
import com.proyecto.sisc.SISC.service.IVentaService;
import com.proyecto.sisc.SISC.service.ProductoService;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
	
	private Logger logg= LoggerFactory.getLogger(AdministradorController.class);

	@Autowired
	private IUsuarioService usuarioService;
	
    @Autowired
    private ProductoService productoService;

    @Autowired
    private IVentaService ventaService;
    
    
    @GetMapping("")
    public String home(Model model) {
    	
    	List<Producto> productos=productoService.findAll();
    	model.addAttribute("productos",productos);
    	
    	return "administrador/home";
    }
    
    
    

    @GetMapping("/usuarios")
    public String usuarios(Model model) {
    	
    	model.addAttribute("usuarios",usuarioService.findAll());
    	
    	return "administrador/usuarios";
    }
    
    @GetMapping("/ventas")
    public String ventas(Model model) {
    	model.addAttribute("ventas",ventaService.findAll());    	
    	return "administrador/ventas";
    }
    @GetMapping("/detalle/{id}")
    public String detalles(Model model, @PathVariable Integer id) {
    	
    	logg.info("Id de la orden: {}",id);
    	Venta venta = ventaService.findById(id).get();
    	
    	model.addAttribute("detalles",venta.getDetalle());
    	
    	return "administrador/detalleventa";
    }
}
