package com.acceso.login.http;



import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.acceso.login.util.Util;



@Component
public class GenericoHttp {

    private final ClienteHttpService httpClient;
   
    public GenericoHttp( ClienteHttpService httpClient) {
        this.httpClient = httpClient;
    }    

    public <T>ResponseEntity<T> getService( String url, MultiValueMap<String, String> paramsApigee, Class<T> objectClass )
		throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        URI uri = UriComponentsBuilder.fromUriString( url ).queryParams( paramsApigee ).build().toUri();
        return getService( uri, objectClass );
    }

    public <T>ResponseEntity<T> getService( String url, String[] paramsApigee, Class<T> objectClass )
		throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {        URI uri = Util.buildUri( url, paramsApigee );
        return getService( uri, objectClass );
    }

    public <T>ResponseEntity<T> getService( URI uri, Class<T> objectClass )
		throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        ResponseEntity<T> response;
        response = this.httpClient.get( uri,objectClass,MediaType.APPLICATION_JSON );
        return response;
    }
    
    public <T>ResponseEntity<T> postService( URI uri,Object data, Class<T> objectClass )
		throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
            ResponseEntity<T> response;
            response = this.httpClient.post( uri, data, objectClass, MediaType.APPLICATION_JSON );
            return response;
        }

}