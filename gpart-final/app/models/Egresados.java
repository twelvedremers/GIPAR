package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Egresados extends Model { 

@Id
public int cod_G;

@OneToOne( optional=false)
@JoinColumn(name = "cod_G", insertable = false, updatable = false , nullable=false)
public  Miembro miembro;

@Constraints.Required(message="Debe ingresar el ocupacion")
@Constraints.MaxLength(25)
@Column(columnDefinition ="VARCHAR(25)" ,nullable=false)

    public  String ocupacion;

    @Constraints.Required(message="Debe ingresar el area")
@Constraints.MaxLength(25)
@Column(columnDefinition ="VARCHAR(25)",nullable=false)
    public  String area;


public static boolean EsEgresado(Miembro a){
    
    for (Egresados b :Egresados.find.all()) 
    	if (a.cod_G==b.cod_G) 
    		return true;
    return false;
    }



    public Egresados(int a,Miembro b,String c, String d){
    	this.cod_G=a;
    	this.miembro=b;
    	this.area=c;
    	this.ocupacion =d;

    	
    }

	// Generic query helper for entity with id Long
    public static Model.Finder<Integer,Egresados> find = new Model.Finder<Integer,Egresados>(Integer.class, Egresados.class);

}