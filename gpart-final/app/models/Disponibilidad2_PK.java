

package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.annotation.EnumValue;
import com.avaje.ebean.*;

@Embeddable
public class Disponibilidad2_PK extends Model{

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
    
    @Column(name="cod_G")
    public int miembro;

    @Column(name="dia")
    public Dias dia;

    @Column(name="hora" ,columnDefinition ="int")
    @Constraints.Min(0)
    @Constraints.Max(24)
    public int hora;

    public Disponibilidad2_PK(Dias dia, int hora,int miembro){
       this.dia = dia;
       this.hora = hora;
       this.miembro=miembro;
    } 
    

    

    @Override
    public int hashCode() {
        return dia.ordinal()+ hora + miembro;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) 
            return true;
        Disponibilidad2_PK b = (Disponibilidad2_PK)obj;
        if(b==null)
            return false;
        if (b.dia.ordinal()==dia.ordinal() && b.hora==hora && b.miembro==miembro)
            return true;
        
        return false;
    }

}
