package com.acceso.login.dto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para encapsular los datos de un item del menu
 */
@Getter
@Setter
@NoArgsConstructor
public class MenuItemDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** identificador del item */
	private String id;

	/** Es el label que se visualiza en pantalla */
	private String label;

	/** Es la descripcion que se visualiza en el tooltip del menu */
	private String title;

	/** es el icono a visualizar al lado del item */
	private String icon;

	/** es la url a redireccionar cuando le dan click al item */
	private String routerLink;

	/** indica si el item padre debe estar expandido */
	private boolean expanded;

	/** son los items de este item o modulo */
	private List<MenuItemDTO> items;

	/** son las acciones que tiene este item */
	private List<MenuItemAccionDTO> acciones;

	/** Variable que almacena la url correspondiente al manual en pdf */
	private String urlManualPDF;

	/** Variable que almacena la url correspondiente al manual en video */
	private String urlManualVideo;

	/**
	 * Metodo que permite agregar un item para este item menu
	 */
	public void agregarItem(MenuItemDTO item) {
		if (this.items == null) {
			this.items = new ArrayList<>();
		}
		this.items.add(item);
	}

	/**
	 * Metodo que permite agregar una accion para este item
	 */
	public void agregarAccion(MenuItemAccionDTO accion) {
		if (this.acciones == null) {
			this.acciones = new ArrayList<>();
		}
		this.acciones.add(accion);
	}

	/**
	 * Metodo que permite buscar un item a partir de su identificador
	 */
	public MenuItemDTO getItemMenu(String id) {
		MenuItemDTO item = null;
		if (this.items != null && !this.items.isEmpty()) {
			for (MenuItemDTO menuItem: this.items) {
				if (menuItem.getId().equals(id)) {
					item = menuItem;
					break;
				}
			}
		}
		return item;
	}
}
