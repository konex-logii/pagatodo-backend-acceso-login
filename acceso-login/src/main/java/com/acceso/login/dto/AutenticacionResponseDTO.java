package com.acceso.login.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * DTO para encapsular los datos que se envía cuando inician sesión ante al
 * sistema
 */
@Data
public class AutenticacionResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** son los datos del usuario autenticado */
	private UsuarioDTO usuario;	

	/** Es el identificador de la empresa a gestionar */
	private Long idEmpresaGestionar;

	/** Son las empresas asociados al usuario autenticado */
	private List<SelectItem> itemsEmpresas;

	/**
	 * Metodo que permie agregar una empresa asociada al usuario
	 */
	public void agregarItemEmpresa(SelectItem item) {
		if (this.itemsEmpresas == null) {
			this.itemsEmpresas = new ArrayList<>(); 
		}
		this.itemsEmpresas.add(item);
	}
}
