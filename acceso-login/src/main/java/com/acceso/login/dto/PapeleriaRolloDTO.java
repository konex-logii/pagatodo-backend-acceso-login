package com.acceso.login.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * DTO que contiene los atributos de la papeleria rollos
 */
@Data
public class PapeleriaRolloDTO implements Serializable {
	private static final long serialVersionUID = 1L;



	/** Identificador de la papeleria por CAJA */
	private Long idCaja;

	/** Identificador de la papeleria por ROLLO */
	private Long idRollo;

	/** Es la serie de la papeleria caja */
	private String serie;

	/** Numero inicial de la serie **/
	private Long nroInicialSerie;

	/** Numero final de la serie **/
	private Long nroFinalSerie;

	/** Identificador del tipo de papeleria */
	private Integer idTipoPapeleria;

	/** Nombre del tipo de papeleria */
	private String nombreTipoPapeleria;
	
	/** Rango inicial de la serie */
	private String rangoInicial;

	/** Rango final de la serie */
	private String rangoFinal;

	
}
