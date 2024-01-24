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

import com.domain.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vo.JwtTokenResponse;

@Service
public class RateTokenUtils {

	@Value("${rateAPI}")
	private String rateURL;

	protected Logger log = LoggerFactory.getLogger(RateTokenUtils.class);

	public static String globalRateToken;

	// getting jwt token from rateapi
	public String JwtAuthenticate(Client client, HttpServletResponse res) throws Exception {

		RestTemplate restTemplate = restTemplate();
		String jwtToken = restTemplate.postForObject(rateURL + "/parcel/authenticate", client, String.class);
		ObjectMapper objectMapper = new ObjectMapper();
		JwtTokenResponse jwtTokenResponse = objectMapper.readValue(jwtToken, JwtTokenResponse.class);
		jwtToken = jwtTokenResponse.getToken();
		System.out.println("Main controller rate API token" + jwtToken);

		RateTokenUtils.globalRateToken = jwtToken;

		System.out.println("Main controller globaltoken " + RateTokenUtils.globalRateToken);

		//
		// Cookie cookie=writeCookie(jwtToken);
		// res.addCookie(cookie);

		return jwtToken;
	}

	private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() throws Exception {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();

		clientHttpRequestFactory.setHttpClient(httpClient());

		return clientHttpRequestFactory;
	}

	private HttpClient httpClient() throws Exception {

		final TrustStrategy acceptingTrustStrategy = (certificate, authType) -> true;
		SSLContext sslContext = new SSLContextBuilder()
				.loadTrustMaterial(null, acceptingTrustStrategy)
				.build();
		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext,
				NoopHostnameVerifier.INSTANCE);
		HttpClient client = HttpClientBuilder
				.create()
				.setSSLSocketFactory(socketFactory)
				.build();
		return client;
	}

	private RestTemplate restTemplate() throws Exception {

		HttpComponentsClientHttpRequestFactory factory = getClientHttpRequestFactory();
		return new RestTemplate(factory);
	}

	// create header for rest template
	private HttpHeaders createHeaders(String jwtToken) {
		return new HttpHeaders() {
			/**
			*
			*/
			private static final long serialVersionUID = 1L;
			{
				setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				String authHeader = "Bearer " + jwtToken;
				add("Authorization", authHeader);
			}
		};
	}

}
