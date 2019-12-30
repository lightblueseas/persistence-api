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
package de.alpharogroup.service.rs.filter;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.annotation.Priority;
import javax.net.ssl.SSLException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import de.alpharogroup.service.rs.annotations.Securable;
import de.alpharogroup.service.rs.enums.AuthenticationScheme;

/**
 * The class {@link AuthenticationFilter} authenticates given tokens from request of users or
 * accounts. <br>
 * <br>
 * An authentication scheme based on tokens follow these steps:
 * <ol>
 * <li>The client sends their credentials (username and password) to the server.
 * <li>The server authenticates the credentials and, if they are valid, generate a token for the
 * user.
 * <li>The server stores the previously generated token in some storage along with the user
 * identifier and an expiration date.
 * <li>The server sends the generated token to the client.
 * <li>The client sends the token to the server in each request.
 * <li>The server, in each request, extracts the token from the incoming request. With the token,
 * the server looks up the user details to perform authentication.
 * <ul>
 * <li>If the token is valid, the server accepts the request.</li>
 * <li>If the token is invalid, the server refuses the request.</li>
 * </ul>
 * </li>
 * <li>Once the authentication has been performed, the server performs authorization.
 * <li>The server can provide an endpoint to refresh tokens.
 * </ol>
 * Note: The step 3 is not required if the server has issued a signed token (such as JWT, which
 * allows you to perform stateless authentication).
 *
 * Note: also see <a href=
 * "https://stackoverflow.com/questions/26777083/best-practice-for-rest-token-based-authentication-with-jax-rs-and-jersey">Best
 * practice for REST token-based authentication with JAX-RS and Jersey</a>
 */
@Securable
@Provider
@Priority(Priorities.AUTHENTICATION)
public abstract class AuthenticationFilter implements ContainerRequestFilter
{

	/** The application and request URI information. */
	@Context
	private UriInfo info;

	/** The resource info. */
	@Context
	private ResourceInfo resourceInfo;

	/** The servlet request. */
	@Context
	private HttpServletRequest servletRequest;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException
	{
		// check the request url path, if it is a sign in request
		try
		{
			if (isSigninRequest(requestContext))
			{
				// ignore them
				return;
			}
			// check if the resource is protected
			if (isSecured())
			{
				// Get the HTTP Authorization header from the request
				String authorizationHeader = requestContext
					.getHeaderString(HttpHeaders.AUTHORIZATION);

				// Check if the HTTP Authorization header is present
				if (authorizationHeader == null)
				{
					throw new NotAuthorizedException("Authorization header must be provided");
				}

				// Extract the token from the HTTP Authorization header
				String token = authorizationHeader.substring("Bearer".length()).trim();

				// Validate the token
				String username = onValidateToken(token);
				requestContext.setSecurityContext(newSecurityContext(username));
			}
		}
		catch (Exception e)
		{
			requestContext.abortWith(newFaultResponse());
		}
	}

	/**
	 * Checks if is the resourceClass is annotated with the annotation {@link Securable}.
	 *
	 * @return true, if is the resourceClass is annotated with the annotation {@link Securable}.
	 */
	protected boolean isSecured()
	{
		Class<?> resourceClass = resourceInfo.getResourceClass();
		Securable securable = resourceClass.getAnnotation(Securable.class);
		if (securable != null)
		{
			return true;
		}
		Method method = resourceInfo.getResourceMethod();
		securable = method.getAnnotation(Securable.class);
		boolean secured = securable != null;
		return secured;
	}

	/**
	 * Checks if the given path is a sign in path. Overwrite this method to provide specific sign in
	 * path for your application.
	 *
	 * @param path
	 *            the sign in path to check.
	 * @return true, if the given path is a sign in path otherwise false.
	 */
	protected boolean isSigninPath(String path)
	{
		boolean isSigninPath = path.equals("auth/credentials") || path.equals("auth/form");
		return isSigninPath;
	}

	/**
	 * Checks if the current request is a is a sign request.
	 *
	 * @param requestContext
	 *            the request context
	 * @return true, if the current request is a is a sign request.
	 * @throws Exception
	 *             occurs if some error like the scheme is not https
	 */
	protected boolean isSigninRequest(ContainerRequestContext requestContext) throws Exception
	{
		boolean isSigninRequest = false;
		String path = info.getPath();
		// check the request url path, if it is a sign in request
		if (isSigninPath(path))
		{
			// check if scheme is https
			if (!isSecureRequest(servletRequest))
			{
				throw new SSLException("use https scheme");
			}
			isSigninRequest = true;
		}
		return isSigninRequest;
	}

	/**
	 * Checks if the current request is a secure request, means that the scheme is https
	 *
	 * @param request the request
	 * @return true, if is secure request
	 */
	protected boolean isSecureRequest(final HttpServletRequest request){
		return request.isSecure();
	}

	/**
	 * Factory callback method for create a new authentication scheme for the header key
	 * 'WWW-Authenticate'. Overwrite to set specific application authentication scheme.
	 *
	 * @return the new authentication scheme.
	 */
	protected String newAuthenticationScheme()
	{
		return AuthenticationScheme.BASIC.getValue();
	}

	/**
	 * Factory callback method for create a new {@link Response} with a 401 status code
	 *
	 * @return the new fault {@link Response} object
	 */
	protected Response newFaultResponse()
	{
		Response faultResponse = Response.status(Response.Status.UNAUTHORIZED)
			.header(HttpHeaders.WWW_AUTHENTICATE,
				newAuthenticationScheme() + " realm=\"" + newRealmValue() + "\"")
			.build();
		return faultResponse;
	}

	/**
	 * Factory callback method for create a new realm value for the header key 'WWW-Authenticate'.
	 * Overwrite to set specific application realm value.
	 *
	 * @return the new realm value.
	 */
	protected String newRealmValue()
	{
		return "alpharogroup.de";
	}

	/**
	 * Factory method for create a new security context with the given user name.
	 *
	 * @param username
	 *            the user name
	 * @return the security context
	 */
	protected SecurityContext newSecurityContext(final String username)
	{
		return new AuthenticationSecurityContext(username);
	}

	/**
	 * Abstract callback method that checks if the given token is valid. For instance if it is not
	 * expired.
	 *
	 * @param token
	 *            the token
	 * @return the string the user name
	 * @throws Exception
	 *             if the token is not valid
	 */
	protected abstract String onValidateToken(String token) throws Exception;

}