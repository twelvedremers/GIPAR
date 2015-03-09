package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Recursos extends Model { 

   @EmbeddedId
   public Recursos_PK recurso;

    @ManyToOne
    @JoinColumn(name = "proyecto_id", insertable = false, updatable = false , nullable=false)
    public Proyecto proyecto;


    public Recursos(Proyecto a, String b){

    	this.recurso=new Recursos_PK(b,a.id);
    	proyecto=a;
    }

 public static List<Recursos> findRecursos( Proyecto proyecto) {
    return find.where().eq("proyecto.id", proyecto.id).findList();
     }

public static Model.Finder<String,Recursos> find = new Model.Finder<String,Recursos>(String.class, Recursos
.class);

}