package com.acceso.login.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO que contiene los datos del RESPONSE de bienvenida
 */
@Getter
@Setter
@NoArgsConstructor
public class BienvenidaRespuestaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** son los items del menu */
	private List<MenuItemDTO> itemsMenu;

	/**
	 * Metodo que permite agregar un item para el menu de la app
	 */
	public void agregarItem(MenuItemDTO item) {
		if (this.itemsMenu == null) {
			this.itemsMenu = new ArrayList<>();
		}
		this.itemsMenu.add(item);
	}
}
