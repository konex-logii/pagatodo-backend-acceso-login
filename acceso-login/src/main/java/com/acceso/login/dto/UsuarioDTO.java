package com.acceso.login.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para encapsular los datos personales del usuario autenticado
 */
@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** identificador del Usuario */
	private Long idUsuario;

	/** usuario*/
	private String usuario;

	/** Es el nombre completo del usuario */
	private String nombreCompleto;

	/** Son los nombres de los roles asignados al usuario */
	private String roles;

	/** Son los identificadores de cada rol que tiene el usuario */
	private String idRoles;

	/** contraseña del usuario */
	private String clave;

	/** Identifica si es el primer ingreso en el sistema */
	private Long primerIngreso;

	/** Nro de telefeno del usaurio autenticado */
	private String numeroTelefono;

	/** correo del usaurio autenticado */
	private String correo;

	/** Indica si el usuario autenticado tiene rol de administrador */
	private boolean administrador;

	/** identificador de la zona a la que pertenece el usuario */
	private Long idZona;

	/** identificador de la sub zona a la que pertenece el usuario */
	private Long idSubZona;

	/** identificador del punto de venta al que pertenece el usuario */
	private Long idPuntoVenta;

	/** Almacena la hora final de programación*/
	private String horaFinal;

	/** Almacena el identificador de plan de comision*/
	private Long idPlanComision;

	/** Almacena el monto del usuario atenticado */
	private BigDecimal montoPremio;

	/** Almacena el id de la caja*/
	private Long idCaja;

	/** Almacena la variable que indica si es cajero el usuario */
	private Boolean isCajero;

	/** Lista de papeleria asociada al usuario*/
	private List<PapeleriaRolloDTO> listaPapeleria;

	/** Identifica el codigo del vendedor */
	private String codigo;

	/** Identifica si el rol tiene programacion o no */
	private boolean rolConProgramacion;
}
