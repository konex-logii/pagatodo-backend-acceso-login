package com.acceso.login.http;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.acceso.login.util.Util;

class HttpTest {

private RestTemplate mockTemplate = Mockito.mock(RestTemplate.class);
	
	Http construirMockHttp() {
		return new Http(mockTemplate);
	}
	
	@SuppressWarnings("unchecked")
	@BeforeEach
	void configurarPruebas() {

		Mockito
			.when(mockTemplate.exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
			.thenReturn(new ResponseEntity<String>("mi respuesta", HttpStatus.I_AM_A_TEAPOT));
	}

	@Test
	void testGet() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
		Http http = construirMockHttp();
		URI uri = Util.buildUri("testurl", "");
		ResponseEntity<String> response = http.get(uri, String.class, MediaType.APPLICATION_JSON);
		
		assertAll("respuesta",
			() -> assertNotNull(response),
			() -> assertNotNull(response.getBody()),
			() -> assertEquals("mi respuesta", response.getBody()),
			() -> assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode())
		);
	}
	@Test
	void testDelete() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
		Http http = construirMockHttp();
		URI uri = Util.buildUri("testurl", "");
		ResponseEntity<String> response = http.delete(uri, String.class, MediaType.APPLICATION_JSON);
		
		assertAll("respuesta",
				() -> assertNotNull(response),
				() -> assertNotNull(response.getBody()),
				() -> assertEquals("mi respuesta", response.getBody()),
				() -> assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode())
				);
	}
	@Test
	void testPost() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
		Http http = construirMockHttp();
		URI uri = Util.buildUri("testurl", "");
		ResponseEntity<String> response = http.post(uri,null, String.class, MediaType.APPLICATION_JSON);
		
		assertAll("respuesta",
				() -> assertNotNull(response),
				() -> assertNotNull(response.getBody()),
				() -> assertEquals("mi respuesta", response.getBody()),
				() -> assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode())
				);
	}
	@Test
	void testPut() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
		Http http = construirMockHttp();
		URI uri = Util.buildUri("testurl", "");
		ResponseEntity<String> response = http.put(uri,null, String.class, MediaType.APPLICATION_JSON);
		
		assertAll("respuesta",
				() -> assertNotNull(response),
				() -> assertNotNull(response.getBody()),
				() -> assertEquals("mi respuesta", response.getBody()),
				() -> assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode())
				);
	}

	@Test
	void testGetSinMediaType() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
		Http http = construirMockHttp();
		URI uri = Util.buildUri("testurl", "");
		// Llama el cliente http con el media type en null
		ResponseEntity<String> response = http.get(uri, String.class, null);
		
		assertAll("respuesta",
			() -> assertNotNull(response),
			() -> assertNotNull(response.getBody()),
			() -> assertEquals("mi respuesta", response.getBody()),
			() -> assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode())
		);
	}

	@Test
	void testGetSinSSL() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
		Http http = construirMockHttp();
		http.desactivarSSL();
		URI uri = Util.buildUri("testurl", "");
		ResponseEntity<String> response = http.get(uri, String.class, MediaType.APPLICATION_JSON);
		
		assertAll("respuesta",
			() -> assertNotNull(response),
			() -> assertNotNull(response.getBody()),
			() -> assertEquals("mi respuesta", (String) response.getBody()),
			() -> assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode())
		);
	}

	@Test
	void testComprobarEstadoSSL() {
		Http http = construirMockHttp();
		// Por defecto compruebo que esté activo
		assertTrue(http.getSSL());
		
		// Comprueba la activación y desactivación del SSL
		http.desactivarSSL();
		assertFalse(http.getSSL());
		http.activarSSL();
		assertTrue(http.getSSL());
	}

}