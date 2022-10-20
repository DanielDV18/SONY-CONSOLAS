package com.proyecto.sisc.SISC.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.sisc.SISC.model.Producto;
import com.proyecto.sisc.SISC.model.Usuario;
import com.proyecto.sisc.SISC.service.IUsuarioService;
import com.proyecto.sisc.SISC.service.ProductoService;
import com.proyecto.sisc.SISC.service.UploadFileService;

@Controller
@RequestMapping("/productos")
public class ProductoController {
    
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private UploadFileService upload;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	//FUNCIONES
	
    @GetMapping("")
    public String show(Model model){
    	model.addAttribute("productos",productoService.findAll());
        return "productos/show";
    }

    @GetMapping("/create")
    public String create(Model m){
		Producto producto = new Producto();
		m.addAttribute("producto", producto);
        return "productos/create";
    }
    
    @PostMapping("/save")
    public String save(@Valid Producto producto,BindingResult res, Model m, @RequestParam("img") MultipartFile file, HttpSession session ) throws IOException {
		if(res.hasErrors()){
			return "productos/create";
		}
		m.addAttribute("producto", producto);
    	
    	LOGGER.info("Este es el objeto del producto {}",producto);
    	Usuario u= usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString() )).get();
    	producto.setUsuario(u);
		//imagen
    	if(producto.getId()==null) { //Primera vez de un producto
    		String nombreImagen= upload.saveImage(file);
    		producto.setImagen(nombreImagen);
    	}
    	
    	else {
    		
    	}
    	productoService.save(producto);
    	return "redirect:/productos";
    }
    
    
    @GetMapping("/editar/{id}")
    public String edit(@PathVariable Integer id, Model model) {
    	Producto producto=new Producto();
    	Optional<Producto> optionalProducto=productoService.get(id);
    	producto= optionalProducto.get();
    	
    	LOGGER.info("Producto encontrado: {}",producto);
    	model.addAttribute("producto",producto);
    	
    	
    	return "productos/editar";
    }
    
    
    @PostMapping("/update")
    public String update(Producto producto, @RequestParam("img") MultipartFile file)  throws IOException {
    	Producto p=new Producto();
		p=productoService.get(producto.getId()).get();
    	
    	if(file.isEmpty()) { //cuando se edita producto pero no la img
			
			producto.setImagen(p.getImagen());
		}
    	
    	else {//Cuando se edita la img
			
	    	
	    	
	    	//Eliminar cuando la img no sea default.jpg
	    	if(!p.getImagen().equals("default.jpg")) {
	    		upload.deleteImage(p.getImagen());
	    		
	    	}
	    	
	    	
			String nombreImagen= upload.saveImage(file);
    		producto.setImagen(nombreImagen);
		}
    	
    	producto.setUsuario(p.getUsuario());
    	productoService.update(producto);
    	return "redirect:/productos";
    }
    
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
    	
    	Producto p = new Producto();
    	p=productoService.get(id).get();
    	
    	
    	//Eliminar cuando la img no sea default.jpg
    	if(!p.getImagen().equals("default.jpg")) {
    		upload.deleteImage(p.getImagen());
    		
    	}
    	productoService.delete(id);
    	return "redirect:/productos";
    }

}
