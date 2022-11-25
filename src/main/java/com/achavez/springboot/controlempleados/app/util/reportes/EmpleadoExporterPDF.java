package com.achavez.springboot.controlempleados.app.util.reportes;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.achavez.springboot.controlempleados.app.models.Empleado;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;


public class EmpleadoExporterPDF {
	
	private List<Empleado> listaEmpleados;

	public EmpleadoExporterPDF(List<Empleado> listaEmpleados) {
		super();
		this.listaEmpleados = listaEmpleados;
	}

	private void escribirCabeceraDeLaTabla(PdfPTable tabla) {
		PdfPCell celda = new PdfPCell();

		celda.setBackgroundColor(Color.gray);
		celda.setPadding(5);

		com.lowagie.text.Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setColor(Color.WHITE);
		fuente.setSize(11);
		celda.setPhrase(new Phrase("ID", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Nombre completo", fuente));
		tabla.addCell(celda);

//		celda.setPhrase(new Phrase("Apellido", fuente));
//		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Email", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Fecha", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Telefono", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Sexo", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Salario", fuente));
		tabla.addCell(celda);
	}

	private void escribirDatosDeLaTabla(PdfPTable tabla) {
		for (Empleado empleado : listaEmpleados) {
			tabla.addCell(String.valueOf(empleado.getId()));
			String nombreCompleto = empleado.getNombre() +" "+ empleado.getPrimerApellido() + " "+ empleado.getSegundoApellido(); 
			tabla.addCell(nombreCompleto);
			tabla.addCell(empleado.getEmail());
			tabla.addCell(empleado.getFechaNacimiento().toString());
			tabla.addCell(String.valueOf(empleado.getTelefono()));
			tabla.addCell(empleado.getSexo());
			tabla.addCell(String.valueOf(empleado.getSalario()));
		}
	}

	public void exportar(HttpServletResponse response) throws DocumentException, IOException {
		Document documento = new Document(PageSize.A4);
		PdfWriter.getInstance(documento, response.getOutputStream());

		documento.open();

		com.lowagie.text.Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fuente.setColor(Color.black);
		fuente.setSize(22);

		Paragraph titulo = new Paragraph("Lista de empleados", fuente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(titulo);

		PdfPTable tabla = new PdfPTable(7);
		tabla.setWidthPercentage(100);
		tabla.setSpacingBefore(15);
		tabla.setWidths(new float[] { 1f, 4.9f, 6f, 2.9f, 3.5f, 1.8f, 2.2f });
		tabla.setWidthPercentage(110);

		escribirCabeceraDeLaTabla(tabla);
		escribirDatosDeLaTabla(tabla);

		documento.add(tabla);
		documento.close();
	}
}
