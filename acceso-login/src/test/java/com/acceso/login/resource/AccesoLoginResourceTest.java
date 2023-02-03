package com.acceso.login.resource;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

	@ParameterizedTest
	@CsvSource(
			{
				"ADATACENTER10,02122022,2,400",
				"PPAPELERIA3,3227077412,2,400",
				"DDIAZ10,1094970590,2,200",
				"DDIAZ1s0,1094970590,2,400",
				"ADATACENTER10,10101012,1,200",
				"123456,123456s,1,400"
			}
	)
	void testIniciosSesion(String clave, String usuario, Integer idAplicacion, Integer estadoEsperado) {
		AutenticacionSolicitudDTO credenciales = new AutenticacionSolicitudDTO();
		credenciales.setClaveIngreso(clave);
		credenciales.setUsuarioIngreso(usuario);
		credenciales.setIdAplicacion(idAplicacion);
		responseEntity = accesoLoginResource.iniciarSesion(credenciales);
		assertAll("resultado",
				() -> assertNotNull(responseEntity.getBody()),
				() -> assertEquals(HttpStatus.valueOf(estadoEsperado),responseEntity.getStatusCode())
		);
	}
	@Test
	void testInicioSesionExcepcion() {
		AutenticacionSolicitudDTO credenciales = new AutenticacionSolicitudDTO();
		responseEntity = accesoLoginResource.iniciarSesion(credenciales);
		assertAll("resultado",
				() -> assertNotNull(responseEntity.getBody()),
				() -> assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode())
		);
	}

}
