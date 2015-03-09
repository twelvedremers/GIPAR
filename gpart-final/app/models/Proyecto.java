package models;

import java.util.*;
import javax.persistence.*;
import java.io.*;
import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import java.text.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.avaje.ebean.*;
import com.avaje.ebean.annotation.EnumValue;


@Entity
public class Proyecto extends Model {

  public enum Estados {
         
         @EnumValue("F")
         Finalizado,
         
         @EnumValue("A")
         Activo,
         
         @EnumValue("P")
         Parado,
     }

    @Id
    public int id;
    
   
    @Constraints.Required
    @Column(columnDefinition ="VARCHAR(20)" ,nullable=false)
    public String area;

    @Constraints.Required
    @Column(columnDefinition ="VARCHAR(200)" ,nullable=false)
    public String descripcion;

    @Constraints.Required(message="Debe ingresar el nombre minimo 30 maximo 45")
    @Constraints.MinLength(10)
    @Constraints.MaxLength(45)
    @Column(columnDefinition ="VARCHAR(45)",nullable=false)
    public String nombre;
    
 	
    @Formats.DateTime(pattern="dd-MM-yyyy")
    public Date tiempo_estimado;

    @Constraints.Required
    @Column(nullable=false)
    public Estados estado;

     @OneToMany(mappedBy = "proyecto",cascade=CascadeType.ALL)

    public  List<Recursos> recursos;

    @OneToMany(mappedBy = "proyecto",cascade=CascadeType.ALL)
    public  List<Reconocimiento> premios;

    @OneToMany(mappedBy = "proyecto",cascade=CascadeType.ALL)
    public  List<Programacion> programacion;

    @ManyToOne
    @Column(nullable=false)
    public Miembro coordinador;

     @OneToMany(mappedBy = "proyecto",cascade=CascadeType.ALL)
    public  List<Asistencia_Proyecto> miembros;

    @OneToMany(mappedBy = "proyecto",cascade=CascadeType.ALL)
    public List<Reporte> reportes;


    public String reseña(){
     int a=descripcion.length(),n=0;
     String nuevo=descripcion;

     if(a>=70)
     return (descripcion.substring(0,70))+"...";
	 n=74-a;
	 for(int i=0;i<n;i++)
      nuevo+=" ";
     return nuevo; 
     
    }

     public static List<Proyecto> Actuales(){
     List<Proyecto> nuevo=new ArrayList<Proyecto>();
     for (Proyecto a : Proyecto.find.all()) 
     	if(a.estado==Estados.Activo)
       		nuevo.add(a);
 	return nuevo;
    }

    public String C_estado(){
     
     if(estado==Estados.Activo)
       	return "green";
      return "red";
    }

    public String getNombre(){

    	return nombre;
    }

     public String getEstimado(){

     	DateFormat df =  DateFormat.getDateInstance();

    	return df.format(tiempo_estimado);
    } 

    public String getarea(){

    	return area;
    }

 public Long DiferenciaT(){
    Long total=0L;
 	for (Programacion a: Programacion.findProgramacion(this)) {
 		total+=a.Diferencia();
 	}
     
     return total;
    }


    public static boolean EsEvento(Proyecto a){

    	if(Evento.find.byId(a.id)!=null)
    		return true;
    	else
    		return false;


    }

      public static List<Proyecto> ProyectosNoEvento(){

    	List<Proyecto> proyectos=new ArrayList<Proyecto>();
    	List<Evento> eventos=Evento.find.all();

    	for (Proyecto a: Proyecto.find.all()) {
    		if(!EsEvento(a))
    			proyectos.add(a);
    		
    	}

    	return proyectos;



    }



    public Proyecto(String titulo,String area,String info,String cedula,Date fechaN){
    

   Random aleatorio =new Random();
    	int id=(int) (aleatorio.nextDouble() * 4000 + 1);

    while( Proyecto.find.byId(id)!= null){
  	id=(int) (aleatorio.nextDouble() * 2500 + 1);
    } 
    
    this.id=id;
    this.area=area;
    this.descripcion=info;
    this.nombre=titulo;
    this.tiempo_estimado=fechaN;
    estado=Estados.Activo;
    recursos=new ArrayList<Recursos>();
    premios=new ArrayList<Reconocimiento>();
    programacion=new ArrayList<Programacion>();
    coordinador=Miembro.find.where().eq("cedula",cedula).findUnique();
    }


     	public int falta(Date a){
      Long valor=0L;
      
      valor=Math.abs(a.getTime()-tiempo_estimado.getTime());
      int resultado = (int)(valor/(3600000*24));
      return resultado;
   
   	}

   

    // Generic query helper for entity with id Long
    public static Model.Finder<Integer,Proyecto> find = new Model.Finder<Integer,Proyecto>(Integer.class, Proyecto.class);
    


}

