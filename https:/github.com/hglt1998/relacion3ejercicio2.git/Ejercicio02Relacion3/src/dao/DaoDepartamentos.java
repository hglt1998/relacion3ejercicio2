package dao;

import java.sql.Connection;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import singleton.DBConnection;

public class DaoDepartamentos {
	/*
	 * PROPIEDADES Y MÉTODOS SINGLETON
	 */

	private Connection con = null;

	private static DaoDepartamentos instance = null;

	private DaoDepartamentos() throws SQLException {
		con = DBConnection.getConnection();
	}

	public static DaoDepartamentos getInstance() throws SQLException {
		if (instance == null)
			instance = new DaoDepartamentos();

		return instance;
	}

	/*
	 * M�TODOS PROPIOS DE LA CLASE DAO
	 */

	public void insert(Departamento d) throws SQLException {

		try (PreparedStatement ps = con.prepareStatement(
				"INSERT INTO departamentos (nombre, sueldominimo) VALUES (?, ?)");) {
			ps.setString(1, d.getNombre());
			ps.setDouble(2, d.getSueldoMinimo());
		
			ps.executeUpdate();
		}

	}

	public List<Departamento> findAll() throws SQLException {

		List<Departamento> result = new ArrayList<>();
		boolean hayDatos=false;

		try (PreparedStatement ps = con.prepareStatement("SELECT * FROM departamentos");) {
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				
				hayDatos=true;
				result.add(new Departamento(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("sueldominimo")));
			}
			rs.close();
		}

		if (!hayDatos) {
			result=null;
		}
		return result;
	}

	public Departamento findByPk(int id) throws SQLException {

		Departamento result = null;
		try (PreparedStatement ps = con.prepareStatement("SELECT * FROM departamentos WHERE id = ?");) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				result = new Departamento(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("sueldominimo"));
			}

			rs.close();
		}

		return result;

	}
	
	
	/* 
	 * Preguntar
	 */
	public Departamento findByNombre(String nombre) throws SQLException {

		Departamento result = null;
		try (PreparedStatement ps = con.prepareStatement("SELECT * FROM departamentos WHERE nombre = ?");) {

			ps.setString(1, nombre);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				result = new Departamento(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("sueldominimo"));
			}

			rs.close();
		}

		return result;

	}

	public List<Empleado> findEmpledosDeUnDepartamento(String nombre) throws SQLException {
		
		List<Empleado> result = new ArrayList<>();
		boolean hayDatos=false;
		Departamento depar;

		try (PreparedStatement ps = con.prepareStatement
				("SELECT * FROM empleados e inner join departamentos d on e.idDepartamento=d.id where d.nombre=?");) {

			ps.setString(1, nombre);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				
				hayDatos=true;
				depar=new Departamento(rs.getInt("d.id"),rs.getString("d.nombre"), rs.getDouble("d.sueldominimo"));
						
						
				result.add(new Empleado(rs.getInt("e.id"), rs.getString("e.nombre"), rs.getString("e.apellidos"),
						rs.getDate("e.fecha_nacimiento").toLocalDate(), rs.getFloat("e.sueldo"), depar));
			}
			rs.close();
		}

		if (!hayDatos) {
			result=null;
		}
		return result;
		
		
	}
	
	public List<Empleado> empleadosConSueldoInferiorAlDebido() throws SQLException {
		List<Empleado> result = new ArrayList<Empleado>();
		boolean hayDatos = false;
		Departamento depar;
		
		
		try (PreparedStatement ps = con.prepareStatement("SELECT * FROM empleados e INNER JOIN departamentos d ON e.idDepartamento = d.id WHERE e.sueldo < d.sueldominimo ")){
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				hayDatos = true;
				depar = new Departamento(rs.getInt("d.id"), rs.getString("d.nombre"), rs.getDouble("d.sueldominimo"));
				
				result.add(new Empleado(rs.getInt("e.id"), rs.getString("e.nombre"), rs.getString("e.apellidos"), rs.getDate("e.fecha_nacimiento").toLocalDate(), rs.getFloat("e.sueldo"), depar));
			}
			rs.close();
		}
		
		if (!hayDatos) {
			result = null;
		}
		
		return result;
	}

	public void deleteByNombre(String nombre) throws SQLException, EmpleadoException {
		Departamento departamento=findByNombre(nombre);
		if (departamento==null) {
			throw new EmpleadoException("No existe el departamento con nombre " + nombre);
		}
		
		//Faltaría antes de borrar asignar a los empleados de este departamento el "1.SIN ASIGNAR")
		
		try ( PreparedStatement ps = 
				con.prepareStatement(
			"UPDATE empleados SET idDepartamento=1 WHERE idDepartamento=?") ){
			
			ps.setInt(1, departamento.getId());

			ps.executeUpdate();

		}

		try ( PreparedStatement ps = con.prepareStatement("DELETE FROM departamentos WHERE id = ?");) {
			
			ps.setInt(1, departamento.getId());

			ps.executeUpdate();

		}
		
	}

}
