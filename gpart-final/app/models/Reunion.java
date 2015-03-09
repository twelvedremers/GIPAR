package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Reunion extends Model { 


@Id
@Column(name="numero_Reunion")
public int n_Reunion;


    @Formats.DateTime(pattern="dd-MM-yyyy")
    @Column(columnDefinition ="DateTime" ,unique=true,nullable=false)
    public Date fecha_Reunion;

@OneToMany(cascade=CascadeType.ALL,mappedBy = "reunion")
public  List<puntos_Fijos> pt_fijos;

@OneToMany(cascade=CascadeType.ALL,mappedBy = "reunion")
public  List<puntos_Varios> pt_varios;

@ManyToMany(mappedBy="asistidos")
@JoinTable(name = "asistencia_miembro")
public List<Miembro> asistencia;

 @ManyToOne
 @JoinColumn(name = "moderador")
 public Miembro miembro;


public Reunion(int n,Date nuevo,int miembro){
n_Reunion=n;
fecha_Reunion=nuevo;
this.miembro=Miembro.find.byId(miembro);
}




public String Cumplida(){

	if(this.asistencia.size()!=0){

		return "green";
	}else return "red";

}

	// Generic query helper for entity with id Long
    public static Model.Finder<Integer,Reunion> find = new Model.Finder<Integer,Reunion>(Integer.class, Reunion.class);

}

