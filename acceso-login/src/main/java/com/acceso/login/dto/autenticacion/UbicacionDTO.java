package com.acceso.login.dto.autenticacion;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para encapsular los datos de la ubicacion del usuario
 */
@Getter
@Setter
@NoArgsConstructor
public class UbicacionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** identificador de la zona a la que pertenece el usuario */
    private Long idZona;

    /** identificador de la sub zona a la que pertenece el usuario */
    private Long idSubZona;

    /** identificador del punto de venta al que pertenece el usuario */
    private Long idPuntoVenta;

    /** Almacena la hora final de programación*/
    private String horaFinal;

    /** identificador de la caja a la que pertenece el usuario */
    private Long idCaja;

    /** Indica si el usuario tiene rol cajero*/
    private boolean cajero;
}
