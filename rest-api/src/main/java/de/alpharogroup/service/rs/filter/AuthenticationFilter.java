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

import de.alpharogroup.service.rs.Securable;

/**
 * The class {@link AuthenticationFilter}.
 */
@Securable
@Provider
@Priority(Priorities.AUTHENTICATION)
public abstract class AuthenticationFilter implements ContainerRequestFilter {

	/** The resource info. */
	@Context
	private ResourceInfo resourceInfo;

	/** The servlet request. */
	@Context
	private HttpServletRequest servletRequest;

	/** The application and request URI information. */
	@Context
	private UriInfo info;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		if (isSigninRequest(requestContext)) {
			// ignore them
			return;
		}
		// check if the resource is should be protected
		isSecuredRequest(requestContext);
	}

	/**
	 * Checks if the current request is a is a secured request. This is the case
	 * if the rest method is annotated with the annotation {@link Securable}.
	 *
	 * @param requestContext
	 *            the request context
	 * @return true, if is secured request
	 */
	protected boolean isSecuredRequest(ContainerRequestContext requestContext) {
		boolean isSecuredRequest = false;
		if (isSecured()) {
			isSecuredRequest = true;
			// Get the HTTP Authorization header from the request
			String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

			// Check if the HTTP Authorization header is present
			if (authorizationHeader == null) {
				throw new NotAuthorizedException("Authorization header must be provided");
			}

			// Extract the token from the HTTP Authorization header
			String token = authorizationHeader.substring("Bearer".length()).trim();

			try {
				// Validate the token
				String username = onValidateToken(token);
				requestContext.setSecurityContext(newSecurityContext(username));
			} catch (Exception e) {
				requestContext.abortWith(newFaultResponse());
			}
		}
		return isSecuredRequest;
	}

	/**
	 * Checks if the current request is a is a sign request.
	 *
	 * @param requestContext
	 *            the request context
	 */
	protected boolean isSigninRequest(ContainerRequestContext requestContext) {
		boolean isSigninRequest = false;
		String path = info.getPath();
		// check the request url path, if it is a sign in request
		if (isSigninPath(path)) {
			// check if scheme is https
			if (!servletRequest.isSecure()) {
				requestContext.abortWith(newFaultResponse());
			}
			isSigninRequest = true;
		}
		return isSigninRequest;
	}

	/**
	 * Checks if the given path is a sign in path. Overwrite this method to
	 * provide specific sign in path for your application.
	 *
	 * @param path
	 *            the sign in path to check.
	 * @return true, if the given path is a sign in path otherwise false.
	 */
	protected boolean isSigninPath(String path) {
		boolean isSigninPath = path.equals("auth/credentials") || path.equals("auth/form");
		return isSigninPath;
	}

	/**
	 * Checks if is secured.
	 *
	 * @param resourceClass
	 *            the resource class
	 * @return true, if is secured
	 */
	protected boolean isSecured() {
		Class<?> resourceClass = resourceInfo.getResourceClass();
		Securable securable = resourceClass.getAnnotation(Securable.class);
		if (securable != null) {
			return true;
		}
		Method method = resourceInfo.getResourceMethod();
		securable = method.getAnnotation(Securable.class);
		boolean secured = securable != null;
		return secured;
	}

	/**
	 * Abstract callback method that checks if the given token is valid. For
	 * instance if it is not expired.
	 *
	 * @param token
	 *            the token
	 * @return the string the user name
	 * @throws Exception
	 *             if the token is not valid
	 */
	protected abstract String onValidateToken(String token) throws Exception;

	/**
	 * Factory callback method for create a new {@link Response}.
	 *
	 * @return the new fault response
	 */
	protected Response newFaultResponse() {
		Response faultResponse = Response.status(Response.Status.UNAUTHORIZED)
				.header("WWW-Authenticate", "Basic realm=\"" + newRealmValue() + "\"").build();
		return faultResponse;
	}

	/**
	 * Factory callback method for create a new realm value for the header key
	 * 'WWW-Authenticate'. Overwrite to set specific application realm value.
	 *
	 * @return the new realm value.
	 */
	protected String newRealmValue() {
		return "alpharogroup.de";
	}

	/**
	 * Factory method for create a new security context with the given user
	 * name.
	 *
	 * @param username
	 *            the user name
	 * @return the security context
	 */
	protected SecurityContext newSecurityContext(final String username) {
		return new AuthenticationSecurityContext(username);
	}

}