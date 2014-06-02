package pfc.obj;

import java.util.ArrayList;

public class Ejercicio {
	
	private int idEjercicio;
	private String nombre;
	private ArrayList<Integer> objetos;
	private double duracion;
	
	public Ejercicio() {
		this.idEjercicio=-1;
		this.nombre="";
		this.objetos=new ArrayList<Integer>();
		this.duracion=0.0;
	}

	public Ejercicio(int idEjercicio, String nombre, ArrayList<Integer> objetos, double duracion) {
		this.idEjercicio = idEjercicio;
		this.nombre = nombre;
		this.objetos = new ArrayList<Integer>(objetos);
		this.duracion=duracion;
	}
	
	public Ejercicio(int idEjercicio, String nombre, String objetos, double duracion) {
		try {
			this.idEjercicio = idEjercicio;
			this.nombre = nombre;
			this.objetos = extra.Utils.ArrayListFromJson(objetos);	
			this.duracion=duracion;
		}catch (Exception e){
			new Ejercicio();
		}
	}

	@Override
	public String toString() {
		return "Ejercicio [idEjercicio=" + idEjercicio + ", nombre=" + nombre
				+ ", objetos=" + objetos + duracion + "]";
	}

	public Integer getIdEjercicio() {
		return idEjercicio;
	}

	public void setIdEjercicio(Integer idEjercicio) {
		this.idEjercicio = idEjercicio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Integer> getObjetos() {
		return objetos;
	}

	public void setObjetos(ArrayList<Integer> objetos) {
		this.objetos = objetos;
	}	
	
	public double getDuracion() {
		return duracion;
	}

	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}


}
