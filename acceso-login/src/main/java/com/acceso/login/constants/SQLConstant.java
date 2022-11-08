package com.acceso.login.constants;

import lombok.experimental.UtilityClass;

/**
 * Clase constante que contiene los DMLs Y DDLs para las consultas nativas
 */
@UtilityClass
public class SQLConstant {

	/** SQL para obtener los datos personales del usuario con base a sus credenciales*/
	public static final String GET_USER_AUTH =
		"SELECT U.ID_USUARIO, "
			  + "(P.PRIMER_NOMBRE || ' ' || P.SEGUNDO_NOMBRE || ' ' || P.PRIMER_APELLIDO || ' ' || P.SEGUNDO_APELLIDO) AS NOMBRE, "
			  + "U.PRIMER_INGRESO, "
			  + "U.CLAVE, "
			  + "LISTAGG(DISTINCT RO.ID_ROL, ',') AS ID_ROLES, "
			  + "U.PLAN_COMISION_ID, "
			  + "U.MONTO_PREMIO, "
			  + "U.NOMBRE_USUARIO, "
			  + "U.ID_ESTADO, "
			  + "UR.CODIGO, "
			  + "RO.ID_ESTADO AS ESTADO_ROL "
		+ "FROM PERSONAS P "
		+ "JOIN USUARIOS U ON(U.ID_USUARIO=P.ID_PERSONA) "
		+ "JOIN USUARIOS_ROLES_EMPRESAS UR ON(UR.ID_USUARIO=U.ID_USUARIO) "
		+ "JOIN ROLES RO ON(RO.ID_ROL=UR.ID_ROL) "
		+ "WHERE U.NOMBRE_USUARIO=? "
		+ "AND UR.ID_ESTADO='" + Estado.ACTIVO
		//+ "'AND RO.ID_ESTADO='" + Estado.ACTIVO
		+"' GROUP BY U.ID_USUARIO, "
		+ "P.PRIMER_NOMBRE || ' ' || P.SEGUNDO_NOMBRE || ' ' || P.PRIMER_APELLIDO || ' ' || P.SEGUNDO_APELLIDO, U.PRIMER_INGRESO,"
		+ " U.CLAVE, "
		+ "U.PLAN_COMISION_ID, " 
		+ "U.MONTO_PREMIO, " 
		+ "U.NOMBRE_USUARIO, " 
		+ "U.ID_ESTADO, " 
		+ "UR.CODIGO, " 
		+ "RO.ID_ESTADO "
		+ "ORDER BY 1,2,3,10,11";

	/** SQL para obtener los datos personales del usuario con base a sus credenciales*/
	public static final String GET_USER_AUTH_MOBILE =
		"SELECT U.ID_USUARIO,"
			  + "P.PRIMER_NOMBRE || ' ' || P.SEGUNDO_NOMBRE || ' ' || P.PRIMER_APELLIDO || ' ' || P.SEGUNDO_APELLIDO AS NOMBRE, "
			  + "U.PRIMER_INGRESO, U.CLAVE, U.NUMERO_TELEFONO, "
			  + "LISTAGG(DISTINCT RO.NOMBRE, ', ') AS ROLES, "
			  + "LISTAGG(DISTINCT RO.ID_ROL, ',') AS ID_ROLES "
		+ "FROM PERSONAS P "
		+ "JOIN USUARIOS U ON(U.ID_USUARIO=P.ID_PERSONA) "
		+ "LEFT JOIN USUARIOS_ROLES_EMPRESAS UR ON(UR.ID_USUARIO=U.ID_USUARIO) "
		+ "LEFT JOIN ROLES RO ON(RO.ID_ROL=UR.ID_ROL) "
		+ "WHERE U.numero_telefono=? "
		+ "AND U.ID_ESTADO='" + Estado.ACTIVO + "' "
		+ "GROUP BY U.ID_USUARIO, "
		+ "P.PRIMER_NOMBRE || ' ' || P.SEGUNDO_NOMBRE || ' ' || P.PRIMER_APELLIDO || ' ' || P.SEGUNDO_APELLIDO, "
		+ "U.PRIMER_INGRESO, U.CLAVE, U.NUMERO_TELEFONO "
		+ "ORDER BY 1,2";

