/**
 * Copyright (C) 2015 Asterios Raptis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.alpharogroup.cxf.rest.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.jsse.TLSServerParameters;
import org.apache.cxf.configuration.security.ClientAuthentication;
import org.apache.cxf.configuration.security.FiltersType;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;

/**
 * The class {@link WebClientExtensions} holds factory methods for server and client parameters.
 */
public class WebClientExtensions {

	/** The Constant DEFAULT_FILTERS_TYPE_INCLUDES. */
	public static final String[] DEFAULT_FILTERS_TYPE_INCLUDES = 
		{ 
			".*_EXPORT_.*", 
			".*_EXPORT1024_.*", 
			".*_WITH_DES_.*",
			".*_WITH_NULL_.*", 
			".*_DH_anon_.*" 
		};

	/**
	 * Sets a mock client authentication to the given client.
	 *
	 * @param client the new mock client authentication
	 */
	public static void setMockClientAuthentication(Object client) {
		ClientConfiguration config = WebClient.getConfig(client);
		// trust all certs...
		HTTPConduit conduit = config.getHttpConduit();

		TLSClientParameters params = conduit.getTlsClientParameters();

		if (params == null) {
			params = new TLSClientParameters();

			params.setTrustManagers(new TrustManager[] { new MockX509TrustManager() });
			params.setDisableCNCheck(true);
			conduit.setTlsClientParameters(params);
		}
	}

	/**
	 * Factory method for load the {@link KeyStore} object from the given file.
	 *
	 * @param type the type of the keystore
	 * @param password the password of the keystore
	 * @param keystoreFile the keystore file
	 * @return the loaded {@link KeyStore} object
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws CertificateException the certificate exception
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws KeyStoreException the key store exception
	 * @deprecated use instead KeyTrustExtensions
	 */
	public static KeyStore newKeyStore(final String type, final String password,
		final File keystoreFile) throws NoSuchAlgorithmException, CertificateException,
		FileNotFoundException, IOException, KeyStoreException
	{
		final KeyStore keyStore = KeyStore.getInstance(type);
		keyStore.load(new FileInputStream(keystoreFile), password.toCharArray());
		return keyStore;
	}

	/**
	 * Resolve the {@link TrustManager} array from the keystore that is resolved from the given parameters.
	 *
	 * @param type the type
	 * @param password the password
	 * @param keystoreFile the keystore file
	 * @param trustManagerAlgorithm the trust manager algorithm
	 * @return the trust manager[]
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws CertificateException the certificate exception
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws KeyStoreException the key store exception
	 */
	public static TrustManager[] resolveTrustManagers(final String type, final String password,
		final File keystoreFile, final String trustManagerAlgorithm)
		throws NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException,
		KeyStoreException
	{
		final KeyStore keyStore = newKeyStore(type, password, keystoreFile);
		final TrustManagerFactory trustFactory = TrustManagerFactory
			.getInstance(trustManagerAlgorithm);
		trustFactory.init(keyStore);
		final TrustManager[] trustManagers = trustFactory.getTrustManagers();
		return trustManagers;
	}

	/**
	 * Resolve the {@link KeyManager} array from the keystore that is resolved from the given parameters.
	 *
	 * @param type the type
	 * @param password the password
	 * @param keystoreFile the keystore file
	 * @param keyManagerAlgorithm the key manager algorithm
	 * @return the key manager[]
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws CertificateException the certificate exception
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws KeyStoreException the key store exception
	 * @throws UnrecoverableKeyException the unrecoverable key exception
	 */
	public static KeyManager[] resolveKeyManagers(final String type, final String password,
		final File keystoreFile, final String keyManagerAlgorithm)
		throws NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException,
		KeyStoreException, UnrecoverableKeyException
	{
		final KeyStore keyStore = newKeyStore(type, password, keystoreFile);
		final KeyManagerFactory keyFactory = KeyManagerFactory.getInstance(keyManagerAlgorithm);
		keyFactory.init(keyStore, password.toCharArray());
		final KeyManager[] keyManagers = keyFactory.getKeyManagers();
		return keyManagers;
	}

	/**
	 * Sets the client configuration.
	 *
	 * @param client
	 *            the client
	 * @param trustManagersKeystoreFile
	 *            the trust managers keystore file
	 * @param trustManagerAlgorithm
	 *            the trust manager algorithm
	 * @param trustManagersKeystoreType
	 *            the trust managers keystore type
	 * @param trustManagersKeystorePassword
	 *            the trust managers keystore password
	 * @param keyManagersKeystoreFile
	 *            the key managers keystore file
	 * @param keyManagerAlgorithm
	 *            the key manager algorithm
	 * @param keyManagersKeystoreType
	 *            the key managers keystore type
	 * @param keyManagersKeystorePassword
	 *            the key managers keystore password
	 * @param cipherSuitesFilter
	 *            the cipher suites filter
	 * @param disableCNCheck
	 *            the disable CN check
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws CertificateException
	 *             the certificate exception
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws KeyStoreException
	 *             the key store exception
	 * @throws UnrecoverableKeyException
	 *             the unrecoverable key exception
	 */
	public static void setClientConfiguration(Object client, File trustManagersKeystoreFile,
			String trustManagerAlgorithm, String trustManagersKeystoreType, String trustManagersKeystorePassword,
			File keyManagersKeystoreFile, String keyManagerAlgorithm, String keyManagersKeystoreType,
			String keyManagersKeystorePassword, FiltersType cipherSuitesFilter, boolean disableCNCheck)
			throws NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException,
			KeyStoreException, UnrecoverableKeyException {

		ClientConfiguration config = WebClient.getConfig(client);
		HTTPConduit httpConduit = config.getHttpConduit();

		TLSClientParameters tlsParams = newTLSClientParameters(trustManagersKeystoreFile, trustManagerAlgorithm, 
				trustManagersKeystoreType, trustManagersKeystorePassword, 
				keyManagersKeystoreFile, keyManagerAlgorithm, 
				keyManagersKeystoreType, keyManagersKeystorePassword, 
				cipherSuitesFilter, disableCNCheck);
				new TLSClientParameters();		

		httpConduit.setTlsClientParameters(tlsParams);
	}
	
