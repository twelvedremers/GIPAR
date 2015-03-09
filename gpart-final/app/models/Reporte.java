package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;
import com.avaje.ebean.annotation.EnumValue;


@Entity

public class Reporte extends Model {

    @Id
    public int N_reporte;
    
    @Constraints.Required
    @Formats.DateTime(pattern="dd-MM-yyyy ")
    @Column(columnDefinition ="DateTime" ,nullable = false)
    public Date fecha;

    @Constraints.Required
    @Constraints.MinLength(30)
    @Constraints.MaxLength(250)
    @Column(columnDefinition ="TEXT", nullable = false)
    public String contenido;

    @ManyToOne
    @Column(nullable=true)
    public Proyecto proyecto;
    
    @Constraints.Required
    @Constraints.MinLength(30)
    @Constraints.MaxLength(1)
    @Column(columnDefinition ="VARCHAR(30)", nullable = false)
    public String tipo_reporte;


  
    public Reporte(int id,String contenido,String t){

    	N_reporte=id;
    	fecha=new Date();
    	this.contenido=contenido;
    	tipo_reporte=t;
    }



    // Generic query helper for entity with id Long
    public static Model.Finder<Integer,Reporte> find = new Model.Finder<Integer,Reporte>(Integer.class, Reporte.class);

}

