/**
 * 
 */
package vista;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Scanner;

import dao.*;

public class EjemploDaoEmpleados {

	private static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {

		try {
			int opcion;

			do {
				menu();
				System.out.print("Introduzca su opción: ");
				opcion = Integer.parseInt(teclado.nextLine());

				switch (opcion) {
				case 0:
					DaoEmpleados dao = DaoEmpleados.getInstance();
					dao.cerrarSesion();
					break;
				case 1:
					listarTodosEmpleados();
					break;
				case 2:
					listarUnEmpleado();
					break;
				case 3:
					nuevoEmpleado();
					break;
				case 4: // os dejo pendiente actuilizar el departamento
					actualizarUnEmpleado();
					break;
				case 5:
					eliminarUnEmpleado();
					break;
				case 6:
					nuevoDepartamento();
					break;

				case 7:
					listarTodosDepartamentos();
					break;

				case 8:
					listarUnDepartamentoPorNombre();
					break;

				case 9:
					listarEmpleadosDeUnDepartamento();

					break;

				case 10:
					borrarUnDepartamento();
					break;

				}

			} while (opcion != 0);
		} catch (SQLException ex) {
			System.out.println("Error: " + ex.getMessage());
		}

	}

	private static void borrarUnDepartamento() throws SQLException {

		DaoDepartamentos daoDepartamento = DaoDepartamentos.getInstance();

		System.out.print("Introduzca el  nombre del departamento: ");
		String nombre = teclado.nextLine();

		System.out.println(
				"¿Está usted seguro (S/N) ? Si se borra el departamento los empleados asignados a él se asignarán al departamento 1-SIN ASIGNAR");
		char respuesta = Character.toUpperCase(teclado.nextLine().charAt(0));

		if (respuesta == 'S') {

			try {
				daoDepartamento.deleteByNombre(nombre);
				System.out.println("Departamento borrado");

			} catch (EmpleadoException e) {
				System.out.println(e.getMessage());
			}
		}

		System.out.println("");
	}

	private static void listarEmpleadosDeUnDepartamento() throws SQLException {

		DaoDepartamentos dao = DaoDepartamentos.getInstance();
		System.out.println("Búsqueda de un departamento");
		System.out.print("Introduzca el  nombre del departamento: ");
		String nombre = teclado.nextLine();

		List<Empleado> lista = null;

		lista = dao.findEmpledosDeUnDepartamento(nombre);

		if (lista != null)

			for (Empleado e : lista) {
				System.out.println(e);
			}
		else
			System.out.println("No hay empleados en ese departamento o no existe el departamento");

		System.out.println("");
	}

	private static void listarUnDepartamentoPorNombre() throws SQLException {
		
		DaoDepartamentos dao = DaoDepartamentos.getInstance();
		System.out.println("Búsqueda de un departamento");
		System.out.print("Introduzca el  nombre del departamento: ");
		String nombre = teclado.nextLine();

		Departamento departamento = dao.findByNombre(nombre);

		if (departamento != null) {
			System.out.println(departamento);
		} else {
			System.out.println("No existe ningún departamento con el nombre " + nombre);
		}

		System.out.println("");
	}

	private static void listarTodosDepartamentos() throws SQLException {

		DaoDepartamentos dao = DaoDepartamentos.getInstance();
		List<Departamento> lista = null;

		lista = dao.findAll();

		if (lista != null)

			for (Departamento d : lista) {
				System.out.println(d);
			}
		else
			System.out.println("No hay departamentos registrados en la base de datos");

		System.out.println("");
	}

	private static void nuevoDepartamento() throws SQLException {

		DaoDepartamentos dao = DaoDepartamentos.getInstance();
		System.out.println("Inserción de un nuevo departamento");
		System.out.print("Introduzca el nombre del departamento: ");
		String nombre = teclado.nextLine();

		System.out.print("Introduzca el sueldo mínimo de este departamento: ");
		double sueldo = Double.parseDouble(teclado.nextLine());

		dao.insert(new Departamento(nombre, sueldo));
		System.out.println("Nuevo departamento insertado");

		System.out.println("");
	}

	public static void menu() {

		System.out.println("SISTEMA DE GESTIÓN DE EMPLEADOS");
		System.out.println("===============================");
		System.out.println("0. Salir");
		System.out.println("1. Listar todos los empleados");
		System.out.println("2. Listar un empleado por su ID");
		System.out.println("3. Insertar un nuevo empleado");
		System.out.println("4. Actualizar un empleado");
		System.out.println("5. Eliminar un empleado");
		System.out.println("6. Insertar nuevo departamento");
		System.out.println("7. Listar todos los departamentos");
		System.out.println("8. Listar departamento por su nombre");
		System.out.println("9. Listar los empleados de un departamento");
		System.out.println("10. Borrar un departamento");

	}

