package com.cypcode.report_microservice.configuration;

import java.io.ByteArrayOutputStream;

import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXmlExporterOutput;

@Slf4j
@Configuration
public class ReportConfiguration {

		public byte[] exportJasperReportBytes(JasperPrint jasperPrint, String reportType) throws JRException {
		    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		    switch (reportType) {
		        case "csv":
		            // Export to CSV
		            JRCsvExporter csvExporter = new JRCsvExporter();
		            csvExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		            csvExporter.setExporterOutput(new SimpleWriterExporterOutput(outputStream));
		            csvExporter.exportReport();
		            break;
		        case "xlsx":
		            // Export to XLSX
		            JRXlsxExporter xlsxExporter = new JRXlsxExporter();
		            xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		            xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
		            xlsxExporter.exportReport();
		            break;
		        case "html":
		            // Export to HTML
		            HtmlExporter htmlExporter = new HtmlExporter();
		            htmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		            htmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(outputStream));
		            htmlExporter.exportReport();
		            break;
		        case "xml":
		            // Export to XML
		            JRXmlExporter xmlExporter = new JRXmlExporter();
		            xmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		            xmlExporter.setExporterOutput(new SimpleXmlExporterOutput(outputStream));
		            xmlExporter.exportReport();
		            break;
		        case "doc":
		            // Export to DOCX (RTF format)
		            JRRtfExporter docxExporter = new JRRtfExporter();
		            docxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		            docxExporter.setExporterOutput(new SimpleWriterExporterOutput(outputStream));
		            docxExporter.exportReport();
		            break;
		        default:
		            // Export to PDF by default
		            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
		            break;
		    }
		    return outputStream.toByteArray();
		}
}
