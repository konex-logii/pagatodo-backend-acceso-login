package com.acceso.login.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acceso.login.dto.AutenticacionSolicitudDTO;
import com.acceso.login.service.AccesoLoginService;
import com.acceso.login.util.ExcepcionComercial;
import com.acceso.login.util.Util;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import static org.reflections.Reflections.log;
/**
 * Servicio que contiene todos los procesos de negocio para la autenticacion
 * localhost:puerto/auth/
 */
@RestController
@RequestMapping("/acceso-login")
public class AccesoLoginResource {

	/** Service para que contiene los procesos de negocio para la autenticacion */
	@Autowired
	private AccesoLoginService accesoLoginService;
	


	/**
	 * Servicio que soporta el proceso de negocio para la autenticacion en el sistema
	 *
	 * @param credenciales DTO que contiene los datos de las credenciales
	 * @return DTO con los datos de la respuesta para la autenticacion en el sistema
	 */
	@PostMapping(path = "/login")
	@ApiOperation(value = "Iniciar Sesion", notes = "Operación para iniciar sesión en el sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error")})
	public ResponseEntity<Object> iniciarSesion(@RequestBody AutenticacionSolicitudDTO credenciales) {
		String logMensaje = "iniciarSesion:  " + credenciales.toString();
		try {
			logMensaje = "INFORMACION " + logMensaje;
			log.info(logMensaje);
			return Util.getResponseSuccessful(this.accesoLoginService.iniciarSesion(credenciales));
		} catch (ExcepcionComercial e) {
			logMensaje = "ERROR COMERCIAL " + logMensaje + " MENSAJE: " +e.getMessage();
			log.info(logMensaje);
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e	) {
			logMensaje = "ERROR  " + logMensaje + " MENSAJE: " +e.getMessage();
            log.info(logMensaje);
			return Util.getResponseError(AccesoLoginResource.class.getSimpleName() + ".iniciarSesion ", e.getMessage());
		}
	}

	
}
