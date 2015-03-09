

package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.annotation.EnumValue;
import com.avaje.ebean.*;

@Embeddable
public class Telefonos_PK extends Model{

    @Constraints.MaxLength(20)
    @Constraints.MinLength(10)
    public String telefono;

    
    public int cod_G;
    

    public Telefonos_PK(String telf , int cod){
    this.telefono=telf;
    this.cod_G=cod;



    }
  
    @Override
    public int hashCode() {
        return telefono.length()+ cod_G;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) 
            return true;
        Telefonos_PK b = (Telefonos_PK)obj;
        if(b==null)
            return false;
        if (b.telefono.length()==telefono.length() && b.cod_G==cod_G)
            return true;
        
        return false;
    }

}
