package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;


@Entity 
public class Reconocimiento extends Model { 

   @Id
   @Column(columnDefinition ="VARCHAR(80)")
   public String premio;

    @ManyToOne
    public Proyecto proyecto;

    public Reconocimiento(String pre, Proyecto pro){
premio=pre;
proyecto=pro;

    }


	// Generic query helper for entity with id Long
    public static Model.Finder<String,Reconocimiento> find = new Model.Finder<String,Reconocimiento>(String.class, Reconocimiento.class);

    public static List<Reconocimiento> findReconocimientos(Proyecto proyecto) {
    return find.where().eq("proyecto.id", proyecto.id).findList();
     }
}