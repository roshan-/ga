package citi.ga.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.AnalyticsScopes;

public class AnalyticsService {
	private static Analytics analytics;

	static HttpTransport newProxyTransport() throws GeneralSecurityException, IOException {
		SSLUtilities.trustAllHostnames();
		SSLUtilities.trustAllHttpsCertificates();
	    NetHttpTransport.Builder builder = new NetHttpTransport.Builder();
	    builder.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("webproxy.wlb2.nam.nsroot.net", 8080)));
	    return builder.build();
	  }				

	public static synchronized Analytics getAnalytics() {
		if (analytics != null) return analytics;
		else {
			try {
				HttpTransport httpTransport= newProxyTransport();
				FileDataStoreFactory credentialsDirectory= new FileDataStoreFactory(new java.io.File("."));
				GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, 
						JacksonFactory.getDefaultInstance(), 
						"271042103535-r9kbabmls1mdks00mb6iug1mtmqrpplk.apps.googleusercontent.com", 
						"ONYPhsC6q5WN8ljot1oTS3BS", Collections.singleton(AnalyticsScopes.ANALYTICS)).setDataStoreFactory(credentialsDirectory).build();
				Credential cred= new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
				analytics= new Analytics.Builder(httpTransport, JacksonFactory.getDefaultInstance(), cred).setApplicationName("CitiVelocity-Analytics/1.0").build();
				return analytics;
			} catch (Exception e) {
				System.out.println("Can't get Analytics Service");
				return null;
			}
		}
	}
}
