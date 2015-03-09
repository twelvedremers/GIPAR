package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import java.util.*;
import com.avaje.ebean.*;

@Entity 
public class Area_articulo extends Model {
 
    @EmbeddedId
    public Area_articulo_PK area_Articulo;
    
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "cod_Articulo", insertable=false, updatable = false , nullable=false)
    public Articulo articulo;

    // Generic query helper for entity with id Long
    public static List<Area_articulo> findArticulo(Articulo articulo) {
    return find.where().eq("articulo.cod_Articulo", articulo.cod_Articulo).findList();
     }

     public Area_articulo(Articulo a, String b){

     	area_Articulo=new Area_articulo_PK(a.cod_Articulo,b);
     	articulo=a;



     }
   

      public static List<Articulo> todosLosTemas(String tema) {
     List<Articulo> lista=new ArrayList<Articulo>();
     
     for(Area_articulo elemento:Area_articulo.find.all())
     {
     	if(elemento.area_Articulo.area.equals(tema))
     		lista.add(elemento.articulo);
     }

     return lista;
 }


    public static Model.Finder<Area_articulo_PK,Area_articulo> find = new Model.Finder<Area_articulo_PK,Area_articulo>(Area_articulo_PK.class, Area_articulo.class);
}

