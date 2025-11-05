package com.sena.springecommerce.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ordenes ")
public class Orden {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // anotacion JPA
	private Integer id;
	private String numero;
	private Date fechacreracion;
	private Double total;

	@ManyToOne
	private Usuario usuarios;

	@OneToMany(mappedBy = "orden")
	private List<DetalleOrden> detalle;

	public Orden() {
		super();

	}

	public Orden(Integer id, String numero, Date fechacreracion, Double total) {
		super();
		this.id = id;
		this.numero = numero;
		this.fechacreracion = fechacreracion;
		this.total = total;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getFechacreracion() {
		return fechacreracion;
	}

	public void setFechacreracion(Date fechacreracion) {
		this.fechacreracion = fechacreracion;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Usuario getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Usuario usuarios) {
		this.usuarios = usuarios;
	}

	public List<DetalleOrden> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<DetalleOrden> detalle) {
		this.detalle = detalle;
	}

	@Override
	public String toString() {
		return "Orden [id=" + id + ", numero=" + numero + ", fechacreracion=" + fechacreracion + ", total=" + total
				+ "]";
	}

}
