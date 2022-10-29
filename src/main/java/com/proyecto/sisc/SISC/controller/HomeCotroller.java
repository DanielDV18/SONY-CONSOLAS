package com.proyecto.sisc.SISC.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.sisc.SISC.model.DetalleVenta;
import com.proyecto.sisc.SISC.model.Producto;
import com.proyecto.sisc.SISC.model.Usuario;
import com.proyecto.sisc.SISC.model.Venta;
import com.proyecto.sisc.SISC.service.IDetalleVentaService;
import com.proyecto.sisc.SISC.service.IUsuarioService;
import com.proyecto.sisc.SISC.service.IVentaService;
import com.proyecto.sisc.SISC.service.ProductoService;

@Controller
@RequestMapping("/")
public class HomeCotroller {

	private final Logger log = LoggerFactory.getLogger(HomeCotroller.class);

	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IVentaService ventaService;
	
	@Autowired
	private IDetalleVentaService detalleVentaService;

	// PARA ALMACENAR LOS DETALLES DE LA VENTA
	List<DetalleVenta> detalles = new ArrayList<DetalleVenta>();

	// DATOS DE LA VENTA
	Venta venta = new Venta();

	@GetMapping("")
	public String home(Model model, HttpSession session) {
		
		log.info("Sesion del usuario: {}",session.getAttribute("idusuario"));
		
		model.addAttribute("productos", productoService.findAll());
		
		
		//SESSION
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		
		return "usuario/index";
	}

	@GetMapping("/index")
    public String index(Model model, HttpSession session) {
		log.info("Sesion del usuario: {}",session.getAttribute("idusuario"));
		
		model.addAttribute("productos", productoService.findAll());
		
		
		//SESSION
		model.addAttribute("sesion", session.getAttribute("idusuario"));
    	return "usuario/index";
    }

	@GetMapping("/store")
    public String store(Model model) {
		model.addAttribute("productos", productoService.findAll());
    	return "usuario/store";
    }

	
	@GetMapping("productohome2/{id}")
	public String productohome2(@PathVariable Integer id, Model model) {
		log.info("Id Producto enviado como parametro {}", id);
		Producto producto = new Producto();
		Optional<Producto> productoOptional = productoService.get(id);
		producto = productoOptional.get();

		model.addAttribute("producto", producto);
		return "usuario/productohome2";
	}

	@PostMapping("/cart")
	public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {

		DetalleVenta detalleventa = new DetalleVenta();
		Producto producto = new Producto();
		double sumaTotal = 0;

		Optional<Producto> optionaProducto = productoService.get(id);
		log.info("Producto añadido: {}", optionaProducto.get());
		log.info("Cantidad: {}", cantidad);
		producto = optionaProducto.get();

		detalleventa.setCantidad(cantidad);
		detalleventa.setPrecio(producto.getPrecio());
		detalleventa.setNombre(producto.getNombre());
		detalleventa.setTotal(producto.getPrecio() * cantidad);
		detalleventa.setProducto(producto);
		
		
		//VALIDAR QUE UN PRODUCTO NO SE AÑADA DOS VECES
		Integer idProducto = producto.getId();
		boolean ingresado=detalles.stream().anyMatch(p -> p.getProducto().getId()==idProducto);
		
		if(!ingresado) {
			// AÑADIR LA VENTA A LA VISTA
			detalles.add(detalleventa);
		}
		

		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

		venta.setTotal(sumaTotal);
		model.addAttribute("cart", detalles);
		model.addAttribute("venta", venta);

		return "usuario/carrito2";
	}

	
	@GetMapping("/getCart")
	public String getCart(Model model, HttpSession session) {
		
		model.addAttribute("cart", detalles);
		model.addAttribute("venta", venta);
		
		//session
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		
		return "/usuario/carrito2";
	}
	
	@GetMapping("/order")
	public String Order(Model model, HttpSession session) {
		
		Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		
		model.addAttribute("cart", detalles);
		model.addAttribute("venta", venta);
		model.addAttribute("usuario", usuario);
		
		return "usuario/resumenorden";
	}
	