	private static void listarTodosEmpleados() throws SQLException {
		DaoEmpleados dao = DaoEmpleados.getInstance();
		List<Empleado> lista = null;

		lista = dao.findAll();

		if (lista != null)

			for (Empleado e : lista) {
				System.out.printf("%d %s %s\t\t (%s) - %.2f  %s %n", e.getId(), e.getNombre(), e.getApellido(),
						e.getFechaNacimiento().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
						e.getSueldo(), e.getDepartamento().getNombre());
			}
		else
			System.out.println("No hay empleados registrados en la base de datos");

		System.out.println("");

	}

	private static void listarUnEmpleado() throws SQLException {
		DaoEmpleados dao = DaoEmpleados.getInstance();
		System.out.println("Búsqueda de un empleado");
		System.out.print("Introduzca el ID del empleado: ");
		int id = Integer.parseInt(teclado.nextLine());

		Empleado empleado = null;

		empleado = dao.findByPk(id);

		if (empleado != null) {
			System.out.printf("%s %s\t\t (%s) - %.2f�  %s %n", empleado.getNombre(), empleado.getApellido(),
					empleado.getFechaNacimiento().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
					empleado.getSueldo(),empleado.getDepartamento().getNombre() );
		} else {
			System.out.println("No existe ning�n registro con ese ID");
		}

		System.out.println("");

	}

	private static void nuevoEmpleado() throws SQLException {
		DaoEmpleados dao = DaoEmpleados.getInstance();
		System.out.println("Inserción de un nuevo empleado");
		System.out.print("Introduzca el nombre del empleado: ");
		String nombre = teclado.nextLine();
		System.out.print("Introduzca los apellidos del empleado: ");
		String apellidos = teclado.nextLine();
		System.out.print("Introduzca la fecha de nacimiento (dd/mm/aaaa) : ");
		String strFecha = teclado.nextLine();
		LocalDate fecha = LocalDate.parse(strFecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		System.out.print("Introduzca el sueldo del empleado: ");
		float sueldo = Float.parseFloat(teclado.nextLine());

		System.out.print("Introduzca el nombre del departamento al que pertenece: ");
		String nombreDepartamento = teclado.nextLine();

		DaoDepartamentos daoDepart = DaoDepartamentos.getInstance();

		Departamento departamento = null;

		departamento = daoDepart.findByNombre(nombreDepartamento);

		
		try {
			if (departamento != null) {

				dao.insert(new Empleado(nombre, apellidos, fecha, sueldo, departamento));
				System.out.println("Nuevo registro insertado");
			} else {
				System.out.println("No existe ningún departamento con el nombre " + nombre);
			}
		}catch( EmpleadoException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("");

	}

	private static void actualizarUnEmpleado() throws SQLException {
		DaoEmpleados dao = DaoEmpleados.getInstance();
		System.out.println("Actualización de un empleado");
		System.out.print("Introduzca el ID del empleado: ");
		int id = Integer.parseInt(teclado.nextLine());

		Empleado empleado = null;

		empleado = dao.findByPk(id);

		if (empleado == null) {
			System.out.println("El empleado a modificar no est� registrado en la base de datos");
		} else {
			System.out.print("Introduzca el nombre del empleado: ");
			String nombre = teclado.nextLine();
			System.out.print("Introduzca los apellidos del empleado: ");
			String apellidos = teclado.nextLine();
			System.out.print("Introduzca la fecha de nacimiento (dd/mm/aaaa) : ");
			String strFecha = teclado.nextLine();
			LocalDate fecha = LocalDate.parse(strFecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			System.out.print("Introduzca el sueldo del empleado: ");
			float sueldo = Float.parseFloat(teclado.nextLine());
			empleado.setNombre(nombre);
			empleado.setApellido(apellidos);
			empleado.setFechaNacimiento(fecha);
			empleado.setSueldo(sueldo);

			dao.update(empleado);
			System.out.println("Registro actualizado");

		}

		System.out.println("");

	}

	private static void eliminarUnEmpleado() throws SQLException {
		DaoEmpleados dao = DaoEmpleados.getInstance();
		System.out.println("Borrado de un empleado");
		System.out.print("Introduzca el ID del empleado: ");
		int id = Integer.parseInt(teclado.nextLine());

		System.out.println("¿Está usted seguro de eliminar dicho registro? (S/N)");
		String opcion = teclado.nextLine();

		if (opcion.equalsIgnoreCase("S")) {

			try {
				dao.delete(id);
				System.out.println("Empleado eliminado");
			} catch (EmpleadoException e) {
				System.out.println(e.getMessage());
			}

		}

		System.out.println("");

	}

}
