package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;
import com.avaje.ebean.annotation.EnumValue;


@Entity
public class Miembro extends Model {

     public static enum Accesos {
         
         @EnumValue("N")
         Normal,
         
         @EnumValue("C")
         Coordinador,
         
         @EnumValue("A")
         Administrador,
     }

     public enum Sexos {
         @EnumValue("M")
         Masculino,
         
         @EnumValue("F")
         Femenino,
     }


    @Id
    public int cod_G;
    
    @Constraints.Required(message="Debe ingresar el username minimo 5 maximo 30")
    @Constraints.MaxLength(30)
    @Constraints.MinLength(5)
    @Column(columnDefinition ="VARCHAR(30)" ,nullable = false)
    public String username;
    
    @Constraints.Required(message="Debe ingresar el password min 4 .max 30")
     @Constraints.MaxLength(30)
    @Constraints.MinLength(4)
    @Column(columnDefinition ="VARCHAR(30)", nullable = false)
    public String password;

    @Column(columnDefinition ="VARCHAR(100)" ,nullable = true)
    public String foto_url;

    @Constraints.Required
    @Column(nullable = false)
    public  Accesos nivel_A;

    @Constraints.Required(message="Debe ingresar la cedula ")
    @Constraints.MinLength(11)
    @Constraints.MaxLength(12)
    @Column(columnDefinition ="VARCHAR(11)", nullable = false,unique=true)
    public String cedula;


    @Constraints.Required(message="Debe ingresar el nombre minimo 3 maximo 40")
    @Constraints.MinLength(3)
    @Constraints.MaxLength(40)
    @Column(columnDefinition ="VARCHAR(45)", nullable = false)
    public String nombre;

    @Constraints.Required(message="Debe ingresar el nombre minimo 3 maximo 45")
    @Constraints.MinLength(3)
    @Constraints.MaxLength(45)
    @Column(columnDefinition ="VARCHAR(45)" ,nullable = false)
    public String apellidos;

	@Constraints.Required(message="Debe ingresar el mail")
    @Constraints.Email
    @Column(columnDefinition ="VARCHAR(50)" ,nullable = false,unique=true)
    public String correo;

    @Constraints.Required
    @Column(nullable = false)
    public  Sexos sexo;
    
    @Constraints.Required
    @Formats.DateTime(pattern="dd-MM-yyyy ")
    @Column(columnDefinition ="DateTime" ,nullable = false)
    public Date fecha_ingreso;
    
    @Constraints.Required
    @Column(nullable = false)
    @OneToMany(mappedBy = "miembro" , cascade=CascadeType.ALL)
    public  List<Telefonos> telefonos;
    
    @Constraints.Required
    @Column(nullable = false)
    @OneToMany(mappedBy = "miembro",cascade=CascadeType.ALL)
    public  List<Disponibilidad> disponibilidad;
    
    @Column(nullable = false)
    @OneToMany(mappedBy = "miembro",cascade=CascadeType.ALL)
    public  List<Area_Interes> area_interes;
    
    @Column(nullable = false)
    public boolean estado;

    @OneToMany(mappedBy = "miembro",cascade=CascadeType.ALL)
    public  List<Asistencia_Proyecto> asistido;

    @OneToMany(mappedBy = "miembro",cascade=CascadeType.ALL)
    public  List<Asistencia_Evento> eventoPlanificado;

    @ManyToMany(cascade=CascadeType.ALL)
    public List<Reunion> asistidos;

    @ManyToMany
    public List<Articulo> articulos;

    @OneToMany(mappedBy = "encargado",cascade=CascadeType.ALL)
    public List<Programacion> dirige;


    public String nombreS(){
return  nombre;

    }

     public String cedula(){
return  cedula;

    }

    public String Estado(){

    	if(estado)
         return  "Activo";
     else   return  "No activo";

    }

     public String EstadoC(){
  if(estado)
         return  "green";
     else   return  "red";

    }

      public boolean Elcoordina(){

      	for ( Proyecto nuevo: Proyecto.find.all()) {
      		  if (nuevo.coordinador.cod_G==this.cod_G && nuevo.estado==Proyecto.Estados.Activo) {
      		  	return true;
      		  	
      		  }
      		
      	}
      	return false;

    }

      public List<Proyecto> ListaProyectos(){
      	List<Proyecto> lista= new ArrayList<Proyecto>();
      	for ( Proyecto nuevo: Proyecto.find.all()) {
      		  if (nuevo.coordinador.cod_G==this.cod_G && nuevo.estado==Proyecto.Estados.Activo) {
      		  	lista.add(nuevo);
      		  	
      		  }
      		
      	}
      	return lista;

    }
    
 
    public String nombreCompleto(){


    	return (nombre +" "+apellidos);
    }

    public Miembro(){
    	
    } 

    public String tipo(){

    	if(Profesor.find.byId(this.cod_G)!=null)
    		return "Profesor";
    	else if(Egresados.find.byId(this.cod_G)!=null)
    		return "Egresados";
    	else 
    		return "Estudiante";
    }

    public Miembro(String username, String password, Accesos nivel_A, String cedula, String nombre, String apellidos, String correo, Sexos sexo, ArrayList<Telefonos> telefonos, ArrayList<Disponibilidad> disponibilidad, ArrayList<Area_Interes> area_interes, boolean estado, ArrayList<Asistencia_Proyecto> asistido, ArrayList<Reunion> asistidos, ArrayList<Articulo> articulos) {
        
        
 Random aleatorio =new Random();

  int id=(int) (aleatorio.nextDouble() * 4000 + 1);

  while( Miembro.find.byId(id)!= null){
  	id=(int) (aleatorio.nextDouble() * 4000 + 1);

}

Date fecha_r = new java.util.Date();

        this.cod_G = id;
        this.username = username;
        this.password = password;
        this.nivel_A = nivel_A;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.sexo = sexo;
        this.fecha_ingreso = fecha_r;
        this.telefonos = telefonos;
        this.disponibilidad = disponibilidad;
        this.area_interes = area_interes;
        this.estado = estado;
        this.asistido = asistido;
        this.asistidos = asistidos;
        this.articulos = articulos;
    }

	 public  List<Asistencia_Evento> ActividadesEvento(){
	       

	      List<Asistencia_Evento> nuevo=new ArrayList<Asistencia_Evento>();

	      for ( Asistencia_Evento a:Asistencia_Evento.find.all()) {

		      	if(this.cod_G==a.miembro.cod_G)
		      	nuevo.add(a);
	      }
	     
	       return nuevo;
}

 public List<Asistencia_Proyecto> buscarProyecto(){
      List<Asistencia_Proyecto> x=new ArrayList<Asistencia_Proyecto>();
      for ( Asistencia_Proyecto a : Asistencia_Proyecto.find.all()) 
      	if(a.miembro.cod_G==this.cod_G)
       		x.add(a);

    	return x;
}


    // Generic query helper for entity with id Long
    public static Model.Finder<Integer,Miembro> find = new Model.Finder<Integer,Miembro>(Integer.class, Miembro.class);

}

