package com.achavez.springboot.controlempleados.app.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.achavez.springboot.controlempleados.app.models.Empleado;

public interface EmpleadoRepository extends PagingAndSortingRepository<Empleado, Long>{

}