	@GetMapping("/saveVenta")
	public String saveVenta(HttpSession session) {
		
		Date fechaCreacion = new Date();
		venta.setFechaCreacion(fechaCreacion);
		venta.setNumero(ventaService.generarNumeroDeVenta());
		
		
		//REFERENCIA DE USUARIO
		Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		
		venta.setUsuario(usuario);
		ventaService.save(venta);
		
		//GUARDAR LOS DETALLES
		for(DetalleVenta dt:detalles) {
			dt.setVenta(venta);
			detalleVentaService.save(dt);
		}
		//CLEAN LISTA DETALLE Y VENTA
		venta = new Venta();
		detalles.clear();
		
		return "redirect:/";
	}
	
	@PostMapping("/buscar")
	public String Buscar(@RequestParam String nombre, Model model) {
		
		log.info("Nombre del producto: {}",nombre );
		
		
		List<Producto> productos=productoService.findAll().stream().filter
		(p -> p.getNombre().contains(nombre)).collect(Collectors.toList());
		
		model.addAttribute("productos",productos);
		return "usuario/index";
	}

	/*Borrar*/

	@PostMapping("/cart2")
	public String addCart2(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {

		DetalleVenta detalleventa = new DetalleVenta();
		Producto producto = new Producto();
		double sumaTotal = 0;

		Optional<Producto> optionaProducto = productoService.get(id);
		log.info("Producto añadido: {}", optionaProducto.get());
		log.info("Cantidad: {}", cantidad);
		producto = optionaProducto.get();

		detalleventa.setCantidad(cantidad);
		detalleventa.setPrecio(producto.getPrecio());
		detalleventa.setNombre(producto.getNombre());
		detalleventa.setTotal(producto.getPrecio() * cantidad);
		detalleventa.setProducto(producto);
		
		
		//VALIDAR QUE UN PRODUCTO NO SE AÑADA DOS VECES
		Integer idProducto = producto.getId();
		boolean ingresado=detalles.stream().anyMatch(p -> p.getProducto().getId()==idProducto);
		
		if(!ingresado) {
			// AÑADIR LA VENTA A LA VISTA
			detalles.add(detalleventa);
		}
		

		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

		venta.setTotal(sumaTotal);
		model.addAttribute("cart2", detalles);
		model.addAttribute("venta", venta);

		return "usuario/carrito2";
	}

	// DELETE DE CARRITO
	@GetMapping("/delete/cart2/{id}")
	public String deleteProdCart2(@PathVariable Integer id, Model model) {

		List<DetalleVenta> ordenesnuevas = new ArrayList<DetalleVenta>();
		
		for (DetalleVenta detalleVenta : detalles) {
			if (detalleVenta.getProducto().getId() != id) {
				ordenesnuevas.add(detalleVenta);
			}
		}
		// NUEVA LISTA CON LOS PRODUCTOS RESTANTES DEL CARRITO
		detalles = ordenesnuevas;

		// RECALCULAR
		double sumaTotal = 0;
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

		venta.setTotal(sumaTotal);
		model.addAttribute("cart2", detalles);
		model.addAttribute("venta", venta);

		return "usuario/carrito2";
	}
	
	
	@GetMapping("/getCart2")
	public String getCart2(Model model, HttpSession session) {
		
		model.addAttribute("cart2", detalles);
		model.addAttribute("venta", venta);
		
		//session
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		
		return "/usuario/carrito2";
	}

	@GetMapping("/order2")
	public String Order2(Model model, HttpSession session) {
		
		Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		
		model.addAttribute("cart2", detalles);
		model.addAttribute("venta", venta);
		model.addAttribute("usuario", usuario);
		
	

		return "usuario/resumenorden2";
	}

	@GetMapping("/saveVenta2")
	public String saveVenta2(HttpSession session) {
		
		Date fechaCreacion = new Date();
		venta.setFechaCreacion(fechaCreacion);
		venta.setNumero(ventaService.generarNumeroDeVenta());
		
		
		//REFERENCIA DE USUARIO
		Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		
		venta.setUsuario(usuario);
		ventaService.save(venta);
		
		//GUARDAR LOS DETALLES
		for(DetalleVenta dt2:detalles) {
			dt2.setVenta(venta);
			detalleVentaService.save(dt2);
		}
		//CLEAN LISTA DETALLE Y VENTA
		venta = new Venta();
		detalles.clear();
		
		return "redirect:/index";
	}
}
