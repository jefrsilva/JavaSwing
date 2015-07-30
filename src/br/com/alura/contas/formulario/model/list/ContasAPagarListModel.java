package br.com.alura.contas.formulario.model.list;

import java.util.List;

import javax.swing.AbstractListModel;

import br.com.alura.contas.modelo.ContaAPagar;

public class ContasAPagarListModel extends AbstractListModel<ContaAPagar> {
	
	private List<ContaAPagar> contas;
	
	public ContasAPagarListModel(List<ContaAPagar> contas) {
		super();
		this.contas = contas;
	}

	@Override
	public int getSize() {
		return contas.size();
	}

	@Override
	public ContaAPagar getElementAt(int index) {
		return contas.get(index);
	}

}
