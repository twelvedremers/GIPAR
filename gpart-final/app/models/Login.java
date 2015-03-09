package models;

public class Login {

	public String usuario;
	public String contraseña;
	public String contraseña_act;
	public String contraseña_new;
	public String contraseña_rep;
	public String tipo;
	public String acceso;
	public String nombre;
	public String apellidos;
	public String correo;
	public String telefono;
	public String semestre;
	public String cedula;
	public String area;
	public String area2;
	public String ocupacion;


	public Login(){


	}

	public Login(String a,String b,String c,String d){
    this.usuario=a;
    this.contraseña=b;
    this.tipo=c;
    this.acceso=d;
	}



	public String getUsuario(){


		return usuario;
	}

}
