package de.alpharogroup.cxf.rest.client;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import de.alpharogroup.check.Check;
import lombok.Getter;

/**
 * The class {@link AbstractRestClient}.
 */
public abstract class AbstractRestClient
{

	/** The Constant DEFAULT_HTTP_PORT. */
	public static final int DEFAULT_HTTP_PORT = 8080;

	/** The Constant BASE_URL_PREFIX. */
	public static final String BASE_URL_PREFIX = "http://localhost";

	/** The Constant DEFAULT_BASE_URL. */
	public static final String DEFAULT_BASE_URL = BASE_URL_PREFIX + ":" + DEFAULT_HTTP_PORT;

	/** The base url. */
	@Getter
	private final String baseUrl;

	/** The providers. */
	@Getter
	private final List<Object> providers;

	/**
	 * Instantiates a new {@link AbstractRestClient} with the default base url.
	 */
	public AbstractRestClient()
	{
		this(DEFAULT_BASE_URL);
	}

	/**
	 * Instantiates a new {@link AbstractRestClient}.
	 *
	 * @param baseUrl
	 *            the base url
	 */
	public AbstractRestClient(final String baseUrl)
	{
		Check.get().notEmpty(baseUrl, "baseUrl");
		this.baseUrl = baseUrl;
		this.providers = newProviders();
	}

	/**
	 * Factory callback method for the list of providers. This method is
	 * invoked in the constructor and can be overridden so users can
	 * add specific providers for the rest client.
	 *
	 * @return the list
	 */
	protected List<Object> newProviders()
	{
		final List<Object> providers = new ArrayList<>();
		providers.add(new JacksonJsonProvider());
		return providers;
	}


	/**
	 * Generic factory method for create new rest resource for the rest client.
	 *
	 * @param <T> the generic type
	 * @param resourceClass the resource class
	 * @return the t
	 */
	protected <T> T newResource(final Class<T> resourceClass)
	{
		final T resource = JAXRSClientFactory.create(getBaseUrl(), resourceClass, getProviders());
		WebClient.client(resource).accept(MediaType.APPLICATION_JSON);
		WebClient.client(resource).type(MediaType.APPLICATION_JSON);
		return resource;
	}

}