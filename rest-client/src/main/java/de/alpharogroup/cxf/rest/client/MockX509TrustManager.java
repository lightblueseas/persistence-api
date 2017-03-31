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

import javax.net.ssl.TrustManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

/**
 * The class {@link MockX509TrustManager} is a mock implementation of the
 * {@link TrustManager} interface.
 */
public class MockX509TrustManager implements TrustManager {

	/**
	 * Does nothing.
	 *
	 * @param chain
	 *            the chain
	 * @param authType
	 *            the auth type
	 * @throws CertificateException
	 *             the certificate exception
	 */
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	/**
	 * Does nothing.
	 *
	 * @param chain
	 *            the chain
	 * @param authType
	 *            the auth type
	 * @throws CertificateException
	 *             the certificate exception
	 */
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	/**
	 * Gets the accepted issuers.
	 *
	 * @return 's an empty array
	 */
	public X509Certificate[] getAcceptedIssuers() {
		return new X509Certificate[] {};
	}
}