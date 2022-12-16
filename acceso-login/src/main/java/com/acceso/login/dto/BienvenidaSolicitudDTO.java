package com.acceso.login.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO que contiene los datos del REQUEST de bienvenida
 */
@Getter
@Setter
@NoArgsConstructor
public class BienvenidaSolicitudDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** identificador del Usuario autenticado en el sistema */
	private Long idUsuario;
	
	/** Es el identificador de la app logueada */
	private Integer idAplicacion;
}