	/** SQL para obtener los items del menu parametrizados en el sistema*/
	public static final String GET_ITEMS_MENU =
			"SELECT REP.ID_RECURSO AS ID_ITEM_PADRE,"
			  + "REP.NOMBRE AS NOMBRE_ITEM_PADRE,"
			  + "REP.DESCRIPCION AS DESC_ITEM_PADRE,"
			  + "RE.ID_RECURSO AS ID_ITEM,"
			  + "RE.NOMBRE AS NOMBRE_ITEM,"
			  + "RE.DESCRIPCION AS DESC_ITEM,"
			  + "RE.URL AS URL_ITEM,"
			  + "ACC.ID_ESTADO AS ESTA_ACCION, "
			  + "ACC.ID_ACCION AS ID_ACCION,"
			  + "ACC.NOMBRE AS NOMBRE_ACCION, "
			  + "RE.ICONO AS ICONO_HIJO, "
			  + "REP.ICONO AS ICONO_PADRE, "
			  + "RE.URL_MANUAL_PDF AS URL_MANUAL_PDF_HIJO, "
			  + "REP.URL_MANUAL_PDF AS URL_MANUAL_PDF_PADRE, "
			  + "RE.URL_MANUAL_VIDEO AS URL_MANUAL_VIDEO_HIJO, "
			  + "REP.URL_MANUAL_VIDEO AS URL_MANUAL_VIDEO_PADRE "
		+ "FROM ROLES_RECURSOS_ACCIONES RRA "
		+ "JOIN ROLES RO ON(RO.ID_ROL=RRA.ID_ROL)"
		+ "JOIN RECURSOS RE ON(RE.ID_RECURSO=RRA.ID_RECURSO)"
		+ "JOIN ACCIONES ACC ON(ACC.ID_ACCION=RRA.ID_ACCION)"
		+ "JOIN RECURSOS REP ON(REP.ID_RECURSO=RE.ID_RECURSO_PADRE)"
		+ "WHERE RO.ID_ROL IN(SELECT URE.ID_ROL FROM USUARIOS_ROLES_EMPRESAS URE WHERE URE.ID_USUARIO=? AND URE.ID_ESTADO='" + Estado.ACTIVO + "')"
		+ "AND RE.ID_APLICACION=? "
		+ "AND RO.ID_ESTADO='" + Estado.ACTIVO
		+ "'AND RRA.ID_ESTADO='" + Estado.ACTIVO
		+ "'AND RE.ID_ESTADO='"+ Estado.ACTIVO
		+ "'AND REP.ID_ESTADO='"+ Estado.ACTIVO
		+ "'ORDER BY RE.URL ASC NULLS FIRST, RE.NOMBRE ASC,REP.NOMBRE ASC";

	/** SQL para obtener los modulos del menu*/
	public static final String GET_MODULOS =
		"SELECT "
			+ "R.ID_RECURSO AS A,"
			+ "R.NOMBRE AS B,"
			+ "R.DESCRIPCION AS C,"
			+ "R.ICONO AS D,"
			+ "R.ID_RECURSO_PADRE AS E,"
			+ "(SELECT CASE WHEN COUNT(1) > 0 THEN 1 ELSE 0 END "
			+ "FROM RECURSOS RE "
			+ "WHERE RE.ID_RECURSO_PADRE = R.ID_RECURSO "
			+ "AND RE.ID_RECURSO IN(SELECT RRE.ID_RECURSO "
			+ "FROM ROLES_RECURSOS_ACCIONES RRE "
			+ "WHERE RRE.ID_ROL "
			+ "IN(SELECT URE.ID_ROL "
			+ "FROM USUARIOS_ROLES_EMPRESAS URE "
			+ "WHERE URE.ID_USUARIO=? "
			+ "AND URE.ID_ESTADO='" + Estado.ACTIVO + "')))AS F "
		+ "FROM RECURSOS R "
		+ "WHERE R.ID_ESTADO='" + Estado.ACTIVO + "'"
		+ "AND (R.ID_RECURSO_PADRE IS NULL OR R.URL IS NULL)"
		+ "AND R.ID_APLICACION=? "
		+ "ORDER BY R.NOMBRE";

