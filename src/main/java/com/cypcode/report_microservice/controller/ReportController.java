package com.cypcode.report_microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cypcode.report_microservice.domain.EmployeeDTO;
import com.cypcode.report_microservice.service.ReportService;
import java.util.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("report")
public class ReportController {

	@Autowired
	private ReportService reportService;
	
	
	@GetMapping("employee-detail")
	public ResponseEntity<?> getEmployeeDetailReport() throws Exception {
		 byte[] bytes = reportService.exportDetailReport("pdf");
	       
		ByteArrayResource resource = new ByteArrayResource(bytes);
       String fileName = "Employee_Detail_Report_" + new Date() + ".pdf";
      
		return ResponseEntity.ok()
               .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
               .contentLength(resource.contentLength())
               .contentType(MediaType.APPLICATION_OCTET_STREAM)
               .body(resource);
	}
	
	@GetMapping("employee-list")
	public ResponseEntity<?> getEmployeeListReport() throws Exception {
		List<EmployeeDTO> dtoList = new ArrayList<EmployeeDTO>();
		dtoList.add(EmployeeDTO.builder().id("50001").name("Cypcode 1").email("test1@cypcode.com").build());
		dtoList.add(EmployeeDTO.builder().id("50002").name("Cypcode 2").email("test2@cypcode.com").build());
		dtoList.add(EmployeeDTO.builder().id("50003").name("Cypcode 3").email("test3@cypcode.com").build());
		
		byte[] bytes = reportService.exportListReport(dtoList, "xlsx");
	       
		ByteArrayResource resource = new ByteArrayResource(bytes);
       String fileName = "Employee_List_Report_" + new Date() + ".xlsx";
      
		return ResponseEntity.ok()
               .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
               .contentLength(resource.contentLength())
               .contentType(MediaType.APPLICATION_OCTET_STREAM)
               .body(resource);
	}
}
