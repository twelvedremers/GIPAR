
package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.annotation.EnumValue;
import com.avaje.ebean.*;

@Embeddable
public class Asistencia1_pk extends Model{
    
   public int miembro_id;
  
   public int proyecto_id;
  
    @Override
    public int hashCode() {
        return miembro_id+proyecto_id;
    }


    public Asistencia1_pk(int a, int b){
    miembro_id=a;
    proyecto_id=b;


    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) 
            return true;
        Asistencia1_pk b = (Asistencia1_pk)obj;
        if(b==null)
            return false;
        if (b.proyecto_id==proyecto_id && b.miembro_id==miembro_id)
            return true;
        
        return false;
    }

}
