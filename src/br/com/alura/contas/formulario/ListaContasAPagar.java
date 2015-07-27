package br.com.alura.contas.formulario;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import br.com.alura.contas.dao.ContaAPagarDAO;
import br.com.alura.contas.formulario.cellrenderer.DataCellRenderer;
import br.com.alura.contas.formulario.cellrenderer.ValorCellRenderer;
import br.com.alura.contas.formulario.listener.RemoveContaAPagarListener;
import br.com.alura.contas.formulario.tablemodel.ContasAPagarTableModel;
import br.com.alura.contas.modelo.ContaAPagar;

public class ListaContasAPagar extends JDialog {

	private JButton botaoEditar;
	private JButton botaoRemover;
	private JTable tabelaContas;

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
		botaoIncluir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evento) {
				new FormularioContasAPagar(ListaContasAPagar.this).mostra();

				carregaLista();
			}
		});
		painelBotoes.add(botaoIncluir);

		botaoEditar = new JButton("Editar");
		botaoEditar.setEnabled(false);
		botaoEditar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tabelaContas.getSelectedRow();
				Long id = (Long) tabelaContas.getValueAt(linhaSelecionada, 0);
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
		botaoRemover.setEnabled(false);
		botaoRemover.addActionListener(new RemoveContaAPagarListener(this));

		painelBotoes.add(botaoRemover);
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
		tabelaContas.setAutoCreateRowSorter(true);
		tabelaContas.setFillsViewportHeight(true);
		tabelaContas.setDefaultRenderer(Double.class, new ValorCellRenderer());
		tabelaContas.setDefaultRenderer(Date.class, new DataCellRenderer());

		tabelaContas.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent evento) {
						int linhasSelecionadas = tabelaContas
								.getSelectedRowCount();
						if (linhasSelecionadas > 0) {
							botaoEditar.setEnabled(true);
							botaoRemover.setEnabled(true);
						} else {
							botaoEditar.setEnabled(false);
							botaoRemover.setEnabled(false);
						}
					}
				});

		return tabelaContas;
	}

	private TableModel criaModeloDaTabela() {
		ContaAPagarDAO contasDAO = new ContaAPagarDAO();
		List<ContaAPagar> contas = contasDAO.getContas();
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
		return (Long) modelo.getValueAt(linhaSelecionada, ContasAPagarTableModel.ID);
	}
}
