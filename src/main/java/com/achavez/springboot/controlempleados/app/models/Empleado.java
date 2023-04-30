package com.achavez.springboot.controlempleados.app.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "empleados")
public class Empleado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@NotEmpty
	private String nombre; 
	@NotEmpty
	private String primerApellido;
	@NotEmpty
	private String segundoApellido;
	@NotEmpty
	@Email
	private String email; 
	@NotEmpty
	private String telefono; 
	@NotNull
	private double salario;
	@NotEmpty
	private String Sexo; 
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern= "yyyy-MM-dd")
	private Date fechaNacimiento;
	
	
	//constructor sin argumentos
	public Empleado() {
		
	}
	//constructor con todos los argumentos
	public Empleado(Long id, @NotEmpty String nombre, @NotEmpty String primerApellido, @NotEmpty String segundoApellido,
			@NotEmpty @Email String email, @NotNull String telefono, @NotNull double salario, @NotEmpty String sexo,
			@NotNull Date fechaNacimiento) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.email = email;
		this.telefono = telefono;
		this.salario = salario;
		Sexo = sexo;
		this.fechaNacimiento = fechaNacimiento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public String getSexo() {
		return Sexo;
	}

	public void setSexo(String sexo) {
		Sexo = sexo;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	} 
	
	
	
	
	
	
	

}
