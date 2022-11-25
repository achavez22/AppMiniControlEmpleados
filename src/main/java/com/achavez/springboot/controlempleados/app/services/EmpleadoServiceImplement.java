package com.achavez.springboot.controlempleados.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.achavez.springboot.controlempleados.app.models.Empleado;
import com.achavez.springboot.controlempleados.app.repository.EmpleadoRepository;


@Service
public class EmpleadoServiceImplement implements EmpleadoService{

	
	@Autowired
	private EmpleadoRepository empleadoRepository; 
	
	@Override
	@Transactional(readOnly = true)
	public List<Empleado> findAll() {
		// TODO Auto-generated method stub
		return (List<Empleado>) empleadoRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Empleado> findAll(Pageable pageable) {
		
		return empleadoRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Empleado empleado) {
		empleadoRepository.save(empleado); 
		
	}

	@Override
	public Empleado findOne(Long id) {
		return empleadoRepository.findById(id).get();
	}

	@Override
	@Transactional
	public void delete(Long id) {
		empleadoRepository.deleteById(id);
		
	}

}
