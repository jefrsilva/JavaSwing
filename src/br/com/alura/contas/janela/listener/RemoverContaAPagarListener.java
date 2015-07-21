package br.com.alura.contas.janela.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.alura.contas.dao.ContaAPagarDAO;
import br.com.alura.contas.modelo.ContaAPagar;

public class RemoverContaAPagarListener implements ActionListener {

	private JFrame frame;
	private JTable tabelaContas;

	public RemoverContaAPagarListener(JFrame frame, JTable tabelaContas) {
		super();
		this.frame = frame;
		this.tabelaContas = tabelaContas;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int resposta = JOptionPane.showConfirmDialog(frame,
				"Deseja mesmo remover esta conta a pagar?",
				"Confirmar exclusão", JOptionPane.YES_NO_OPTION);

		if (resposta == JOptionPane.YES_OPTION) {
			int linhaSelecionada = tabelaContas.getSelectedRow();
			Long id = (Long) tabelaContas.getValueAt(linhaSelecionada, 0);

			ContaAPagar conta = new ContaAPagar();
			conta.setId(id);

			ContaAPagarDAO contasAPagarDAO = new ContaAPagarDAO();
			contasAPagarDAO.remove(conta);
			contasAPagarDAO.fecha();

			DefaultTableModel modelo = (DefaultTableModel) tabelaContas
					.getModel();
			modelo.removeRow(linhaSelecionada);
		}
	}

}
