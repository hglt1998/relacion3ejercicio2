/**
 * CLASE QUE IMPLEMENTA EL PATR�N SINGLETON PARA OBTENER LA CONSULTA A LA BASE DE DATOS
 */
package singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Openwebinars
 *
 */
public class DBConnection {
	
	private static final String CONTRASENNA = "x1k00sdc";

	private static final String USUARIO = "USER06";

	private static final String JDBC_URL = "jdbc:mysql://iescristobaldemonroy.duckdns.org:8081";
	
	private static Connection instance = null;
	
	private DBConnection() throws SQLException {
		
		Properties props = new Properties();
		props.put("user", USUARIO);
		props.put("password", CONTRASENNA);
		instance = DriverManager.getConnection(JDBC_URL, USUARIO,CONTRASENNA);
		
	
		
	
	}
	
	public static Connection getConnection() throws SQLException {
		if (instance == null) {
			new DBConnection();
		}
		
		return instance;
	}
	

}
