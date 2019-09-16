package co.sgiraldo.clienteREST;

import java.lang.annotation.Target;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class ConsumeWSREST {
	
    /**
     * Atributo que representa la dirección el REST a consumir
     */
	private static final String REST_URI = "https://jsonplaceholder.typicode.com/users";

	/**
	 * Instancia que representa el cliente para acceder al recurso web
	 */
	private static Client client = ClientBuilder.newClient();
	
	/**
	 * Metodo que obtiene los elementos del recurso web y los almacena en un arreglo
	 * de tipo usuario
	 * @return
	 */
	public static User[] getJsonUser() {
		
		return client.target(REST_URI).request(MediaType.APPLICATION_JSON).get(User[].class);
		
	}

	/**
	 * Metodo que realiza la conexión a la base de datos y realiza la inserción
	 * de los datos en sus respectivas tablas.
	 * @param usuarios
	 */
	public static void insertResultDB (User[] usuarios) {
		
		try (Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres",
				"0325")) {
			
			if (conn != null) {
				
				for(int i=0; i<usuarios.length;i++) {
				
				/**
				 * Sentencias para ingresar los datos correspondientes a la
				 * compañia de un usuario en su respectiva tabla	
				 */
				String statementCompany = "INSERT INTO company (name,catchPhrase,bs) VALUES (?,?,?)";

				PreparedStatement pstmt = conn.prepareStatement(statementCompany);
				pstmt.setString(1, usuarios[i].getCompany().getName());;
				pstmt.setString(2, usuarios[i].getCompany().getCatchPhrase());
				pstmt.setString(3, usuarios[i].getCompany().getBs());
				pstmt.executeUpdate();
				
				/**
				 * Sentencias para ingresar los datos correspondientes a un 
				 * usuario en su respectiva tabla	
				 */
				String statementUser = "INSERT INTO usuario (id,name,username,email,phone,website,company_id) VALUES (?,?,?,?,?,?,?)";

				PreparedStatement pstmtUser = conn.prepareStatement(statementUser);
				pstmtUser.setInt(1, usuarios[i].getId());;
				pstmtUser.setString(2, usuarios[i].getName());
				pstmtUser.setString(3, usuarios[i].getUsername());
				pstmtUser.setString(4, usuarios[i].getEmail());
				pstmtUser.setString(5, usuarios[i].getPhone());
				pstmtUser.setString(6, usuarios[i].getWebsite());
				pstmtUser.setString(7, usuarios[i].getCompany().getName());
				pstmtUser.executeUpdate();
				
				/**
				 * Sentencias para ingresar los datos correspondientes a la dirección
				 * de un usuario determina en su respectiva tabla	
				 */
				String statementAddress = "INSERT INTO address (street,suite,city,zipcode,user_id) VALUES (?,?,?,?,?)";

				PreparedStatement pstmtAddress = conn.prepareStatement(statementAddress);
				pstmtAddress.setString(1, usuarios[i].getAddress().getStreet());;
				pstmtAddress.setString(2, usuarios[i].getAddress().getSuite());
				pstmtAddress.setString(3, usuarios[i].getAddress().getCity());
				pstmtAddress.setString(4, usuarios[i].getAddress().getZipcode());
				pstmtAddress.setInt(5, usuarios[i].getId());
				pstmtAddress.executeUpdate();
				
				/**
				 * Sentencias para ingresar los datos correspondientes a la geoposición
				 * asociada a una dirección en su respectiva tabla	
				 */
				String statementGeo = "INSERT INTO geo (lat,lng,address_user_id) VALUES (?,?,?)";

				PreparedStatement pstmtGeo = conn.prepareStatement(statementGeo);
				pstmtGeo.setString(1, usuarios[i].getAddress().getGeo().getLat());;
				pstmtGeo.setString(2, usuarios[i].getAddress().getGeo().getLng());
				pstmtGeo.setInt(3, usuarios[i].getId());
				pstmtGeo.executeUpdate();

				System.out.println("Conexión existosa!");
				
				}

			} else {
				System.out.println("Error de conexión!");
			}
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {

		User[] usuarios = getJsonUser();
		
		insertResultDB(usuarios);
		
		System.out.println(usuarios.length);
		
	}
}
