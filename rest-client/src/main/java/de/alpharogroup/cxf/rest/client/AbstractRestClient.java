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

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;

import de.alpharogroup.check.Check;
import de.alpharogroup.service.rs.providers.CustomTypeModulesRegistrationProvider;
import lombok.Getter;

/**
 * The class {@link AbstractRestClient}.
 */
public abstract class AbstractRestClient {

	/**
	 * The enum {@link Host}.
	 */
	enum Host {
	localhost;
	};

	/**
	 * The enum Scheme.
	 */
	enum Scheme {
		http, https
	}

	/** The Constant BASE_HTTP_URL_PREFIX. */
	public static final String BASE_HTTP_URL_PREFIX = Scheme.http.name() + "://" + Host.localhost.name();

	/** The Constant BASE_HTTPS_URL_PREFIX. */
	public static final String BASE_HTTPS_URL_PREFIX = Scheme.https.name() + "://" + Host.localhost.name();

	/** The Constant DEFAULT_BASE_URL. */
	public static final String DEFAULT_BASE_HTTP_URL = BASE_HTTP_URL_PREFIX + ":" + DefaultPort.HTTP.getPort();

	/** The Constant DEFAULT_BASE_URL. */
	public static final String DEFAULT_BASE_HTTPS_URL = BASE_HTTPS_URL_PREFIX + ":" + DefaultPort.HTTPS.getPort();

	/** The base url. */
	@Getter
	private final String baseUrl;

	/** The providers. */
	@Getter
	private final List<Object> providers;

	/**
	 * Instantiates a new {@link AbstractRestClient} with the default base url.
	 */
	public AbstractRestClient() {
		this(DEFAULT_BASE_HTTP_URL);
	}

	/**
	 * Instantiates a new {@link AbstractRestClient}.
	 *
	 * @param baseUrl the base url
	 */
	public AbstractRestClient(final String baseUrl) {
		Check.get().notEmpty(baseUrl, "baseUrl");
		this.baseUrl = baseUrl;
		this.providers = newProviders();
	}

	/**
	 * Factory callback method for the list of providers. This method is invoked in
	 * the constructor and can be overridden so users can add specific providers for
	 * the rest client.
	 *
	 * @return the list
	 */
	protected List<Object> newProviders() {
		final List<Object> providers = new ArrayList<>();
		providers.add(new CustomTypeModulesRegistrationProvider());
		return providers;
	}

	/**
	 * Generic factory method for create new rest resource for the rest client.
	 *
	 * @param               <T> the generic type
	 * @param resourceClass the resource class
	 * @return the t
	 */
	protected <T> T newResource(final Class<T> resourceClass) {
		final T resource = JAXRSClientFactory.create(getBaseUrl(), resourceClass, getProviders());
		WebClient.client(resource).accept(MediaType.APPLICATION_JSON);
		WebClient.client(resource).type(MediaType.APPLICATION_JSON);
		return resource;
	}

}
