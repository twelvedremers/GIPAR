
package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.annotation.EnumValue;
import com.avaje.ebean.*;

@Embeddable
public class Area_articulo_PK  extends Model{
    

    @Constraints.MaxLength(20)
    @Constraints.MinLength(5)
    @Column(columnDefinition ="VARCHAR(20)", nullable = false)
   public String area;
  
   public int cod_Articulo;
  
    @Override
    public int hashCode() {
        return cod_Articulo+area.length();
    }


    public Area_articulo_PK(int a, String b){
area=b;
cod_Articulo=a;

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) 
            return true;
        Area_articulo_PK  b = (Area_articulo_PK )obj;
        if(b==null)
            return false;
        if (b.area==area && b.cod_Articulo==cod_Articulo)
            return true;
        
        return false;
    }

}
