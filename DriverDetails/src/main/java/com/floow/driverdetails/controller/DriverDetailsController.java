package com.floow.driverdetails.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.floow.driverdetails.model.Driver;
import com.floow.driverdetails.service.DriverDetailsService;

@RestController
public class DriverDetailsController {
	Logger logger = LoggerFactory.getLogger(DriverDetailsController.class);

	@Autowired
	private DriverDetailsService driverDetailsService;

	@PostMapping(path = "/driver/create", consumes = "application/json")
	public ResponseEntity<Driver> createDriver(@Valid @RequestBody Driver driver) throws IOException {
		
		driverDetailsService.createDriver(driver);
		logger.info("Driver created succcessfully");
		return new ResponseEntity<>(HttpStatus.CREATED);
		
	}

	@GetMapping(path = "/drivers", produces = "application/json")
	public ResponseEntity<List<Driver>> getDriversList() throws FileNotFoundException, IOException {

		List<Driver> driverList = null;
		driverList = driverDetailsService.getDriversList();
		logger.info("Drivers list fetched succcessfully");
		return new ResponseEntity<>(driverList, HttpStatus.OK);

	}

	@GetMapping(path = "/drivers/byDate", produces = "application/json")
	public ResponseEntity<List<Driver>> getDriversByDate(@RequestParam String date)
			throws FileNotFoundException, IOException {
		List<Driver> driversList = new ArrayList<Driver>();
		driversList = driverDetailsService.getDriversByDate(date);
		logger.info("Driver list  by date fetched succcessfully");
		return new ResponseEntity<>(driversList, HttpStatus.OK);
	}
}
