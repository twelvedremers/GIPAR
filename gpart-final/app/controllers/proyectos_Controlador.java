package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import views.html.include.*;
import models.*;
import play.data.*;
import views.html.proyectos_eventos.*;
import java.util.*;
import java.text.*;
import java.util.Calendar;


public class proyectos_Controlador extends Controller {

  
    public static Result ListarProyectos(){
    	
    	List<Proyecto> listaP=Proyecto.find.all();
    	List<Evento>   listaV=Evento.find.all();
       

        //los proyectos que no son eventos
    	for(Evento elemento:listaV)
    	listaP.remove(elemento.proyecto);
        Form<Formulario> formulario = Form.form(Formulario.class);
        

        /////obtener los temas

        List<String>   temas =new ArrayList<String>();
        for(Proyecto proyecto:listaP)
        {
          temas.add(proyecto.area.toUpperCase());

        }

        	HashSet<String> hashSet = new HashSet<String>(temas);
            temas.clear();
            temas.addAll(hashSet); 


    	return ok(proyectos.render(listaP,formulario,temas));

    }

     
     public static Result Ver(Integer id) {

        List<Recursos> lista=Recursos.find.where().eq("proyecto_id",id).findList() ;
        List<Asistencia_Proyecto> lista2=Asistencia_Proyecto.find.where().eq("proyecto_id",id).findList() ;
        List<Miembro>  lista3=new ArrayList<Miembro>();
        List<Programacion>  lista4=Programacion.findProgramacion(Proyecto.find.byId(id));
        List<Reconocimiento> lista5=Reconocimiento.find.where().eq("proyecto_id",id).findList() ;
        for(Asistencia_Proyecto elemento:lista2)
        {
        if(elemento.Estado_M)
        lista3.add(elemento.miembro);
        }
        

        return ok(vista_proyecto.render(Proyecto.find.byId(id),lista,lista3,lista4,lista5));
    }
  
  


  public static Result RegistrarP(){
        
        Form<Formulario> formulario= Form.form(Formulario.class).bindFromRequest();


 List<Miembro> miembros=Miembro.find.all();
        List<Miembro> posiblesC=new ArrayList<Miembro>();
          for (Miembro a : Miembro.find.all()) {
     	if(!a.estado)
     		miembros.remove(a);
     	
     }
        
        List<Evento> listaV=Evento.find.all();
        List<Proyecto> listaP=Proyecto.find.all();
        
           //los proyectos que no son eventos
    	for(Evento elemento:listaV)
    	listaP.remove(elemento.proyecto);
       
       
        
///////////////////////////////////////////posibles Cordinadores

        int x=0;
        for(Miembro a:miembros){
        x=0;
         for(Proyecto proyecto:listaP){

            if(proyecto.coordinador.cod_G==a.cod_G)
                x++;
         }
         
         if(x<2)
             posiblesC.add(a);

        }

//////////////////////////////validaciones
  

   if(formulario.data().get("titulo").length() > 45 || formulario.data().get("titulo").length() < 2 || formulario.data().get("area").length() > 20 || formulario.data().get("area").length() < 4 || formulario.data().get("contenido").length() > 200 || formulario.data().get("contenido").length() < 4)
{
 flash("invalid", "error al cargar los datoss");
            return badRequest(registrar_proyecto.render(formulario,posiblesC));

}

String strFecha =formulario.data().get("hasta");
 SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy/MM/dd HH:mm");

Date fecha = null;
try {

    fecha = formatoDelTexto.parse(strFecha);

} catch (ParseException ex) {
  

 flash("invalid","problemas de formato fecha");
            return badRequest(registrar_proyecto.render(formulario,posiblesC));
    
}

Date prueba=new Date();
if(prueba.after(fecha)){

flash("invalid", "La fecha aproximada ya ocurrio");
            return badRequest(registrar_proyecto.render(formulario,posiblesC));

}

 
    //////////registro del proyecto
            Proyecto nuevo=new Proyecto(
            formulario.data().get("titulo"),
            formulario.data().get("area"),
            formulario.data().get("contenido"),
            formulario.data().get("cordinador"),
            fecha
        	);

        nuevo.save();


    	return redirect(routes.proyectos_Controlador.agregarProyecto(nuevo.id,1));

    }


