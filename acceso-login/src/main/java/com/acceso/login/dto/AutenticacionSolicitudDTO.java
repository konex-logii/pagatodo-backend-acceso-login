package com.acceso.login.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO para encapsular los datos que se envia desde el cliente
 * (angular, app movil) al momento de la autenticacion del sistema
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AutenticacionSolicitudDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Es la clave de ingreso al sistema */
	private String claveIngreso;

	/** Es el usuario de ingreso al sistema */
	private String usuarioIngreso;

	/** Indica a que aplicacion se va autenticar */
	private Integer idAplicacion;
}
