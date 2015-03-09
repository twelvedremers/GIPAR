package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;
import com.avaje.ebean.annotation.EnumValue;


@Entity
public class Cordinadores extends Model {

    


    @Id
    public int cod_G;
    
     @JoinColumn(name = "cod_G", insertable = false, updatable = false , nullable=false)
    @OneToOne
    public Miembro miembro;

 
    @Formats.DateTime(pattern="dd-MM-yyyy ")
    @Column(columnDefinition ="DateTime" ,nullable = false)
    public Date fecha_ingreso;

   
    @Formats.DateTime(pattern="dd-MM-yyyy ")
    @Column(columnDefinition ="DateTime" ,nullable =true)
    public Date fecha_Salida;


    public Cordinadores(Miembro miembro, Date fecha_ingreso){
    this.cod_G=miembro.cod_G;
    this.miembro=miembro;
    this.fecha_ingreso=fecha_ingreso;

    }
 
    // Generic query helper for entity with id Long
    public static Model.Finder<Integer,Cordinadores> find = new Model.Finder<Integer,Cordinadores>(Integer.class, Cordinadores.class);

}