      public static Result RegistroP(){
        Form<Formulario> formulario = Form.form(Formulario.class);
        List<Miembro> miembros=Miembro.find.all();
        List<Miembro> posiblesC=new ArrayList<Miembro>();
         for (Miembro a : Miembro.find.all()) {
     	if(!a.estado)
     		miembros.remove(a);
     	
     }
        List<Evento> listaV=Evento.find.all();
        List<Proyecto> listaP=Proyecto.find.all();
        
           //los proyectos que no son eventos
    	for(Evento elemento:listaV)
    	listaP.remove(elemento.proyecto);
       
       

         int x=0;
        for(Miembro a:miembros){
        x=0;
         for(Proyecto proyecto:listaP){

            if(proyecto.coordinador.cod_G==a.cod_G)
                x++;
         }
         
         if(x<2)
             posiblesC.add(a);

        }



    	return ok(registrar_proyecto.render(formulario,posiblesC));

    }







public static Result ListarTemaP(){
         

         Form<Formulario> tema_seleccionado= Form.form(Formulario.class).bindFromRequest();

        
   	     List<Proyecto> listaT=Proyecto.find.where().eq("area",tema_seleccionado.data().get("area").toLowerCase()).findList() ;
    	List<Evento>   listaV=Evento.find.all();
        List<Proyecto> listaP=Proyecto.find.all();
        
        //los proyectos que no son eventos
    	for(Evento elemento:listaV)
    	listaP.remove(elemento.proyecto);


        for(Evento elemento:listaV)
    	listaT.remove(elemento.proyecto);
        

        /////obtener los temas

        List<String>   temas =new ArrayList<String>();
        for(Proyecto proyecto:listaP)
        {
          temas.add(proyecto.area);

        }

        	HashSet<String> hashSet = new HashSet<String>(temas);
            temas.clear();
            temas.addAll(hashSet); 

      if(tema_seleccionado.data().get("area").toLowerCase().compareTo("todos")!=0)
    	return ok(proyectos.render(listaT,tema_seleccionado,temas));
    else
    	return ok(proyectos.render(listaP,tema_seleccionado,temas));
    }




/////////////////////////////////////////////////////////////////////////////////7


    public static Result agregarProyecto(Integer z,int tipo){
      
      Form<Formulario> formulario= Form.form(Formulario.class);
      List<Evento> listaV=Evento.find.all();
      Proyecto nuevo=Proyecto.find.byId(z);
      Map<String,String> productMap= new HashMap<String,String>();
      List<Miembro> miembros=Miembro.find.all();
      miembros.remove(nuevo.coordinador);
      List<Reconocimiento> premios=Reconocimiento.findReconocimientos(nuevo);
        for (Miembro a : Miembro.find.all()) {
     	if(!a.estado)
     		miembros.remove(a);
     	
     }
      List<Miembro> posiblesM=new ArrayList<Miembro>(miembros);
      List<Asistencia_Evento> asistencia=null;
      if(tipo==2)
      {
       asistencia=Asistencia_Evento.find.where().eq("evento_id",z+"").findList();

      }
      
      if(tipo==1){
        List<Proyecto> listaP=Proyecto.find.all();
        
           //los proyectos que no son eventos
    	for(Evento elemento:listaV)
    	listaP.remove(elemento.proyecto);
       
     
       // listo los miembros q participen en menos de tres proyectos
      int veces=0;
       for(Miembro a:miembros){
         veces=0;

         for(Proyecto proyecto:listaP){
          for (Miembro b : Asistencia_Proyecto.buscarAsistencia(nuevo)) {
          	if(a.cod_G==b.cod_G)
          		veces++;
          }
         }
         if(veces<=3)
             posiblesM.add(a);
        }


       // elimino a los que ya participan
        for ( Miembro extraño: Asistencia_Proyecto.buscarAsistencia(nuevo)) {
         	posiblesM.remove(extraño);
         } 


        for (Miembro y:posiblesM ) {
        productMap.put(y.nombreCompleto(),y.cedula);
        	
        }
        
}



if(tipo==1)
  return ok(registrar_ExtrasP.render(nuevo.id,formulario,Asistencia_Proyecto.buscarAsistencia(nuevo),productMap,Programacion.findProgramacion(nuevo),Recursos.findRecursos(nuevo),asistencia,premios,tipo));
else {

List<Miembro> listaMM=Miembro.find.all();
  for (Miembro a : Miembro.find.all()) {
     	if(!a.estado)
     		listaMM.remove(a);
     	
     }
for ( Miembro ss: Asistencia_Evento.buscarAsistencia(nuevo)) {
listaMM.remove(ss);
	
}


 return ok(registrar_ExtrasP.render(nuevo.id,formulario,listaMM,productMap,Programacion.findProgramacion(nuevo),Recursos.findRecursos(nuevo),asistencia,premios,tipo));

}
    }

/////////////////////////////////////////////////////////////////////////////////////////////////
 







