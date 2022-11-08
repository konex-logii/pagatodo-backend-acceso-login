package com.acceso.login.jdbc;

import java.sql.*;

import lombok.experimental.UtilityClass;

/**
 * Clase que permite cerrar los recursos de JDBC
 */
@UtilityClass
public class CerrarRecursosJDBC {

	/**
	 * Metodo que permite cerrar el recurso PreparedStatement
	 */
	public static void closePreparedStatement(PreparedStatement pst) throws SQLException {
		if (pst != null) {
			pst.close();
		}
	}

	/**
	 * Metodo que permite cerrar el recurso ResultSet
	 */
	public static void closeResultSet(ResultSet res) throws SQLException {
		if (res != null) {
			res.close();
		}
	}

	/**
	 * Metodo que permite cerrar el recurso Statement
	 */
	public static void closeStatement(Statement stm) throws SQLException {
		if (stm != null) {
			stm.close();
		}
	}

	/**
	 * Metodo que permite cerrar el recurso Connection SQL
	 */
	public static void closeConnection(Connection connection) throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
}
