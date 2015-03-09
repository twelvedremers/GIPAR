
package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.annotation.EnumValue;
import com.avaje.ebean.*;

@Embeddable
public class Puntos_PK extends Model{
    
   public String punto;
  
   public int reunion_id;
  
    @Override
    public int hashCode() {
        return punto.length()+reunion_id;
    }



   public Puntos_PK(String puntos, int reunion_id){
   this.punto=puntos;
   this.reunion_id=reunion_id;


   }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) 
            return true;
        Puntos_PK b = (Puntos_PK)obj;
        if(b==null)
            return false;
        if (b.reunion_id==reunion_id && b.punto==punto)
            return true;
        
        return false;
    }

}