    /////////////////////////////////////////////////////////////////////////////////////


  public static Result VerE(Integer id) {
      
       List<Recursos> listaR=Recursos.find.where().eq("proyecto_id",id).findList();
       List<Reconocimiento> listaP=Reconocimiento.find.where().eq("proyecto_id",id).findList() ;
       List<Asistencia_Evento> listaAV=Asistencia_Evento.find.where().eq("evento_id",id).findList();
       List<Programacion>  listaPr=Programacion.findProgramacion(Proyecto.find.byId(id));
       List<Asistencia_Proyecto> listaA=Asistencia_Proyecto.find.where().eq("proyecto_id",id).findList() ;
        return ok(vista_evento.render(Evento.find.byId(id),listaR,listaP,listaAV,listaPr,listaA));
    }

 public static Result ListarEventos(){

 	List<String>   temas =new ArrayList<String>();
 	Form<Formulario> formulario = Form.form(Formulario.class);

        for(Evento evento:Evento.find.all()){
          temas.add(evento.proyecto.getarea());
      }
	    HashSet<String> hashSet = new HashSet<String>(temas);
            temas.clear();
            temas.addAll(hashSet); 

    	return ok(eventos.render(Evento.find.all(),formulario,temas));
    }

public static Result ListarTemaE(){

	Form<Formulario> tema_seleccionado= Form.form(Formulario.class).bindFromRequest();
    List<Evento>   listaV=Evento.find.all();
    List<String>   temas =new ArrayList<String>();
    List<Evento>   listaF=new ArrayList<Evento>();
    Evento elemento;
   
    for(Evento evento:Evento.find.all())
          temas.add(evento.proyecto.getarea());
    
    List<Proyecto> listaT=Proyecto.find.where().eq("area",tema_seleccionado.data().get("area")).findList() ;
	
	for(Proyecto proyecto:listaT){
      elemento=Evento.find.byId(proyecto.id);
      if(elemento!=null)
    	listaF.add(elemento);
    }

    HashSet<String> hashSet = new HashSet<String>(temas);
            temas.clear();
            temas.addAll(hashSet); 

    if(tema_seleccionado.data().get("area").toLowerCase().compareTo("todos")!=0)
    	return ok(eventos.render(listaF,tema_seleccionado,temas));
    else
    	return ok(eventos.render(listaV,tema_seleccionado,temas));
    }

    
  


 


//////////////////////////////////////////////////////////////////////////////////////////

         public static Result RegistroE(){
        Form<Formulario> formulario = Form.form(Formulario.class);
        List<Miembro> miembros=Miembro.find.all();
         for (Miembro a : Miembro.find.all()) {
     	if(!a.estado)
     		miembros.remove(a);
     	
     }

     
    	return ok(Registrar_evento.render(formulario,miembros));

    }

