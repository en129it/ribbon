
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.springframework.core.io.Resource;

public class TrustSSLSocketFactory extends SSLSocketFactory {

	private SSLContext sslContext = null;

	public TrustSSLSocketFactory(Resource aTrustStoreResource) throws Exception {		
		KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
		trustStore.load(aTrustStoreResource.getInputStream(), "changeit".toCharArray());
		
		TrustManagerFactory factory = TrustManagerFactory.getInstance("X509");
		factory.init(trustStore);
		
		sslContext = SSLContext.getInstance("SSL");
		sslContext.init(null, factory.getTrustManagers(), null);
	}
	
	@Override
	public String[] getDefaultCipherSuites() {
		return sslContext.getDefaultSSLParameters().getCipherSuites();
	}

	@Override
	public String[] getSupportedCipherSuites() {
		return getDefaultCipherSuites();
	}

	@Override
	public Socket createSocket(String aHost, int aPort) throws IOException, UnknownHostException {
		return sslContext.getSocketFactory().createSocket(aHost, aPort);
	}

	@Override
	public Socket createSocket(InetAddress aHost, int aPort) throws IOException {
		return sslContext.getSocketFactory().createSocket(aHost, aPort);
	}
	
	@Override
	public Socket createSocket(Socket aSocket, String aHost, int aPort, boolean isAutoClose) throws IOException {
		return sslContext.getSocketFactory().createSocket(aSocket, aHost, aPort, isAutoClose);
	}

	@Override
	public Socket createSocket(String aHost, int aPort, InetAddress aLocalAddress, int aLocalPort) throws IOException, UnknownHostException {
		return sslContext.getSocketFactory().createSocket(aHost, aPort, aLocalAddress, aLocalPort);
	}

	@Override
	public Socket createSocket(InetAddress aHost, int aPort, InetAddress aLocalAddress, int aLocalPort) throws IOException {
		return sslContext.getSocketFactory().createSocket(aHost, aPort, aLocalAddress, aLocalPort);
	}

	/**
	 return new Client.Default(new SSLSocketFactory(), null)
	 */
	
}
