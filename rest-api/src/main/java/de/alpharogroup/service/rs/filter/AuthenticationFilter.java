package de.alpharogroup.service.rs.filter;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.apache.poi.ss.formula.functions.FinanceLib;

import java.security.Principal;

import de.alpharogroup.service.rs.Securable;

/**
 * The class {@link AuthenticationFilter}.
 */
@Securable
@Provider
@Priority(Priorities.AUTHENTICATION)
public abstract class AuthenticationFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

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
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}

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
	 * Factory method for create a new security context with the given user
	 * name.
	 *
	 * @param username
	 *            the user name
	 * @return the security context
	 */
	protected SecurityContext newSecurityContext(final String username) {
		return new SecurityContext() {

			@Override
			public Principal getUserPrincipal() {

				return new Principal() {

					@Override
					public String getName() {
						return username;
					}
				};
			}

			@Override
			public boolean isUserInRole(final String role) {
				return true;
			}

			@Override
			public boolean isSecure() {
				return false;
			}

			@Override
			public String getAuthenticationScheme() {
				return null;
			}
		};
	}

}