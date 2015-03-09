package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Asistencia_Proyecto extends Model { 

   @EmbeddedId
   public Asistencia1_pk clave ;

    @ManyToOne
    @JoinColumn(name = "miembro_id", insertable = false, updatable = false , nullable=false)
    public Miembro miembro;

    @ManyToOne
    @JoinColumn(name = "proyecto_id", insertable = false, updatable = false, nullable=false)
    public  Proyecto proyecto;

   @Column(nullable=false)
   public boolean Estado_M;


   public String estado(){

   	if(Estado_M)
   		return "participo";
   	else
   		return "no participo";
   }

    public String estadoC(){

   	if(Estado_M)
   		return "green";
   	else
   		return "red";
   }
   
   public Asistencia_Proyecto(Miembro miembro,Proyecto proyecto){

   this.miembro=miembro;
   this.proyecto=proyecto;
   clave=new Asistencia1_pk(miembro.cod_G,proyecto.id);
   Estado_M=true;
   }





    public static List<Miembro> buscarAsistencia(Proyecto proyecto){
       
       List<Miembro> personas=new ArrayList<Miembro>();
      for ( Asistencia_Proyecto a : Asistencia_Proyecto.find.where().eq("proyecto_id",proyecto.id).findList()) {
       	personas.add(a.miembro);
       } 

    return personas;
    }

    public static List<Asistencia_Proyecto> buscarAsistenciaT(Proyecto proyecto){
    return Asistencia_Proyecto.find.where().eq("proyecto_id",proyecto.id+"").findList();
    }


   
      public static Integer numeroA(Proyecto proyecto){
       
      List<Miembro> miembros=buscarAsistencia(proyecto);

      return miembros.size();
    }

    public static Integer numeroFA(Proyecto proyecto){
      List<Miembro> miembros=buscarAsistencia(proyecto);
      int n=0;

      for ( Miembro a: miembros) {
      	if(a.estado)
      		n++;
      	
      }
      return n;
    }



	 //Generic query helper for entity with id Long
  public static Model.Finder<Asistencia1_pk,Asistencia_Proyecto> find = new Model.Finder<Asistencia1_pk,Asistencia_Proyecto>(Asistencia1_pk.class, Asistencia_Proyecto.class);

}