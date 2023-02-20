package com.acceso.login.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.acceso.login.constants.*;
import com.acceso.login.dto.*;
import com.acceso.login.dto.autenticacion.UbicacionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.acceso.login.dto.SelectItem;
import com.acceso.login.util.ExcepcionComercial;

import com.acceso.login.util.Util;
import com.acceso.login.http.GenericoHttp;

import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Service para que contiene los procesos de negocio para la autenticacion
 */
@Service
@SuppressWarnings("unchecked")
public class AccesoLoginService {

	@Autowired
	private GenericoHttp generic;

	/**
	 * Servicio que soporta el proceso de negocio para la autenticacion en el
	 * sistema
	 *
	 * @param credenciales DTO que contiene los datos de las credenciales
	 * @return DTO con los datos del response para la autenticacion en el sistema
	 * @throws URISyntaxException 
	 * @throws Exception
	 */
	public AutenticacionRespuestaDTO iniciarSesion(AutenticacionSolicitudDTO credenciales) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, ExcepcionComercial, URISyntaxException  {
		if (credenciales != null && !Util.isNull(credenciales.getClaveIngreso()) && !Util.isNull(credenciales.getUsuarioIngreso())) {
			Object obj = validarExisteUsuario(credenciales);

			// se hace el llamado a validarExisteUSuario() ValidarRol()
			if (obj != null) {
				String codificado = (String) ((List<?>) obj).get(Numero.TRES.valueI);
				AutenticacionRespuestaDTO response = obtenerAutenticacionRespuestaDTO(credenciales,obj,codificado);
				if (response != null) return response;
				throw new ExcepcionComercial(MensajesNegocioClaves.KEY_AUTENTICACION_FALLIDA.value);
			}
		}
		throw new ExcepcionComercial(MensajesNegocioClaves.KEY_AUTENTICACION_FALLIDA.value);
	}
	/**
	 * metodo que se encarga de armar todo el dto
	 * @param credenciales
	 * @param obj
	 * @param codificado
	 * @throws URISyntaxException 
	 */
	private AutenticacionRespuestaDTO obtenerAutenticacionRespuestaDTO(AutenticacionSolicitudDTO credenciales,Object obj,String codificado) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, ExcepcionComercial, URISyntaxException {
		if(validarClave(credenciales,codificado)){
			Long idUsuario =((Integer) ((List<?>) obj).get(Numero.ZERO.valueI)).longValue();
			if(idUsuario != null && !idUsuario.equals(Numero.ZERO.valueL)){
				// Se valida si el rol del usuario esta activo o retorna la consulta
				Object obtenerRolEstado = obtenerRolEstado(credenciales);
				
				// se verifica que el usuario si este activo
				String estado = ((List<?>) obj).get(Numero.SEIS.valueI).toString();
				if (!Estado.ACTIVO.equals(estado)) {
					throw new ExcepcionComercial(MensajesNegocioClaves.KEY_USUARIO_NO_ACTIVO.value);
				}
				// se construye el DTO con los datos personales del usuario
				UsuarioDTO usuario = new UsuarioDTO();
				usuario.setIdUsuario(idUsuario);
				usuario.setNombreCompleto(((List<?>) obj).get(Numero.UNO.valueI).toString());
				usuario.setPrimerIngreso(((Integer) ((List<?>) obj).get(Numero.DOS.valueI)).longValue());
				usuario.setClave(codificado);
				usuario.setIdRoles(((List<?>) obtenerRolEstado).get(Numero.ZERO.valueI).toString());
				BigDecimal monto = obtenerBigDecimalValidacion(obj, usuario,credenciales);
				usuario.setMontoPremio(monto);
				usuario.setUsuario(((List<?>) obj).get(Numero.CINCO.valueI).toString());
				usuario.setCodigo(((List<?>) obj).get(Numero.ZERO.valueI).toString());
				// se construye el response con los datos configurados
				AutenticacionRespuestaDTO response = new AutenticacionRespuestaDTO();
				// se configura las empresas asociadas al usuario autenticado
				obtenerEmpresasUsuario(response, idUsuario);
				// se verifica si el usuario tiene ROL administrador
				verificarRolAdministrador(usuario);
				//obtener menu services
				BienvenidaRespuestaDTO menu = obtenerMenu(usuario.getIdUsuario(),credenciales.getIdAplicacion());
				response.setMenu(menu);
				// se verifica si el usuario tiene programacion
				if(Boolean.TRUE.equals(obtenerProgramacionRol(idUsuario))) {
					UbicacionDTO programacion = obtenerProgramacionUsuario(idUsuario);
					usuario.setIsCajero(programacion.isCajero());
					usuario.setIdZona(programacion.getIdZona());
					usuario.setIdSubZona(programacion.getIdSubZona());
					usuario.setIdPuntoVenta(programacion.getIdPuntoVenta());
					usuario.setHoraFinal(programacion.getHoraFinal());
					usuario.setIdCaja(programacion.getIdCaja());
					usuario.setRolConProgramacion(true);
					extractedIsBackoffice(credenciales, idUsuario, usuario, response, programacion);

				}
				response.setUsuario(usuario);
				return response;
			}
		}
		return null;
	}
	private void extractedIsBackoffice(AutenticacionSolicitudDTO credenciales, Long idUsuario, UsuarioDTO usuario, AutenticacionRespuestaDTO response, UbicacionDTO programacion) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, ExcepcionComercial  {
		if(!Constant.ID_APLICACION_BACKOFFICE.equals(credenciales.getIdAplicacion())) {
			Object[] verifiEmpresa = verificarEmpresaVenta(response.getItemsEmpresas());
			if (Boolean.TRUE.equals(verifiEmpresa[0])) {
				response.setIdEmpresaGestionar(((Integer) verifiEmpresa[1]).longValue());
				if (!programacion.isCajero()) {
					usuario.setListaPapeleria(obtnenerRollosPorIdVendedor(idUsuario, response.getIdEmpresaGestionar(), credenciales.getIdAplicacion().longValue()));
					//si el usuario no es administrador, se valida si el punto de venta, la subzona y la zona estén activas
					//y se valida que esté en web ventas
					if( programacion.getIdZona()!= null && programacion.getIdPuntoVenta()!= null) {
						validacionPuntoVentaZonaSubZonaActiva( programacion.getIdZona(), programacion.getIdPuntoVenta(), idUsuario);

					}
				}
			}
		}
	}
	/**
	 * Metodo que permite verificar si el usuario está asociado a una empresa
	 * @throws Exception
	 */
	private Object[] verificarEmpresaVenta(List<SelectItem> itemsEmpresas)  throws  ExcepcionComercial  {
		Object[] result = new  Object[2] ;
		result[0]= false;
		if (itemsEmpresas != null && !itemsEmpresas.isEmpty()) {
			for (SelectItem selectItem : itemsEmpresas) {
				if (selectItem.getOtherValue().equals(true)) {
					result[0]= true;
					result[1]= selectItem.getValue();
					return result;
				}

			}
		}
		throw new ExcepcionComercial(MensajesNegocioClaves.KEY_EMPRESA_USUARIO.value);
	}
	private BigDecimal obtenerBigDecimalValidacion(Object obj, UsuarioDTO usuario, AutenticacionSolicitudDTO credenciales) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, ExcepcionComercial  {
		obtenerPlanComisionId(credenciales);
		if(obtenerPlanComisionId(credenciales) !=null) {
			usuario.setIdPlanComision(obtenerPlanComisionId(credenciales));
		}
		BigDecimal monto = BigDecimal.ZERO;
		if(((List<?>) obj).get(Numero.CUATRO.valueI) != null) {
			monto = new BigDecimal((Integer) ((List<?>) obj).get(Numero.CUATRO.valueI));
		}
		return monto;
	}

