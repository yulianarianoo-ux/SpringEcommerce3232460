package com.sena.springecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.springecommerce.model.Orden;
import com.sena.springecommerce.model.Usuario;
import com.sena.springecommerce.repository.IDetalleOrden;
import com.sena.springecommerce.repository.IOrdenRepository;


@Service
public class OrdenServiceImplement implements IOrdenService{

    private final IDetalleOrden IDetalleOrden;
	
	@Autowired
	private IOrdenRepository ordenRepository;

    OrdenServiceImplement(IDetalleOrden IDetalleOrden) {
        this.IDetalleOrden = IDetalleOrden;
    }

	@Override
	public Orden save(Orden orden) {
		// TODO Auto-generated method stub
		return ordenRepository.save(orden);
	}

	@Override
	public List<Orden> findAll() {
		// TODO Auto-generated method stub
		return ordenRepository.findAll();
	}

	@Override
	public List<Orden> findByUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return ordenRepository.findByUsuario(usuario);
	}

	@Override
	public Optional<Orden> findById(Integer Id) {
		// TODO Auto-generated method stub
		return ordenRepository.findById(Id);
	}

	@Override
	public String generarNumeroOrden() {
		// TODO Auto-generated method stub
		int numero=0;
		String numeroConcatenado="";
		List<Orden> ordenes =findAll();
		List<Integer>numeros =new ArrayList<>();
		//Funciones de java  8
		//variables anonima 
		ordenes.stream().forEach(o-> numeros.add(Integer.parseInt(o.getNumero())));
		//validacion
		if (ordenes.isEmpty()) {
			numero=1;
		}else {
			numero=numeros.stream().max(Integer::compare).get();
			numero++;
		}
		//numero de ordenes 
		if (numero<10) {
			numeroConcatenado ="000000000000"+String.valueOf(numero);
			
		}else if (numero < 100) {
			numeroConcatenado ="00000000000"+String.valueOf(numero);
			
	}else if(numero < 1000) {
		numeroConcatenado ="000000000000"+String.valueOf(numero);
		
		
	}
		return numeroConcatenado;
	}
	
	
}
