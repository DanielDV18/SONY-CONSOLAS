package com.proyecto.sisc.SISC.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "usuarios") 
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    private String nombre;
    private String username;
    @NotEmpty
    @Email
    @Column(length=40, nullable=false, unique = true)
    private String email;
    private String direccion;
    @NotEmpty
    private String telefono;
    private String tipo;
    private boolean isEnabled;
    @NotEmpty
    private String password;
  
    
    //RELACIONES
    @OneToMany(mappedBy = "usuario")
    private List<Producto> productos;

    
    @OneToMany(mappedBy = "usuario")
    private List<Venta> ventas;
    public Usuario(){

    }


    public Usuario(Integer id, @NotEmpty String nombre, String username, @NotEmpty @Email String email,
            String direccion, String telefono, String tipo, boolean isEnabled, @NotEmpty String password,
            List<Producto> productos, List<Venta> ventas) {
        this.id = id;
        this.nombre = nombre;
        this.username = username;
        this.email = email;
        this.direccion = direccion;
        this.telefono = telefono;
        this.tipo = tipo;
        this.isEnabled = isEnabled;
        this.password = password;
        this.productos = productos;
        this.ventas = ventas;
    }



    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public List<Producto> getProductos() {
        return productos;
    }


    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }


	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", username=" + username + ", email=" + email
				+ ", direccion=" + direccion + ", telefono=" + telefono + ", tipo=" + tipo + ", password=" + password
				+ "]";
	}


   
    
}
