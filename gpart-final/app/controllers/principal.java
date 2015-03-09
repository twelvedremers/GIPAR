package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import play.data.validation.*;
import views.html.*;
import views.html.include.*;
import views.html.proyectos_eventos.*;
import views.html.login.*;
import java.util.*;
import play.libs.mailer.*;
import models.*;
import play.data.Form.*;

public class principal extends Controller {


  public static boolean Enviar(String tema,String contenido, List<String> enviados){

try{

    
for (String a : enviados) {
  	
Email email = new Email();
email.setSubject(tema);
email.setFrom("Mister FROM <gpart.pagP@gmail.com>");
email.addTo("Miss TO <"+a+">");
email.setBodyText(contenido);
email.setBodyHtml("<html><body><p>An <b>html</b> message</p></body></html>");


MailerPlugin.send(email);
}

}catch (Exception e) {

    
	return false;
	
}
return true;

  }


    public static void EnviarInvitacion(){
    
      List<Reunion> reuniones=Reunion.find.all();
      Date hoy=new Date();
      Long valor=0L;
      for (Reunion reu : reuniones) {
      valor=Math.abs(hoy.getTime()-reu.fecha_Reunion.getTime()); 
      if((valor/(3600000*24))<1){
       
      List<Miembro> usuarios=Miembro.find.all();
	  List<String> correos=new ArrayList<String>();
      for (Miembro a :usuarios ) {
      correos.add(a.correo);
    	}

       Enviar("recordatorio de reunion","El GIpar les recuerda q deben asistir a la reunion el dia de hoy"+hoy,correos);
      }
      	
      }
    }


    public static Result index() {

    		EnviarInvitacion();
    	   session().clear();
        return ok(index.render());
    }

    public static Result inicio() {
    	  
        return ok(index.render());
    }


    public static Result acerca(){
        return ok(quienes_somos.render());
    }


    public static Result login(){

    	session().clear();
    	Form<Login> formulario =Form.form(Login.class);

        return ok(iniciar_sesion.render(formulario));
    }
    