	/**
	 * Metodo que permite obtener las empresas del usuario autenticado
	 */
	private void obtenerEmpresasUsuario(AutenticacionRespuestaDTO response, Long idUsuario) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, ExcepcionComercial  {
		SelectItem itemEmpresa = obtenerEmpresasUsuario(idUsuario);
		if (itemEmpresa != null ) {
			response.agregarItemEmpresa(itemEmpresa);
		}
	}
	/**
	 * Metodo que permite verificar si el usuario tiene rol administrador
	 */
	private void verificarRolAdministrador(UsuarioDTO usuario) {
		String idRoles = usuario.getIdRoles();
		if (!Util.isNull(idRoles)) {
			String[] roles = usuario.getIdRoles().split(Constant.COMA);
			for (String idROL : roles) {
				if (Constant.ID_ADMINISTRADOR.equals(idROL)) {
					usuario.setAdministrador(true);
					return;
				}
			}
		}
	}

	/** Conexiones micros */
	private Object validarExisteUsuario(AutenticacionSolicitudDTO credenciales) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, ExcepcionComercial  {
		ResponseEntity<?> response;
		URI uri;
		String url = UrlMicrosSolicitud.ACCESO_USUARIOS+"validarExisteUsuario?claveIngreso={0}&usuarioIngreso={1}&idAplicacion={2}";
		List<String> params = new ArrayList<>();
		params.add(credenciales.getClaveIngreso());
		params.add(credenciales.getUsuarioIngreso());
		params.add(credenciales.getIdAplicacion().toString());
		String[] array = params.toArray(new String[2]);
		uri = Util.buildUri(url, array);
		try {
			response = this.generic.getService(uri, Object.class);
			List<Object> result = (List<Object>) response.getBody();
			if (result != null && !result.isEmpty()) {
				return result;
			}
		}catch (HttpClientErrorException e) {
			throw new ExcepcionComercial(returnExcepcionComercial(e.getResponseBodyAsString()));
		}
		return null;
	}
	@SuppressWarnings("null")
	private boolean validarClave(AutenticacionSolicitudDTO credenciales, String codificado) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {

		ResponseEntity<?> response;
		boolean body;
		URI uri;
		String url = UrlMicrosSolicitud.ACCESO_USUARIOS+"verificarClave?clave={0}&codificada={1}";
		List<String> params = new ArrayList<>();
		params.add(credenciales.getClaveIngreso());
		params.add(codificado);
		String[] array = params.toArray(new String[1]);
		uri = Util.buildUri(url, array);
		response = this.generic.getService(uri, Object.class);
		body = (boolean) response.getBody();

		if (response.getStatusCode() == HttpStatus.OK && !ObjectUtils.isEmpty(body)) {
			return body;
		}
		return false;
	}
	private Object obtenerRolEstado(AutenticacionSolicitudDTO credenciales) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, ExcepcionComercial  {
		ResponseEntity<?> response;
		URI uri;
		String url = UrlMicrosSolicitud.ACCESO_ROLES+"obtenerRolEstado?usuarioIngreso={0}";
		List<String> params = new ArrayList<>();
		params.add(credenciales.getUsuarioIngreso());
		String[] array = params.toArray(new String[0]);
		uri = Util.buildUri(url, array);
		try {
			response = this.generic.getService(uri, Object.class);
			List<Object> result = (List<Object>) response.getBody();
			if (result != null && !result.isEmpty()) {
				return result.get(Numero.ZERO.valueI);
			}
		}catch (HttpClientErrorException e) {
			throw new ExcepcionComercial(returnExcepcionComercial(e.getResponseBodyAsString()));
		}
		return null;
	}
	@SuppressWarnings("null")
	private Long obtenerPlanComisionId(AutenticacionSolicitudDTO credenciales) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, ExcepcionComercial {

		ResponseEntity<?> response;
		Long body;
		URI uri;
		String url = UrlMicrosSolicitud.COMISIONES+"obtenerPlanComisionId?usuarioIngreso={0}";
		List<String> params = new ArrayList<>();
		params.add(credenciales.getUsuarioIngreso());
		String[] array = params.toArray(new String[0]);
		uri = Util.buildUri(url, array);
		try {
			response = this.generic.getService(uri, Object.class);
			body = response.getBody() != null ? ((Integer) response.getBody()).longValue(): null;
			if (response.getStatusCode() == HttpStatus.OK && !ObjectUtils.isEmpty(body)) {
				return body;
			}
		} catch (HttpClientErrorException e) {
			throw new ExcepcionComercial(returnExcepcionComercial(e.getResponseBodyAsString()));
		}
		return null;
	}
	/**
	 * metodo que retorna la excepcion
	 * @param msj que es mensaje de excepcion
	*/
	private String returnExcepcionComercial(String msj) {
		String[] parts = msj.split(":");
		return parts[1].substring(0, parts[1].length()-2).replace('"', ' ').trim();
	}
	private SelectItem obtenerEmpresasUsuario(Long idUsuario) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, ExcepcionComercial  {
		ResponseEntity<?> response;
		URI uri;
		String url = UrlMicrosSolicitud.ADMINISTRACION_EMPRESAS+"obtenerEmpresasUsuario?idUsuarioIngreso={0}";
		List<String> params = new ArrayList<>();
		params.add(idUsuario.toString());
		String[] array = params.toArray(new String[0]);
		uri = Util.buildUri(url, array);
		try {
			response = this.generic.getService(uri, SelectItem.class);
			if (response.getBody() != null ) {
				return (SelectItem) response.getBody();
			}
		} catch (HttpClientErrorException e) {
			throw new ExcepcionComercial(returnExcepcionComercial(e.getResponseBodyAsString()));
		}
		return null;
	}
	@SuppressWarnings("null")
	private boolean obtenerProgramacionRol(Long idUsuario) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException  {

		ResponseEntity<?> response;
		boolean body;
		URI uri;
		String url = UrlMicrosSolicitud.ACCESO_ROLES+"obtenerProgramacionRol?idUsuario={0}";
		List<String> params = new ArrayList<>();
		params.add(idUsuario.toString());
		String[] array = params.toArray(new String[0]);
		uri = Util.buildUri(url, array);
		response = this.generic.getService(uri, Object.class);
		body = (boolean) response.getBody();

		if (response.getStatusCode() == HttpStatus.OK && !ObjectUtils.isEmpty(body)) {
			return body;
		}
		return false;
	}
	private UbicacionDTO obtenerProgramacionUsuario(Long idUsuario) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, ExcepcionComercial  {
		ResponseEntity<?> response;
		URI uri;
		String url = UrlMicrosSolicitud.ADMINISTRACION_HORARIO+"obtenerProgramacionUsuario?idUsuarioIngreso={0}";
		List<String> params = new ArrayList<>();
		params.add(idUsuario.toString());
		String[] array = params.toArray(new String[0]);
		uri = Util.buildUri(url, array);
		try {
			response = this.generic.getService(uri, UbicacionDTO.class);
			if (response.getBody() != null ) {
				return (UbicacionDTO) response.getBody();
			}
		} catch (HttpClientErrorException e) {
			throw new ExcepcionComercial(returnExcepcionComercial(e.getResponseBodyAsString()));
		}
		return null;
	}
	private List<PapeleriaRolloDTO> obtnenerRollosPorIdVendedor(Long idVendedor ,Long idEmpresa ,Long idAplicacion ) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, ExcepcionComercial  {
		ResponseEntity<?> response;
		URI uri;
		String url = UrlMicrosSolicitud.ADMINISTRACION_PAPELERIA+"obtenerRollosPorIdVendedor?idVendedor={0}&idEmpresa={1}&idAplicacion={2}";
		List<String> params = new ArrayList<>();
		params.add(idVendedor.toString());
		params.add(idEmpresa.toString());
		params.add(idAplicacion.toString());
		String[] array = params.toArray(new String[2]);
		uri = Util.buildUri(url, array);
		try {
			response = this.generic.getService(uri, Object.class);
			if (response.getBody() != null ) {
				return (List<PapeleriaRolloDTO>) response.getBody();
			}
		} catch (HttpClientErrorException e) {
			throw new ExcepcionComercial(returnExcepcionComercial(e.getResponseBodyAsString()));
		}
		return Collections.emptyList();
	}
	@SuppressWarnings("null")
	private boolean validacionPuntoVentaZonaSubZonaActiva(Long idZona,Long idPuntoVenta, Long idUsuario) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, ExcepcionComercial  {

		ResponseEntity<?> response;
		boolean body;
		URI uri;
		String url = UrlMicrosSolicitud.ADMINISTRACION_OPERACION_COMERCIAL+"validacionPuntoVentaZonaSubZonaActiva?idUsuario={0}&idZona={1}&idPuntoVenta={2}";
		List<String> params = new ArrayList<>();
		params.add(idUsuario.toString());
		params.add(idZona.toString());
		params.add(idPuntoVenta.toString());
		String[] array = params.toArray(new String[2]);
		uri = Util.buildUri(url, array);
		try {
		response = this.generic.getService(uri, Object.class);
		body = (boolean) response.getBody();
		if (response.getStatusCode() == HttpStatus.OK && !ObjectUtils.isEmpty(body)) {
			return body;
		}
		} catch (HttpClientErrorException e) {
			throw new ExcepcionComercial(returnExcepcionComercial(e.getResponseBodyAsString()));
		}
		return false;
	}


	private BienvenidaRespuestaDTO obtenerMenu(Long idUsuario,int idAplicacion) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, URISyntaxException  {
		ResponseEntity<?> response;
		URI uri;
		String url = UrlMicrosSolicitud.MENUS+"validarPrivilegiosMenu";
		uri = new URI(url);
		
		BienvenidaSolicitudDTO menu = new BienvenidaSolicitudDTO();
		menu.setIdAplicacion(idAplicacion);
		menu.setIdUsuario(idUsuario);
		
		response = this.generic.postService(uri,menu, BienvenidaRespuestaDTO.class);
		if (response.getBody() != null ) {
			return (BienvenidaRespuestaDTO) response.getBody();
		}
		return null;
	}
}
