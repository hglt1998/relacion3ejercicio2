/**
 * EJEMPLO DE IMPLEMENTACI�N DEL PATR�N DAO CON LA TABLA PERSONAS
 * OFRECEMOS LOS M�TODOS 
 * 
 * - insert
 * - findAll
 * - findByPk
 * - update
 * - delete
 * 
 * TAMBI�N IMPLEMENTAMOS EL PATR�N SINGLETON
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import singleton.DBConnection;

/**
 * @author Openwebinars
 *
 */
public class DaoEmpleados {

	/*
	 * PROPIEDADES Y M�TODOS SINGLETON
	 */

	private Connection con = null;

	private static DaoEmpleados instance = null;

	private DaoEmpleados() throws SQLException {
		con = DBConnection.getConnection();
	}

	public static DaoEmpleados getInstance() throws SQLException {
		if (instance == null)
			instance = new DaoEmpleados();

		return instance;
	}

	/*
	 * M�TODOS PROPIOS DE LA CLASE DAO
	 */

	public void insert(Empleado e) throws SQLException, EmpleadoException {
		
		// Aunque esto ya e comprobó en el main, sería mejor comprobarlo aquí también,
		// que el departamento con ese id existe
		
		Departamento departamento = DaoDepartamentos.getInstance().findByPk(e.getDepartamento().getId());
		if ( departamento==null) {
			throw new EmpleadoException("No existe el departamento " + e.getDepartamento().getId() );
		}

		try (PreparedStatement ps = con.prepareStatement(
				"INSERT INTO empleados (nombre, apellidos, fecha_nacimiento, sueldo,idDepartamento) VALUES (?, ?, ?, ?,?)");) {
			ps.setString(1, e.getNombre());
			ps.setString(2, e.getApellido());
			ps.setDate(3, Date.valueOf(e.getFechaNacimiento()));
			ps.setFloat(4, e.getSueldo());
			ps.setInt(5, e.getDepartamento().getId());

			ps.executeUpdate();
		}

	}

	public List<Empleado> findAll() throws SQLException {

		List<Empleado> result = new ArrayList<>();
		boolean hayDatos=false;
		Departamento dep;

		try (PreparedStatement ps = con.prepareStatement("SELECT * FROM empleados e inner join departamentos d on e.idDepartamento=d.id");) {
			ResultSet rs = ps.executeQuery();

			
			while (rs.next()) {
				
				hayDatos=true;
				
				dep=new Departamento(rs.getInt("d.id"), rs.getString("d.nombre"), rs.getDouble("d.sueldominimo"));
				result.add(new Empleado(rs.getInt("e.id"), rs.getString("e.nombre"), rs.getString("e.apellidos"),
						rs.getDate("e.fecha_nacimiento").toLocalDate(), rs.getFloat("e.sueldo"),dep));
			}
			rs.close();
		}

		if (!hayDatos) {
			result=null;
		}
		return result;
	}

	public Empleado findByPk(int id) throws SQLException {

		Empleado result = null;
		Departamento dep;
		try (PreparedStatement ps = con.prepareStatement("SELECT * FROM empleados e inner join departamentos d on e.idDepartamento=d.id WHERE e.id = ?");) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				
				dep=new Departamento(rs.getInt("d.id"), rs.getString("d.nombre"), rs.getDouble("d.sueldominimo"));
				result = new Empleado(rs.getInt("e.id"), rs.getString("e.nombre"), rs.getString("e.apellidos"),
						rs.getDate("e.fecha_nacimiento").toLocalDate(), rs.getFloat("e.sueldo"), dep);
			}

			rs.close();
		}

		return result;

	}

	
	public void delete(int id) throws SQLException, EmpleadoException {
		Empleado empleado=findByPk(id);
		if (empleado==null) {
			throw new EmpleadoException("No existe el empleado con id " + id);
		}

		try ( PreparedStatement ps = con.prepareStatement("DELETE FROM empleados WHERE id = ?");) {
			
			ps.setInt(1, id);

			ps.executeUpdate();

		}
	}

	public void update(Empleado e) throws SQLException {

		if (e.getId() == 0)
			return;
		
		// Os dejo este apartado pendiente de terminar
		
		// Faltaría actualizar el departamento
		
		// Abría que comprobar que el departamento con ese código existe
		
		// La actualización tendría que hacer dos UPDATE, por si se han modificado datos de la tabla
		// departamento

		try( PreparedStatement ps = con.prepareStatement(
				"UPDATE empleados SET nombre = ?, apellidos = ?, fecha_nacimiento = ?, sueldo = ? WHERE id = ?");){

		ps.setString(1, e.getNombre());
		ps.setString(2, e.getApellido());
		ps.setDate(3, Date.valueOf(e.getFechaNacimiento()));
		ps.setFloat(4, e.getSueldo());
		ps.setInt(5, e.getId());

		ps.executeUpdate();

	
		}

	}

	public void cerrarSesion() throws SQLException {
		con.close();

	}

}
