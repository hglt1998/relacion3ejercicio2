/**
 * CLASE MODELO QUE REPRESENTA LOS DATOS DE UN EMPLEADO.
 */
package dao;

import java.time.LocalDate;

/**
 * @author Openwebinars
 *
 */
public class Empleado {
	
	private int id;
	private String nombre;
	private String apellido;
	private LocalDate fechaNacimiento;
	private float sueldo;
	private Departamento departamento;
	
	

	
	public Empleado(String nombre, String apellido, LocalDate fechaNacimiento, float sueldo, Departamento depar) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.sueldo = sueldo;
		this.departamento=depar;
	}

	
	public Empleado(int id, String nombre, String apellido, LocalDate fechaNacimiento, float sueldo, Departamento depar) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.sueldo = sueldo;
		this.departamento=depar;
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


	public String getApellido() {
		return apellido;
	}


	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}


	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}


	public float getSueldo() {
		return sueldo;
	}


	public void setSueldo(float sueldo) {
		this.sueldo = sueldo;
	}

	

	public Departamento getDepartamento() {
		return departamento;
	}


	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empleado other = (Empleado) obj;
		if (id != other.id)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Empleado [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", fechaNacimiento="
				+ fechaNacimiento + ", sueldo=" + sueldo + ", departamento=" + departamento + "]";
	}


	
	
	
	
	

}