  public static Result RegistrarE(){
        
         Form<Formulario> formulario= Form.form(Formulario.class).bindFromRequest();
         List<Miembro> miembros=Miembro.find.all();
  for (Miembro a : Miembro.find.all()) {
     	if(!a.estado)
     		miembros.remove(a);
     	
     }






 if(formulario.data().get("titulo").length() > 45 || formulario.data().get("titulo").length() < 2 || formulario.data().get("area").length() > 20 || formulario.data().get("area").length() < 4 || formulario.data().get("contenido").length() > 200 || formulario.data().get("contenido").length() < 4  || formulario.data().get("direccion").length()>80 || formulario.data().get("direccion").length()<2 )
{
 flash("invalid", "error al cargar los datos");
            return badRequest(Registrar_evento.render(formulario,miembros));

}

String strFecha =formulario.data().get("hasta");
 SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy/MM/dd HH:mm");

Date fecha = null;
try {

    fecha = formatoDelTexto.parse(strFecha);

} catch (ParseException ex) {
  

 flash("invalid", "problemas de formato fecha");
           return badRequest(Registrar_evento.render(formulario,miembros));
    
}

Date prueba=new Date();
if(prueba.after(fecha)){

flash("invalid", "La fecha aproximada ya ocurrio");
           return badRequest(Registrar_evento.render(formulario,miembros));

}

            Proyecto nuevo=new Proyecto(
            formulario.data().get("titulo"),
            formulario.data().get("area"),
            formulario.data().get("contenido"),
            formulario.data().get("cordinador"),
            fecha
        	);
        
            Evento nuevo2=new Evento(
            nuevo,
            formulario.data().get("valor"),
            formulario.data().get("direccion")
        	);


        nuevo.save();
        nuevo2.save();

     

    	return redirect(routes.proyectos_Controlador.agregarProyecto(nuevo.id,2));

    }


/////////////////////////////////////////////////////////////////////////////////////////////////
 






 
    /////////////////////////////////////////////////////////////////////////////////////



public static Result Participantes(Integer id,Integer t){

Form<Formulario> formulario= Form.form(Formulario.class).bindFromRequest();

Formulario data = Form.form(Formulario.class).bindFromRequest().get();
if(t==1){
Proyecto nuevo=Proyecto.find.byId(id);
List<Miembro> equipo=new ArrayList<Miembro>();

if(data.escritores==null){
  flash("invalid","no se selecciono ningun miembro");
  return redirect(routes.proyectos_Controlador.agregarProyecto(nuevo.id,t));
}

for (String miembro :data.escritores) {
   equipo.add(Miembro.find.where().eq("cedula",miembro).findUnique());
}


Asistencia_Proyecto nuevo2;

for (Miembro a : equipo) {
	nuevo2=new Asistencia_Proyecto(a,nuevo);
    nuevo2.save();
}

}else{

Miembro persona=Miembro.find.where().eq("cedula",data.area).findUnique();
Evento nuevo4=Evento.find.byId(id);
Asistencia_Evento nuevo5=new Asistencia_Evento(persona,nuevo4,data.comision);
    nuevo5.save();
}

return redirect(routes.proyectos_Controlador.agregarProyecto(id,t));
}



public static Result registrarPlanificacion(Integer id,Integer t){


Form<Formulario> data = Form.form(Formulario.class).bindFromRequest();
Formulario formulario=data.get();
Proyecto nuevo=Proyecto.find.byId(id);
Miembro nuevo3=Miembro.find.where().eq("cedula",formulario.persona).findUnique();


   int n;
   List<Programacion> z=Programacion.find.where().eq("proyecto_id",nuevo.id).orderBy("id_programacion desc").findList();
   if(z!=null){
    if(z.size()!=0)
     n=(z.get(0).clave.id_programacion)+1;
    else
    n=1;

   } else
   	n=1;

String strFecha1 =formulario.desde;
String strFecha2 =formulario.hasta;
 SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy/MM/dd HH:mm");

Date fecha1 = null;
Date fecha2 = null;

try {

    fecha1 = formatoDelTexto.parse(strFecha1);
    fecha2 = formatoDelTexto.parse(strFecha2);

} catch (ParseException ex) {
  

 flash("invalid","problemas de formato fecha");
 return redirect(routes.proyectos_Controlador.agregarProyecto(nuevo.id,t));
     
}

Date prueba=new Date();
if(prueba.after(fecha1) || prueba.after(fecha2)){

flash("invalid", "La fecha inscritas ya ocurrieron");
   return redirect(routes.proyectos_Controlador.agregarProyecto(nuevo.id,t));

}

if(fecha1.after(fecha2)){

flash("invalid", "La fechas estan invertidas");
   return redirect(routes.proyectos_Controlador.agregarProyecto(nuevo.id,t));

}




   
Programacion nuevo2=new Programacion(n,
	id,
    fecha1,
    fecha2,
    formulario.actividad,
formulario.persona
	);



nuevo2.save();

	return redirect(routes.proyectos_Controlador.agregarProyecto(nuevo.id,t));

}

public static Result registrarRecurso(Integer id,Integer t){

 Form<Formulario> formulario= Form.form(Formulario.class).bindFromRequest();
 Formulario data = Form.form(Formulario.class).bindFromRequest().get();
Proyecto nuevo=Proyecto.find.byId(id);
 
 Recursos nuevo2=new Recursos(nuevo,data.recurso);
 nuevo2.save();

	return redirect(routes.proyectos_Controlador.agregarProyecto(nuevo.id,t));
}


/////////////////////////////////////////////////////////////////////////////////////////////////
 






 
    /////////////////////////////////////////////////////////////////////////////////////

public static Calendar DateToCalendar(Date date){ 
  Calendar cal = Calendar.getInstance();
  cal.setTime(date);
  return cal;
}

