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

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/authentication")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public abstract class AuthenticationEndpoint
{

	/**
	 * Authenticate against your user-management-system.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @throws Exception
	 *             is thrown if the credentials are not valid
	 */
	protected abstract void authenticate(String username, String password) throws Exception;

	/**
	 * Authenticate user.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @return the response
	 */
	@POST
	public Response authenticateUser(@FormParam("username") String username,
		@FormParam("password") String password)
	{
		try
		{

			// Authenticate the given user
			authenticate(username, password);

			// Create a new token for the given user
			String token = newToken(username);

			// Set the token on the response and return it
			return Response.ok(token).build();

		}
		catch (Exception e)
		{
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	/**
	 * Create a new token that is associated with the given username.
	 *
	 * @param username
	 *            the username
	 * @return the new token that is associated with the given username.
	 */
	protected abstract String newToken(String username);
}