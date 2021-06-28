package com.floow.driverdetails.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.floow.driverdetails.model.Driver;
import com.floow.driverdetails.service.DriverDetailsService;

@RunWith(SpringRunner.class)
@WebMvcTest(DriverDetailsController.class)
public class DriverDetailsControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	DriverDetailsService driverDetailsService;

	@Test
	public void createDriverTest() throws Exception {

		Driver driver = new Driver("100", "Jack", "Super", "1983-05-12", "2021-06-28");
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(driver);
		given(driverDetailsService.createDriver(Mockito.any())).willReturn(HttpStatus.CREATED.toString());
		mockMvc.perform(post("/driver/create").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isCreated());

	}

	@Test
	public void getDriversByDateTest() throws Exception {

		Driver driver = new Driver("100", "Jack", "Super", "1983-05-12", "2021-06-28");
		Driver driver1 = new Driver("101", "Jill", "Mark", "1984-06-15", "2021-06-28");
		List<Driver> driversList = new ArrayList<Driver>();
		driversList.add(driver);
		driversList.add(driver1);
		given(driverDetailsService.getDriversByDate(Mockito.anyString())).willReturn(driversList);
		mockMvc.perform(get("/drivers/byDate").queryParam("date", "1980-05-12").contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
				.andExpect(jsonPath("$[0].lastName", is("Super")))
		        .andExpect(jsonPath("$[1].lastName", is("Mark")));
	}

	@Test
	public void getAllDriversTest() throws Exception {

		Driver driver = new Driver("100", "Jack", "Super", "1983-05-12", "2021-06-28");
		List<Driver> driversList = new ArrayList<Driver>();
		driversList.add(driver);
		given(driverDetailsService.getDriversList()).willReturn(driversList);
		mockMvc.perform(get("/drivers").contentType(MediaType.APPLICATION_JSON))
		           .andExpect(status().isOk());
	}
	
	@Test
	public void getException_DriverObjectNull() throws Exception {
		Driver driver =null;
		List<Driver> driversList = new ArrayList<Driver>();
		driversList.add(driver);
		given(driverDetailsService.getDriversList()).willReturn(driversList);
		mockMvc.perform(get("/drivers").contentType(MediaType.APPLICATION_JSON))
		           .andExpect(status().isBadRequest());
		
	}

}
