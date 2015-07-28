package br.com.alura.contas.formulario.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import br.com.alura.contas.dao.ContaAPagarDAO;
import br.com.alura.contas.formulario.FormularioContasAPagar;
import br.com.alura.contas.modelo.ContaAPagar;

public class InsereOuAlteraContaAPagarListener implements ActionListener {

	private FormularioContasAPagar formulario;

	public InsereOuAlteraContaAPagarListener(FormularioContasAPagar formulario) {
		this.formulario = formulario;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Inserir")) {
			insere();
		} else if (e.getActionCommand().equals("Alterar")){
			altera();
		}
	}

	private void insere() {
		ContaAPagarDAO dao = new ContaAPagarDAO();
		ContaAPagar conta = formulario.pegaContaDoFormulario();
		dao.insere(conta);
		dao.fecha();

		formulario.limpaCampos();

		JOptionPane.showMessageDialog(formulario,
				"Conta a pagar incluída com sucesso!");
	}

	private void altera() {
		ContaAPagarDAO dao = new ContaAPagarDAO();
		ContaAPagar contaAlterada = formulario.pegaContaDoFormulario();
		contaAlterada.setId(contaAlterada.getId());
		dao.altera(contaAlterada);
		dao.fecha();

		JOptionPane.showMessageDialog(formulario,
				"Conta a pagar alterada com sucesso!");
		formulario.dispose();
	}

}
