package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import play.data.*;
import com.avaje.ebean.*;
import java.sql.Timestamp;

@Entity 
public class Programacion extends Model { 

   @EmbeddedId
   public Planificacion_PK clave;

    @ManyToOne
    @JoinColumn(name = "proyecto_id", insertable = false, updatable = false , nullable=false)
    public Proyecto proyecto;
    
    @Constraints.Required
    @Formats.DateTime(pattern="dd-MM-yyyy-hh-mm" )
    @Column(columnDefinition ="DateTime", nullable = false)
    public Date fecha_programada_inicial;

    @Formats.DateTime(pattern="dd-MM-yyyy-hh-mm")
    @Column(columnDefinition ="DateTime", nullable = true)
    public Date fecha_programada_final;

    @Formats.DateTime(pattern="dd-MM-yyyy-hh-mm")   //cuando realmente ocurrio
    @Column(columnDefinition ="DateTime", nullable = true)
    public Date fecha_Real;
   
   @Constraints.Required
   @Column(columnDefinition ="VARCHAR(80)", nullable = false)
   public String actividad;

    @ManyToOne
    @Column( nullable = false)
    public Miembro encargado;

  public String getProgramacionI(){

        
        long lnMilisegundos = fecha_programada_inicial.getTime();
        Timestamp sqlTimestamp = new Timestamp(lnMilisegundos);
    	return (sqlTimestamp+" ");
    }

  public String getProgramacionF(){

     long lnMilisegundos = fecha_programada_final.getTime();
        Timestamp sqlTimestamp = new Timestamp(lnMilisegundos);
    	return (sqlTimestamp+" ");
    }
    
   public Long Diferencia(){
   	if(fecha_Real!=null){
    Long vfecha=fecha_Real.getTime()-fecha_programada_inicial.getTime();
     return (vfecha/(3600000*24));}
     else return  0L;
    }
    

    
    public Programacion(int x,int detalle,Date a, Date b, String c,String d){
   
   	proyecto=Proyecto.find.byId(detalle);
   	this.clave=new Planificacion_PK(x,proyecto.id);
   	fecha_programada_inicial=a;
   fecha_programada_final=b;
   fecha_Real=null;
    actividad=c;
    encargado=Miembro.find.where().eq("cedula",d).findUnique();
    }



   
     public static List<Programacion> findProgramacion(Proyecto proyecto) {
    return find.where().eq("proyecto.id", proyecto.id).findList();
     }

      public static Integer NProgramacion(Proyecto proyecto) {
    return findProgramacion(proyecto).size();
     }

     public static Integer NPFrogramacion(Proyecto proyecto) {

     	List<Programacion> lista= findProgramacion(proyecto);
        int n=0;
     	for(Programacion a: lista)
     		if (a.fecha_Real!=null) {
     			n++;
     			
     		}

    return n;
     }
   
	// Generic query helper for entity with id Long
    public static Model.Finder<Planificacion_PK,Programacion> find = new Model.Finder<Planificacion_PK,Programacion>(Planificacion_PK.class, Programacion.class);

}