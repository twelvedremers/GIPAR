package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Estudiante extends Model { 
@Id
public int cod_G;

@OneToOne(optional=false)
@JoinColumn(name = "cod_G", insertable = false, updatable = false , nullable=false)
public  Miembro miembro;

@Constraints.Required(message="Debe ingresar el semestre")
@Constraints.Max(10)
@Constraints.Min(0)
@Column(nullable=false)
    public  int  semestre;


    public Estudiante(){

    	
    }

    public Estudiante(int a,Miembro b, int c){
    	this.cod_G=a;
    	this.miembro=b;
    	this.semestre=c;

    	
    }

    public static boolean EsEstudiante(Miembro a){
    
    for (Estudiante b :Estudiante.find.all()) 
    	if (a.cod_G==b.cod_G) 
    		return true;
    return false;
    }



	// Generic query helper for entity with id Long
    public static Model.Finder<Integer,Estudiante> find = new Model.Finder<Integer,Estudiante>(Integer.class, Estudiante.class);

}

