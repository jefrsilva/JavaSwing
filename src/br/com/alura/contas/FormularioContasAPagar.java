package br.com.alura.contas;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import br.com.alura.contas.dao.ContaAPagarDAO;
import br.com.alura.contas.modelo.ContaAPagar;

public class FormularioContasAPagar extends JFrame {
	private JComboBox<String> comboCategoria;
	private JTextField campoDescricao;
	private JFormattedTextField campoValor;
	private JFormattedTextField campoVencimento;
	private JButton botaoInserir;
	private JButton botaoCancelar;

	public FormularioContasAPagar() {
		setTitle("Formulário de Contas a Pagar");
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel painelCampos = criaPainelDeCampos();
		JPanel painelBotoes = criaPainelDeBotoes();

		JPanel painelConteudo = new JPanel();
		BoxLayout layoutConteudo = new BoxLayout(painelConteudo,
				BoxLayout.PAGE_AXIS);
		painelConteudo.setLayout(layoutConteudo);
		painelConteudo.add(painelCampos);
		painelConteudo.add(painelBotoes);
		setContentPane(painelConteudo);

		pack();
	}

	private JPanel criaPainelDeBotoes() {
		botaoInserir = new JButton("Inserir");
		botaoInserir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ContaAPagar conta = getContaAPagar();
				ContaAPagarDAO dao = new ContaAPagarDAO();
				dao.insere(conta);
				dao.fecha();
			}
		});

		botaoCancelar = new JButton("Cancelar");
		botaoCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Cancelar inserção ou alteração aqui
			}
		});

		JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		painelBotoes.add(botaoCancelar);
		painelBotoes.add(botaoInserir);
		return painelBotoes;
	}

	private JPanel criaPainelDeCampos() {
		JPanel painelCampos = new JPanel();
		GroupLayout grupoCampos = new GroupLayout(painelCampos);
		painelCampos.setLayout(grupoCampos);

		JLabel rotuloCategoria = new JLabel("Categoria :");
		String[] categoria = { "Energia", "Água", "Gás" };
		comboCategoria = new JComboBox<>(categoria);

		JLabel rotuloDescricao = new JLabel("Descrição :");
		campoDescricao = new JTextField(25);

		JLabel rotuloValor = new JLabel("Valor :");
		campoValor = new JFormattedTextField(new DefaultFormatterFactory(
				new NumberFormatter(NumberFormat.getCurrencyInstance()),
				new NumberFormatter(NumberFormat.getCurrencyInstance()),
				new NumberFormatter()));
		campoValor.setValue(new Double(0.0));

		JLabel rotuloVencimento = new JLabel("Vencimento :");
		campoVencimento = new JFormattedTextField(DateFormat.getDateInstance());
		campoVencimento.setValue(Calendar.getInstance().getTime());

		grupoCampos.setHorizontalGroup(grupoCampos
				.createSequentialGroup()
				.addGroup(
						grupoCampos
								.createParallelGroup(
										GroupLayout.Alignment.TRAILING)
								.addComponent(rotuloCategoria)
								.addComponent(rotuloDescricao)
								.addComponent(rotuloValor)
								.addComponent(rotuloVencimento))
				.addGroup(
						grupoCampos
								.createParallelGroup(
										GroupLayout.Alignment.LEADING)
								.addComponent(comboCategoria)
								.addComponent(campoDescricao)
								.addComponent(campoValor)
								.addComponent(campoVencimento)));

		grupoCampos.setVerticalGroup(grupoCampos
				.createSequentialGroup()
				.addGroup(
						grupoCampos
								.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
								.addComponent(rotuloCategoria)
								.addComponent(comboCategoria))
				.addGroup(
						grupoCampos
								.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
								.addComponent(rotuloDescricao)
								.addComponent(campoDescricao))
				.addGroup(
						grupoCampos
								.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
								.addComponent(rotuloValor)
								.addComponent(campoValor))
				.addGroup(
						grupoCampos
								.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
								.addComponent(rotuloVencimento)
								.addComponent(campoVencimento)));

		grupoCampos.setAutoCreateGaps(true);
		grupoCampos.setAutoCreateContainerGaps(true);
		return painelCampos;
	}

	public void mostra() {
		setVisible(true);
	}

	public ContaAPagar getContaAPagar() {
		String categoria = (String) comboCategoria.getSelectedItem();
		String descricao = campoDescricao.getText();
		double valor = ((Number) campoValor.getValue()).doubleValue();
		Date vencimento = (Date) campoVencimento.getValue();

		return new ContaAPagar(categoria, descricao, valor, vencimento);
	}
}
