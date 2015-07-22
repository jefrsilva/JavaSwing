package br.com.alura.contas.janela;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import br.com.alura.contas.dao.ContaAPagarDAO;
import br.com.alura.contas.modelo.ContaAPagar;

public class FormularioContasAPagar extends JDialog {

	private JComboBox<String> comboCategoria;
	private JTextField campoDescricao;
	private JFormattedTextField campoValor;
	private JFormattedTextField campoVencimento;
	private JButton botaoInserir;
	private JButton botaoCancelar;

	private ContaAPagar conta;

	public FormularioContasAPagar(ListaContasAPagar listaContasAPagar) {
		super(listaContasAPagar, "Formulário de Contas a Pagar", true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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
		setLocationRelativeTo(null);
	}

	public FormularioContasAPagar(ListaContasAPagar listaContasAPagar,
			ContaAPagar conta) {
		this(listaContasAPagar);

		this.conta = conta;
		comboCategoria.setSelectedItem(conta.getCategoria());
		campoDescricao.setText(conta.getDescricao());
		campoValor.setValue(conta.getValor());
		campoVencimento.setValue(conta.getVencimento());
		botaoInserir.setText("Alterar");
	}

	private JPanel criaPainelDeBotoes() {
		botaoInserir = new JButton("Inserir");
		botaoInserir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (conta == null) {
					ContaAPagarDAO dao = new ContaAPagarDAO();
					ContaAPagar conta = pegaContaDoFormulario();
					dao.insere(conta);
					dao.fecha();
					
					limpaCampos();

					JOptionPane.showMessageDialog(FormularioContasAPagar.this,
							"Conta a pagar incluída com sucesso!");
				} else {
					ContaAPagarDAO dao = new ContaAPagarDAO();
					ContaAPagar contaAlterada = pegaContaDoFormulario();
					contaAlterada.setId(conta.getId());
					dao.altera(contaAlterada);
					dao.fecha();
					
					JOptionPane.showMessageDialog(FormularioContasAPagar.this,
							"Conta a pagar alterada com sucesso!");
					FormularioContasAPagar.this.dispose();
				}
					
			}
		});

		botaoCancelar = new JButton("Cancelar");
		botaoCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		painelBotoes.add(botaoCancelar);
		painelBotoes.add(botaoInserir);
		return painelBotoes;
	}

	protected void limpaCampos() {
		comboCategoria.setSelectedIndex(0);
		campoDescricao.setText("");
		campoValor.setValue(0.0);
		campoVencimento.setValue(Calendar.getInstance().getTime());
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
		campoVencimento = new JFormattedTextField(new SimpleDateFormat(
				"dd/MM/yyyy"));
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

	private ContaAPagar pegaContaDoFormulario() {
		ContaAPagar conta = new ContaAPagar();
		conta.setCategoria((String) comboCategoria.getSelectedItem());
		conta.setDescricao(campoDescricao.getText());
		conta.setValor(((Number) campoValor.getValue()).doubleValue());
		conta.setVencimento((Date) campoVencimento.getValue());
		return conta;
	}
	
	public ContaAPagar getContaAPagar() {
		return conta;
	}
}
