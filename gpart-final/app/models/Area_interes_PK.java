
package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.annotation.EnumValue;
import com.avaje.ebean.*;

@Embeddable
public class Area_interes_PK extends Model{
    
    @Constraints.MaxLength(30)
    @Constraints.MinLength(5)
    @Column(columnDefinition ="VARCHAR(30)", nullable = false)
   public String area;
  
   public int miembro_id;

     public Area_interes_PK(String area , int cod){
    this.area=area;
    this.miembro_id=cod;
   }

  
    @Override
    public int hashCode() {
        return miembro_id+area.length();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) 
            return true;
        Area_interes_PK b = (Area_interes_PK)obj;
        if(b==null)
            return false;
        if (b.area==area && b.miembro_id==miembro_id)
            return true;
        
        return false;
    }

}