	public static final String SELECT_MAIL_ID_PERSON = "select P.correo_electronico from personas p where p.id_persona=:id";

	public static final String GET_PROGRAMACION_VENDEDOR = 
			"SELECT HVD.ZONA, HVD.SUB_ZONA, HVD.PUNTO_VENTA, HVD.CAJA, HORA_FINAL, "
			+ "(SELECT CASE WHEN COUNT(1) > 0 THEN 1 ELSE 0 END "
			+ "FROM PERSONAS P JOIN USUARIOS U ON(U.ID_USUARIO=P.ID_PERSONA) "
			+ "JOIN USUARIOS_ROLES_EMPRESAS UR ON(UR.ID_USUARIO=U.ID_USUARIO) "
			+ "JOIN ROLES RO ON(RO.ID_ROL=UR.ID_ROL) "
			+ "WHERE U.ID_USUARIO =:idUsuario "
			+ "AND RO.ID_CAJERO = 1) AS CAJERO "
			+ "FROM HORARIOS_VENDEDORES HV "
			+ "JOIN HORARIOS_VENDEDORES_DETALLES HVD ON (HV.ID_HORARIO_VENDEDOR = HVD.ID_HORARIO_VENDEDOR) "
			+ "JOIN HORARIOS HO ON(HO.CODIGO_HORA=HVD.HORA) "
			+ "WHERE HV.ID_VENDEDOR =:idUsuario "
			+ "AND TO_CHAR(HVD.DIA,'YYYY-MM-DD') = TO_CHAR(CURRENT_DATE,'YYYY-MM-DD') "
			+ "AND HO.HORA_INICIAL <= TO_CHAR(SYSTIMESTAMP, 'HH24:MI:SS') "
			+ "AND TO_CHAR(SYSTIMESTAMP, 'HH24:MI:SS') <= HO.HORA_FINAL "
			+ "AND HV.ID_ESTADO='" + Estado.ACTIVO
			+ "' AND HVD.ID_ESTADO='" + Estado.ACTIVO
			+ "' AND HO.ID_ESTADO='"+ Estado.ACTIVO +"'";

	public static final String GET_ROL_PROGRAMACION = "SELECT CASE WHEN COUNT(1) > 0 THEN 1 ELSE 0 END" 
			+ " FROM PERSONAS P" 
			+ " JOIN USUARIOS U ON(U.ID_USUARIO=P.ID_PERSONA)"
			+ " JOIN USUARIOS_ROLES_EMPRESAS UR ON(UR.ID_USUARIO=U.ID_USUARIO)"
			+ " JOIN ROLES RO ON(RO.ID_ROL=UR.ID_ROL)"
			+ " WHERE U.ID_USUARIO =:idUsuario"
			+ " AND RO.ID_PROGRAMACION = '"+ Estado.ACTIVO +"'";

