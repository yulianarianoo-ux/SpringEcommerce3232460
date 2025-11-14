package com.sena.springecommerce.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.springecommerce.model.DetalleOrden;
import com.sena.springecommerce.model.Orden;
import com.sena.springecommerce.model.Producto;
import com.sena.springecommerce.model.Usuario;
import com.sena.springecommerce.service.IDetalleOrdenService;
import com.sena.springecommerce.service.IOrdenService;
import com.sena.springecommerce.service.IProductoService;
import com.sena.springecommerce.service.IUsuarioService;

@RestController
@RequestMapping("/aipordenes")
public class APIOrden {

	@Autowired
	private IProductoService productoService;

	@Autowired
	private IDetalleOrdenService detalleService;

	@Autowired
	private IOrdenService ordenService;

	@Autowired
	private IUsuarioService usuarioService;

	// Crear una orden con varios detalles
	@PostMapping("/apiordenesfinal")
	public ResponseEntity<Orden> crearOrdenConDetalles(@RequestBody Orden orden) {

		// Validar que el ususario exista
		if (orden.getUsuario() == null || orden.getUsuario().getId() == null) {
			throw new RuntimeException("Debe asignar un usuario a la orden");

		}
		Usuario usuario = usuarioService.findById(orden.getUsuario().getId())
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado con id :" + orden.getUsuario().getId()));
		orden.setUsuario(usuario);
		Date fecha = new Date();
		orden.setFechacreracion(fecha);
		orden.setNumero(ordenService.generarNumeroOrden());
		orden.setTotal(0.0);

		Orden ordenGuardada = ordenService.save(orden);

		// Crear lista detalles finales

		List<DetalleOrden> detalleGuardado = new ArrayList<>();

		// Validar y guardar cada detalle
		if (orden.getDetalle() != null) {
			for (DetalleOrden detalle : orden.getDetalle()) {

				// Buscar producto
				Producto producto = productoService.get(detalle.getProducto().getId()).orElseThrow(
						() -> new RuntimeException("Producto no encontrado con id :" + detalle.getProducto().getId()));

				// Verificar disponibilidad
				if (producto.getCantidad() < detalle.getCantidad()) {
					throw new RuntimeException("No hay suficiente stock del producto:" + producto.getNombre());

				}

				// Actualizar stock
				producto.setCantidad(Integer.valueOf((int) (producto.getCantidad() - detalle.getCantidad())));
				productoService.save(producto);
				detalle.setProducto(producto);

				// Calcular total del detalle

				detalle.setPrecio(producto.getPrecio());
				detalle.setTotal(producto.getPrecio() * detalle.getCantidad());
				detalle.setOrden(orden);
				detalle.setNombre(producto.getNombre());

				// Guardar Detalles
				DetalleOrden detalleOrden = detalleService.save(detalle);
				detalleGuardado.add(detalleOrden);
			}
		}

		// Calcular total de la orden
		double totalOrden = detalleGuardado.stream().mapToDouble(DetalleOrden::getTotal).sum();
		ordenGuardada.setTotal(totalOrden);

		// Guardar la orden (Ultimo paso)
		ordenGuardada.setDetalle(detalleGuardado);
		ordenService.save(ordenGuardada);

		return ResponseEntity.status(HttpStatus.CREATED).body(ordenGuardada);

	}

	// Endpoint POST para crear detalle

	// Endpoint GET para obtener todos los productos
	@GetMapping("/list")
	public List<Orden> getAllOrdens() {
		return ordenService.findAll();
	}

	// Endpoint GET para mostar una orden por ID
	@GetMapping("/orden/{id}")
	public ResponseEntity<Orden> getOrdeByEntity(@PathVariable Integer id) {
		Optional<Orden> orden = ordenService.findById(id);
		return orden.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

}
