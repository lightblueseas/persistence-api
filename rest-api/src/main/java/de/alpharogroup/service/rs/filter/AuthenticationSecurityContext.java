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

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

/**
 * The class {@link AuthenticationSecurityContext} is a simple implementation of
 * the {@link SecurityContext} interface.
 */
public class AuthenticationSecurityContext implements SecurityContext {

	/** The username. */
	private final String username;

	/**
	 * Instantiates a new {@link AuthenticationSecurityContext}.
	 *
	 * @param username the username
	 */
	public AuthenticationSecurityContext(String username) {
		this.username = username;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAuthenticationScheme() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Principal getUserPrincipal() {

		return new Principal() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public String getName() {
				return username;
			}
		};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSecure() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isUserInRole(final String role) {
		return true;
	}

}
