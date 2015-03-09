package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Evento extends Model { 

    @Id
    public int id_Evento;
 
   @JoinColumn(name = "id_Evento",insertable = false, updatable = false , nullable=false)
    @OneToOne(cascade=CascadeType.ALL)
    public Proyecto proyecto;
    
    public float costo_total;

   @Constraints.Required
   @Column(columnDefinition ="VARCHAR(80)",nullable=false)

   public String ubicacion;

     @OneToMany(mappedBy = "evento",cascade=CascadeType.ALL)
    public  List<Asistencia_Evento> asisEvento;

   
    public  boolean estado(){
 	 if(this.proyecto.estado==Proyecto.Estados.Activo)
 	 	return true;
 	 else
 	 	return false;

    }
   public Evento(Proyecto proyecto,String valor, String ubicacion){
   
   this.proyecto=proyecto;
   id_Evento=proyecto.id;
   costo_total= Float.parseFloat(valor);
   this.ubicacion=ubicacion;


   }





	// Generic query helper for entity with id Long
    public static Model.Finder<Integer,Evento> find = new Model.Finder<Integer,Evento>(Integer.class, Evento.class);

}