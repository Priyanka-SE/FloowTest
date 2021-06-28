package com.floow.driverdetails.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.floow.driverdetails.model.Driver;

public interface DriverDetailsService {
	
	public String createDriver(Driver driver) throws IOException;
	public List<Driver> getDriversList() throws FileNotFoundException, IOException;
	public List<Driver> getDriversByDate(String date) throws FileNotFoundException, IOException;
	
}
