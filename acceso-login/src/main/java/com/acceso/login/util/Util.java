package com.acceso.login.util;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.acceso.login.constants.Numero;
import com.acceso.login.dto.MensajeRespuestaDTO;

import lombok.experimental.UtilityClass;

/**
 * Clase que contiene los metodo utilitarios del sistema
 *
 */
@UtilityClass
public class Util {

	/**
	 * Metodo que permite validar si un valor es null o vacio
	 */
	public static boolean isNull(String valor) {
		return valor == null || valor.trim().length() == Numero.ZERO.valueI.intValue();
	}


	/**
	 * Metodo que permite construir el response de respuesta exitoso
	 */
	public static ResponseEntity<Object> getResponseSuccessful(Object body) {
		return ResponseEntity.status(HttpStatus.OK).body(body);
	}

	/**
	 * Metodo que permite construir el response de respuesta OK
	 */
	public static ResponseEntity<Object> getResponseOk() {
		return ResponseEntity.status(HttpStatus.OK).body(new MensajeRespuestaDTO(HttpStatus.OK.getReasonPhrase()));
	}

	/**
	 * Metodo que permite construir el response de respuesta BAD REQUEST
	 */
	public static ResponseEntity<Object> getResponseBadRequest(String bussinesMessage) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensajeRespuestaDTO(bussinesMessage));
	}

	/**
	 * Metodo que permite construir el response de respuesta INTERNAL_SERVER_ERROR 
	 * 
	 * @param metodo, metodo donde se origino el error
	 * @param error, mensaje de la exception lanzada
	 */
	public static ResponseEntity<Object> getResponseError(String metodo, String error) {
		if (error == null || error.trim().length() == Numero.ZERO.valueI.intValue()) {
			error = "Exception lanzada por NullPointerException.";
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MensajeRespuestaDTO(metodo + error));
	}

	
	 public static URI buildUri( String url, String... params ) {

	        URI uri;

	        try {

				Integer count = 0;

	            for( String param : params ) {
	                url = url.replace( "{" + ( count++ ) + "}", param );
	            }

	            uri = new URI( url );
	        }
	        catch( URISyntaxException ex ) {
	            uri = null;
	        }

	        return uri;
	    }
}
