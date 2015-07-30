package br.com.alura.contas.formulario.model.table;

import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.alura.contas.modelo.ContaAPagar;

public class ContasAPagarTableModel extends AbstractTableModel {

	public static final int ID = 0;
	public static final int CATEGORIA = 1;
	public static final int DESCRICAO = 2;
	public static final int VALOR = 3;
	public static final int VENCIMENTO = 4;
	
	private String[] colunas = { "Id", "Categoria", "Descrição", "Valor",
			"Vencimento" };
	private List<ContaAPagar> dados;

	public ContasAPagarTableModel(List<ContaAPagar> dados) {
		super();
		this.dados = dados;
	}

	@Override
	public int getRowCount() {
		return dados.size();
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ContaAPagar conta = dados.get(rowIndex);
		switch (columnIndex) {
		case ID:
			return conta.getId();
		case CATEGORIA:
			return conta.getCategoria();
		case DESCRICAO:
			return conta.getDescricao();
		case VALOR:
			return conta.getValor();
		case VENCIMENTO:
			return conta.getVencimento();
		default:
			return null;
		}
	}

	@Override
	public String getColumnName(int column) {
		return colunas[column];
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex) {
		case VALOR:
			return Double.class;
		case VENCIMENTO:
			return Date.class;
		default:
			return super.getColumnClass(columnIndex);	
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
