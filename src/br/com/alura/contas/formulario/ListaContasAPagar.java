package br.com.alura.contas.formulario;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import br.com.alura.contas.dao.ContaAPagarDAO;
import br.com.alura.contas.formulario.cellrenderer.table.DataCellRenderer;
import br.com.alura.contas.formulario.cellrenderer.table.ValorCellRenderer;
import br.com.alura.contas.formulario.listener.RemoveContaAPagarListener;
import br.com.alura.contas.formulario.model.table.ContasAPagarTableModel;
import br.com.alura.contas.modelo.ContaAPagar;

public class ListaContasAPagar extends JDialog {

	private JTable tabelaContas;
	private JButton botaoEditar;
	private JButton botaoRemover;
	private JButton botaoBaixa;

	public ListaContasAPagar(JanelaInicial janelaInicial) {
		super(janelaInicial, "Contas a Pagar", true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel painelConteudo = new JPanel();
		BoxLayout layoutConteudo = new BoxLayout(painelConteudo,
				BoxLayout.PAGE_AXIS);
		painelConteudo.setLayout(layoutConteudo);

		JPanel painelLista = criaPainelLista();
		JPanel painelBotoes = criaPainelBotoes();

		painelConteudo.add(painelLista);
		painelConteudo.add(painelBotoes);
		setContentPane(painelConteudo);

		pack();
		setLocationRelativeTo(null);
	}

	private JPanel criaPainelBotoes() {
		JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		painelBotoes.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

		JButton botaoIncluir = new JButton("Incluir");
		botaoIncluir.setMnemonic(KeyEvent.VK_I);
		botaoIncluir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evento) {
				new FormularioContasAPagar(ListaContasAPagar.this).mostra();

				carregaLista();
			}
		});
		painelBotoes.add(botaoIncluir);

		botaoEditar = new JButton("Editar");
		botaoEditar.setMnemonic(KeyEvent.VK_E);
		botaoEditar.setEnabled(false);
		botaoEditar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Long id = getIdContaSelecionada();
				ContaAPagarDAO contaAPagarDAO = new ContaAPagarDAO();
				ContaAPagar conta = contaAPagarDAO.busca(id);
				contaAPagarDAO.fecha();

				new FormularioContasAPagar(ListaContasAPagar.this, conta)
						.mostra();

				carregaLista();
			}
		});
		painelBotoes.add(botaoEditar);

		botaoRemover = new JButton("Remover");
		botaoRemover.setMnemonic(KeyEvent.VK_R);
		botaoRemover.setEnabled(false);
		botaoRemover.addActionListener(new RemoveContaAPagarListener(this));
		painelBotoes.add(botaoRemover);
		
		botaoBaixa = new JButton("Dar baixa");
		botaoBaixa.setMnemonic(KeyEvent.VK_B);
		botaoBaixa.setEnabled(false);
		botaoBaixa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Long id = getIdContaSelecionada();
				ContaAPagarDAO contaAPagarDAO = new ContaAPagarDAO();
				ContaAPagar conta = contaAPagarDAO.busca(id);
				conta.setPagamento(Calendar.getInstance().getTime());
				contaAPagarDAO.altera(conta);
				contaAPagarDAO.fecha();
				
				carregaLista();
			}
		});
		painelBotoes.add(botaoBaixa);
		return painelBotoes;
	}

	private JPanel criaPainelLista() {
		JPanel painelLista = new JPanel();

		Border borda = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder bordaComTitulo = BorderFactory.createTitledBorder(borda,
				"Contas a pagar");
		bordaComTitulo.setTitleJustification(TitledBorder.LEADING);
		painelLista
				.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createEmptyBorder(10, 10, 10, 10),
						bordaComTitulo));

		tabelaContas = criaTabela();
		JScrollPane painelComScroll = new JScrollPane(tabelaContas);
		painelComScroll.setPreferredSize(new Dimension(600, 200));
		painelLista.add(painelComScroll);

		return painelLista;
	}

	private JTable criaTabela() {
		TableModel modelo = criaModeloDaTabela();
		JTable tabelaContas = new JTable(modelo);
		tabelaContas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaContas.setAutoCreateRowSorter(true);
		tabelaContas.setFillsViewportHeight(true);
		tabelaContas.setDefaultRenderer(Double.class, new ValorCellRenderer());
		tabelaContas.setDefaultRenderer(Date.class, new DataCellRenderer());

		tabelaContas.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent evento) {
						if (temContaSelecionada()) {
							ativaBotoesDeEdicao();
						} else {
							desativaBotoesDeEdicao();
						}
					}
				});

		return tabelaContas;
	}
	
	private void ativaBotoesDeEdicao() {
		defineEstadoDosBotoesDeEdicao(true);
	}

	private void desativaBotoesDeEdicao() {
		defineEstadoDosBotoesDeEdicao(false);
	}

	private void defineEstadoDosBotoesDeEdicao(boolean estado) {
		botaoEditar.setEnabled(estado);
		botaoRemover.setEnabled(estado);
		botaoBaixa.setEnabled(estado);
	}
	
	private boolean temContaSelecionada() {
		return tabelaContas.getSelectedRowCount() > 0;
	}

	private TableModel criaModeloDaTabela() {
		ContaAPagarDAO contasDAO = new ContaAPagarDAO();
		List<ContaAPagar> contas = contasDAO.getContasAPagar();
		contasDAO.fecha();

		TableModel modelo = new ContasAPagarTableModel(contas);
		return modelo;
	}

	public void mostra() {
		setVisible(true);
	}

	public void carregaLista() {
		TableModel modelo = criaModeloDaTabela();
		tabelaContas.setModel(modelo);
	}

	public Long getIdContaSelecionada() {
		int linhaSelecionada = tabelaContas.getSelectedRow();
		TableModel modelo = tabelaContas.getModel();
		return (Long) modelo.getValueAt(linhaSelecionada,
				ContasAPagarTableModel.ID);
	}
}