	/**Permite obtner los rollos asignados a un vendedor*/
	public static final String OBTENER_ROLLOS_VENDEDOR = "SELECT  PA.ID_PAPELERIA_CAJA AS ID_CAJA, "
			+ " PD.ID_PAPELERIA_ROLLO AS ID_ROLLO, "
			+ " PA.SERIE AS SERIE, PD.ROLLO_INICIAL AS INICIAL, "
			+ " PD.ROLLO_FINAL AS FINAL, TP.ID_TIPO_PAPELERIA AS TIPO_PAPELERIA, "
			+ " TP.NOMBRE AS NOMBRE, PD.COLILLA_ACTUAL AS COLILLA "
			+ " FROM PAPELERIAS_CAJAS PA "
			+ " JOIN PAPELERIAS_ROLLOS PD ON(PD.ID_PAPELERIA_CAJA=PA.ID_PAPELERIA_CAJA) "
			+ " JOIN TIPOS_PAPELERIAS TP ON(TP.ID_TIPO_PAPELERIA=PA.ID_TIPO_PAPELERIA) "
			+ " WHERE PD.ID_VENDEDOR =:idVendedor "
			+ " AND TP.ID_APLICACION =:idAplicacion "
			+ " AND PD.ID_ESTADO_VENDEDOR='"+Estado.ASIGNADO+"'  "
			+ " AND (PD.COLILLA_ACTUAL <= PD.ROLLO_FINAL OR PD.COLILLA_ACTUAL IS NULL) "
			+ " ORDER BY PD.ID_PAPELERIA_ROLLO";

	/**Permite obtner las empresas asociadas al usuario autenticado*/
	public static final String GET_EMPRESAS_USUARIOS =
		"SELECT DISTINCT "
			+ "EM.ID_EMPRESA AS A,"
			+ "EM.NIT_EMPRESA || ' - ' || EM.RAZON_SOCIAL AS B,"
			+ "(SELECT LISTAGG(DISTINCT UPPER(RO.NOMBRE),', ')FROM USUARIOS_ROLES_EMPRESAS RY JOIN ROLES RO ON(RO.ID_ROL=RY.ID_ROL)WHERE RY.ID_USUARIO=UR.ID_USUARIO AND RY.ID_EMPRESA=UR.ID_EMPRESA AND RY.ID_ESTADO='" + Estado.ACTIVO + "' AND RO.ID_ESTADO='" + Estado.ACTIVO + "')AS C,"
			+ "UR.EMPRESA_VENTA AS D "
		+ "FROM USUARIOS_ROLES_EMPRESAS UR "
		+ "JOIN EMPRESAS EM ON(EM.ID_EMPRESA=UR.ID_EMPRESA)"
		+ "WHERE UR.ID_USUARIO=? "
		+ "AND UR.ID_ESTADO='" + Estado.ACTIVO +"'"
		+ "AND EM.ID_ESTADO='" + Estado.ACTIVO + "'";

	/** Se Utiliza para consultar el identificador de la apuesta con las series */
	public static final String GET_SERIE =
			"SELECT A.ID_APUESTA FROM APUESTAS A "+
			"JOIN APUESTAS_SORTEOS AD  ON A.ID_APUESTA = AD.ID_APUESTA " +
			"JOIN SORTEOS_DETALLES SD  ON SD.ID_SORTEO_DETALLE = AD.ID_SORTEO_DETALLE "+
			"JOIN SORTEOS S ON SD.ID_SORTEO  = S.ID_SORTEO "+
			"WHERE S.ID_EMPRESA =? AND A.SERIE_UNO=? AND A.SERIE_DOS=? ";

	/** Se utiliza para validar si el login existe */
	public static final String EXISTS_LOGIN_USER = 
			"SELECT CASE WHEN COUNT(1) > 0 "
		  + "THEN 1 ELSE 0 END AS exist "
		  + "FROM USUARIOS "
		  + "WHERE NOMBRE_USUARIO=?";

	/** Se utiliza para obtener el estado del usuario */
	public static final String GET_ESTADO_USER = 
			"SELECT ID_ESTADO FROM USUARIOS WHERE ID_USUARIO=?";

	/* Se utiliza para insertar el codigo automatico que se genera para mandar por sms **/
	public static final String INSERT_SMS_CODE =
			"INSERT INTO SMS_CODIGO "+
					"(CODIGO,IDENTIFICADOR,VALIDACION,FECHA_ACCION,ID_TIPO_CODIGO_VALIDACION) "+
					"VALUES (?,?,?,?,?)";

