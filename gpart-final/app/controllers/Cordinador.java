package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;
import play.mvc.Http.MultipartFormData.FilePart;
import views.html.*;
import views.html.include.*;
import models.*;
import play.data.*;
import com.avaje.ebean.*; 
import views.html.proyectos_eventos.*;
import views.html.login.*;
import views.html.cordinador.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
 import java.io.*;

public class Cordinador extends Controller {


 ////////////////////// herramientas////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////////////////////



public static boolean validateEmail(String email) {
 
      

   String formatoEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 
        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile(formatoEmail);
 
        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
 
    }
public static boolean isNumber(String numero) {
 
 String formatonNumerico = "[0-9]*";
 
        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile(formatonNumerico);
 
        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(numero);
        return matcher.matches();
 
    }

 public enum Accesos {
         
        
         Normal,
         Coordinador,
         Administrador,
     }

     public enum Sexos {
       
         Masculino,
         Femenino,
     }


public static boolean guardarArchivo(FilePart picture,String letras){

String fileName="";
File file=null;
 
 // si la foto existe

  if (picture != null) {

     String contentType = picture.getContentType(); // se extrae nombre y tipo

     file = picture.getFile();
      Logger.debug("Got dest: " + file );
      File dest=null;

      if(contentType.compareTo("image/jpg")==0)
      { dest = new File("public/fotos_perfil/"+letras+".jpg");
        fileName=letras+".jpg";
      }else {
      dest = new File("public/fotos_perfil/"+letras+".png");
      fileName=letras+".png";
      }
      Logger.debug("Got dest: " + dest.getAbsolutePath() );
    
  	try {


FileInputStream in = new FileInputStream(file);
FileOutputStream out = new FileOutputStream(dest);
int c;


while( (c = in.read() ) != -1)
out.write(c);


in.close();
out.close();


} catch(IOException e) {
	 Logger.debug("Hubo un error de entrada/salida!!!" );
	 return false;
     
} 

}
else{


fileName = "defecto.png";
session("fotox",fileName);
return false;

}

session("fotox",fileName);
return true;
 

}



///////validacion de miembro   /////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////////////////7



public static Result registro(){

Form<Login> formulario =Form.form(Login.class);
List<String>  accesos=new ArrayList<String>();

if(session().get("nivel_A").compareTo("Administrador")==0)
accesos.add("Cordinador");

accesos.add("Normal");

List<String>  tipo= new ArrayList<String>();

tipo.add("estudiante");
tipo.add("Egresado");
tipo.add("Profesor");
 return ok(registro_general.render(formulario,tipo,accesos));
}






public static Result registroS(){

	Form<Login> formulario = Form.form(Login.class).bindFromRequest();
        
List<String>  accesos=new ArrayList<String>();
if(session().get("nivel_A").compareTo("Administrador")==0)
accesos.add("Cordinador");

accesos.add("Normal");

List<String>  tipo= new ArrayList<String>();

tipo.add("estudiante");
tipo.add("Egresado");
tipo.add("Profesor");

        // Debe validad si hubo un error de sistema
        if (formulario.hasErrors()) {
      flash("error", "Please correct errors above.");
      return badRequest(registro_general.render(formulario,tipo,accesos));
    }

       
        // Debe validad si algun campo vacio
  if(formulario.data().get("tipo")==null || formulario.data().get("acceso")==null) {
            flash("invalid", "Datos invalidos campos vacios");
            return badRequest(registro_general.render(formulario,tipo,accesos));
     }
    
    // valida los campos
   if(formulario.data().get("usuario").length() > 30 || formulario.data().get("usuario").length() < 5 || formulario.data().get("contraseña_new").length() > 30 || formulario.data().get("contraseña_new").length() < 4 || formulario.data().get("contraseña_rep").length() > 30 || formulario.data().get("contraseña_rep").length() < 4)
{
 flash("invalid", "formatos de usuario o contraseña invalida");
            return badRequest(registro_general.render(formulario,tipo,accesos));

}

// valida si la contraseña es correcta
 if(!formulario.data().get("contraseña_new").equals(formulario.data().get("contraseña_rep")))
{
 flash("invalid", "Las contraseñas no coinciden");
            return badRequest(registro_general.render(formulario,tipo,accesos));

}

// valida si el nombre de usuario esta disponible
Miembro usuario = Miembro.find.where().eq("username", formulario.data().get("usuario")).findUnique();
    
      if(usuario != null) {
            flash("invalid", "el nombre de usuario existe");
            return badRequest(registro_general.render(formulario,tipo,accesos));
 }

 // valida si el usuario a resgistrar si no sea estudiante cordinador

 if(formulario.data().get("tipo").compareTo("estudiante")==0 && formulario.data().get("acceso").compareTo("Cordinador")==0 ) {
            flash("invalid", "Los estudiantes no pueden ser coordinador general solo profesores y egresados");
            return badRequest(registro_general.render(formulario,tipo,accesos));
 }


// cargar imagen de perfil
  

    
     session("username1",formulario.data().get("usuario"));
     session("tipo2",""+formulario.data().get("tipo"));
     session("acceso3",formulario.data().get("acceso"));
     session("contraseña4",formulario.data().get("contraseña_new"));

if(formulario.data().get("tipo").compareTo("estudiante")==0){

return redirect(routes.Cordinador.registroE());

} else if(formulario.data().get("tipo").compareTo("Egresado")==0){

return redirect(routes.Cordinador.registroEg());

} else {

	return redirect (routes.Cordinador.registroP());
}


}









/////////////////////////////////// /////////////////////////////////////////////////////////
////////////////////registro de Estudiantes
/////////////////////////////////////////////////////////////////////////////////////////7



public static Result registroE(){
       
    	Form<Login> formulario =Form.form(Login.class);
       List<String>  sexo= new ArrayList<String>();

 sexo.add("Femenino");
sexo.add("Masculino");

    	
        return ok(registrar_estudiante.render(formulario,sexo));
       
}




///validacion y creacion del estudiantes

public static Result registrarE(){

  List<String>  sexo= new ArrayList<String>();

 sexo.add("Femenino");
sexo.add("Masculino");


Login form=new Login(session("username1"),session("tipo2"),session("acceso3"),session("contraseña4"));
Form<Login> formulario = Form.form(Login.class).bindFromRequest();
   
   if (formulario.hasErrors()) {
      flash("error", "Please correct errors above.");
      return badRequest(registrar_estudiante.render(formulario,sexo));
    }
   if( formulario.data().get("sexo")==null) {
            flash("invalid", "Datos invalidos campos vacios");
            return badRequest(registrar_estudiante.render(formulario,sexo));
   }

   if(formulario.data().get("nombre").length() > 45 || formulario.data().get("nombre").length() < 3 || formulario.data().get("apellidos").length() < 3 || formulario.data().get("apellidos").length() >45 || formulario.data().get("correo").length() > 50 || formulario.data().get("correo").length() < 8 ||formulario.data().get("telefono").length() < 4 || formulario.data().get("telefono").length() > 20 ||formulario.data().get("semestre").length() != 1 || formulario.data().get("cedula").length() < 6 || formulario.data().get("cedula").length() > 13 || formulario.data().get("interes").length() > 30 || formulario.data().get("interes").length() < 5)
{
 flash("invalid", "formatos de los campos no validas");
            return badRequest(registrar_estudiante.render(formulario,sexo));

}
  
  if(!validateEmail(formulario.data().get("correo")))
  {
    flash("invalid", "Correo no valido");
            return badRequest(registrar_estudiante.render(formulario,sexo));

  }

if(!isNumber(formulario.data().get("telefono")))
  {
    flash("invalid", "Formato de telefono no valida");
            return badRequest(registrar_estudiante.render(formulario,sexo));

  }


///////////variables para crear nuevo objeto


   MultipartFormData body = request().body().asMultipartFormData();

 FilePart picture = body.getFile("foto_perfil");


Miembro.Sexos sex;
if(formulario.data().get("sexo").compareTo("Femenino")==0)
	sex = Miembro.Sexos.Femenino;
else
	sex = Miembro.Sexos.Masculino;




 Miembro nuevo=  new Miembro(
 	session().get("username1"),
 	session().get("contraseña4"),
 	Miembro.Accesos.Normal,
 	formulario.data().get("cedula"),
 	formulario.data().get("nombre"),
 	formulario.data().get("apellidos"),
 	formulario.data().get("correo"),
 	sex,
 	new ArrayList<Telefonos>(),
 	new ArrayList<Disponibilidad>(),
 	new ArrayList<Area_Interes>(),
 	true,
 	new ArrayList<Asistencia_Proyecto>(),
 	new ArrayList<Reunion>(),
 	new ArrayList<Articulo>()

);


if(!guardarArchivo(picture,Integer.toString(nuevo.cod_G))){

flash("invalid", "La foto se coloco por defecto ya que la imagen no es valida");

}

nuevo.foto_url="fotos_perfil/"+session().get("fotox");

 Telefonos mytelefono=new Telefonos(formulario.data().get("telefono"),nuevo);
 nuevo.telefonos.add(mytelefono);

 Area_Interes miInteres=new Area_Interes(formulario.data().get("interes"),nuevo);
nuevo.area_interes.add(miInteres);

Estudiante nuevo2=new Estudiante(nuevo.cod_G,nuevo,Integer.parseInt(formulario.data().get("semestre")));

nuevo.save();
nuevo2.save();
mytelefono.save();
  miInteres.save();
	 
   flash("correct", "Registro realizada con exito");
   session().remove("username1");
   session().remove("tipo2");
   session().remove("acceso3");
   session().remove("contraseña4");


	return redirect(routes.Cordinador.ModificarM(nuevo.cod_G));
}
 












