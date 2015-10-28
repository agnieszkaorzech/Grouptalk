package edu.upc.eetac.dsa.grouptalk;


import edu.upc.eetac.dsa.grouptalk.GrouptalkMediaType;
import edu.upc.eetac.dsa.grouptalk.dao.StingDAO;
import edu.upc.eetac.dsa.grouptalk.dao.StingDAOImpl;
import edu.upc.eetac.dsa.grouptalk.entity.AuthToken;
import edu.upc.eetac.dsa.grouptalk.entity.Sting;
import edu.upc.eetac.dsa.grouptalk.entity.StingCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by Hp on 2015-10-16.
 */
@Path("stings")
public class StingResource {
    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GrouptalkMediaType.GROUPTALK_STING)
    public Response createSting(@FormParam("subject") String subject, @FormParam("content") String content, @Context UriInfo uriInfo) throws URISyntaxException {
        if (subject == null || content == null)
            throw new BadRequestException("all parameters are mandatory");
        StingDAO stingDAO = new StingDAOImpl();
        Sting sting = null;
        AuthToken authenticationToken = null;
        try {
            sting = stingDAO.createSting(securityContext.getUserPrincipal().getName(), subject, content);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + sting.getId());
        return Response.created(uri).type(GrouptalkMediaType.GROUPTALK_STING).entity(sting).build();
    }

    /*@GET
    @Produces(BeeterMediaType.BEETER_STING_COLLECTION)
    public StingCollection getStings(){
        StingCollection stingCollection = null;
        StingDAO stingDAO = new StingDAOImpl();
        try {
            stingCollection = stingDAO.getStings();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }

        return stingCollection;
    }*/

    /*@GET
    @Produces(BeeterMediaType.BEETER_STING_COLLECTION)
    public StingCollection getStings(@QueryParam("before")long before){
        StingCollection stingCollection = null;
        StingDAO stingDAO = new StingDAOImpl();
        try {
            if(before == 0) before = System.currentTimeMillis();
            stingCollection = stingDAO.getStings(before);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return stingCollection;
    }*/


    @GET
    @Produces(GrouptalkMediaType.GROUPTALK_STING_COLLECTION)
    public StingCollection getStings(@QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
        StingCollection stingCollection = null;
        StingDAO stingDAO = new StingDAOImpl();
        try {
            if (before && timestamp == 0) timestamp = System.currentTimeMillis();
            stingCollection = stingDAO.getStings(timestamp, before);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return stingCollection;
    }


    @Path("/{id}")
    @GET
    @Produces(GrouptalkMediaType.GROUPTALK_STING)
    public Response getSting(@PathParam("id") String id, @Context Request request) {
        // Create cache-control
        CacheControl cacheControl = new CacheControl();
        Sting sting = null;
        StingDAO stingDAO = new StingDAOImpl();
        try {
            sting = stingDAO.getStingById(id);
            if (sting == null)
                throw new NotFoundException("Sting with id = " + id + " doesn't exist");

            // Calculate the ETag on last modified date of user resource
            EntityTag eTag = new EntityTag(Long.toString(sting.getLastModified()));

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
            rb = Response.ok(sting).cacheControl(cacheControl).tag(eTag);
            return rb.build();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }



    /*@Path("/{id}")
    @GET
    @Produces(BeeterMediaType.BEETER_STING)
    public Sting getSting(@PathParam("id") String id){
        Sting sting = null;
        StingDAO stingDAO = new StingDAOImpl();
        try {
            sting = stingDAO.getStingById(id);
            if(sting == null)
                throw new NotFoundException("Sting with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return sting;
    }*/

    @Path("/{id}")
    @PUT
    @Consumes(GrouptalkMediaType.GROUPTALK_STING)
    @Produces(GrouptalkMediaType.GROUPTALK_STING)
    public Sting updateUSting(@PathParam("id") String id, Sting sting) {
        if(sting == null)
            throw new BadRequestException("entity is null");
        if(!id.equals(sting.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        String userid = securityContext.getUserPrincipal().getName();
        if(!userid.equals(sting.getUserid()))
            throw new ForbiddenException("operation not allowed");

        StingDAO stingDAO = new StingDAOImpl();
        try {
            sting = stingDAO.updateSting(id, sting.getSubject(), sting.getContent());
            if(sting == null)
                throw new NotFoundException("Sting with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return sting;
    }

    @Path("/{id}")
    @DELETE
    public void deleteSting(@PathParam("id") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        StingDAO stingDAO = new StingDAOImpl();
        try {
            String ownerid = stingDAO.getStingById(id).getUserid();
            if(!userid.equals(ownerid))
                throw new ForbiddenException("operation not allowed");
            if(!stingDAO.deleteSting(id))
                throw new NotFoundException("Sting with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
}