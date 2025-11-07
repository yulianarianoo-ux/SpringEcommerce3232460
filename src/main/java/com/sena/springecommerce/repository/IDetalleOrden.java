package com.sena.springecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.springecommerce.model.DetalleOrden;

@Repository

public interface IDetalleOrden extends JpaRepository<DetalleOrden, Integer> {

}
