package com.sena.springecommerce.controller;

import java.util.List;
import java.util.Optional;

import com.sena.springecommerce.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.springecommerce.model.Usuario;

@RestController
@RequestMapping("/apiusuario")

public class APIUsuarioController {

	@Autowired
	IUsuarioService usuariosService;

	// Endpoint GET para obtener todos los productos
	@GetMapping("/list")
	public List<Usuario> getUsuarios() {
		return usuariosService.findAll();

	}

	// Endpoint GET para obtener un productor por ID
	@GetMapping("/usuario/{id}")
	public ResponseEntity<Usuario> getUsuarioByEntity(@PathVariable Integer id) {
		Optional<Usuario> usuario = usuariosService.get(id);
		return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

	}

	// Endpoint POST para crear un nuevo producto
	@PostMapping("/create")
	public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
		Usuario savedUsuario = usuariosService.save(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
	}

//Endpoint PUT para actualizar un producto
	@PutMapping("/update/{id}")
	public ResponseEntity<Usuario> updateUsuario(@PathVariable Integer id, @RequestBody Usuario productDetails) {
		Optional<Usuario> usuario = usuariosService.get(id);
		if (!usuario.isPresent()) {
			return ResponseEntity.notFound().build();

		}
		Usuario existingUsuario = usuario.get();
		existingUsuario.setDireccion(productDetails.getDireccion());
		existingUsuario.setEmail(productDetails.getEmail());
		existingUsuario.setNombre(productDetails.getNombre());
		existingUsuario.setPassword(productDetails.getPassword());
		existingUsuario.setTelefono(productDetails.getTelefono());
		usuariosService.update(existingUsuario);
		return ResponseEntity.ok(existingUsuario);
	}

	// Endpoint DELETE para eliminar un producto
	@DeleteMapping("/delet/{id}")
	public ResponseEntity<?> deleteUsuario(@PathVariable Integer id) {
		Optional<Usuario> usuario = usuariosService.get(id);
		if (!usuario.isPresent()) {
			return ResponseEntity.notFound().build();

		}
		usuariosService.delete(id);
		return ResponseEntity.ok().build();
	}

}
