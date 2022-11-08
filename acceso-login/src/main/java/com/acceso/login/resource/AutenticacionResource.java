package com.acceso.login.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acceso.login.dto.AutenticacionRequestDTO;
import com.acceso.login.service.AutenticacionService;
import com.acceso.login.util.BusinessException;
import com.acceso.login.util.Util;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Servicio que contiene todos los procesos de negocio para la autenticacion
 * localhost:puerto/auth/
 */
@RestController
@RequestMapping("/seguridad")
public class AutenticacionResource {

	/** Service para que contiene los procesos de negocio para la autenticacion */
	@Autowired
	private AutenticacionService autenticacionService;

	/**
	 * Servicio que soporta el proceso de negocio para la autenticacion en el sistema
	 *
	 * @param credenciales DTO que contiene los datos de las credenciales
	 * @return DTO con los datos del response para la autenticacion en el sistema
	 */
	@PostMapping(path = "/login",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Iniciar Sesion", notes = "Operación para iniciar sesión en el sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error")})
	public ResponseEntity<Object> iniciarSesion(@RequestBody AutenticacionRequestDTO credenciales) {
		try {
			return Util.getResponseSuccessful(this.autenticacionService.iniciarSesion(credenciales));
		} catch (BusinessException e) {
		
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e	) {
			
			return Util.getResponseError(AutenticacionResource.class.getSimpleName() + ".iniciarSesion ", e.getMessage());
		}
	}

	
}
