package br.com.alura.contas.formulario.cellrenderer.list;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.Format;
import java.text.SimpleDateFormat;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import br.com.alura.contas.modelo.ContaAPagar;

public class ContasAPagarListCellRenderer implements ListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		ContaAPagar conta = (ContaAPagar) value;

		JPanel painelConteudo = new JPanel(new FlowLayout(FlowLayout.LEADING));
		
		Color corDoItem = Color.BLACK;
		if (conta.estaVencida()) {
			corDoItem = Color.RED;
		}
		
		JLabel rotuloCategoria = new JLabel(conta.getCategoria());
		rotuloCategoria.setPreferredSize(new Dimension(80, 16));
		rotuloCategoria.setForeground(corDoItem);
		
		JLabel rotuloDescricao = new JLabel(conta.getDescricao());
		rotuloDescricao.setPreferredSize(new Dimension(200, 16));
		rotuloDescricao.setForeground(corDoItem);

		Format format = new SimpleDateFormat("dd/MM/yyyy");
		String vencimento = format.format(conta.getVencimento());
		JLabel rotuloVencimento = new JLabel(vencimento);
		rotuloVencimento.setPreferredSize(new Dimension(80, 16));
		rotuloVencimento.setForeground(corDoItem);

		painelConteudo.add(rotuloCategoria);
		painelConteudo.add(rotuloDescricao);
		painelConteudo.add(rotuloVencimento);

		return painelConteudo;
	}

}