	/**
	 * Factory method for create a new {@link TLSClientParameters} from the given parameters.
	 *
	 * @param trustManagersKeystoreFile the trust managers keystore file
	 * @param trustManagerAlgorithm the trust manager algorithm
	 * @param trustManagersKeystoreType the trust managers keystore type
	 * @param trustManagersKeystorePassword the trust managers keystore password
	 * @param keyManagersKeystoreFile the key managers keystore file
	 * @param keyManagerAlgorithm the key manager algorithm
	 * @param keyManagersKeystoreType the key managers keystore type
	 * @param keyManagersKeystorePassword the key managers keystore password
	 * @param cipherSuitesFilter the cipher suites filter
	 * @param disableCNCheck the disable CN check
	 * @return the new {@link TLSClientParameters}.
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws CertificateException the certificate exception
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws KeyStoreException the key store exception
	 * @throws UnrecoverableKeyException the unrecoverable key exception
	 */
	public static TLSClientParameters newTLSClientParameters(File trustManagersKeystoreFile,
			String trustManagerAlgorithm, String trustManagersKeystoreType, String trustManagersKeystorePassword,
			File keyManagersKeystoreFile, String keyManagerAlgorithm, String keyManagersKeystoreType,
			String keyManagersKeystorePassword, FiltersType cipherSuitesFilter, boolean disableCNCheck)
			throws NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException,
			KeyStoreException, UnrecoverableKeyException {		
		TLSClientParameters tlsClientParameters = new TLSClientParameters();
		tlsClientParameters.setDisableCNCheck(disableCNCheck);
		TrustManager[] tm = resolveTrustManagers(trustManagersKeystoreType, trustManagersKeystorePassword,
				trustManagersKeystoreFile, trustManagerAlgorithm);
		tlsClientParameters.setTrustManagers(tm);
		KeyManager[] km = resolveKeyManagers(keyManagersKeystoreType, keyManagersKeystorePassword,
				keyManagersKeystoreFile, keyManagerAlgorithm);
		tlsClientParameters.setKeyManagers(km);
		tlsClientParameters.setCipherSuitesFilter(cipherSuitesFilter);
		return tlsClientParameters;
	}

	/**
	 * Factory method for create a new {@link TLSServerParameters} from the given parameters.
	 *
	 * @param trustManagersKeystoreFile the trust managers keystore file
	 * @param trustManagerAlgorithm the trust manager algorithm
	 * @param trustManagersKeystoreType the trust managers keystore type
	 * @param trustManagersKeystorePassword the trust managers keystore password
	 * @param keyManagersKeystoreFile the key managers keystore file
	 * @param keyManagerAlgorithm the key manager algorithm
	 * @param keyManagersKeystoreType the key managers keystore type
	 * @param keyManagersKeystorePassword the key managers keystore password
	 * @param cipherSuitesFilter the cipher suites filter
	 * @param clientAuthentication the client authentication
	 * @return the new {@link TLSServerParameters}.
	 * @throws KeyStoreException the key store exception
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws CertificateException the certificate exception
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws UnrecoverableKeyException the unrecoverable key exception
	 */
	public static TLSServerParameters newTLSServerParameters(File trustManagersKeystoreFile,
			String trustManagerAlgorithm, String trustManagersKeystoreType, String trustManagersKeystorePassword,
			File keyManagersKeystoreFile, String keyManagerAlgorithm, String keyManagersKeystoreType,
			String keyManagersKeystorePassword, FiltersType cipherSuitesFilter, ClientAuthentication clientAuthentication) throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException {
		TLSServerParameters tlsServerParameters = new TLSServerParameters();
		TrustManager[] tm =  resolveTrustManagers(trustManagersKeystoreType, trustManagersKeystorePassword,
				trustManagersKeystoreFile, trustManagerAlgorithm);				
		tlsServerParameters.setTrustManagers(tm);		
		KeyManager[] km = resolveKeyManagers(keyManagersKeystoreType, keyManagersKeystorePassword,
				keyManagersKeystoreFile, keyManagerAlgorithm);				
		tlsServerParameters.setKeyManagers(km);
		tlsServerParameters.setCipherSuitesFilter(cipherSuitesFilter);
		tlsServerParameters.setClientAuthentication(clientAuthentication);
		return tlsServerParameters;
	}

	/**
	 * Factory method for create a new {@link FiltersType} from the given includes.
	 *
	 * @param includes the includes
	 * @return the new {@link FiltersType} with the given includes set.
	 */
	public static FiltersType newCipherSuitesFilter(String... includes) {
		FiltersType filtersType = new FiltersType();
		for (String include : includes) {
			filtersType.getInclude().add(include);
		}
		return filtersType;
	}

	/**
	 * Factory method for create a new {@link FiltersType} with the default includes.
	 *
	 * @return the new {@link FiltersType} with the default includes set.
	 */
	public static FiltersType newCipherSuitesFilter() {
		return newCipherSuitesFilter(DEFAULT_FILTERS_TYPE_INCLUDES);
	}

}
