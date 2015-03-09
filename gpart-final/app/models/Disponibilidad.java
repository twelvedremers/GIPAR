
package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Disponibilidad extends Model {
 
 @EmbeddedId 
 public Disponibilidad2_PK disponibilidad;

 @ManyToOne
@JoinColumn(name = "cod_G", insertable = false, updatable = false , nullable=false)
 public Miembro miembro;

 public Disponibilidad(String dia,int hora,Miembro nuevo){

 	 Disponibilidad2_PK.Dias dias=null;
        	if(dia.compareTo("Lunes")==0){
             dias=Disponibilidad2_PK.Dias.Lunes;
        	}else if(dia.compareTo("Martes")==0){
             dias=Disponibilidad2_PK.Dias.Martes;
        	}else if(dia.compareTo("Miercoles")==0){
             dias=Disponibilidad2_PK.Dias.Miercoles;
        	}else if(dia.compareTo("Jueves")==0){
             dias=Disponibilidad2_PK.Dias.Jueves;
        	}else
             dias=Disponibilidad2_PK.Dias.Viernes;

 	disponibilidad=new Disponibilidad2_PK(dias,hora,nuevo.cod_G);
 	miembro=nuevo;


 }
   
   public static List<Disponibilidad> findDisponibilidad( Miembro miembro) {
    return find.where().eq("miembro", miembro).findList();
     }
    // Generic query helper for entity with id Long
    public static Model.Finder<Disponibilidad2_PK,Disponibilidad> find = new Model.Finder<Disponibilidad2_PK,Disponibilidad>(Disponibilidad2_PK.class, Disponibilidad.class);

}
