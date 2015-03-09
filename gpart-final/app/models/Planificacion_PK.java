
package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.annotation.EnumValue;
import com.avaje.ebean.*;

@Embeddable
public class Planificacion_PK extends Model{
    
   public int  id_programacion;
  
   public int proyecto_id;
  
    @Override
    public int hashCode() {
        return id_programacion+proyecto_id;
    }

    public Planificacion_PK(int a, int b){

    	id_programacion=a;

        proyecto_id=b;


    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) 
            return true;
        Planificacion_PK b = (Planificacion_PK)obj;
        if(b==null)
            return false;
        if (b.proyecto_id==proyecto_id && b.id_programacion==id_programacion)
            return true;
        
        return false;
    }

}
