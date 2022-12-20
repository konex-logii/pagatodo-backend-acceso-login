package com.acceso.login.constants;

/**
 * DTO para encapsular los datos que se envia desde el cliente
 * (angular, app movil) al momento de la autenticacion del sistema
 */

public class UrlMicrosSolicitud {
    /** Es la clave de ingreso al sistema */
    public static final String accesoUsuarios = "http://logii-test-alb-696494596.us-east-1.elb.amazonaws.com:8200/acceso-usuarios/";
    public static final String accesoRoles = "http://logii-test-alb-696494596.us-east-1.elb.amazonaws.com:8199/accesoRoles/";
    public static final String comisiones = "http://logii-test-alb-696494596.us-east-1.elb.amazonaws.com:8197/comisiones/";
    public static final String administracionEmpresas = "http://logii-test-alb-696494596.us-east-1.elb.amazonaws.com:8198/administracionEmpresas/";
    public static final String administracionHorario = "http://logii-test-alb-696494596.us-east-1.elb.amazonaws.com:8195/administracionHorario/";
    public static final String administracionPapeleria = "http://logii-test-alb-696494596.us-east-1.elb.amazonaws.com:8190/administracionPapeleria/";
    public static final String adminOperacionComercial = "http://logii-test-alb-696494596.us-east-1.elb.amazonaws.com:8194/operacion-comercial/";
    public static final String menus = "http://logii-test-alb-696494596.us-east-1.elb.amazonaws.com:8390/menu/";
}