 ////////////////////////////////////////////////////////////////////////////////////////
////////////////////registro de Egresados

//////////////////////////////////////////////////////////////////////////////////////////


 public static Result registroEg(){

   	 Form<Login> formulario =Form.form(Login.class);
   	 List<String>  sexo= new ArrayList<String>();

 sexo.add("Femenino");
sexo.add("Masculino");

    	

        return ok(registro_egresados.render(formulario,sexo));
}




public static Result registrarEg(){
	 Login form=new Login(session("username1"),session("tipo2"),session("acceso3"),session("contraseña4"));
Form<Login> formulario = Form.form(Login.class).bindFromRequest();

List<String>  sexo= new ArrayList<String>();

 sexo.add("Femenino");
sexo.add("Masculino");


 if (formulario.hasErrors()) {
      flash("error", "Please correct errors above.");
      return badRequest(registro_egresados.render(formulario,sexo));
    }

   if(formulario.data().get("sexo")==null) {
            flash("invalid", "Datos invalidos campos vacios");
            return badRequest(registro_egresados.render(formulario,sexo));
   }

   if(formulario.data().get("nombre").length() > 45 || formulario.data().get("nombre").length() < 3 || formulario.data().get("apellidos").length() < 3 || formulario.data().get("apellidos").length() >45 || formulario.data().get("correo").length() > 50 || formulario.data().get("correo").length() < 8 ||formulario.data().get("telefono").length() < 4 || formulario.data().get("telefono").length() > 20 ||  formulario.data().get("area").length() < 3  || formulario.data().get("area").length() >25 || formulario.data().get("cedula").length() < 6 || formulario.data().get("cedula").length() > 13 || formulario.data().get("ocupacion").length() < 3  || formulario.data().get("ocupacion").length() >25 || formulario.data().get("interes").length() > 30 || formulario.data().get("interes").length() < 5)
{
 flash("invalid", "formatos de los campos no validas");
            return badRequest(registro_egresados.render(formulario,sexo));

}
  
  if(!validateEmail(formulario.data().get("correo")))
  {
    flash("invalid", "Correo no valido");
            return badRequest(registro_egresados.render(formulario,sexo));

  }

if(!isNumber(formulario.data().get("telefono")))
  {
    flash("invalid", "Formato de telefono no valida");
            return badRequest(registro_egresados.render(formulario,sexo));

  }


 
/////////registro del miembro

   MultipartFormData body = request().body().asMultipartFormData();

 FilePart picture = body.getFile("foto_perfil");


Miembro.Sexos sex;
if(formulario.data().get("sexo").compareTo("Femenino")==0)
	sex = Miembro.Sexos.Femenino;
else
	sex = Miembro.Sexos.Masculino;
 

Miembro.Accesos acceso=Miembro.Accesos.Normal;
if(session().get("acceso3").compareTo("Coordinador")==0)
 acceso=Miembro.Accesos.Coordinador;

 Miembro nuevo=  new Miembro(
 	session().get("username1"),
 	session().get("contraseña4"),
 	acceso,
 	formulario.data().get("cedula"),
 	formulario.data().get("nombre"),
 	formulario.data().get("apellidos"),
 	formulario.data().get("correo"),
 	sex,
 	new ArrayList<Telefonos>(),
 	new ArrayList<Disponibilidad>(),
 	new ArrayList<Area_Interes>(),
 	true,
 	new ArrayList<Asistencia_Proyecto>(),
 	new ArrayList<Reunion>(),
 	new ArrayList<Articulo>()

);


if(!guardarArchivo(picture,Integer.toString(nuevo.cod_G))){

flash("invalid", "La foto se coloco por defecto ya que la imagen no es valida");

}

nuevo.foto_url="fotos_perfil/"+session().get("fotox");

 Telefonos mytelefono=new Telefonos(formulario.data().get("telefono"),nuevo);
nuevo.telefonos.add(mytelefono);
 

 Area_Interes miInteres=new Area_Interes(formulario.data().get("interes"),nuevo);
nuevo.area_interes.add(miInteres);



Egresados nuevo2=new Egresados(nuevo.cod_G,nuevo,formulario.data().get("area"),formulario.data().get("ocupacion"));

nuevo.save();
nuevo2.save();
 mytelefono.save();
 miInteres.save();

if(session().get("acceso3").compareTo("Coordinador")==0 ){

	Cordinadores lider=new Cordinadores(nuevo,new Date());
	lider.save();
}



   flash("correct", "Registro realizada con exito");
   session().remove("username1");
   session().remove("tipo2");
   session().remove("acceso3");
   session().remove("contraseña4");


	return redirect(routes.Cordinador.ModificarM(nuevo.cod_G));

}






