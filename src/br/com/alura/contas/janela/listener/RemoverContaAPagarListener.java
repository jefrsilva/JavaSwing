package br.com.alura.contas.janela.listener;	

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.alura.contas.dao.ContaAPagarDAO;
import br.com.alura.contas.modelo.ContaAPagar;

public class RemoverContaAPagarListener implements ActionListener {

	private JTable tabelaContas;
	
	public RemoverContaAPagarListener(JTable tabelaContas) {
		super();
		this.tabelaContas = tabelaContas;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int linhaSelecionada = tabelaContas.getSelectedRow();
		Long id = (Long) tabelaContas.getValueAt(linhaSelecionada, 0);
		
		ContaAPagar conta = new ContaAPagar();
		conta.setId(id);
		
		ContaAPagarDAO contasAPagarDAO = new ContaAPagarDAO();
		contasAPagarDAO.remove(conta);
		contasAPagarDAO.fecha();
		
		DefaultTableModel modelo = (DefaultTableModel) tabelaContas.getModel();
		modelo.removeRow(linhaSelecionada);
	}

}
