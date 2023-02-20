package com.acceso.login.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.net.URI;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.acceso.login.dto.MensajeRespuestaDTO;

class UtilTest {

	@Test
	void testGetResponseSuccessful() {
		Object cuerpo = "Contenido";
		ResponseEntity<Object> response = Util.getResponseSuccessful(cuerpo);
		assertAll("resultado",
				() -> assertNotNull(response),
				() -> assertEquals(cuerpo, response.getBody()),
				() -> assertEquals(HttpStatus.OK, response.getStatusCode())
			);
	}

	@Test
	void testGetResponseError() {
		String metodo = "POST";
		String error = "No se pudo conectar";
		ResponseEntity<Object> response = Util.getResponseError(metodo, error);
		assertAll("resultado",
				() -> assertNotNull(response),
				() -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode())
			);
	}

	@Test
	void testGetResponseErrorConErrorNull() {
		String metodo = "POST";
		ResponseEntity<Object> response = Util.getResponseError(metodo, null);
		MensajeRespuestaDTO mensajeDTO = (MensajeRespuestaDTO) response.getBody();

		// Valida que el mensaje de respuesta contenga la palabra "exception"
		@SuppressWarnings("null")
		boolean contienePalabraException = Pattern
				.compile(".*exception.*", Pattern.CASE_INSENSITIVE)
				.matcher(mensajeDTO.getMensaje())
				.find();

		assertAll("resultado",
				() -> assertNotNull(response),
				() -> assertNotNull(mensajeDTO),
				() -> assertEquals(true, contienePalabraException),
				() -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode())
			);
	}

	@Test
	void testGetResponseErrorConErrorZeroLenght() {
		String metodo = "POST";
		ResponseEntity<Object> response = Util.getResponseError(metodo, "");
		MensajeRespuestaDTO mensajeDTO = (MensajeRespuestaDTO) response.getBody();

		// Valida que el mensaje de respuesta contenga la palabra "exception"
		@SuppressWarnings("null")
		boolean contienePalabraException = Pattern
				.compile(".*exception.*", Pattern.CASE_INSENSITIVE)
				.matcher(mensajeDTO.getMensaje())
				.find();

		assertAll("resultado",
				() -> assertNotNull(response),
				() -> assertNotNull(mensajeDTO),
				() -> assertEquals(true, contienePalabraException),
				() -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode())
			);
	}
	
	@Test
	void testBuildBadUri() {
		String url = "\n\t\r\\\"";
		assertNull(Util.buildUri(url, new String[0]));
	}
	
	@Test
	void testBuildUriConParametros() {
		String url = "http://www.miurl.com/q?search={0}&user={1}";
		String[] parametros = {
			"producto",
			"admin"
		};
		URI uri = Util.buildUri(url, parametros);
		assertAll("utilidad de uri",
			() -> assertNotNull(uri),
			() -> assertNotEquals(url, uri.toString()),
			() -> assertThat(uri.toString())
				.matches(".*search.*producto.*"),
			() -> assertThat(uri.toString())
				.matches(".*user.*admin.*")
		);
	}

}
