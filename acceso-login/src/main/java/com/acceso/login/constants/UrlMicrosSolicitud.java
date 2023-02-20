package com.acceso.login.constants;

/**
 * DTO para encapsular los datos que se envia desde el cliente
 * (angular, app movil) al momento de la autenticacion del sistema
 */

public final class UrlMicrosSolicitud {
	private UrlMicrosSolicitud() {}
    /** Es la clave de ingreso al sistema */
    public static final String ACCESO_USUARIOS = "http://logii-test-alb-696494596.us-east-1.elb.amazonaws.com:8200/acceso-usuarios/";
    public static final String ACCESO_ROLES = "http://logii-test-alb-696494596.us-east-1.elb.amazonaws.com:8199/accesoRoles/";
    public static final String COMISIONES = "http://logii-test-alb-696494596.us-east-1.elb.amazonaws.com:8197/comisiones/";
    public static final String ADMINISTRACION_EMPRESAS = "http://logii-test-alb-696494596.us-east-1.elb.amazonaws.com:8198/administracionEmpresas/";
    public static final String ADMINISTRACION_HORARIO = "http://logii-test-alb-696494596.us-east-1.elb.amazonaws.com:8195/administracionHorario/";
    public static final String ADMINISTRACION_PAPELERIA = "http://logii-test-alb-696494596.us-east-1.elb.amazonaws.com:8190/administracionPapeleria/";
    public static final String ADMINISTRACION_OPERACION_COMERCIAL = "http://logii-test-alb-696494596.us-east-1.elb.amazonaws.com:8194/operacion-comercial/";
    public static final String MENUS = "http://logii-test-alb-696494596.us-east-1.elb.amazonaws.com:8390/menu/";
}
