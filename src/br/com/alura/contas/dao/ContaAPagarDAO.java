package br.com.alura.contas.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.alura.contas.modelo.ContaAPagar;

public class ContaAPagarDAO {

	private Connection connection;

	public ContaAPagarDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}

	public void insere(ContaAPagar conta) {
		try {
			String sql = "insert into contasapagar (categoria, descricao, valor, vencimento)"
					+ " values (?, ?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, conta.getCategoria());
			stmt.setString(2, conta.getDescricao());
			stmt.setDouble(3, conta.getValor());
			stmt.setDate(4, new java.sql.Date(conta.getVencimento().getTime()));
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void fecha() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
