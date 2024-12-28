package com.cypcode.report_microservice.service;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import com.cypcode.report_microservice.configuration.ReportConfiguration;
import com.cypcode.report_microservice.domain.EmployeeDTO;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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
import java.util.*;
import java.io.*;
@Slf4j
@Service
public class ReportService {
	
	@Autowired
	private ReportConfiguration reportConfiguration;
	
	public byte[] exportDetailReport(String type) throws Exception {
		 
		String template = "templates/DetailReport.jrxml";
	    
		 Map<String, Object> parameters = new HashMap<>();

		    parameters.put("title", "Cypcode Detail Report");
		    parameters.put("id", "5000");
		    parameters.put("name", "Cypcode");
		    parameters.put("email", "test@cypcode.com");

		    String path = ResourceUtils.getFile("classpath:" + template).getAbsolutePath();
		    JasperReport jasperReport = JasperCompileManager.compileReport(path);
		    JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource((Arrays.asList(new EmployeeDTO())));
		    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, datasource);

		    return reportConfiguration.exportJasperReportBytes(jasperPrint, type);
	}
	
	public byte[] exportListReport(Object data, String type) throws Exception {
		 
		String template = "templates/ListReport.jrxml";
	    JRBeanCollectionDataSource listDatasource = new JRBeanCollectionDataSource((Collection<?>) data);
	    
		 Map<String, Object> parameters = new HashMap<>();

		    parameters.put("title", "Cypcode List Report");

		    String path = ResourceUtils.getFile("classpath:" + template).getAbsolutePath();
		    JasperReport jasperReport = JasperCompileManager.compileReport(path);
		    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, listDatasource);

		    return reportConfiguration.exportJasperReportBytes(jasperPrint, type);
	}
}
