package br.com.alura.contas.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:mysql://localhost/contas",
					"root", "root");
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

}