  public static Result ListarReuniones(){
       

        List<Reunion> lista=Reunion.find.all();
        List<Reunion> nuevos=new ArrayList();
        List<Reunion> viejos=new ArrayList();
        List<Reunion> proximo=new ArrayList();

        for ( Reunion n: lista) {
        	if(n.asistencia.size()>0)
        		{
        			viejos.add(n);
        		} else{

        			nuevos.add(n);
        		}
        	
        }

        ///////////calcular los dias de esta semana

        Calendar hoy = Calendar.getInstance();
        int dia = hoy.get(Calendar.DAY_OF_WEEK);

        Calendar limiteSuperior = Calendar.getInstance();
        limiteSuperior.add(Calendar.DATE, 7-dia);
         Calendar limiteInferior = Calendar.getInstance();
         limiteInferior.add(Calendar.DATE, 1-dia);


        for ( Reunion n: nuevos) 
        	if(limiteInferior.before(DateToCalendar(n.fecha_Reunion)) && limiteSuperior.after(DateToCalendar(n.fecha_Reunion)))
        		proximo.add(n);
        	
        
		for ( Reunion n: proximo)
			 nuevos.remove(n);
			
		
    	return ok(lista_reuniones.render(nuevos,proximo,viejos));


    }


    public static Result RegistrarReuniones(){
    Form<Formulario> reunion = Form.form(Formulario.class);
     Miembro miembro=null;
    List<Reunion> lista=Reunion.find.where().orderBy("numero_Reunion desc").findList();
    List<Miembro> listaMiembros=Miembro.find.all();
    Reunion ultimo=lista.get(0);
    if(listaMiembros.indexOf(ultimo.miembro)>=(listaMiembros.size()-1))
    miembro=listaMiembros.get(0);
    else
    miembro=listaMiembros.get(listaMiembros.indexOf(ultimo.miembro)+1);	
    session("escritor",miembro.cedula);
    return ok(Registrar_reunion.render(reunion,miembro));

    }

public static Result Registrar(){

  Form<Formulario> data= Form.form(Formulario.class).bindFromRequest();
   
   int n;
   List<Reunion> z=Reunion.find.where().orderBy("numero_Reunion desc").findList();
   if(z!=null){
    if(z.size()!=0)
     n=(z.get(0).n_Reunion)+1;
    else
    n=1;

   } else
   	n=1;

 Miembro persona=Miembro.find.where().eq("cedula",session().get("escritor")).findUnique();
 session().remove("escritor");


  /// modificar date por valor fecha

String strFecha =data.data().get("desde");
 SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy/MM/dd HH:mm");

Date fecha = null;
try {

    fecha = formatoDelTexto.parse(strFecha);

} catch (ParseException ex) {
  

 flash("invalid","problemas de formato fecha");
            return redirect(routes.proyectos_Controlador.RegistrarReuniones());
    
}

Date prueba=new Date();
if(prueba.after(fecha)){

flash("invalid", "La fecha aproximada ya ocurrio");
            return redirect(routes.proyectos_Controlador.RegistrarReuniones());

}

Reunion nuevo=new Reunion(n,fecha,persona.cod_G);

puntos_Fijos yo= new puntos_Fijos(data.data().get("titulo"),nuevo);
nuevo.save();
yo.save();

return redirect(routes.proyectos_Controlador.registro_extraReunion(nuevo.n_Reunion));
}


public static Result registro_extraReunion(Integer id){
    Reunion reunion=Reunion.find.byId(id);
     Form<Formulario> data= Form.form(Formulario.class);
	session("coordinador","yo");
 
return ok(Registrar_reunionP.render(data,puntos_Fijos.findPunto(reunion),puntos_Varios.findPunto(reunion),id));
}

