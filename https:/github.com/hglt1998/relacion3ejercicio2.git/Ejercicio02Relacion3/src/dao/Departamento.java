package dao;

public class Departamento {

	private int id;
	private String nombre;
	private double sueldoMinimo;
	public Departamento(int id, String nombre, double sueldoMinimo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.sueldoMinimo = sueldoMinimo;
	}
	public Departamento(String nombre, double sueldoMinimo) {
		super();
		this.nombre = nombre;
		this.sueldoMinimo = sueldoMinimo;
	}
	public int getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getSueldoMinimo() {
		return sueldoMinimo;
	}
	public void setSueldoMinimo(double sueldoMinimo) {
		this.sueldoMinimo = sueldoMinimo;
	}
	@Override
	public String toString() {
		return "Departamento [id=" + id + ", nombre=" + nombre + ", sueldoMinimo=" + sueldoMinimo + "]";
	}
	
	
	
}