  public static Result ingresar() {
        
        Form<Login> formulario = Form.form(Login.class).bindFromRequest();
        
        
        // Debe ingresar la el usuario y la contraseña
        if (formulario.hasErrors()) {
      flash("error", "Please correct errors above.");
      return badRequest(iniciar_sesion.render(formulario));
    }
     
     if(formulario.data().get("usuario") == "" || formulario.data().get("contraseña") == "") {
            flash("invalid", "Debe ingresar usuario y contraseña");
            return badRequest(iniciar_sesion.render(formulario));
        }
        // Busca el usuario por usuario y contraseña
       Miembro usuario = Miembro.find.where().eq("username", formulario.data().get("usuario")).eq("password", formulario.data().get("contraseña")).findUnique();
      
      if(usuario == null) {
            flash("invalid", "usuario y/o contraseña incorrecto/s");
            return badRequest(iniciar_sesion.render(formulario));
       }

        //se guarda los datos de perfil

       session("username",usuario.username);
        session("foto_url",""+usuario.foto_url);
       session("nivel_A",usuario.nivel_A.toString());
       session("nombre",usuario.nombre);
       session("apellidos",usuario.apellidos);
       session("cod_G",Integer.toString(usuario.cod_G));


 // Login correcto, se setean las variables de session
      return redirect(routes.principal.perfil());
   }

public static Result perfil(){
     int a= Integer.parseInt(session("cod_G"));
     Miembro usuario = Miembro.find.byId(a);
     List<Asistencia_Proyecto> listaE=Asistencia_Proyecto.find.where().eq("miembro_id",a).findList();  
     List<Proyecto> listaP=new ArrayList<Proyecto>();

     for(Asistencia_Proyecto asistencia:listaE){
     	if(asistencia.Estado_M=true && asistencia.proyecto.C_estado().compareTo("green")==0)
       	listaP.add(asistencia.proyecto);
     }
    
   //parte para las novedades 
     List<Proyecto> proyectos_Novedades=Proyecto.find.where().eq("estado","A").orderBy("id desc").findList();
     List<Evento>   listaV=Evento.find.all();
       

        //los proyectos que no son eventos
    	for(Evento elemento:listaV)
    	proyectos_Novedades.remove(elemento.proyecto);

     if(proyectos_Novedades.size()>=3)
     proyectos_Novedades=proyectos_Novedades.subList(0, 2);
     else{
     	if(proyectos_Novedades.size()>1){       //visualiza los primeros proyectos
        proyectos_Novedades=proyectos_Novedades.subList(0, 1);
     	}
     }

     List<Evento> evento_Novedades=new ArrayList<Evento>();
     
     for ( Evento x: Evento.find.where().orderBy("id_evento desc").findList()) {
     	if(x.estado())
     		evento_Novedades.add(x);
     }

     if(evento_Novedades.size()>3)
     evento_Novedades=evento_Novedades.subList(0, 2);
     else{
     	if(evento_Novedades.size()>1){         //visualiza los primeros eventos
        evento_Novedades=evento_Novedades.subList(0, 1);
     	}
     }

     List<Articulo> articulo_Novedades=Articulo.find.where().orderBy("cod_Articulo desc").findList();
     if(articulo_Novedades.size()>3)
     articulo_Novedades=articulo_Novedades.subList(0, 2);
     else{
     	if(articulo_Novedades.size()>1){                //visualiza los primeros articulos
        articulo_Novedades=articulo_Novedades.subList(0, 1);
     	}
     }
  

	return ok(perfil.render(usuario,listaP,proyectos_Novedades,evento_Novedades,articulo_Novedades));
}

public static Result salir(){

     session().clear();
	return redirect(routes.principal.index());
}

public static Result enviarCorreo(){
	 Form<Formulario> formulario = Form.form(Formulario.class).bindFromRequest();

	List<Miembro> usuarios;
	List<String> correos=new ArrayList<String>();
	if(formulario.data().get("area").compareTo("todos")==0){
	 	usuarios=Miembro.find.all();
	}
	else{
		Proyecto proyecto=Proyecto.find.where().eq("nombre",formulario.data().get("area")).findUnique();
			if (Proyecto.EsEvento(proyecto))
				usuarios=Asistencia_Evento.buscarAsistencia(proyecto);
			else
		usuarios=Asistencia_Proyecto.buscarAsistencia(proyecto);
	}
    for (Miembro a :usuarios ) {
    	correos.add(a.correo);
    	
    }



     if(Enviar(formulario.data().get("titulo"),formulario.data().get("contenido"),correos)){
     flash("invalid", "El correo se envio con exito");
 }else
    flash("invalid", "No hay internet para esta accion");
	
	return redirect(routes.principal.Correo());

}

public static Result Correo(){
	 Form<Formulario> formulario = Form.form(Formulario.class);
	 List<String> tipos=new ArrayList<String>();
	for (Proyecto nuevo : Proyecto.find.all()) {
		if(nuevo.estado == Proyecto.Estados.Activo)
		tipos.add(nuevo.nombre);
		
	}
        
return ok(enviar_correo.render(formulario,tipos));
	
}


public static void ReportesSemanales(){

	 int n;
	 Long valor=0L;
	 Date hoy=new Date();

  	Reporte z=null;
   List<Reporte> y=Reporte.find.where().orderBy("N_reporte desc").findList();
   	if(y!=null){if(y.size()!=0) n=(y.get(0).N_reporte)+1;
    		else n=1;} else n=1;
  
    	if( Reporte.find.where().eq("tipo_reporte","proyecto semanal General").findList().size()!=0)
    	{
    

 		 z= Reporte.find.where().eq("tipo_reporte","proyecto semanal General").orderBy("N_reporte desc").findList().get(0);
 		 if(Math.abs(z.fecha.getTime()-hoy.getTime())/(3600000*24)>7){
 		 	 Logger.debug("funciona el generador de reporte");
 		 	 String contenido=formato(1);
 		 	  Reporte nuevo=new Reporte(n,contenido,"proyecto semanal General");
 		 	 nuevo.save();
 		 	  n++; 
 		 	}
		} else	 {
			Logger.debug("Se crea el primer reporte semanal ");
			  String contenido=formato(1);
 		 	  Reporte nuevo=new Reporte(n,contenido,"proyecto semanal General");
 		 	 nuevo.save();
 		 	  n++;
	}
		

    	if( Reporte.find.where().eq("tipo_reporte","Evento semanal General").findList().size()!=0)
    	{
  
 		 z= Reporte.find.where().eq("tipo_reporte","Evento semanal General").orderBy("N_reporte desc").findList().get(0);
 		 if(Math.abs(z.fecha.getTime()-hoy.getTime())/(3600000*24)>7){
 		 	 Logger.debug("funciona el generador de reporte");
 		 	  String contenido=formato(2);
 		 	  Reporte nuevo=new Reporte(n,contenido,"Evento semanal General");
 		 	 nuevo.save(); 
 		 	}

 		 

    	} else {Logger.debug("Se crea el primer reporte semanal");
    	   String contenido=formato(2);
 		 	  Reporte nuevo=new Reporte(n,contenido,"Evento semanal General");
				 nuevo.save();
 		 	 	
    	}



}

public static void ReportesMensuales(){


	 int n;
	 Long valor=0L;
	 Date hoy=new Date();

  	Reporte z=null;
   List<Reporte> y=Reporte.find.where().orderBy("N_reporte desc").findList();
   	if(y!=null){if(y.size()!=0) n=(y.get(0).N_reporte)+1;
    		else n=1;} else n=1;
  
    	if( Reporte.find.where().eq("tipo_reporte","Mensual proyectos General").findList().size()!=0)
    	{
    

 		 z= Reporte.find.where().eq("tipo_reporte","Mensual proyectos General").orderBy("N_reporte desc").findList().get(0);
 		 if(Math.abs(z.fecha.getTime()-hoy.getTime())/(3600000*24)>30){
 		 	 Logger.debug("funciona el generador de reporte");
 		 	  String contenido=formato(1);
 		 	  Reporte nuevo=new Reporte(n,contenido,"Mensual proyectos General");
 		 	 nuevo.save();
 		 	  n++; 
 		 	}
		} else	 {Logger.debug("Se crea el primer reporte Mensual ");
		  String contenido=formato(1);
 		 	  Reporte nuevo=new Reporte(n,contenido,"Mensual proyectos General");
 		 	 nuevo.save();
 		 	  n++; 
	}
		

    	if( Reporte.find.where().eq("tipo_reporte","Mensual Eventos General").findList().size()!=0)
    	{
  
 		 z= Reporte.find.where().eq("tipo_reporte","Mensual Eventos General").orderBy("N_reporte desc").findList().get(0);
 		 if(Math.abs(z.fecha.getTime()-hoy.getTime())/(3600000*24)>30)
 		 	 {Logger.debug("Se crea el primer reporte mensual");
 		  String contenido=formato(2);
 		 	  Reporte nuevo=new Reporte(n,contenido,"Mensual Eventos General");
 		 	 nuevo.save();
 		 	  n++; 
 		 }

 		 
    	} else {
    		Logger.debug("Se crea el primer reporte mensual");
    	  String contenido=formato(2);
 		 	  Reporte nuevo=new Reporte(n,contenido,"Mensual Eventos General");
 		 	 nuevo.save();
 		 	  n++; 
    }



    	if( Reporte.find.where().eq("tipo_reporte","asistencia Reunion mensual").findList().size()!=0)
    	{
  
 		 z= Reporte.find.where().eq("tipo_reporte","asistencia Reunion mensual").orderBy("N_reporte desc").findList().get(0);
 		 if(Math.abs(z.fecha.getTime()-hoy.getTime())/(3600000*24)>30)
 		 	 {Logger.debug("funciona el genera estadistica asistencia Reunion");
 		 	  String contenido=formato(5);
 		 	  Reporte nuevo=new Reporte(n,contenido,"asistencia Reunion mensual");
 		 	 nuevo.save();
 		 	  n++; 
 		 }

 		 
    	} else {Logger.debug("funciona el genera estadistica asistencia Reunion");
    	  String contenido=formato(5);
 		 	  Reporte nuevo=new Reporte(n,contenido,"asistencia Reunion mensual");
 		 	 nuevo.save();
 		 	  n++; 
    }

    	if( Reporte.find.where().eq("tipo_reporte","participacion Miembro").findList().size()!=0)
    	{
  
 		 z= Reporte.find.where().eq("tipo_reporte","participacion Miembro").orderBy("N_reporte desc").findList().get(0);
 		 if(Math.abs(z.fecha.getTime()-hoy.getTime())/(3600000*24)>30)
 		 	 {Logger.debug("funciona el genera estadistica  participacion Miembro");
 		 	  String contenido=formato(6);
 		 	  Reporte nuevo=new Reporte(n,contenido,"participacion Miembro");
 		 	 nuevo.save();
 		 	  n++; 
 		 }

 		 
    	} else {Logger.debug("funciona el genera estadistica  participacion Miembro");
    		  String contenido=formato(6);
 		 	  Reporte nuevo=new Reporte(n,contenido,"participacion Miembro");
 		 	 nuevo.save();
 		 	  n++; 
    }

    if( Reporte.find.where().eq("tipo_reporte","Organizacion Actual").findList().size()!=0)
    	{
  
 		 z= Reporte.find.where().eq("tipo_reporte","Organizacion Actual").orderBy("N_reporte desc").findList().get(0);
 		 if(Math.abs(z.fecha.getTime()-hoy.getTime())/(3600000*24)>90)
 		 	 {Logger.debug("funciona el genera estadistica Organizacion");
 		 	  String contenido=formato(7);
 		 	  Reporte nuevo=new Reporte(n,contenido,"Organizacion Actual");
 		 	 nuevo.save();
 		 	   
 		 }

 		 
    	} else {Logger.debug("funciona el genera estadistica Organizacion");
    	  String contenido=formato(7);
 		 	  Reporte nuevo=new Reporte(n,contenido,"Organizacion Actual");
 		 	 nuevo.save();
 		 	   
    }




}



public static Result ReportesPersonalMensual(Integer id){
Form<Formulario> formulario = Form.form(Formulario.class);
Miembro persona=Miembro.find.byId(id);
return ok(reporte.render(formulario,persona));
}

public static Result CrearReporteM(){
Form<Formulario> formulario = Form.form(Formulario.class).bindFromRequest();
 Formulario data=formulario.get();

 int n;
   List<Reporte> z=Reporte.find.where().orderBy("N_reporte desc").findList();
   if(z!=null){
    if(z.size()!=0)
     n=(z.get(0).N_reporte)+1;
    else
    n=1;
   } else
   	n=1;

   	Proyecto nueva_version=Proyecto.find.where().eq("id",data.area).findUnique();
   	 Reporte reporte=new Reporte(n,data.contenido,"Reporte Mensual de Proyecto");
 	reporte.proyecto=nueva_version;
 	reporte.save();
	return redirect(routes.principal.Reporte());
}








public static Result Reporte(){

ReportesSemanales();
ReportesMensuales();

/////////////////////////7
	Form<Formulario> tema_seleccionado= Form.form(Formulario.class);
    List<String>   temas =new ArrayList<String>();
   
   
    for(Reporte nuevo:Reporte.find.all())
          temas.add(nuevo.tipo_reporte);
    
   
    HashSet<String> hashSet = new HashSet<String>(temas);
            temas.clear();
            temas.addAll(hashSet); 

	return ok(Lista_Reportes.render(Reporte.find.all(),tema_seleccionado,temas));
}

public static Result VerReporte(Integer id){

    Reporte reporte=Reporte.find.byId(id);

    if(reporte.tipo_reporte.compareTo("Proyecto reporte Final")==0){

                Proyecto nuevo=Proyecto.find.byId(reporte.proyecto.id);
 
         		return ok(repo_proyectosC.render(nuevo,reporte,reporte.contenido,0));
      
    	}

    if(reporte.tipo_reporte.compareTo("Evento reporte Final")==0){

    		Proyecto nuevo=Proyecto.find.byId(reporte.proyecto.id);
    	
         	return ok(repo_eventoC.render(nuevo,reporte,reporte.contenido,0));
         
    	}

    if(reporte.tipo_reporte.compareTo("Reporte Mensual de Proyecto")==0){

    		Proyecto nuevo=Proyecto.find.byId(reporte.proyecto.id);
    	
         	return ok(repo_eventoC.render(nuevo,reporte,reporte.contenido,1));
         
    	}
    	else	
			return ok(repo_General.render(reporte));
}

public static Result BorrarReporte(Integer id){


	Reporte nuevo=Reporte.find.byId(id);
	nuevo.delete();

return redirect(routes.principal.Reporte());

}


public static Result GenerarReporte(int id){


Miembro actual=Miembro.find.byId(id);
String direccion="";
	//////////////////////codigo de alejandro///////////////
    try{
     CarnetGT.generarCarnet("   GIPAR",actual);
    }catch (Exception e) {
    	direccion="carnet/carnet.pdf";
    	e.printStackTrace();
    	return ok("error");
    }
	
	direccion="carnet/"+actual.cedula+".pdf";
   return ok(CarnetG.render(direccion));
}




public static Result TemasReporte(){

	Form<Formulario> tema_seleccionado= Form.form(Formulario.class).bindFromRequest();
    List<String>   temas =new ArrayList<String>();
   
   
    for(Reporte nuevo:Reporte.find.all())
          temas.add(nuevo.tipo_reporte);
    
    List<Reporte> listaT=Reporte.find.where().eq("tipo_reporte",tema_seleccionado.data().get("area")).findList() ;
	

    HashSet<String> hashSet = new HashSet<String>(temas);
            temas.clear();
            temas.addAll(hashSet); 

    if(tema_seleccionado.data().get("area").toLowerCase().compareTo("todos")!=0)
    	return ok(Lista_Reportes.render(listaT,tema_seleccionado,temas));
    else
    	return ok(Lista_Reportes.render(Reporte.find.all(),tema_seleccionado,temas));
    }

    

