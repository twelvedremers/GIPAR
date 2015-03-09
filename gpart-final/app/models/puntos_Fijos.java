package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class puntos_Fijos extends Model { 


@EmbeddedId
public Puntos_PK punto;

 @ManyToOne
 @JoinColumn(name = "reunion_id", insertable = false, updatable = false , nullable=false)
 public Reunion reunion;

public puntos_Fijos(String punto,Reunion reunion){

	this.punto= new Puntos_PK(punto,reunion.n_Reunion);
	this.reunion=reunion;


}


public static List<puntos_Fijos> findPunto( Reunion reunion) {
    return find.where().eq("reunion_id", reunion.n_Reunion).findList();
    }
	// Generic query helper for entity with id Long
    public static Model.Finder<Puntos_PK,puntos_Fijos> find = new Model.Finder<Puntos_PK,puntos_Fijos>(Puntos_PK.class, puntos_Fijos.class);

}

