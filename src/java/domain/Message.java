/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import resources.MessageValid;
import resources.NameValid;

/**
 *
 * @author Steven
 */
@Entity
@Table(name = "TBL_MESSAGES")
@NamedQueries({
    @NamedQuery(name = "Message.findAll",query = "SELECT m FROM Message m")
        
})
public class Message {
    
    @Id
    @GeneratedValue
    private int id;
    
    
    //private Date date;
    @NameValid(message = "Naam mag niet leeg zijn")
    private String name;
    @MessageValid(message="Uw bericht mag niet leeg zijn")
    private String message;
    
    public Message()
    {
        
    }
   /* public Message(String name, String message)
    {
       date = new Date(System.currentTimeMillis()); 
    }
/
    public void setDate(Date date) {
        this.date = date;
    }
*/
    public int getId() {
        return id;
    }

  /*  public Date getDate() {
        return date;
    }
*/
    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Message other = (Message) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}
