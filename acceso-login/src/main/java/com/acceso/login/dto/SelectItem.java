package com.acceso.login.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * DTO que representa un ITEM para la lista desplegable
 */
@Data
public class SelectItem implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del Item */
	private transient Object value;

	/** Se utiliza para obtener otro value */
	private transient Object otherValue;

	/** label del item */
	private String label;

	/** Se utiliza para obtener otro label */
	private String otherLabel;
}
