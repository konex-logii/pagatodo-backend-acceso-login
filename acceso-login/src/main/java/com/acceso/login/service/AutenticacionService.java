package com.acceso.login.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.acceso.login.constants.MessagesBussinesKey;
import com.acceso.login.dto.AutenticacionRequestDTO;
import com.acceso.login.dto.AutenticacionResponseDTO;
import com.acceso.login.util.BusinessException;
import com.acceso.login.util.Util;

/**
 * Service para que contiene los procesos de negocio para la autenticacion
 */
@Service
@SuppressWarnings("unchecked")
public class AutenticacionService {

	/** Contexto de la persistencia del sistema */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Servicio que soporta el proceso de negocio para la autenticacion en el
	 * sistema
	 *
	 * @param credenciales DTO que contiene los datos de las credenciales
	 * @return DTO con los datos del response para la autenticacion en el sistema
	 */
	public AutenticacionResponseDTO iniciarSesion(AutenticacionRequestDTO credenciales) throws BusinessException {
		if (credenciales != null && !Util.isNull(credenciales.getClaveIngreso())
				&& !Util.isNull(credenciales.getUsuarioIngreso())) {

			// se hace el llamado a validarExisteUSuario() ValidarRol()
			if (true) {
				// se hace el llamado a validarZona() y ValidarPDV()
				// se hace el llamado a validarHorario()
				// se hace el llamado a validarPlanComision()
				// se hace el llamado a validarPapeleria()
				// se hace el llamado a validarTipoUsuario()
				// se hace el llamado a validarEmpresa usuario()
			}

		}
		throw new BusinessException(MessagesBussinesKey.KEY_AUTENTICACION_FALLIDA.value);
	}

}
