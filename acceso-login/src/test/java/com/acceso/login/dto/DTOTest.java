package com.acceso.login.dto;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DTOTest {

	 @Test
	    void testAgregarItem() {
	        MenuItemDTO menuItem = new MenuItemDTO();
	        MenuItemDTO subMenuItem = new MenuItemDTO();

	        menuItem.agregarItem(subMenuItem);

	        assertNotNull(menuItem.getItems());
	        assertEquals(1, menuItem.getItems().size());
	        assertEquals(subMenuItem, menuItem.getItems().get(0));
	    }

	    @Test
	    void testAgregarAccion() {
	        MenuItemDTO menuItem = new MenuItemDTO();
	        MenuItemAccionDTO accion = new MenuItemAccionDTO();

	        menuItem.agregarAccion(accion);

	        assertNotNull(menuItem.getAcciones());
	        assertEquals(1, menuItem.getAcciones().size());
	        assertEquals(accion, menuItem.getAcciones().get(0));
	    }
	    @Test
	    void testSelectItem() {
	    	SelectItem menuItem = new SelectItem();
	    	menuItem.setLabel("datos");
	    	menuItem.setOtherLabel("datos");
	    	assertNotNull(menuItem.getLabel());
	    	assertNotNull(menuItem.getOtherLabel());

	    }

	    @Test
	    void testGetItemMenu() {
	        MenuItemDTO menuItem = new MenuItemDTO();
	        menuItem.setId("1");

	        List<MenuItemDTO> subMenuItems = new ArrayList<>();
	        MenuItemDTO subMenuItem = new MenuItemDTO();
	        subMenuItem.setId("2");
	        subMenuItems.add(subMenuItem);

	        menuItem.setItems(subMenuItems);

	        assertNotNull(menuItem.getItemMenu("2"));
	        assertEquals(subMenuItem, menuItem.getItemMenu("2"));
	    }
	    
	    @Test
	     void testMenuItemDTO() {
	        MenuItemDTO menuItem = new MenuItemDTO();
	        menuItem.setId("item1");
	        menuItem.setLabel("Item 1");
	        menuItem.setTitle("Descripción item 1");
	        menuItem.setIcon("fa-check");
	        menuItem.setRouterLink("/item1");
	        menuItem.setExpanded(true);
	        List<MenuItemDTO> items = new ArrayList<>();
	        menuItem.setItems(items);
            List<MenuItemAccionDTO> acciones = new ArrayList<>();
            menuItem.setAcciones(acciones);
            menuItem.setUrlManualPDF("https://manual.pdf");
            menuItem.setUrlManualVideo("https://manual.video");

            assertEquals("item1", menuItem.getId());
            assertEquals("Item 1", menuItem.getLabel());
            assertEquals("Descripción item 1", menuItem.getTitle());
            assertEquals("fa-check", menuItem.getIcon());
            assertEquals("/item1", menuItem.getRouterLink());
            assertEquals(true, menuItem.isExpanded());
            assertEquals(items, menuItem.getItems());
            assertEquals(acciones, menuItem.getAcciones());
            assertEquals("https://manual.pdf", menuItem.getUrlManualPDF());
            assertEquals("https://manual.video", menuItem.getUrlManualVideo());
	    }
	    
	    @Test
	    void testMenuItemAccionDTO() {
	        MenuItemAccionDTO accion = new MenuItemAccionDTO();
	        accion.setNombre("Accion1");
	        accion.setIdAccion(1L);

	        assertEquals("Accion1", accion.getNombre());
	        assertEquals(1L, accion.getIdAccion());
	    }
	    
	    @Test
	    void testUsuarioDTO() {
	    	 UsuarioDTO usuario = new UsuarioDTO();

	         usuario.setIdUsuario(1L);
	         usuario.setUsuario("johndoe");
	         usuario.setNombreCompleto("John Doe");
	         usuario.setRoles("admin, user");
	         usuario.setIdRoles("1, 2");
	         usuario.setClave("password123");
	         usuario.setPrimerIngreso(0L);
	         usuario.setNumeroTelefono("1234567890");
	         usuario.setCorreo("johndoe@example.com");
	         usuario.setAdministrador(true);
	         usuario.setIdZona(10L);
	         usuario.setIdSubZona(100L);
	         usuario.setIdPuntoVenta(1000L);
	         usuario.setHoraFinal("12:00");
	         usuario.setIdPlanComision(10000L);
	         usuario.setMontoPremio(new BigDecimal("100000.00"));
	         usuario.setIdCaja(100000L);
	         usuario.setIsCajero(true);
	         List<PapeleriaRolloDTO> listaPapeleria = new ArrayList<>();
	         usuario.setListaPapeleria(listaPapeleria);
	         usuario.setCodigo("VEND-001");
	         usuario.setRolConProgramacion(true);

	         assertEquals(1L, usuario.getIdUsuario());
	         assertEquals("johndoe", usuario.getUsuario());
	         assertEquals("John Doe", usuario.getNombreCompleto());
	         assertEquals("admin, user", usuario.getRoles());
	         assertEquals("1, 2", usuario.getIdRoles());
	         assertEquals("password123", usuario.getClave());
	         assertEquals(0L, usuario.getPrimerIngreso());
	         assertEquals("1234567890", usuario.getNumeroTelefono());
	         assertEquals("johndoe@example.com", usuario.getCorreo());
	         assertEquals(true, usuario.isAdministrador());
	         assertEquals(10L, usuario.getIdZona());
	         assertEquals(100L, usuario.getIdSubZona());
	         assertEquals(1000L, usuario.getIdPuntoVenta());
	         assertEquals("12:00", usuario.getHoraFinal());
	         assertEquals(10000L, usuario.getIdPlanComision());
	         assertEquals(new BigDecimal("100000.00"), usuario.getMontoPremio());
	         assertEquals(100000L, usuario.getIdCaja());
	         assertEquals(true, usuario.getIsCajero());
	         List<PapeleriaRolloDTO> listaPapeleriaTest = new ArrayList<>();
	         assertEquals(listaPapeleriaTest, usuario.getListaPapeleria());
	         assertEquals("VEND-001", usuario.getCodigo());
	         assertEquals(true, usuario.isRolConProgramacion());
	    }
	    
	    @Test
		void testAccesores() {
			String mensajeDefinido = "Mensaje generico";
			MensajeRespuestaDTO mensaje = new MensajeRespuestaDTO();
			assertNull(mensaje.getMensaje());

			mensaje.setMensaje(mensajeDefinido);
			assertAll("valores MensajeRespuestaDto",
				() -> assertNotNull(mensaje.getMensaje()),
				() -> assertEquals(mensajeDefinido, mensaje.getMensaje())
			);
		}


}