   public static Result verReuniones(Integer id){

     	Reunion  reunion=Reunion.find.byId(id);
     	List<Miembro> todos=Miembro.find.all();
      
     	List<puntos_Fijos> pf=puntos_Fijos.find.where().eq("reunion_id",id).findList();
     	List<puntos_Varios> pV=puntos_Varios.find.where().eq("reunion_id",id).findList();

    	return ok(vista_reuniones.render(reunion,pf,pV,todos));

    }

    public static Result registrarPunto(Integer id){
     Form<Formulario> data= Form.form(Formulario.class).bindFromRequest();
     Reunion reunion=Reunion.find.byId(id);
   
    if(data.data().get("puntoF")!=null){
     puntos_Fijos nuevo=new puntos_Fijos(data.data().get("puntoF"),reunion);
     nuevo.save();
    }
    else
    {
    puntos_Varios nuevo2=new puntos_Varios(data.data().get("puntoV"),reunion);
     nuevo2.save();
    }
    
   
    

    return redirect(routes.proyectos_Controlador.registro_extraReunion(id));

    }


// borrado de proyecto y evento

  

    public static Result borrarPM(Integer id,Integer m){
    
    if(Evento.find.byId(id)!=null){

    Asistencia2_pk viejo1= new Asistencia2_pk(m,id);
    Asistencia_Evento finals=Asistencia_Evento.find.byId(viejo1);
    finals.delete();
    
    }else{
    Asistencia1_pk viejo2= new Asistencia1_pk(m,id);
     Asistencia_Proyecto finals2=Asistencia_Proyecto.find.byId(viejo2);
    finals2.delete();
    }
    flash("correct","borrado con exito");
     if(Evento.find.byId(id)!=null)
    return redirect(routes.proyectos_Controlador.VerE(id));
     return redirect(routes.proyectos_Controlador.Ver(id));
    }

    public static Result borrarPP(Integer id,Integer p){

  	
    Planificacion_PK dp= new Planificacion_PK(p,id);
     Programacion finals=Programacion.find.byId(dp);
    finals.delete();
    flash("correct","borrado con exito");
    if(Evento.find.byId(id)!=null)
    return redirect(routes.proyectos_Controlador.VerE(id));
    return redirect(routes.proyectos_Controlador.Ver(id));

    }


    public static Result borrarP(Integer id){
    
  	Proyecto viejo=Proyecto.find.byId(id);

  	viejo.delete();
    flash("correct","borrado con exito");
    return redirect(routes.proyectos_Controlador.ListarProyectos());
     
    }


