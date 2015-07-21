package br.com.alura.contas.janela;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class JanelaInicial extends JFrame {

	public JanelaInicial() {
		super("Controle de Contas a Pagar e a Receber");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel painelConteudo = new JPanel();
		painelConteudo.setLayout(new BoxLayout(painelConteudo,
				BoxLayout.PAGE_AXIS));

		JButton botaoListaContasAPagar = new JButton("Contas a Pagar");
		painelConteudo.add(botaoListaContasAPagar, BorderLayout.CENTER);

		botaoListaContasAPagar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new ListaContasAPagar().mostra();
			}
		});

		setContentPane(painelConteudo);
		pack();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new JanelaInicial().mostra();
			}
		});
	}

	public void mostra() {
		setVisible(true);
	}
}
