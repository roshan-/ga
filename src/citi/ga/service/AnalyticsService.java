package citi.ga.service;

import java.io.File;
import java.io.InputStreamReader;
import java.util.Collections;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.AnalyticsScopes;

public class AnalyticsService {
	private static Analytics analytics;

	public static synchronized Analytics getAnalytics() {
		if (analytics != null) return analytics;
		else {
			try {
				HttpTransport httpTransport= GoogleNetHttpTransport.newTrustedTransport();
				FileDataStoreFactory credentialsDirectory= new FileDataStoreFactory(new File("."));
				GoogleClientSecrets secretFile= GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), 
						new InputStreamReader(AnalyticsService.class.getResourceAsStream("client_secrets.json")));
				GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JacksonFactory.getDefaultInstance(), 
						secretFile, Collections.singleton(AnalyticsScopes.ANALYTICS)).setDataStoreFactory(credentialsDirectory).build();
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
