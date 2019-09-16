package co.sgiraldo.clienteSOAP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.tempuri.Calculator;
import org.tempuri.CalculatorSoap;

public class ConsumeWSSOAP {
	
	/**
	 * Atributo que describe la operación de suma que se realiza
	 *  al consumir el servicios
	 */
	private static final String operationAdd= "Add";
	
	/**
	 * Atributo que describe la operación de division que se realiza
	 *  al consumir el servicios
	 */
	private static final String operationDivide= "Divide";
	
	/**
	 * Atributo que describe la operación de multiplicación que se realiza
	 *  al consumir el servicios
	 */
	private static final String operationMultiply= "Multiply";
	
	/**
	 * Atributo que describe la operación de resta que se realiza
	 *  al consumir el servicios
	 */
	private static final String operationSubtract= "Subtract";
	

	public static void main(String[] args) {

		consumeService();
	}
	
	/**
	 * Metodo para conectar y almacenar los resultados obtenidos al consumir los endpoints
	 * en la Base de datos
	 * @param result Representa el resultado obtenido al realizar la operación
	 * @param operation Representa la información sobre la operación que se realizo
	 */
	public static void insertDB (double result, String operation) {
		
		try (Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres",
				"0325")) {
			if (conn != null) {

				String statement = "INSERT INTO result (result,operation) VALUES (?,?)";

				PreparedStatement pstmt = conn.prepareStatement(statement);

				pstmt.setDouble(1, result);
				pstmt.setString(2, operation);

				pstmt.executeUpdate();
				conn.close();

				System.out.println("Conexión existosa!");

			} else {
				System.out.println("Error de conexión!");
			}
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Metodo para consumir los endpoints de las operaciones en el recurso web
	 */
	public static void consumeService() {

		CalculatorSoap service;

		Calculator calculator = new Calculator();

		service = calculator.getCalculatorSoap();

		double resultMultiply = service.multiply(1, 1);
		
		double resultAdd = service.add(4, 5);
		
		double resultDivide = service.divide(36, 4);
		
		double resultSubtract = service.subtract(25, 19);
		
		/**
		 * Sentencia para consumir el endpoint que representa la 
		 * multiplicación 
		 */
		insertDB(resultMultiply, operationMultiply);
		
		/**
		 * Sentencia para consumir el endpoint que representa la 
		 * suma 
		 */
		insertDB(resultAdd, operationAdd);
		
		/**
		 * Sentencia para consumir el endpoint que representa la 
		 * division 
		 */
		insertDB(resultDivide, operationDivide);
		
		/**
		 * Sentencia para consumir el endpoint que representa la 
		 * resta 
		 */
		insertDB(resultSubtract, operationSubtract);
	
	
	}
	
	
	
	}





