package com.acceso.login.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para encapsular las acciones que tiene cada item del menu
 */
@Getter
@Setter
@NoArgsConstructor
public class MenuItemAccionDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** nombre del accion del item */
	private String nombre;

	/** identificador del accion del item */
	private Long idAccion;
}

