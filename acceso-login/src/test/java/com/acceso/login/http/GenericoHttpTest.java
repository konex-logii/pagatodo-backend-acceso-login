package com.acceso.login.http;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.acceso.login.constants.UrlMicrosSolicitud;
import com.acceso.login.util.ExcepcionComercial;
import com.acceso.login.util.Util;

@SpringBootTest
class GenericoHttpTest {


	private Http mockHttp = Mockito.mock(Http.class);
	private GenericoHttp http = new GenericoHttp(mockHttp);
	
	@Autowired
	private GenericoHttp httpOk;
	
	@SuppressWarnings("unchecked")
	@BeforeEach
	void configurarPruebas() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, ExcepcionComercial {
		
		ResponseEntity<String> respuestaFalsa = new ResponseEntity<String>("My entity", HttpStatus.I_AM_A_TEAPOT);
		
		Mockito
			.when(mockHttp.get(any(URI.class),(Class<String>)any(Class.class), any(MediaType.class)))
			.thenReturn(respuestaFalsa);
	}
	

	@Test
	void testRespuestaHttpMultivalor() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, ExcepcionComercial {
		MultiValueMap<String, String> multivalores = new LinkedMultiValueMap<>();
		multivalores.add("llave-a", "millave1");
		multivalores.add("llave-b", "millave2");
		
		ResponseEntity<Object> response = http.getService("randomurl", multivalores,Object.class);
		
		// Valida el objeto mock
		assertAll("respuesta",
			() -> assertNotNull(response),
			() -> assertNotNull(response.getBody()),
			() -> assertEquals("My entity", (String)response.getBody()),
			() -> assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode())
		);
	}

	@Test
	void testRespuestaHttpArregloString() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, ExcepcionComercial {
		
		String[] params = new String[] {
			"param1",
			"param2",
			"param3"
		};
		ResponseEntity<Object> response = http.getService("randomurl", params,Object.class);
		
		// Valida el objeto mock
		assertAll("respuesta",
			() -> assertNotNull(response),
			() -> assertNotNull(response.getBody()),
			() -> assertEquals("My entity", (String)response.getBody()),
			() -> assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode())
		);
	}

}