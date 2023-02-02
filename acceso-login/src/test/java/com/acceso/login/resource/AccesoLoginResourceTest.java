package com.acceso.login.resource;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.acceso.login.dto.AutenticacionSolicitudDTO;
@SpringBootTest
class AccesoLoginResourceTest {
	@Autowired
	AccesoLoginResource accesoLoginResource;
	private static ResponseEntity<Object> responseEntity =  null ;
	private static String claveIngresoTest ="DDIAZ10";
	private static String usuarioIngresoTest = "1094970590";
	private static Integer idAplicacion = 2;
	
	@Test
	void testIniciarSesionVentas() {
		AutenticacionSolicitudDTO credenciales = new AutenticacionSolicitudDTO();
		credenciales.setClaveIngreso(claveIngresoTest);
		credenciales.setUsuarioIngreso(usuarioIngresoTest);
		credenciales.setIdAplicacion(idAplicacion);
		responseEntity = accesoLoginResource.iniciarSesion(credenciales);
		assertAll("resultado",
				() -> assertNotNull(responseEntity.getBody()),
				() -> assertEquals(HttpStatus.OK,responseEntity.getStatusCode())
		);
	}
	@Test
	void testIniciarSesionVentasFallas() {
		AutenticacionSolicitudDTO credenciales = new AutenticacionSolicitudDTO();
		credenciales.setClaveIngreso("DDIAZ1s0");
		credenciales.setUsuarioIngreso(usuarioIngresoTest);
		credenciales.setIdAplicacion(idAplicacion);
		responseEntity = accesoLoginResource.iniciarSesion(credenciales);
		assertAll("resultado",
				() -> assertNotNull(responseEntity.getBody()),
				() -> assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode())
		);
	}
	@Test
	void testIniciarSesionBackoffices() {
		AutenticacionSolicitudDTO credenciales = new AutenticacionSolicitudDTO();
		credenciales.setClaveIngreso("ADATACENTER10");
		credenciales.setUsuarioIngreso("10101012");
		credenciales.setIdAplicacion(1);
		responseEntity = accesoLoginResource.iniciarSesion(credenciales);
		assertAll("resultado",
				() -> assertNotNull(responseEntity.getBody()),
				() -> assertEquals(HttpStatus.OK,responseEntity.getStatusCode())
		);
	}
	@Test
	void testIniciarSesionBackofficesFalla() {
		AutenticacionSolicitudDTO credenciales = new AutenticacionSolicitudDTO();
		credenciales.setClaveIngreso("123456");
		credenciales.setUsuarioIngreso("123456s");
		credenciales.setIdAplicacion(1);
		responseEntity = accesoLoginResource.iniciarSesion(credenciales);
		assertAll("resultado",
				() -> assertNotNull(responseEntity.getBody()),
				() -> assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode())
		);
	}
	@Test
	void testIniciarSesionVentasRolInactivo() {
		AutenticacionSolicitudDTO credenciales = new AutenticacionSolicitudDTO();
		credenciales.setClaveIngreso("ADATACENTER10");
		credenciales.setUsuarioIngreso("02122022");
		credenciales.setIdAplicacion(2);
		responseEntity = accesoLoginResource.iniciarSesion(credenciales);
		assertAll("resultado",
				() -> assertNotNull(responseEntity.getBody()),
				() -> assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode())
		);
	}
	@Test
	void testIniciarSesionVentasUsuarioInactivo() {
		AutenticacionSolicitudDTO credenciales = new AutenticacionSolicitudDTO();
		credenciales.setClaveIngreso("PPAPELERIA32");
		credenciales.setUsuarioIngreso("3227077412");
		credenciales.setIdAplicacion(2);
		responseEntity = accesoLoginResource.iniciarSesion(credenciales);
		assertAll("resultado",
				() -> assertNotNull(responseEntity.getBody()),
				() -> assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode())
		);
	}

}
