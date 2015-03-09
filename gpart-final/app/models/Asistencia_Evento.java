package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Asistencia_Evento extends Model { 

   @EmbeddedId
   public Asistencia2_pk clave ;

    @ManyToOne
    @JoinColumn(name = "miembro_id", insertable = false, updatable = false , nullable=false)
    public Miembro miembro;

    @ManyToOne
    @JoinColumn(name = "evento_id", insertable = false, updatable = false, nullable=false)
    public Evento evento;
   
   @Column(nullable=false,columnDefinition ="VARCHAR(45)")
   @Constraints.Required
   @Constraints.MinLength(5)
    @Constraints.MaxLength(45)
   public String comision;


public Asistencia_Evento(Miembro miembro,Evento evento,String comision){

   this.miembro=miembro;
   this.evento=evento;
   clave=new Asistencia2_pk(miembro.cod_G,evento.id_Evento);
   this.comision=comision;
   }


 public static List<Miembro> buscarAsistencia(Proyecto proyecto){
       
       List<Miembro> personas=new ArrayList<Miembro>();
      for ( Asistencia_Evento a : Asistencia_Evento.find.where().eq("evento_id",proyecto.id).findList()) {
       	personas.add(a.miembro);
       } 
       return personas;
}



	 //Generic query helper for entity with id Long
  public static Model.Finder<Asistencia2_pk,Asistencia_Evento> find = new Model.Finder<Asistencia2_pk,Asistencia_Evento>(Asistencia2_pk.class, Asistencia_Evento.class);

}