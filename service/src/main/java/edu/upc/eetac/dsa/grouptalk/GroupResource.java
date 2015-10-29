package edu.upc.eetac.dsa.grouptalk;

import edu.upc.eetac.dsa.grouptalk.dao.GroupDAO;
import edu.upc.eetac.dsa.grouptalk.dao.GroupDAOImpl;
import edu.upc.eetac.dsa.grouptalk.entity.AuthToken;
import edu.upc.eetac.dsa.grouptalk.entity.Group;
import edu.upc.eetac.dsa.grouptalk.entity.GroupCollection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;

import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by Hp on 2015-10-28.
 */
@Path("groups")
public class GroupResource {
    @Context
    private SecurityContext securityContext;
    @RolesAllowed("administrator")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GrouptalkMediaType.GROUPTALK_AUTH_TOKEN)
    public Response createGroup(@FormParam("theme")String theme, @FormParam("description")String description, @Context UriInfo uriInfo)throws URISyntaxException {
        if (theme == null || description == null)
            throw new BadRequestException("all parameters are mandatory");

        GroupDAO groupDAO = new GroupDAOImpl();
        Group group =null;

        AuthToken authenticationToken = null;

        try {
           group = groupDAO.createGroup(securityContext.getUserPrincipal().getName(), theme, description);
            // delete this line
            //group.setId("hhhh");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + group.getId());
        return Response.created(uri).type(GrouptalkMediaType.GROUPTALK_GROUP).entity(group).build();
    }









    /*@GET
    @Produces(GrouptalkMediaType.GROUPTALK_GROUP_COLLECTION)
    public GroupCollection getGroups(@QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
        GroupCollection groupCollection = null;
        GroupDAO groupDAO = new GroupDAOImpl();
        try {
            if (before && timestamp == 0) timestamp = System.currentTimeMillis();
            groupCollection = groupDAO.getGroups(timestamp, before);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return groupCollection;
    }*/


    @Path("/{id}")
    @GET
    @Produces(GrouptalkMediaType.GROUPTALK_GROUP)
    public Response getGroup(@PathParam("id") String id, @Context Request request) {
        // Create cache-control
        CacheControl cacheControl = new CacheControl();
        Group group = null;
        GroupDAO groupDAO = new GroupDAOImpl();
        try {
            group = groupDAO.getGroupById(id);
            if (group == null)
                throw new NotFoundException("Group with id = " + id + " doesn't exist");

            // Calculate the ETag on last modified date of user resource
            EntityTag eTag = new EntityTag(Long.toString(group.getLastModified()));

            // Verify if it matched with etag available in http request
            Response.ResponseBuilder rb = request.evaluatePreconditions(eTag);

            // If ETag matches the rb will be non-null;
            // Use the rb to return the response without any further processing
            if (rb != null) {
                return rb.cacheControl(cacheControl).tag(eTag).build();
            }

            // If rb is null then either it is first time request; or resource is
            // modified
            // Get the updated representation and return with Etag attached to it
            rb = Response.ok(group).cacheControl(cacheControl).tag(eTag);
            return rb.build();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }




    @Path("/{id}")
    @PUT
    @Consumes(GrouptalkMediaType.GROUPTALK_GROUP)
    @Produces(GrouptalkMediaType.GROUPTALK_GROUP)
    public Group updateUGroup(@PathParam("id") String id, Group group) {
        if(group == null)
            throw new BadRequestException("entity is null");
        if(!id.equals(group.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        String userid = securityContext.getUserPrincipal().getName();
        if(!userid.equals(group.getGroupid()))
            throw new ForbiddenException("operation not allowed");

        GroupDAO groupDAO = new GroupDAOImpl();
        try {
            group = groupDAO.updateGroup(id, group.getTheme(), group.getDescription());
            if(group == null)
                throw new NotFoundException("Group with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return group;
    }

    @Path("/{id}")
    @DELETE
    public void deleteGroup(@PathParam("id") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        GroupDAO groupDAO = new GroupDAOImpl();
        try {
            String ownerid = groupDAO.getGroupById(id).getGroupid();
            if(!userid.equals(ownerid))
                throw new ForbiddenException("operation not allowed");
            if(!groupDAO.deleteGroup(id))
                throw new NotFoundException("Group with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

}







