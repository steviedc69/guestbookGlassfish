/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package json;

import domain.Message;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Steven
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class MessageListWriter implements MessageBodyWriter<List<Message>>{

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        if(!List.class.isAssignableFrom(type))
        {
            return false;
        }
        
          if (genericType instanceof ParameterizedType) {
            Type[] arguments = ((ParameterizedType) genericType).getActualTypeArguments();
            return arguments.length == 1 && arguments[0].equals(Message.class);
        } else {
            return false;
        }
    }
    

    @Override
    public long getSize(List<Message> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(List<Message> messages, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        
        
        JsonArrayBuilder jsonMessages = Json.createArrayBuilder();
        
        for(Message m : messages)
        {
            JsonObjectBuilder jsonMessage = Json.createObjectBuilder();
            jsonMessage.add("id", m.getId());
            //jsonMessage.add("date", m.getDate().getTime());
            jsonMessage.add("name", m.getName());
            jsonMessage.add("message", m.getMessage());
            jsonMessages.add(jsonMessage);
        }
        try(JsonWriter out = Json.createWriter(entityStream))
        {
            out.writeArray(jsonMessages.build());
        }
                
    }
    
    
    
}
