package com.floow.driverdetails.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.floow.driverdetails.exception.InvalidRequestException;
import com.floow.driverdetails.model.Driver;

@Service
public class DriverDetailsServiceImpl implements DriverDetailsService {
	
	Logger logger = LoggerFactory.getLogger(DriverDetailsServiceImpl.class);

	@Value("${data.file}")
	String dataFile;

	@Override
	public String createDriver(Driver driver) throws IOException {
		if (isValidDate(driver.getDateOfBirth())) {
			StringBuilder driverDetails = new StringBuilder();
			driverDetails.append(UUID.randomUUID().toString()).append(",").append(driver.getFirstName()).append(",")
					.append(driver.getLastName()).append(",").append(driver.getDateOfBirth()).append(",")
					.append(DateTimeFormatter.ofPattern("YYYY-MM-dd").format(LocalDate.now())).append("\n");

			try (FileOutputStream writer = new FileOutputStream(dataFile, true)) {

				writer.write(driverDetails.toString().getBytes());
				logger.info("Driver details saved");
			}
		}

		return HttpStatus.CREATED.toString();
	}

	@Override
	public List<Driver> getDriversList() throws FileNotFoundException, IOException {
		List<Driver> driversList = new ArrayList<Driver>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(dataFile))))) {
			Stream<String> stream = br.lines();
			driversList = stream.map(record -> {
				Driver driver = new Driver();
				driver.setDriverID((record.split(",")[0]));
				driver.setFirstName(record.split(",")[1]);
				driver.setLastName(record.split(",")[2]);
				driver.setDateOfBirth(record.split(",")[3]);
				driver.setCreationDate(record.split(",")[4]);
				return driver;
			}).collect(Collectors.toList());
		}
		return driversList;

	}

	@Override
	public List<Driver> getDriversByDate(String date) throws FileNotFoundException, IOException {
		List<Driver> driversList = new ArrayList<Driver>();
		if (isValidDate(date)) {
			try (BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(dataFile))))) {
				Stream<String> stream = br.lines();

				driversList = stream
						.filter(record -> LocalDate.parse(record.split(",")[4]).isAfter(LocalDate.parse(date)))
						.map(record -> {
							Driver driver = new Driver();
							driver.setDriverID((record.split(",")[0]));
							driver.setFirstName(record.split(",")[1]);
							driver.setLastName(record.split(",")[2]);
							driver.setDateOfBirth(record.split(",")[3]);
							driver.setCreationDate(record.split(",")[4]);
							return driver;
						}).collect(Collectors.toList());

			}
		}

		return driversList;
	}

	private Boolean isValidDate(String date) {
		boolean result = false;
		try {
			LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
			result = true;
		} catch (Exception exception) {
			logger.error(exception.getMessage());
			throw new InvalidRequestException("Please send the date in the format yyyy-MM-DD");

		}

		return result;
	}

}
