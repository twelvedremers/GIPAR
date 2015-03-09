
package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.annotation.EnumValue;
import com.avaje.ebean.*;

@Embeddable
public class Recursos_PK extends Model{
    
    @Column(columnDefinition ="VARCHAR(50)")
   public String recurso;
  
   public int proyecto_id;
  
    @Override
    public int hashCode() {
        return recurso.length()+proyecto_id;
    }


    public Recursos_PK(String recurso,int b){

    	this.recurso=recurso;
    	proyecto_id=b;


    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) 
            return true;
        Recursos_PK b = (Recursos_PK)obj;
        if(b==null)
            return false;
        if (b.proyecto_id==proyecto_id && b.recurso==recurso)
            return true;
        
        return false;
    }

}
