package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;
import com.avaje.ebean.annotation.EnumValue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity

public class Articulo extends Model {


    @Id
    public int cod_Articulo;
    
    @Constraints.Required(message="Debe ingresar un Titulo minimo 6 maximo 30")
    @Constraints.MaxLength(45)
    @Constraints.MinLength(8)
    @Column(columnDefinition ="VARCHAR(45)" ,nullable = false)
    public String titulo;
    
     @Constraints.Required(message="ingrese contenido")
    @Constraints.MinLength(8)
    @Column(columnDefinition ="TEXT" ,nullable = false)
    public String contenido;
    
    @Constraints.Required
    @Formats.DateTime(pattern="dd-MM-yyyy ")
    @Column(columnDefinition ="DateTime" ,nullable = false)
    public Date fecha;

    @Column(name= "area ",nullable = false)
    @OneToMany(cascade=CascadeType.ALL ,mappedBy = "articulo")
    public  List<Area_articulo> area_articulo;

    @ManyToMany(cascade=CascadeType.ALL ,mappedBy = "articulos")
    public List<Miembro> escritores;

    @OneToOne
    @Column(nullable = true)
    public Proyecto proyecto;
   
   

   public List<Area_articulo> getAreas(){

return area_articulo;

   }



    public Articulo(){


    }

    public Articulo(String titulo,String contenido,List<Miembro> escritores){
    

   Random aleatorio =new Random();

    int id=(int) (aleatorio.nextDouble() * 4000 + 1);

    while( Articulo.find.byId(id)!= null){
  	id=(int) (aleatorio.nextDouble() * 4000 + 1);
    }
     this.cod_Articulo=id;
     this.titulo=titulo;
     this.contenido=contenido;
     this.fecha= new java.util.Date();
     area_articulo=new ArrayList<Area_articulo>();
     this.escritores=escritores;
     proyecto=null;


    }






     
     public List<Articulo> findProyecto( Proyecto proyecto) {
    return find.where().eq("proyecto.id", proyecto.id).findList();
     }

      public String reseña(){
     int a=contenido.length(),n=0;
     String nuevo=contenido;

     if(a>=200)
     return (contenido.substring(0,200))+"...";
	 n=200-a;
	 for(int i=0;i<n;i++)
      nuevo+=" ";
     return nuevo; 
     
    }

    
   


    // Generic query helper for entity with id Long
    public static Model.Finder<Integer,Articulo> find = new Model.Finder<Integer,Articulo>(Integer.class, Articulo.class);

}

