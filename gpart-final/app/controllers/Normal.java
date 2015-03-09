package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import views.html.include.*;
import models.*;
import play.data.*;
import com.avaje.ebean.*; 
import views.html.cordinador.*;
import views.html.login.*;
import views.html.articulo.*;
import java.util.*;
import play.mvc.Http.*;
import play.mvc.Http.MultipartFormData.FilePart;
import java.io.File;
 import java.io.*;


public class Normal extends Controller {


 public static Result confi(Integer id){
    Form<Login> formulario = Form.form(Login.class);
    Miembro nuevo=Miembro.find.byId(id);
	return ok(configuracion.render(formulario,nuevo));
}

 public static Result ModificarDatos(Integer id){


    Form<Login> formulario = Form.form(Login.class).bindFromRequest();

    MultipartFormData body = request().body().asMultipartFormData();
  	FilePart picture = body.getFile("foto_perfil");

    Login data=formulario.get();
    Miembro nuevo=Miembro.find.byId(id);

     if (data.usuario!=null && data.usuario.length()!=0) {

     	Miembro prueba = Miembro.find.where().eq("username", data.usuario).findUnique();
    
      		if(prueba != null) {
            	flash("invalid", "el nombre de usuario existe");
            	return redirect(routes.Normal.verMiembro(id));
 			}
		nuevo.username=data.usuario;
		nuevo.save();
	 }

	 if ((data.contraseña_new!=null && data.contraseña_new.length()!=0 )&& (data.contraseña_act!=null && data.contraseña_act.length()!=0)) {

     	if(data.contraseña_act.equals(nuevo.password)){
			nuevo.password=data.contraseña_new;
			nuevo.save();
     	}
    
	 }
    
    if(picture!=null){

    	if(Cordinador.guardarArchivo(picture,Integer.toString(nuevo.cod_G))){
         nuevo.foto_url="fotos_perfil/"+session().get("fotox");
         nuevo.save();
         session("foto_url",""+nuevo.foto_url);
     	} else
     	flash("invalid", "La foto se coloco por defecto ya que la imagen no es valida");
    }
    
	return redirect(routes.Normal.verMiembro(id));
}





