/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package json;

import domain.Message;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Steven
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class MessageReader implements MessageBodyReader<Message>{

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Message.class.isAssignableFrom(type);
    }

    @Override
    public Message readFrom(Class<Message> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        
        try(JsonReader in = Json.createReader(entityStream))
        {
            JsonObject jsonMessage = in.readObject();
            Message message = new Message();
            message.setId(jsonMessage.getInt("id"));
            //message.setDate(new Date(jsonMessage.getJsonNumber("date").longValue()));
            message.setName(jsonMessage.getString("name",null));
            message.setMessage(jsonMessage.getString("message",null));
            
            return message;
        }
        catch(JsonException | ClassCastException ex)
        {
            throw new BadRequestException("JSON invoer verkeerd");
        }
    }
    
}