	/** Se utiliza para verificar si el codigo existe*/
	public static final String VERIFICA_SMS_CODE =
			"SELECT COUNT(CODIGO) AS CODIGO FROM SMS_CODIGO "+
					"WHERE CODIGO =? "+
					"AND EXPIRACION != ?";

	/** Se utiliza Para actualizar el estado del codigo con el celular o correo*/
	public static final String UPDATE_SMS_CODE =
			"UPDATE SMS_CODIGO SET "+
					"VALIDACION = ? ,"+
					"EXPIRACION = ? "+
					"WHERE CODIGO = ?";

	/** Se utiliza para verificar si existen valores con el telefono o correo*/
	public static final String EXPIRACION_SMS_CODE =
			"SELECT COUNT(IDENTIFICADOR) AS IDENTIFICADOR FROM SMS_CODIGO "+
					"WHERE IDENTIFICADOR =? "+
					"AND VALIDACION != ?";

	/** Se utiliza Para actualizar el estado del codigo con el celular*/
	public static final String UPDATE_EXPIRE_SMS_CODE =
			"UPDATE SMS_CODIGO SET "+
					"EXPIRACION = ? "+
					"WHERE identificador = ?";
	
	/** Se utiliza para obtener el estado de zona, sub zona y punto de venta*/
	public static final String GET_ACTIVO_ZONA_ZUBZONA_PUNTO ="SELECT distinct TO_CHAR(PV.ID_ESTADO) AS A, TO_CHAR(PV.NOMBRE_PUNTO_VENTA) AS B, "
			+ "TO_CHAR(EZ.ID_ESTADO) AS C, TO_CHAR(EZ.NOMBRE_ZONA) AS D, TO_CHAR(SZ.ID_ESTADO) AS E, TO_CHAR(SZ.NOMBRE_SUB_ZONA) AS F  "
			+ "FROM HORARIOS_VENDEDORES HV "
			+ "JOIN HORARIOS_VENDEDORES_DETALLES HVD ON(HVD.ID_HORARIO_VENDEDOR=HV.ID_HORARIO_VENDEDOR)"
			+ "JOIN HORARIOS H ON( HV.id_empresa = H.id_empresa and  HV.id_horario_vendedor= hvd.id_horario_vendedor)"
			+ "JOIN PUNTOS_VENTAS PV ON HVD.punto_venta = pv.id_punto_venta "
			+ "JOIN EMPRESAS_ZONAS EZ ON PV.ID_ZONA = EZ.ID_ZONA "
			+ "LEFT JOIN  SUB_ZONAS SZ ON PV.ID_SUB_ZONA = SZ.ID_SUB_ZONA "
			+ "WHERE  HV.id_vendedor=? AND TO_CHAR(HVD.DIA,'YYYY-MM-DD') = TO_CHAR(CURRENT_DATE,'YYYY-MM-DD') "
			+ "AND H.HORA_INICIAL <= TO_CHAR(SYSTIMESTAMP, 'HH24:MI:SS') AND TO_CHAR(SYSTIMESTAMP, 'HH24:MI:SS') <= H.HORA_FINAL "
			+ "AND HV.ID_ESTADO='" + Estado.ACTIVO +"' "
			+ "AND HVD.ID_ESTADO='" + Estado.ACTIVO+"' "
			+ "AND H.ID_ESTADO='"+ Estado.ACTIVO +"' "
			+ "AND EZ.ID_ZONA=? "
			+ "AND pv.id_punto_venta=? ";
	
	/** obtener id del municipio segun la descripcion*/
	public static final String GET_MUNICIPIO_ID = 
			"SELECT ID_CIUDAD, NOMBRE FROM CIUDADES"
					+" WHERE NOMBRE LIKE ?";

	/** obtener id de la empresa de acuerdo la ciudad*/
	public static final String GET_EMPRESA_ID =
			"SELECT ID_EMPRESA FROM EMPRESAS"
					+" WHERE CIUDAD = ?";

}
