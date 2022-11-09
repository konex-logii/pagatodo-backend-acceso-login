package com.acceso.login.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.acceso.login.constants.MessagesBussinesKey;
import com.acceso.login.dto.AutenticacionRequestDTO;
import com.acceso.login.dto.AutenticacionResponseDTO;
import com.acceso.login.util.BusinessException;
import com.acceso.login.util.Util;
import com.acceso.login.util.http.GenericoHttp;

/**
 * Service para que contiene los procesos de negocio para la autenticacion
 */
@Service
@SuppressWarnings("unchecked")
public class AutenticacionService {

	@Autowired
	private GenericoHttp generic;

	/**
	 * Servicio que soporta el proceso de negocio para la autenticacion en el
	 * sistema
	 *
	 * @param credenciales DTO que contiene los datos de las credenciales
	 * @return DTO con los datos del response para la autenticacion en el sistema
	 * @throws Exception
	 */
	public AutenticacionResponseDTO iniciarSesion(AutenticacionRequestDTO credenciales) throws Exception {

		AutenticacionResponseDTO aut = new AutenticacionResponseDTO();
		if (credenciales != null && !Util.isNull(credenciales.getClaveIngreso())
				&& !Util.isNull(credenciales.getUsuarioIngreso())) {

			boolean obj = validarExisteUsuario(credenciales);

			// se hace el llamado a validarExisteUSuario() ValidarRol()
			if (obj) {
				aut.setUsuario(null);
				// se hace el llamado a validarZona() y ValidarPDV()
				// se hace el llamado a validarHorario()
				// se hace el llamado a validarPlanComision()
				// se hace el llamado a validarPapeleria()
				// se hace el llamado a validarTipoUsuario()
				// se hace el llamado a validarEmpresa usuario()
			}
			return aut;
		}
		throw new BusinessException(MessagesBussinesKey.KEY_AUTENTICACION_FALLIDA.value);
	}

	private boolean validarExisteUsuario(AutenticacionRequestDTO credenciales) throws Exception {

		ResponseEntity<?> response;

		boolean body;

		URI uri;

		String url = "http://localhost:8083/acceso-usuarios/validarExisteUsuario?claveIngreso={0}&usuarioIngreso={1}&idAplicacion={2}";
		List<String> params = new ArrayList<String>();
		params.add(credenciales.getClaveIngreso());
		params.add(credenciales.getUsuarioIngreso());
		params.add(credenciales.getIdAplicacion().toString());
		String[] array = params.toArray(new String[2]);

		uri = Util.buildUri(url, array);

		response = this.generic.getService(uri, Object.class);

		body = (boolean) response.getBody();

		if (response.getStatusCode() == HttpStatus.OK && !ObjectUtils.isEmpty(body)) {
			return body;

		}
		return false;

	}
}
