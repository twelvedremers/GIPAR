package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Profesor extends Model { 
@Id
public int cod_G;

@OneToOne(optional=false)
@JoinColumn(name = "cod_G", insertable = false, updatable = false , nullable=false)
public  Miembro miembro;

@Constraints.Required(message="Debe ingresar el area")
@Constraints.MaxLength(25)
@Column(columnDefinition ="VARCHAR(25)")
    public  String area;


public static boolean EsProfesor(Miembro a){
    
    for (Profesor b :Profesor.find.all()) 
    	if (a.cod_G==b.cod_G) 
    		return true;
    return false;
    }


 public Profesor(int a,Miembro b,String c){
    	this.cod_G=a;
    	this.miembro=b;
    	this.area=c;
    

    	
    }
	// Generic query helper for entity with id Long
    public static Model.Finder<Integer,Profesor> find = new Model.Finder<Integer,Profesor>(Integer.class, Profesor.class);

}