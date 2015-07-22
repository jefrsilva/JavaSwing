package br.com.alura.contas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	public List<ContaAPagar> getContas() {
		List<ContaAPagar> contas = new ArrayList<ContaAPagar>();
		try {
			String sql = "select * from contasapagar";
			PreparedStatement stmt = connection.prepareStatement(sql);
			boolean temResultados = stmt.execute();
			if (temResultados) {
				ResultSet resultado = stmt.getResultSet();
				while (resultado.next()) {
					ContaAPagar conta = constroiConta(resultado);
					contas.add(conta);
				}
				resultado.close();
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contas;
	}

	private ContaAPagar constroiConta(ResultSet resultado) throws SQLException {
		ContaAPagar conta = new ContaAPagar();
		conta.setId(resultado.getLong("id"));
		conta.setCategoria(resultado.getString("categoria"));
		conta.setDescricao(resultado.getString("descricao"));
		conta.setValor(resultado.getDouble("valor"));
		conta.setVencimento(resultado.getDate("vencimento"));
		return conta;
	}
	
	public void remove(ContaAPagar conta) {
		try {
			String sql = "delete from contasapagar where id = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setLong(1, conta.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ContaAPagar busca(Long id) {
		ContaAPagar conta = null;
		try {
			String sql = "select * from contasapagar where id = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setLong(1, id);
			stmt.execute();
			ResultSet resultado = stmt.getResultSet();
			if (resultado != null) {
				resultado.next();
				conta = constroiConta(resultado);
			}
			resultado.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conta;
	}

	public void altera(ContaAPagar conta) {
		try {
			String sql = "update contasapagar SET categoria=?, descricao=?, valor=?, vencimento=?"
					+ " WHERE id=?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, conta.getCategoria());
			stmt.setString(2, conta.getDescricao());
			stmt.setDouble(3, conta.getValor());
			stmt.setDate(4, new java.sql.Date(conta.getVencimento().getTime()));
			stmt.setLong(5, conta.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
