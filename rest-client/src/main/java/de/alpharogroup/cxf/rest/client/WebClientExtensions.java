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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.jsse.TLSServerParameters;
import org.apache.cxf.configuration.security.ClientAuthentication;
import org.apache.cxf.configuration.security.FiltersType;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import de.alpharogroup.crypto.ssl.KeyTrustExtensions;

/**
 * The class {@link WebClientExtensions} holds factory methods for server and client parameters.
 */
public class WebClientExtensions
{

	/** The Constant DEFAULT_FILTERS_TYPE_EXCLUDES. */
	public static final String[] DEFAULT_FILTERS_TYPE_EXCLUDES = { ".*_DH_anon_.*" };

	/** The Constant DEFAULT_FILTERS_TYPE_INCLUDES. */
	public static final String[] DEFAULT_FILTERS_TYPE_INCLUDES = { ".*_EXPORT_.*",
			".*_EXPORT1024_.*", ".*_WITH_DES_.*", ".*_WITH_AES_.*", ".*_WITH_NULL_.*" };

	/**
	 * Factory method for create a new {@link FiltersType} with the default includes and excludes.
	 *
	 * @return the new {@link FiltersType} with the default includes set.
	 */
	public static FiltersType newCipherSuitesFilter()
	{
		return newCipherSuitesFilter(DEFAULT_FILTERS_TYPE_INCLUDES, DEFAULT_FILTERS_TYPE_EXCLUDES);
	}

	/**
	 * Factory method for create a new {@link FiltersType} from the given includes and excludes.
	 *
	 * @param includes
	 *            the includes
	 * @param excludes
	 *            the excludes
	 * @return the new {@link FiltersType} with the given includes set.
	 */
	public static FiltersType newCipherSuitesFilter(String[] includes, String[] excludes)
	{
		final FiltersType filtersType = new FiltersType();
		for (final String include : includes)
		{
			filtersType.getInclude().add(include);
		}
		for (final String exclude : excludes)
		{
			filtersType.getExclude().add(exclude);
		}
		return filtersType;
	}

	/**
	 * Factory method for create a new {@link ClientAuthentication} from the given parameters.
	 *
	 * @param want
	 *            the want
	 * @param required
	 *            the required
	 * @return the new {@link ClientAuthentication} from the given parameters.
	 */
	public static ClientAuthentication newClientAuthentication(boolean want, boolean required)
	{
		final ClientAuthentication clientAuthentication = new ClientAuthentication();
		clientAuthentication.setWant(want);
		clientAuthentication.setRequired(required);
		return clientAuthentication;
	}

	/**
	 * Factory method for create a new {@link HTTPClientPolicy} object from the given parameters.
	 *
	 * @param connectionTimeout
	 *            the connection timeout
	 * @param allowChunking
	 *            the allow chunking
	 * @param receiveTimeout
	 *            the receive timeout
	 * @return the new {@link HTTPClientPolicy}.
	 */
	public static HTTPClientPolicy newHTTPClientPolicy(Long connectionTimeout,
		boolean allowChunking, Long receiveTimeout)
	{
		final HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(connectionTimeout);
		httpClientPolicy.setAllowChunking(allowChunking);
		httpClientPolicy.setReceiveTimeout(receiveTimeout);
		return httpClientPolicy;
	}

	/**
	 * Factory method for create a new {@link JAXRSServerFactoryBean} from the given parameters.
	 * Service and resource classes have to be added.
	 *
	 * @param serverConfigFile
	 *            the server config file
	 * @param baseUrl
	 *            the base url
	 * @return the new {@link JAXRSServerFactoryBean} from the given parameters.
	 */
	public static JAXRSServerFactoryBean newJAXRSServerFactoryBean(String serverConfigFile,
		String baseUrl)
	{
		final JAXRSServerFactoryBean bean = new JAXRSServerFactoryBean();
		final SpringBusFactory bf = new SpringBusFactory();
		final Bus bus = bf.createBus(serverConfigFile);
		bean.setBus(bus);
		bean.setAddress(baseUrl);
		return bean;
	}