   public static Result borrarE(Integer id){
    Evento viejo=Evento.find.byId(id);
   
    for (Articulo arti :Articulo.find.all()) {

    	if(arti.proyecto!=null){
        
        if(arti.proyecto.id==id)
        {
        	arti.proyecto=null;
        	arti.save();
        }

    	}
    	
    }
  	viejo.delete();
  
    flash("correct","borrado con exito");
    return redirect(routes.proyectos_Controlador.ListarEventos());
     


    }


// borrado de Reuniones y sus puntos

 public static Result borrarR(Integer id){

Reunion.find.ref(id).delete();
  
    flash("correct","borrado con exito");
    return redirect(routes.proyectos_Controlador.ListarReuniones());
  }

 public static Result borrarPF(Integer id,String p){
    

    Puntos_PK clave=new Puntos_PK(p,id);
    puntos_Fijos viejo = puntos_Fijos.find.byId(clave);
    viejo.delete();
    flash("correct","borrado con exito");
    return redirect(routes.proyectos_Controlador.registro_extraReunion(id));
  }

 public static Result borrarPV(Integer id,String p){
 	  Puntos_PK clave=new Puntos_PK(p,id);
      puntos_Varios viejo = puntos_Varios.find.byId(clave);
      viejo.delete();
    flash("correct","borrado con exito");
    return redirect(routes.proyectos_Controlador.registro_extraReunion(id));
  }


public static Result registrarPremio(Integer id,Integer t){

 Form<Formulario> formulario= Form.form(Formulario.class).bindFromRequest();
 Formulario data = Form.form(Formulario.class).bindFromRequest().get();
Proyecto nuevo=Proyecto.find.byId(id);
 
 Reconocimiento nuevo2=new Reconocimiento(data.premio,nuevo);
 nuevo2.save();

	return redirect(routes.proyectos_Controlador.agregarProyecto(nuevo.id,t));
}


/////////////////////////////////////////////////////////////////////////////////////

public static Result MostrarAsistencia(Integer id,Integer tipo){

 Form<Formulario> formulario= Form.form(Formulario.class);
 Map<Miembro,Boolean> lista=new HashMap<Miembro,Boolean>();

if (tipo==1) {

	Proyecto nuevo=Proyecto.find.byId(id);
	List<Miembro> evento=Asistencia_Evento.buscarAsistencia(nuevo);
    
	for (Miembro a : evento){ 
		if (a.estado) 
		lista.put(a,false);
	}
		
	return ok(asistencia.render(formulario,lista,id,tipo));
	
}else if(tipo==2){

    
    Reunion nuevo2=Reunion.find.byId(id);
 
   
	for (Miembro a : Miembro.find.all())
	{ if (a.estado) 
		lista.put(a,false);	
	}
	return ok(asistencia.render(formulario,lista,id,tipo));

}else

	return ok(asistencia.render(formulario,null,id,tipo));


}


public static Result AsistenciaN(Integer id,Integer t){

 Form<Formulario> formulario= Form.form(Formulario.class).bindFromRequest();
 Formulario data=formulario.get();

  if(t==2){

Reunion nuevo=Reunion.find.byId(id);
List<Miembro> miembros=new ArrayList<Miembro>();
for (String x : data.asistido) 
miembros.add(Miembro.find.byId(Integer.parseInt(x)));
nuevo.asistencia=miembros;
nuevo.save();
return redirect(routes.proyectos_Controlador.verReuniones(id));
}else if(t==1){

Proyecto nuevo2=Proyecto.find.byId(id);
List<Miembro> miembros=new ArrayList<Miembro>();
for (String x : data.asistido) 
miembros.add(Miembro.find.byId(Integer.parseInt(x)));
List<Miembro> todos=Asistencia_Evento.buscarAsistencia(nuevo2);

Asistencia_Proyecto asistenciaE;
for (Miembro y :todos ) {
	asistenciaE=new Asistencia_Proyecto(y,nuevo2);
	if(!miembros.contains(y)){
    asistenciaE.Estado_M=false;
	}
	asistenciaE.save();
}
 nuevo2.estado=Proyecto.Estados.Finalizado;

 ////////////encontrar el ultimo reporte
int n;
   List<Reporte> z=Reporte.find.where().orderBy("N_reporte desc").findList();
   if(z!=null){
    if(z.size()!=0)
     n=(z.get(0).N_reporte)+1;
    else
    n=1;
   } else
   	n=1;

/////////////////////////


 Reporte reporte=new Reporte(n,data.contenido,"Evento reporte Final");
 reporte.proyecto=nuevo2;
 reporte.save();

return redirect(routes.proyectos_Controlador.VerE(id));

}

Proyecto nuevo3=Proyecto.find.byId(id);

 nuevo3.estado=Proyecto.Estados.Finalizado;
 
 
 List<Asistencia_Proyecto> lista=Asistencia_Proyecto.buscarAsistenciaT(nuevo3);

for (Asistencia_Proyecto ab : lista) {
	ab.Estado_M=false;
	ab.save();	
}
 
 int n;
   List<Reporte> z=Reporte.find.where().orderBy("N_reporte desc").findList();
   if(z!=null){
    if(z.size()!=0)
     n=(z.get(0).N_reporte)+1;
    else
    n=1;
   } else
   	n=1;


 Reporte reporte=new Reporte(n,data.contenido,"Proyecto reporte Final");
 reporte.proyecto=nuevo3;
 reporte.save();


return redirect(routes.proyectos_Controlador.Ver(id));
}


public static Result actualizar(Integer id,Integer tipo){

Planificacion_PK dp= new Planificacion_PK(tipo,id);
Programacion finals=Programacion.find.byId(dp);

Date hoy=new Date();
if (hoy.after(finals.fecha_programada_inicial)) {
finals.fecha_Real=hoy;
finals.save();	
} else{
finals.fecha_programada_inicial=hoy;
finals.fecha_Real=hoy;
finals.fecha_programada_final=hoy;
finals.save();

}

return redirect(routes.proyectos_Controlador.Ver(id));
}

public static Result modificarGeneral(Integer id,Integer tipo){
 Form<Formulario> formulario= Form.form(Formulario.class);
return ok(editar_proyectos.render(formulario,tipo,id));
}

public static Result CambioGeneral(Integer id,Integer tipo){

    Form<Formulario> formulario = Form.form(Formulario.class).bindFromRequest();

    Formulario data=formulario.get();
    Proyecto nuevo=Proyecto.find.byId(id);


     if (data.titulo!=null && data.titulo.length()!=0 && data.titulo.length()<45 ) {
      nuevo.nombre=data.titulo;
      nuevo.save();
	 }

	  if (data.area!=null && data.area.length()!=0 && data.area.length()<20 ) {
      nuevo.area=data.area;
      nuevo.save();
	 }
	   if (data.contenido!=null && data.contenido.length()!=0 && data.contenido.length()<200 ) {
      nuevo.descripcion=data.contenido;
      nuevo.save();
	 }
     
     if (data.hasta!=null && data.hasta!="") {

       	SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy/MM/dd HH:mm");

		Date fecha = null;
		try {
				fecha = formatoDelTexto.parse(data.hasta);
			} catch (ParseException ex) { }

	  if(!nuevo.tiempo_estimado.after(fecha)){
      nuevo.tiempo_estimado=fecha;
      nuevo.save();
  }
	 }

     

	 if(tipo==2){
 
 		 Evento nuevo2=Evento.find.byId(id);

 		  if (data.direccion!=null && data.direccion.length()!=0 && data.direccion.length()<80 ) {
      		nuevo2.ubicacion=data.direccion;
      		nuevo2.save();
			}

		  if (data.valor!=null && data.valor.length()!=0 && data.valor.length()<80 ) {
      		nuevo2.costo_total=Float.parseFloat(data.valor);
      		nuevo2.save();
			}

	 }
	 
if(tipo==1)
return redirect(routes.proyectos_Controlador.Ver(id));
return redirect(routes.proyectos_Controlador.VerE(id));
}





}