 ////////////////////////////////////////////////////////////////////////////////////////
////////////////////registro de Profesores

//////////////////////////////////////////////////////////////////////////////////////////









 public static Result registroP(){
 	
Form<Login> formulario =Form.form(Login.class);
List<String>  sexo= new ArrayList<String>();

 sexo.add("Femenino");
sexo.add("Masculino");


        return ok(registro_de_profesores.render(formulario,sexo));
        
}




///validacion y creacion del profesor


public static Result registrarP(){

List<String>  sexo= new ArrayList<String>();

 sexo.add("Femenino");
sexo.add("Masculino");

Login form=new Login(session("username1"),session("tipo2"),session("acceso3"),session("contraseña4"));
Form<Login> formulario = Form.form(Login.class).bindFromRequest();

 if (formulario.hasErrors()) {
      flash("error", "Please correct errors above.");
      return badRequest(registro_de_profesores.render(formulario,sexo));
    }

    if( formulario.data().get("sexo")==null) {
            flash("invalid", "Datos invalidos campos vacios");
            return badRequest(registro_de_profesores.render(formulario,sexo));
   }

   if(formulario.data().get("nombre").length() > 45 || formulario.data().get("nombre").length() < 3 || formulario.data().get("apellidos").length() < 3 || formulario.data().get("apellidos").length() >45 || formulario.data().get("correo").length() > 50 || formulario.data().get("correo").length() < 8 ||formulario.data().get("telefono").length() < 4 || formulario.data().get("telefono").length() > 20 ||  formulario.data().get("area").length() < 3  || formulario.data().get("area").length() >25 || formulario.data().get("cedula").length() < 6 || formulario.data().get("cedula").length() > 13)
{
 flash("invalid", "formatos de los campos no validas");
            return badRequest(registro_de_profesores.render(formulario,sexo));

}
  
  if(!validateEmail(formulario.data().get("correo")))
  {
    flash("invalid", "Correo no valido");
            return badRequest(registro_de_profesores.render(formulario,sexo));

  }

if(!isNumber(formulario.data().get("telefono")))
  {
    flash("invalid", "Formato de telefono no valida");
            return badRequest(registro_de_profesores.render(formulario,sexo));

  }


/////////registro del miembro

   MultipartFormData body = request().body().asMultipartFormData();

 FilePart picture = body.getFile("foto_perfil");


Miembro.Accesos acceso=Miembro.Accesos.Normal;
if(session().get("acceso3").compareTo("Coordinador")==0)
 acceso=Miembro.Accesos.Coordinador;

Miembro.Sexos sex;
if(formulario.data().get("sexo").compareTo("Femenino")==0)
	sex = Miembro.Sexos.Femenino;
else
	sex = Miembro.Sexos.Masculino;
 
Date fecha_r = new java.util.Date();


 Miembro nuevo=  new Miembro(
 	session().get("username1"),
 	session().get("contraseña4"),
 	acceso,
 	formulario.data().get("cedula"),
 	formulario.data().get("nombre"),
 	formulario.data().get("apellidos"),
 	formulario.data().get("correo"),
 	sex,
 	new ArrayList<Telefonos>(),
 	new ArrayList<Disponibilidad>(),
 	new ArrayList<Area_Interes>(),
 	true,
 	new ArrayList<Asistencia_Proyecto>(),
 	new ArrayList<Reunion>(),
 	new ArrayList<Articulo>()

);

if(!guardarArchivo(picture,Integer.toString(nuevo.cod_G))){

flash("invalid", "La foto se coloco por defecto ya que la imagen no es valida");

}


nuevo.foto_url="fotos_perfil/"+session().get("fotox");

 Telefonos mytelefono=new Telefonos(formulario.data().get("telefono"),nuevo);

nuevo.telefonos.add(mytelefono);


 Area_Interes miInteres=new Area_Interes(formulario.data().get("interes"),nuevo);
  nuevo.area_interes.add(miInteres);

Profesor nuevo2=new Profesor(nuevo.cod_G,nuevo,formulario.data().get("area"));

nuevo.save();
nuevo2.save();
 mytelefono.save();
miInteres.save();

if(session().get("acceso3").compareTo("Coordinador")==0 ){

	Cordinadores lider=new Cordinadores(nuevo,new Date());
	lider.save();
}

	 
   flash("correct", "Registro realizada con exito");
   session().remove("username1");
   session().remove("tipo2");
   session().remove("acceso3");
   session().remove("contraseña4");


	return redirect(routes.Cordinador.ModificarM(nuevo.cod_G));
   
}

/////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////

