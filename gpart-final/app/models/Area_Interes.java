package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Area_Interes extends Model {
 
    @EmbeddedId
    public Area_interes_PK area_interes;
    
    @ManyToOne(optional=false)
    @JoinColumn(name = "miembro_id", insertable = false, updatable = false , nullable=false)
    public Miembro miembro;

 public Area_Interes(String interes, Miembro a){
    	miembro=a;
    	area_interes=new Area_interes_PK(interes,a.cod_G);

    }


    // Generic query helper for entity with id Long

     public List<Area_Interes> findInteres( Miembro miembro) {
    return find.where().eq("miembro.cod_G", miembro.cod_G).findList();
     }

    public static Model.Finder<Area_interes_PK,Area_Interes> find = new Model.Finder<Area_interes_PK,Area_Interes>(Area_interes_PK.class, Area_Interes.class);

}

