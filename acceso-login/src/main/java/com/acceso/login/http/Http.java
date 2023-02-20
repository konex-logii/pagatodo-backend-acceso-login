package com.acceso.login.http;

import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.Duration;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;



/**
 * Implementa las peticiones HTPP con los verbos GET, POST, PUT, DELETE
 *
 * @author Ingeneo
 *
 */

@Component
public class Http implements ClienteHttpService {
	public Http() {
        template = construirRestTemplate(1);
	}
	
	public Http(RestTemplate restTemplate) {
		template = restTemplate;
	}
	
	private final Logger log = LoggerFactory.getLogger(Http.class);
	private RestTemplate template;
	private boolean activeSSL = true;

	private RestTemplate construirRestTemplate(Integer timeoutHttp) {
        RestTemplateBuilder builder = new RestTemplateBuilder();
		return builder
            .setReadTimeout( Duration.ofMinutes( timeoutHttp ) )
            .setConnectTimeout( Duration.ofMinutes( timeoutHttp ) )
            .build();
	}

    @Override
    public <T>ResponseEntity<T> get( URI uri, Class<T> object, MediaType mediaType ) 
    	throws IOException , KeyManagementException, NoSuchAlgorithmException, KeyStoreException  {
        return execute( uri, HttpMethod.GET, null, object, mediaType,  true );
    }

    @Override
    public <T>ResponseEntity<T> delete( URI uri, Class<T> object, MediaType mediaType)
    		throws IOException , KeyManagementException, NoSuchAlgorithmException, KeyStoreException  {
        return execute( uri, HttpMethod.DELETE, null, object, mediaType,  true );
    }

    @Override
    public <T>ResponseEntity<T> post( URI uri, Object data, Class<T> objectClass, MediaType mediaType )
    		throws IOException , KeyManagementException, NoSuchAlgorithmException, KeyStoreException  {
        return execute( uri, HttpMethod.POST, data, objectClass, mediaType,  true );
    }

    @Override
    public <T>ResponseEntity<T> put( URI uri, Object data, Class<T> objectClass, MediaType mediaType )
    		throws IOException , KeyManagementException, NoSuchAlgorithmException, KeyStoreException  {
        return execute( uri, HttpMethod.PUT, data, objectClass, mediaType, true );
    }

 

    private <T>ResponseEntity<T> execute( URI uri, HttpMethod httpMethod, Object requestParam, Class<T> objectClass, MediaType mediaType,  Boolean activeSSL )
    	throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

        HttpHeaders headers = new HttpHeaders();

     

        if( mediaType != null ) {
            headers.setContentType( mediaType );
        }

        HttpEntity<Object> request;

        if( requestParam == null ) {
            request = new HttpEntity<>( headers );
        }
        else {
            request = new HttpEntity<>( requestParam, headers );
        }

        ResponseEntity<T> response;

        Long initTime;
        Long endTime;
        Long totalTime;

        initTime = System.currentTimeMillis();

       
        if( Boolean.FALSE.equals(activeSSL) ) {
            template.setRequestFactory( this.requestFactory() );
        }

        response = template.exchange( uri, httpMethod, request, objectClass );

        endTime = System.currentTimeMillis();

        totalTime = endTime - initTime;
        String mensajeInformacionTiempo = "El tiempo de respuesta de " + uri + " es de " + totalTime + " ms";
        log.info(mensajeInformacionTiempo);
       
        return response;
    }

    private HttpComponentsClientHttpRequestFactory requestFactory()
        throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = SSLContexts.custom()
            .loadTrustMaterial( null, acceptingTrustStrategy )
            .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory( sslContext );

        CloseableHttpClient httpClient = HttpClients.custom()
            .setSSLSocketFactory( csf )
            .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
            new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient( httpClient );

        return requestFactory;
    }
    public void activarSSL() {
    	activeSSL = true;
    }
    
    public void desactivarSSL() {
    	activeSSL = false;
    }
    
    public boolean getSSL() {
    	return activeSSL;
    }

}