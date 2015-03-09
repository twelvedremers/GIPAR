

package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.annotation.EnumValue;
import com.avaje.ebean.*;

@Embeddable
public class Disponibilidad_PK extends Model{

    public enum Dias {
         
         @EnumValue("Lun")
         Lunes,
         
         @EnumValue("Mar")
         Martes,
         
         @EnumValue("Mie")
         Miercoles,

         @EnumValue("Jue")
         Jueves,
         
         @EnumValue("Vie")
         Viernes,

     }
    
    @Column(name="dia")
    public Dias dia;

    @Column(name="hora" ,columnDefinition ="int")
    @Constraints.Min(0)
    @Constraints.Max(23)
    public int hora;

    public Disponibilidad_PK(Dias dia, int hora){
       this.dia = dia;
       this.hora = hora;
    } 

    @Override
    public int hashCode() {
        return dia.ordinal()+ hora;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) 
            return true;
        Disponibilidad_PK b = (Disponibilidad_PK)obj;
        if(b==null)
            return false;
        if (b.dia.ordinal()==dia.ordinal() && b.hora==hora)
            return true;
        
        return false;
    }

}
