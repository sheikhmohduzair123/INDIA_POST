package com.util;

import java.util.Arrays;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.controllers.ClientController;
import com.domain.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vo.JwtTokenResponse;

@Service
public class ServerTokenUtils {
	
	protected Logger log = LoggerFactory.getLogger(ServerTokenUtils.class);
	
	  @Value("${server.url}")
	    private String serverURL;

	
	public static String globalServerToken;
	
	
	//rest call for getting token from serverApi
	  public String JwtAuthenticate(Client client,HttpServletResponse res) throws Exception {

		    log.info(" getting token on rest call "+client);
			RestTemplate restTemplate = restTemplate(client.getClientId(), client.getClientToken());

			String jwtToken = restTemplate.postForObject(serverURL + "/server/authenticate", client, String.class);
	    	ObjectMapper objectMapper = new ObjectMapper();
	    	JwtTokenResponse jwtTokenResponse = objectMapper.readValue(jwtToken, JwtTokenResponse.class);
	    	jwtToken = jwtTokenResponse.getToken();
	    	ServerTokenUtils.globalServerToken=jwtToken;
	    	//Cookie cookie=writeCookie(jwtToken);
			//res.addCookie(cookie);
			return jwtToken;
		}

	  
	  private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory(String clientId, String password) throws Exception
	    {
	        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
	                = new HttpComponentsClientHttpRequestFactory();

	        clientHttpRequestFactory.setHttpClient(httpClient(clientId,password));

	        return clientHttpRequestFactory;
	    }

	    private HttpClient httpClient(String clientId, String password) throws Exception
	    {
	        //CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	        final TrustStrategy acceptingTrustStrategy = (certificate, authType) -> true;
	        SSLContext sslContext = new SSLContextBuilder()
	                .loadTrustMaterial(null, acceptingTrustStrategy)
	                .build();
	        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

//	        credentialsProvider.setCredentials(AuthScope.ANY,
//	                new UsernamePasswordCredentials(clientId,password));

	        HttpClient client = HttpClientBuilder
	                .create()
	                //.setDefaultCredentialsProvider(credentialsProvider)
	                .setSSLSocketFactory(socketFactory)
	                .build();
	        return client;
	    }

	    private RestTemplate restTemplate(String clientId, String clientToken) throws Exception {

	        HttpComponentsClientHttpRequestFactory factory = getClientHttpRequestFactory(clientId, clientToken);
	        return new RestTemplate(factory);
	    }

		//create  header for rest call
		  private HttpHeaders createHeaders(String jwtToken)
		  { return new HttpHeaders() {
			  {
				  log.info("creating header for rest call auth ");
				  setContentType(MediaType.APPLICATION_JSON);
				  setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				  String authHeader = "Bearer " + jwtToken;
				  add("Authorization", authHeader );
		      }
		    };
		  }
		 
		  

}