      public static Result ModificarM(Integer id){
     Form<Formulario> data= Form.form(Formulario.class);
     Miembro persona=Miembro.find.byId(id);
     List<String> dias=new ArrayList<String>();
     List<String> horas=new ArrayList<String>();

     dias.add("Lunes");
     dias.add ("Martes");
     dias.add ("Miercoles");
     dias.add ("Jueves");
     dias.add ("Viernes");   
   

     for (int i=0;i<=23 ;i++ ) {

     	horas.add(i+"");
     	
     }

     List<Disponibilidad> dispo=Disponibilidad.findDisponibilidad(persona);
   
    return ok(Modificar_Miembro.render(data,Telefonos.findTelefono(persona),dias,horas,dispo,persona));

    }
   



    public static Result registrarTelf(Integer id){
     Form<Formulario> data= Form.form(Formulario.class).bindFromRequest();
     Miembro persona=Miembro.find.byId(id);
   
    if(data.data().get("telf")!=null){
     Telefonos nuevo=new Telefonos(data.data().get("telf"),persona);
     nuevo.save();
    }
    
    return redirect(routes.Cordinador.ModificarM(id));

    }

     public static Result registrardispo(Integer id){
     Form<Formulario> data= Form.form(Formulario.class).bindFromRequest();
     Miembro persona=Miembro.find.byId(id);
   
    if(data.data().get("dia")!=null && data.data().get("hora")!=null){
     Disponibilidad nuevo=new Disponibilidad(data.data().get("dia"),Integer.parseInt(data.data().get("hora")),persona);
     nuevo.save();
    }
    
    return redirect(routes.Cordinador.ModificarM(id));

    }
   
   public static Result Desactivar(Integer id){
    
     Miembro persona=Miembro.find.byId(id);
     for(Asistencia_Proyecto todos: Asistencia_Proyecto.find.all())
     {
      if(todos.miembro.cod_G==id)
      {
       todos.Estado_M=false;
       todos.save();
      }

     }
     
      for(Asistencia_Evento todos: Asistencia_Evento.find.all())
     {
      if(todos.miembro.cod_G==id)
      {
       todos.delete();
      }

     }

     if(persona.nivel_A!=Miembro.Accesos.Normal){

     	Cordinadores aux=Cordinadores.find.byId(id);
     	aux.fecha_Salida=new Date();
     	aux.save();

     }
     persona.estado=false;
     persona.save();

    return redirect(routes.proyectos_Controlador.registro_extraReunion(id));

    }


    

}