 public static String formato(int tipo){

 	String salida="<br><br>";

 	switch (tipo) {

 		case 1:{

 				for (Proyecto elem : Proyecto.ProyectosNoEvento()) {
 					
 					if(elem.C_estado().compareTo("green")==0){
 				
 					salida+="<h4><b>Nombre:</b></h4><p>"+elem.nombre+"</p>";
 					salida+="<h4><b>Estado:</b></h4><p>"+elem.estado+"</p>";
 					salida+="<h4><b>Area:</b></h4><p>"+elem.area+"</p>";
 					salida+="<h4><b>Numero de participantes: </b></h4><p>"+Asistencia_Proyecto.numeroFA(elem)+"</p>";
 					salida+="<h4><b>Fecha Planificada:</b></h4><p>"+elem.tiempo_estimado+"</p>";  
 					salida+="<h4><b>Numero de actividades Propuestas:</b></h4><p>"+Programacion.NProgramacion(elem)+"</p>";
 					salida+="<h4><b>Numero de actividades finalizadas:</b></h4><p>"+Programacion.NPFrogramacion(elem)+"</p>";
 					salida+="<br>";
 					salida+="<legend class=\"text-center\"> </legend>";
 					salida+="<br>";
 				}

}

					salida+="<h4><b>Numero total de proyecto:</b></h4><p>"+Proyecto.ProyectosNoEvento().size()+"</p>";
 					salida+="<legend class=\"text-center\"> </legend>";
 					salida+="<br>";

 break;}
 		case 2:{

 				for (Evento elem : Evento.find.all()) {

 					if(elem.proyecto.C_estado().compareTo("green")==0){
 					
 				
 					salida+="<h4><b>Nombre:</b></h4><p>"+elem.proyecto.nombre+"</p>";
 					salida+="<h4><b>Ubicacion:</b></h4><p>"+elem.ubicacion+"</p>";
 					salida+="<h4><b>Estado:</b></h4><p>"+elem.proyecto.estado+"</p>";
 					salida+="<h4><b>Area:</b></h4><p>"+elem.proyecto.area+"</p>";
 					salida+="<h4><b>Numero de participantes: </b></h4><p>"+Asistencia_Evento.buscarAsistencia(elem.proyecto).size()+"</p>";
 					salida+="<h4><b>Fecha Planificada:</b></h4><p>"+elem.proyecto.tiempo_estimado+"</p>";  
 					salida+="<h4><b>Numero de actividades Propuestas:</b></h4><p>"+Programacion.NProgramacion(elem.proyecto)+"</p>";
 					salida+="<h4><b>Numero de actividades finalizadas:</b></h4><p>"+Programacion.NPFrogramacion(elem.proyecto)+"</p>";
 					salida+="<h4><b>Presupuesto:</b></h4><p>"+elem.costo_total+"</p>";
 					salida+="<br>";
 					salida+="<legend class=\"text-center\"> </legend>";
 					salida+="<br>";}
 				}

					salida+="<h4><b>Numero total de Eventos:</b></h4><p>"+Evento.find.all().size()+"</p>";
 					salida+="<legend class=\"text-center\"> </legend>";
 					salida+="<br>";

 			break;
 		}


 		case 6:{

 			for (Miembro elem : Miembro.find.all()) {
 					

 				if(elem.estado){

 					salida+="<h4><b>Nombre:</b></h4><p>"+elem.nombreCompleto()+"</p>";
 					salida+="<h4><b>nivel de acceso:</b></h4><p>"+elem.nivel_A+"</p>";
 					salida+="<br>";
 					salida+="<h4><b>Actividades donde estaba planificado participar:</b></h4>";
 					salida+="<ol>";
 					for(Asistencia_Evento a:elem.ActividadesEvento())
 						salida+="<li>"+a.evento.proyecto.getNombre()+" participando como "+a.comision+"</li>";
 					salida+="</ol>";
 					salida+="<h4><b>Asistencia de proyecto:</b></h4>";
 					salida+="<ol>";
 					for(Asistencia_Proyecto a:elem.buscarProyecto())
 						if(a.Estado_M)
 					 		salida+="<li>"+a.proyecto.getNombre()+" </li>";
 					salida+="</ol>";
 					
 					salida+="<legend class=\"text-center\"> </legend>";
 					salida+="<br>";
 				}
 				}
					
 			break;}
 		case 5:{

 						for (Reunion elem : Reunion.find.all()) {
 							if(elem.asistencia.size()>0){
								salida+="<h4><b>n° Reunion:</b></h4><p>"+elem.n_Reunion+"</p>";
			 					salida+="<h4><b>fecha de Reunion:</b></h4><p>"+elem.fecha_Reunion+"</p>";
			 					salida+="<br>";
			 					salida+="<h4><b>asistencia:</b></h4>";
			 					salida+="<ol>";

	 					for(Miembro a : Miembro.find.all()){
		 						salida+="<li><p>"+a.nombreCompleto()+" - ";
		 						if(elem.asistencia.contains(a))
		 							salida+="<b style=\"color: green\">asistio</b></p></li>";
		 						else
		 							salida+="<b style=\"color: red\"> no asistio</b></p></li>";
		 					}
		 					salida+="</ol>";
		 					salida+="<br>";
		 					salida+="<h4>n° total de miembro asistido:</h4><p>"+elem.asistencia.size()+"</p>";
		 					salida+="<h4>% de asistencia:</h4><p>"+((elem.asistencia.size()/(Miembro.find.all().size()*1.00)*100))+"</p>";
		 					salida+="<br>";
		 					salida+="<legend class=\"text-center\"> </legend>";
		 					salida+="<br>";
	 				}
 				}
 			break;
 		}


 		case 7:{Logger.debug("Entro");

 				for (Cordinadores elem : Cordinadores.find.all()) {
 						Logger.debug("Entro2");
 				if(elem.miembro.estado){
 					Logger.debug("Entro2");
 					salida+="<h4><b>Cordinador: </b></h4><p>"+elem.miembro.nombreCompleto()+"</p>";
 					salida+="<h4><b>Miembros Actuales: </b></h4><p>"+(Miembro.find.all().size()-1)+"</p>";
 					salida+="<h4><b>Proyecto Actuales: </b></h4><p>"+Proyecto.find.where().eq("estado","Activo").findList().size()+"</p>";
			 		salida+="<h4><b>fecha de Ingreso:</b></h4><p>"+elem.fecha_ingreso+"</p>";
			 		salida+="<h4><b>Estado</b></h4><p>Activo</p>";
			 		salida+="<br>";
			 		salida+="<legend class=\"text-center\"> </legend>";
		 			salida+="<br>";
			 		
 				}else{
 					Logger.debug("Entro3");
 					salida+="<h4><b>Cordinador: </b></h4><p>"+elem.miembro.nombreCompleto()+"</p>";
			 		salida+="<h4><b>fecha de Ingreso:</b></h4><p>"+elem.fecha_ingreso+"</p>";
			 		salida+="<h4><b>fecha de salida:</b></h4><p>"+elem.fecha_Salida+"</p>";
			 		salida+="<br>";
			 		salida+="<legend class=\"text-center\"> </legend>";
		 			salida+="<br>";


 				}
 					
 				}

 		}
 		
 	}


return salida;
 }
 

}


