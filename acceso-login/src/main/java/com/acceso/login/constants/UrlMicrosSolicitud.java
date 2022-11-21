package com.acceso.login.constants;

import lombok.Data;

/**
 * DTO para encapsular los datos que se envia desde el cliente
 * (angular, app movil) al momento de la autenticacion del sistema
 */
@Data
public class UrlMicrosSolicitud {
    /** Es la clave de ingreso al sistema */
    public static final String accesoUsuarios = "http://localhost:8083/acceso-usuarios/";
    public static final String accesoRoles = "http://localhost:8087/accesoRoles/";
    public static final String comisiones = "http://localhost:8085/comisiones/";
    public static final String administracionEmpresas = "http://localhost:8086/administracionEmpresas/";
    public static final String administracionHorario = "http://localhost:8084/administracionHorario/";
    public static final String administracionPapeleria = "http://localhost:8190/administracionPapeleria/";
    public static final String adminOperacionComercial = "http://localhost:8082/operacion-comercial/";
}
