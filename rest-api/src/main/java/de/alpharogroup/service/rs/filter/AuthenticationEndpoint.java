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
public abstract class AuthenticationEndpoint {

    /**
     * Authenticate user.
     *
     * @param username the username
     * @param password the password
     * @return the response
     */
    @POST
    public Response authenticateUser(@FormParam("username") String username, 
                                     @FormParam("password") String password) {
        try {

            // Authenticate the given user 
            authenticate(username, password);

            // Create a new token for the given user
            String token = newToken(username);

            // Set the token on the response and return it
            return Response.ok(token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }      
    }

    /**
     * Authenticate against your user-management-system.
     *
     * @param username the username
     * @param password the password
     * @throws Exception is thrown if the credentials are not valid
     */
    protected abstract void authenticate(String username, String password) throws Exception;

    /**
     * Create a new token that is associated with the given username.
     *
     * @param username the username
     * @return the new token that is associated with the given username.
     */
    protected abstract String newToken(String username);
}