 public static Result ModificarPersonal(Integer id){

    Form<Login> formulario = Form.form(Login.class).bindFromRequest();
    Miembro nuevo=Miembro.find.byId(id);
    Login data=formulario.get();
	   if (data.nombre!=null && data.nombre.trim().length()!=0 && data.nombre.compareTo("")!=0) {
		   nuevo.nombre=data.nombre;
		   nuevo.save();
		   session("nombre",nuevo.nombre);
	   	}
   	  if (data.apellidos!=null && data.apellidos.trim().length()!=0 && data.apellidos.compareTo("")!=0) {
   nuevo.apellidos=data.apellidos;
   nuevo.save();
   session("apellidos",nuevo.apellidos);
   	 }
   	  if (data.area!=null){ 
   	 	if(data.area.trim().length()!=0) {
   	 		Profesor prof=Profesor.find.byId(id);
   			prof.area=data.area;
   			prof.save();
   		 }
   		}

   		if (data.area2!=null){ 
   	 		if(data.area2.trim().length()!=0) {
   	 	    Egresados egre=Egresados.find.byId(id);
   			egre.area=data.area2;
   			egre.save();
   		 	}
   		}
   	 if (data.ocupacion!=null){ 
   	 	if(data.ocupacion.trim().length()!=0) {
   	 		Egresados egre=Egresados.find.byId(id);
   			egre.ocupacion=data.ocupacion;
   			egre.save();
   		 }
   		}
   	 if (data.semestre!=null){ 
   	 	if(data.semestre.length()!=0) {
   	 		Estudiante est=Estudiante.find.byId(id);
   			est.semestre=Integer.parseInt(data.semestre);
   			est.save();
   		 }
   		}
  
	return redirect(routes.Normal.verMiembro(id));
}





public static Result buscaC(){

Form<Login> formulario = Form.form(Login.class).bindFromRequest();
List<Miembro> listaA=new ArrayList();

List<String> tipos=new ArrayList<String>();
tipos.add("Profesor");
tipos.add("Egresados");
tipos.add("Estudiante");

for (Miembro a : Miembro.find.all()) {

if(a.tipo().equals(formulario.data().get("area")))
	listaA.add(a);	
}

if(!(formulario.data().get("area").compareTo("todos")==0))
	return ok(Lista_miembros.render(listaA,formulario,tipos));
else
	return ok(Lista_miembros.render(Miembro.find.all(),formulario,tipos));

}



public static Result Lista_miembro(){

	int numero=Integer.parseInt(session().get("cod_G"));

Miembro yo=Miembro.find.byId(numero);
List<Miembro> listaP=Miembro.find.all();
listaP.remove(yo);
Form<Login> formulario =Form.form(Login.class);

List<String> tipos=new ArrayList<String>();
tipos.add("Profesor");
tipos.add("Egresados");
tipos.add("Estudiante");

  return ok(Lista_miembros.render(listaP,formulario,tipos));

} 


public static Result Listar_articulos(){

List<Articulo> listaA=Articulo.find.all();
Form<Formulario> formulario =Form.form(Formulario.class);

List<String>   temas =new ArrayList<String>();

for(Area_articulo tema:Area_articulo.find.all()){

          temas.add(tema.area_Articulo.area.toUpperCase());

}

  HashSet<String> hashSet = new HashSet<String>(temas);
            temas.clear();
            temas.addAll(hashSet); 

  return ok(Articulos.render(listaA,formulario,temas));

} 


public static Result Listar_articulosT(){

Form<Formulario> tema_seleccionado= Form.form(Formulario.class).bindFromRequest();

List<Articulo> listaAr=Area_articulo.todosLosTemas(tema_seleccionado.data().get("area"));

HashSet<Articulo> hashSet2 = new HashSet<Articulo>(listaAr);
            listaAr.clear();
            listaAr.addAll(hashSet2); 



List<String>   temas =new ArrayList<String>();

for(Area_articulo tema:Area_articulo.find.all())
          temas.add(tema.area_Articulo.area.toUpperCase());

HashSet<String> hashSet = new HashSet<String>(temas);
            temas.clear();
            temas.addAll(hashSet); 



  return ok(Articulos.render(listaAr,tema_seleccionado,temas));

} 


public static Result ver(int a){


 Articulo articulo=Articulo.find.byId(a);
 
  return ok(vista_articulo.render(articulo,Area_articulo.findArticulo(articulo)));

} 


public static Result verMiembro(int a){

 Miembro persona=Miembro.find.byId(a);
 Profesor persona2=Profesor.find.byId(a);
 Estudiante persona3=Estudiante.find.byId(a);
 Egresados persona4=Egresados.find.byId(a);

 List<Telefonos> telefonos = Telefonos.find.where().eq("cod_G",a).findList();
  List<Disponibilidad> dispo = Disponibilidad.find.where().eq("cod_G",a).findList();
  
  session("areaX","");
  session("semestreX","");
  session("ocupacionX","");

if(persona2!=null){
 session("areaX",persona2.area);
 session().remove("semestreX");
 session().remove("ocupacionX");

}else if (persona3!=null){
session("semestreX",Integer.toString(persona3.semestre));
session().remove("areaX");
 session().remove("ocupacionX");

}else if(persona4!=null){
session("ocupacionX",persona4.ocupacion);
session("areaX",persona4.area);
 session().remove("semestreX");

}



  return ok(vista_miembro.render(persona,telefonos,dispo));

} 


public static Result RegistroAr(){
Form<Formulario> formulario = Form.form(Formulario.class);
List<Miembro> escritor = Miembro.find.where().eq("nivel_A","C").findList();
escritor.add(Miembro.find.where().eq("nivel_A","A").findUnique());
Map<String,String> productMap= new HashMap<String,String>();
for (Miembro persona : escritor) {
   productMap.put(persona.nombreCompleto(), persona.cedula);
}
List<Evento> eventos=Evento.find.all();
return ok(registrar_articulo.render(formulario,productMap,eventos));
}




public static Result RegistrarAr(){



Form<Formulario> data= Form.form(Formulario.class).bindFromRequest();

List<Miembro> escritor = Miembro.find.where().eq("nivel_A","C").findList();
escritor.add(Miembro.find.where().eq("nivel_A","A").findUnique());
List<Evento> eventos=Evento.find.all();

Proyecto nuevos=null;
Map<String,String> productMap= new HashMap<String,String>();
for (Miembro persona : escritor) {
   productMap.put(persona.nombreCompleto(), persona.cedula);
}

Formulario articulo = data.get();

if(articulo.evento!=null){

	if(articulo.evento.compareTo("")!=0){
     nuevos=Proyecto.find.where().eq("id",articulo.evento).findUnique();
	}


}


if(articulo.escritores==null)
{
flash("invalid", "Error seleccione al menos un escritor");
	return badRequest(registrar_articulo.render(data,productMap,eventos));

}
if(articulo.escritores.size()==0 || articulo.escritores.size()>3 ){
	flash("invalid", "Error el articulo minimo debe tener un autor y maximo 3");
	return badRequest(registrar_articulo.render(data,productMap,eventos));
} 

List<Miembro> escritos=new ArrayList<Miembro>(); 
for(String a:articulo.escritores)
escritos.add( Miembro.find.where().eq("cedula",a).findUnique());


 Articulo nuevo=new Articulo(articulo.titulo,articulo.contenido,escritos);
 Area_articulo a= new Area_articulo(nuevo,articulo.area);
nuevo.area_articulo.add(a);
nuevo.proyecto=nuevos;
nuevo.save();
a.save();

	flash("correct", "La creacion se realizo con exito");
return redirect(routes.Normal.Listar_articulos());
}

