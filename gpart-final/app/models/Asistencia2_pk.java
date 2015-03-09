package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.annotation.EnumValue;
import com.avaje.ebean.*;

@Embeddable
public class Asistencia2_pk extends Model{
    
   public int miembro_id;
  
   public int evento_id;
  
    @Override
    public int hashCode() {
        return miembro_id+evento_id;
    }
     public Asistencia2_pk(int a, int b){
    miembro_id=a;
    evento_id=b;


    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) 
            return true;
        Asistencia2_pk b = (Asistencia2_pk)obj;
        if(b==null)
            return false;
        if (b.evento_id==evento_id && b.miembro_id==miembro_id)
            return true;
        
        return false;
    }

}
