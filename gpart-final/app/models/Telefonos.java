package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Telefonos extends Model {
 
    @EmbeddedId 
    public Telefonos_PK telefono;

    @ManyToOne
    @JoinColumn(name = "cod_G", insertable=false,updatable=false)
    public Miembro miembro;


    public Telefonos(String telf, Miembro a){
    	miembro=a;
    	telefono=new Telefonos_PK(telf,a.cod_G);



    }

     public static List<Telefonos> findTelefono( Miembro miembro) {
    return find.where().eq("miembro.cod_G", miembro.cod_G).findList();
     }

    // Generic query helper for entity with id Long
    public static Model.Finder<Telefonos_PK,Telefonos> find = new Model.Finder<Telefonos_PK,Telefonos>(Telefonos_PK.class, Telefonos.class);

}

