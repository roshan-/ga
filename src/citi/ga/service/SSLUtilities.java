package citi.ga.service;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * This class provide various static methods that relax X509 certificate and
 * hostname verification while using the HTTPS.
 *
 * Attribution: http://schrepfler.blogspot.com.br/2009/06/relaxing-ssl-validation-for-jaxws.html
 */
public final class SSLUtilities {

	/**
	 * @deprecated see {@link #_hostnameVerifier}.
	 */
	private static HostnameVerifier __hostnameVerifier;
	/**
	 * @deprecated see {@link #_trustManagers}.
	 */
	private static TrustManager[] __trustManagers;
	private static HostnameVerifier _hostnameVerifier;
	private static TrustManager[] _trustManagers;

	/**
	 * @deprecated see {@link #_trustAllHostnames()}.
	 */
	private static void __trustAllHostnames() {
		if (__hostnameVerifier == null) {
			__hostnameVerifier = new _FakeHostnameVerifier();
		} 
		HttpsURLConnection.setDefaultHostnameVerifier(__hostnameVerifier);
	} 

	/**
	 * @deprecated see {@link #_trustAllHttpsCertificates()}.
	 */
	private static void __trustAllHttpsCertificates() {
		SSLContext context;

		if (__trustManagers == null) {
			__trustManagers = new TrustManager[]{new _FakeX509TrustManager()};
		} 
		try {
			context = SSLContext.getInstance("SSLv3");
			context.init(null, __trustManagers, new SecureRandom());
		} catch (GeneralSecurityException gse) {
			throw new IllegalStateException(gse.getMessage());
		} 
		HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
	} 

	private static boolean isDeprecatedSSLProtocol() {
		return ("com.sun.net.ssl.internal.www.protocol".equals(System.getProperty("java.protocol.handler.pkgs")));
	} 

	private static void _trustAllHostnames() {
		if (_hostnameVerifier == null) {
			_hostnameVerifier = new FakeHostnameVerifier();
		} 
		HttpsURLConnection.setDefaultHostnameVerifier(_hostnameVerifier);
	} 

	private static void _trustAllHttpsCertificates() {
		SSLContext context;

		if (_trustManagers == null) {
			_trustManagers = new TrustManager[]{new FakeX509TrustManager()};
		} 
		try {
			context = SSLContext.getInstance("SSLv3");
			context.init(null, _trustManagers, new SecureRandom());
		} catch (GeneralSecurityException gse) {
			throw new IllegalStateException(gse.getMessage());
		} 
		HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
	}

	protected static void trustAllHostnames() {
		if (isDeprecatedSSLProtocol()) {
			__trustAllHostnames();
		} else {
			_trustAllHostnames();
		} 
	} 

	protected static void trustAllHttpsCertificates() {
		if (isDeprecatedSSLProtocol()) {
			__trustAllHttpsCertificates();
		} else {
			_trustAllHttpsCertificates();
		} 
	}

	/**
	 * @deprecated see {@link SSLUtilities.FakeHostnameVerifier}.
	 */
	protected static class _FakeHostnameVerifier
	implements HostnameVerifier {

		public boolean verify(String hostname, SSLSession session) {
			return (true);
		}
	} 

	protected static class _FakeX509TrustManager
	implements X509TrustManager {

		private static final X509Certificate[] _AcceptedIssuers =
				new X509Certificate[]{};

		public boolean isClientTrusted(X509Certificate[] chain) {
			return (true);
		} 

		public boolean isServerTrusted(X509Certificate[] chain) {
			return (true);
		} 

		public X509Certificate[] getAcceptedIssuers() {
			return (_AcceptedIssuers);
		} 

		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			throw new UnsupportedOperationException("Not supported yet.");
		}
	} 

	protected static class FakeHostnameVerifier implements HostnameVerifier {

		public boolean verify(String hostname,
				SSLSession session) {
			return (true);
		} 
	} 

	protected static class FakeX509TrustManager implements X509TrustManager {

		private static final X509Certificate[] _AcceptedIssuers =
				new X509Certificate[]{};

		public void checkClientTrusted(X509Certificate[] chain,
				String authType) {
		} 

		public void checkServerTrusted(X509Certificate[] chain,
				String authType) {
		} 

		public X509Certificate[] getAcceptedIssuers() {
			return (_AcceptedIssuers);
		} 
	} 
} 