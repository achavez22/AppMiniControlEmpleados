package com.achavez.springboot.controlempleados.app.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.achavez.springboot.controlempleados.app.models.Empleado;
import com.achavez.springboot.controlempleados.app.services.EmpleadoService;
import com.achavez.springboot.controlempleados.app.util.paginacion.PageRender;
import com.achavez.springboot.controlempleados.app.util.reportes.EmpleadoExportarHojaCalculo;
import com.achavez.springboot.controlempleados.app.util.reportes.EmpleadoExporterPDF;
import com.lowagie.text.DocumentException;

@Controller
public class EmpleadoController {
	
	@Autowired
	private EmpleadoService empleadoService; 


	@GetMapping({"", "/","/empleados"})
	public String listarEmpleados(@RequestParam(name = "page", defaultValue = "0") int page , 
			Model model) { 
		
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Empleado> empleados = empleadoService.findAll(pageRequest);
		PageRender<Empleado> pageRender = new PageRender<>("/empleados", empleados);
		model.addAttribute("titulo", "Empleados"); 
		model.addAttribute("empleados", empleados); 
		model.addAttribute("page", pageRender); 
		return "empleados"; 
	}
	
	//ver
	@GetMapping("/ver/{id}")
	public String verDetalle(@PathVariable(value= "id") Long id, Map<String, Object> model,RedirectAttributes flash ) { 
		Empleado empleado = empleadoService.findOne(id);
		if(empleado == null) { 
			flash.addFlashAttribute("error", "el empleado no existe en la base de datos"); 
			return "redirect: /empleados"; 
		}
		model.put("empleado", empleado);
		model.put("titulo", "Detalles del empleado "); //+ empleado.getNombre()); 
		return "ver";
	}
	
	
	@GetMapping("/form")
	public String mostrarFormEmpleado(Map<String, Object> model) { 
		
		Empleado empleado = new Empleado(); 
		model.put("empleado", empleado); 
		model.put("titulo", "Registro de empleado");
		return "form_empleado"; 
		
	}
	
	
	@PostMapping("/form")
	public String guardarEmpleado(@Valid Empleado empleado, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) { 
		if(result.hasErrors()) { 
			model.addAttribute("registro de cliente"); 
			return "form_empleado"; 
		}
		String mensaje = (empleado.getId() != null)? "El empleado ha sido editado exitosamente" : "El empleado registrado con exito" ; 
		empleadoService.save(empleado); 
		status.setComplete(); 
		flash.addFlashAttribute("success", mensaje); 
			
		return "redirect:/empleados";  
	}
	
	@GetMapping("/form/{id}")
	public String editarEmpleado(@PathVariable(value ="id") Long id, Map<String, Object> model, RedirectAttributes flash) { 
		Empleado  empleado = null; 
		if(id > 0 ) { 
			empleado = empleadoService.findOne(id); 
			if(empleado == null) { 
				flash.addAttribute("error", "El ID del empleado no existe en la base de datos."); 
				return "redicrect:/empleados"; 
			}
		}else { 
			flash.addFlashAttribute("error", "El ID del empleado no puede ser 0");
			return "redirect: /empleados"; 
		}
		
		model.put("empleado", empleado); 
		model.put("titulo", "editar empleado"); 
		return "form_empleado"; 
		
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminarEmpleado(@PathVariable(value ="id") Long id, RedirectAttributes flash) {
		if(id > 0 ) { 
			empleadoService.delete(id); 
			flash.addFlashAttribute("success", "Empleado eliminado con exito");
		}
		
		return "redirect:/empleados"; 
	}
	
	@GetMapping("/exportarPDF")
	public void exportarListadoDeEmpleadosEnPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_" + fechaActual + ".pdf";
		
		response.setHeader(cabecera, valor);
		
		List<Empleado> empleados = empleadoService.findAll();
		
		EmpleadoExporterPDF exporter = new EmpleadoExporterPDF(empleados);
		exporter.exportar(response);
	}
	
	@GetMapping("/exportarExcel")
	public void exportarListadoDeEmpleadosEnExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/octet-stream");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_" + fechaActual + ".xlsx";
		
		response.setHeader(cabecera, valor);
		
		List<Empleado> empleados = empleadoService.findAll();
		
		EmpleadoExportarHojaCalculo exporter = new EmpleadoExportarHojaCalculo(empleados);
		exporter.exportar(response);
	}
	
	
}
