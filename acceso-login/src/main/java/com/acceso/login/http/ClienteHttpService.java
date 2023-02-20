package com.acceso.login.http;


import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public interface ClienteHttpService {

	/**
	 * Metodo para peticiones GET
	 * @param uri
	 * @param object
	 * @param mediaType
     * @param auth
	 * @return
     * @throws java.lang.Exception
	 */
	<T>ResponseEntity<T> get(URI uri, Class<T> object, MediaType mediaType) throws IOException,  KeyManagementException, NoSuchAlgorithmException, KeyStoreException;
	/**
	 * Metodo para peticiones DELETE
	 * @param uri
	 * @param object
	 * @param mediaType
     * @param auth
	 * @return
     * @throws java.lang.Exception
	 */
	<T>ResponseEntity<T> delete(URI uri, Class<T> object, MediaType mediaType) throws IOException,  KeyManagementException, NoSuchAlgorithmException, KeyStoreException;
	/**
	 * Metodo para peticiones POST
	 * @param uri
	 * @param data
	 * @param objectClass
	 * @param mediaType
	 * @param auth
	 * @return
     * @throws java.lang.Exception
	 */
	<T>ResponseEntity<T> post(URI uri, Object data, Class<T> objectClass, MediaType mediaType) throws IOException,  KeyManagementException, NoSuchAlgorithmException, KeyStoreException;
	/**
	 * Metodo para peticiones PUT
	 * @param uri
	 * @param data
	 * @param objectClass
	 * @param mediaType
	 * @param auth
	 * @return
     * @throws java.lang.Exception
	 */
	<T>ResponseEntity<T> put(URI uri, Object data, Class<T> objectClass, MediaType mediaType) throws IOException,  KeyManagementException, NoSuchAlgorithmException, KeyStoreException;
}
