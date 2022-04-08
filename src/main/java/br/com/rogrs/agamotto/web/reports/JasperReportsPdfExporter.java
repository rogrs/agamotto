package br.com.rogrs.agamotto.web.reports;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;


public class JasperReportsPdfExporter implements JasperReportsExporter {

	@Override
	public void export(JasperPrint jp, String fileName, HttpServletResponse response) throws JRException, IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// Create a JRPdfExporter instance
		JRPdfExporter exporter = new JRPdfExporter();

		// Here we assign the parameters jp and baos to the exporter
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

		// Retrieve the exported report in PDF format
		exporter.exportReport();

		// Specifies the response header
		response.setHeader("Content-Disposition", "inline; filename=" + fileName);

		// Make sure to set the correct content type
		// Each format has its own content type
		response.setContentType("application/pdf");
		response.setContentLength(baos.size());

		// Retrieve the output stream
		ServletOutputStream outputStream = response.getOutputStream();
		// Write to the output stream
		baos.writeTo(outputStream);
		// Flush the stream
		outputStream.flush();

	}
}