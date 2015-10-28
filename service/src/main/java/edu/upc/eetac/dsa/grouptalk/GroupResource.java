package edu.upc.eetac.dsa.grouptalk;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;

/**
 * Created by Hp on 2015-10-28.
 */
@Path("groups")
public class GroupResource {
    @RolesAllowed("administrator")

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GrouptalkMediaType.GROUPTALK_AUTH_TOKEN)
    public Response createGroup()throws URISyntaxException {

    }

}