	/**
	 * Factory method for create a new {@link TLSServerParameters} from the given parameters.
	 *
	 * @param keystoreDir
	 *            the keystore dir
	 * @param keystoreFilename
	 *            the keystore filename
	 * @param keystoreType
	 *            the keystore type
	 * @param keystorePassword
	 *            the keystore password
	 * @return the new {@link TLSServerParameters}.
	 * @throws UnrecoverableKeyException
	 *             the unrecoverable key exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws CertificateException
	 *             the certificate exception
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws KeyStoreException
	 *             the key store exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static TLSClientParameters newTLSClientParameters(File keystoreDir,
		String keystoreFilename, String keystoreType, String keystorePassword)
		throws UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException,
		FileNotFoundException, KeyStoreException, IOException
	{
		final File keystoreFile = new File(keystoreDir, keystoreFilename);
		final File trustManagersKeystoreFile = keystoreFile;
		final String trustManagerAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
		final String trustManagersKeystoreType = keystoreType;
		final String trustManagersKeystorePassword = keystorePassword;
		final boolean disableCNCheck = true;
		final String keyManagerAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
		final File keyManagersKeystoreFile = keystoreFile;
		final String keyManagersKeystoreType = keystoreType;
		final String keyManagersKeystorePassword = keystorePassword;
		final FiltersType cipherSuitesFilter = WebClientExtensions.newCipherSuitesFilter();
		final TLSClientParameters tlsClientParameters = WebClientExtensions.newTLSClientParameters(
			trustManagersKeystoreFile, trustManagerAlgorithm, trustManagersKeystoreType,
			trustManagersKeystorePassword, keyManagersKeystoreFile, keyManagerAlgorithm,
			keyManagersKeystoreType, keyManagersKeystorePassword, cipherSuitesFilter,
			disableCNCheck);
		return tlsClientParameters;
	}

	/**
	 * Factory method for create a new {@link TLSClientParameters} from the given parameters.
	 *
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
	 * @return the new {@link TLSClientParameters}.
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
	public static TLSClientParameters newTLSClientParameters(File trustManagersKeystoreFile,
		String trustManagerAlgorithm, String trustManagersKeystoreType,
		String trustManagersKeystorePassword, File keyManagersKeystoreFile,
		String keyManagerAlgorithm, String keyManagersKeystoreType,
		String keyManagersKeystorePassword, FiltersType cipherSuitesFilter, boolean disableCNCheck)
		throws NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException,
		KeyStoreException, UnrecoverableKeyException
	{
		final TLSClientParameters tlsClientParameters = new TLSClientParameters();
		tlsClientParameters.setDisableCNCheck(disableCNCheck);
		final TrustManager[] tm = KeyTrustExtensions.resolveTrustManagers(trustManagersKeystoreType,
			trustManagersKeystorePassword, trustManagersKeystoreFile, trustManagerAlgorithm);
		tlsClientParameters.setTrustManagers(tm);
		final KeyManager[] km = KeyTrustExtensions.resolveKeyManagers(keyManagersKeystoreType,
			keyManagersKeystorePassword, keyManagersKeystoreFile, keyManagerAlgorithm);
		tlsClientParameters.setKeyManagers(km);
		tlsClientParameters.setCipherSuitesFilter(cipherSuitesFilter);
		return tlsClientParameters;
	}

	/**
	 * Factory method for create a new {@link TLSServerParameters} from the given parameters.
	 *
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
	 * @param clientAuthentication
	 *            the client authentication
	 * @return the new {@link TLSServerParameters}.
	 * @throws KeyStoreException
	 *             the key store exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws CertificateException
	 *             the certificate exception
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws UnrecoverableKeyException
	 *             the unrecoverable key exception
	 */
	public static TLSServerParameters newTLSServerParameters(File trustManagersKeystoreFile,
		String trustManagerAlgorithm, String trustManagersKeystoreType,
		String trustManagersKeystorePassword, File keyManagersKeystoreFile,
		String keyManagerAlgorithm, String keyManagersKeystoreType,
		String keyManagersKeystorePassword, FiltersType cipherSuitesFilter,
		ClientAuthentication clientAuthentication)
		throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
		FileNotFoundException, IOException, UnrecoverableKeyException
	{
		final TLSServerParameters tlsServerParameters = new TLSServerParameters();
		final TrustManager[] tm = KeyTrustExtensions.resolveTrustManagers(trustManagersKeystoreType,
			trustManagersKeystorePassword, trustManagersKeystoreFile, trustManagerAlgorithm);
		tlsServerParameters.setTrustManagers(tm);
		final KeyManager[] km = KeyTrustExtensions.resolveKeyManagers(keyManagersKeystoreType,
			keyManagersKeystorePassword, keyManagersKeystoreFile, keyManagerAlgorithm);
		tlsServerParameters.setKeyManagers(km);
		tlsServerParameters.setCipherSuitesFilter(cipherSuitesFilter);
		tlsServerParameters.setClientAuthentication(clientAuthentication);
		return tlsServerParameters;
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
		String trustManagerAlgorithm, String trustManagersKeystoreType,
		String trustManagersKeystorePassword, File keyManagersKeystoreFile,
		String keyManagerAlgorithm, String keyManagersKeystoreType,
		String keyManagersKeystorePassword, FiltersType cipherSuitesFilter, boolean disableCNCheck)
		throws NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException,
		KeyStoreException, UnrecoverableKeyException
	{

		final ClientConfiguration config = WebClient.getConfig(client);
		final HTTPConduit httpConduit = config.getHttpConduit();

		final TLSClientParameters tlsParams = newTLSClientParameters(trustManagersKeystoreFile,
			trustManagerAlgorithm, trustManagersKeystoreType, trustManagersKeystorePassword,
			keyManagersKeystoreFile, keyManagerAlgorithm, keyManagersKeystoreType,
			keyManagersKeystorePassword, cipherSuitesFilter, disableCNCheck);
		new TLSClientParameters();

		httpConduit.setTlsClientParameters(tlsParams);
	}

	/**
	 * Sets a mock client authentication to the given client.
	 *
	 * @param client
	 *            the new mock client authentication
	 */
	public static void setMockClientAuthentication(Object client)
	{
		final ClientConfiguration config = WebClient.getConfig(client);
		// trust all certs...
		final HTTPConduit conduit = config.getHttpConduit();

		TLSClientParameters params = conduit.getTlsClientParameters();

		if (params == null)
		{
			params = new TLSClientParameters();

			params.setTrustManagers(new TrustManager[] { new MockX509TrustManager() });
			params.setDisableCNCheck(true);
			conduit.setTlsClientParameters(params);
		}
	}

	/**
	 * Sets the TLS client parameters.
	 *
	 * @param client
	 *            the client
	 * @param tlsClientParameters
	 *            the tls client parameters
	 */
	public static void setTLSClientParameters(Object client,
		TLSClientParameters tlsClientParameters)
	{
		final ClientConfiguration config = WebClient.getConfig(client);
		final HTTPConduit conduit = config.getHttpConduit();

		final HTTPClientPolicy httpClientPolicy = conduit.getClient();
		if (httpClientPolicy == null)
		{
			conduit.setClient(newHTTPClientPolicy(36000l, false, 32000l));
		}

		conduit.setTlsClientParameters(tlsClientParameters);
	}
}
