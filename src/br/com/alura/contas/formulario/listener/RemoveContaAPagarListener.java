package br.com.alura.contas.formulario.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.alura.contas.dao.ContaAPagarDAO;
import br.com.alura.contas.formulario.ListaContasAPagar;
import br.com.alura.contas.modelo.ContaAPagar;

public class RemoveContaAPagarListener implements ActionListener {

	private ListaContasAPagar listaContasAPagar;

	public RemoveContaAPagarListener(ListaContasAPagar dialog) {
		super();
		this.listaContasAPagar = dialog;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int resposta = JOptionPane.showConfirmDialog(listaContasAPagar,
				"Deseja mesmo remover esta conta a pagar?",
				"Confirmar exclusão", JOptionPane.YES_NO_OPTION);

		if (resposta == JOptionPane.YES_OPTION) {
			Long id = listaContasAPagar.getIdContaSelecionada();
			
			ContaAPagar conta = new ContaAPagar();
			conta.setId(id);

			ContaAPagarDAO contasAPagarDAO = new ContaAPagarDAO();
			contasAPagarDAO.remove(conta);
			contasAPagarDAO.fecha();
			
			listaContasAPagar.carregaLista();
		}
	}

}