  public static Result borrarA(Integer id){

  	Articulo viejo=Articulo.find.byId(id);
  	viejo.delete();
    flash("correct","borrado con exito");
    return redirect(routes.Normal.Listar_articulos());
     


    }

      public static Result borrarTelefono(Integer id,String telefono){
   Telefonos_PK clave=new Telefonos_PK(telefono,id);

   Telefonos telf=Telefonos.find.byId(clave);
  telf.delete();
     
   return redirect(routes.Normal.verMiembro(id));
    }


        public static Result borrarDisponibilidad(String dia,Integer hora,Integer id){
         
         Disponibilidad2_PK.Dias dias=null;
        	if(dia.compareTo("Lunes")==0){
             dias=Disponibilidad2_PK.Dias.Lunes;
        	}else if(dia.compareTo("Martes")==0){
             dias=Disponibilidad2_PK.Dias.Martes;
        	}else if(dia.compareTo("Miercoles")==0){
             dias=Disponibilidad2_PK.Dias.Miercoles;
        	}else if(dia.compareTo("Jueves")==0){
             dias=Disponibilidad2_PK.Dias.Jueves;
        	}else
             dias=Disponibilidad2_PK.Dias.Viernes;

           Disponibilidad2_PK clave=new Disponibilidad2_PK(dias,hora,id);

           Disponibilidad.find.ref(clave).delete();

    
   return redirect(routes.Normal.verMiembro(id));


    }



public static Result borrarMiembro(int id){

if(Cordinadores.find.byId(id)!=null){
   Cordinadores.find.ref(id).delete();
   }

  if(Estudiante.find.byId(id)!= null){
   Estudiante.find.byId(id).delete();
  }else if(Egresados.find.byId(id)!=null){
  	Egresados.find.byId(id).delete();
  }else  if(Profesor.find.byId(id)!=null)
  Profesor.find.byId(id).delete();
 
for (Proyecto elem : Proyecto.find.all()) {
	
	if(elem.coordinador.cod_G==id)
	{elem.coordinador=Miembro.find.byId(Integer.parseInt(session().get("cod_G")));
	elem.save();
	}
}

for (Reunion elem : Reunion.find.all()) {
	
	if(elem.miembro.cod_G==id)
	{elem.miembro=Miembro.find.byId(Integer.parseInt(session().get("cod_G")));
	elem.save();
	}
}

 Miembro.find.byId(id).delete();
    
   return redirect(routes.Normal.Lista_miembro());

    }



}
