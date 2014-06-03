/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package resources;

import domain.Message;
import java.net.URI;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Steven
 */
@Path("messages")
@Transactional(dontRollbackOn = {BadRequestException.class, NotFoundException.class})
public class Messages {
    
    @PersistenceContext
    private EntityManager em;
    @Resource
    private Validator validator;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> getAllMessages(@QueryParam("first")@DefaultValue("0") int first,@QueryParam("results")@DefaultValue("10")int results)
    {
        TypedQuery<Message> mQuery = em.createNamedQuery("Message.findAll",Message.class);
        mQuery.setFirstResult(first);
        mQuery.setMaxResults(results);
        return mQuery.getResultList();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMessage(Message message)
    {
        if(em.find(Message.class, message.getId())!=null)
        {
            throw new BadRequestException("Boodschap bestaat al");
        }
        
        Set<ConstraintViolation<Message>> violations = validator.validate(message);
        if(!violations.isEmpty())
        {
            throw new BadRequestException("Boodschap is oncorrect");
        }
        em.persist(message);
        return Response.created(URI.create("/"+message.getId())).build();
    }
  
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Message getMessage(@PathParam("id")int id)
    {
        Message message = em.find(Message.class, id);
        if(message == null)
        {
            throw new NotFoundException("boodschap niet gevonden");
        }
        
        return message;
    }
            
    @Path("{id}")
    @DELETE
    public void removeMessage(@PathParam("id")int id)
    {
        Message message = em.find(Message.class, id);
        if(message == null)
        {
            throw new NotFoundException("boodschap niet gevonden");
        }
        em.remove(message);
    }
    
}
