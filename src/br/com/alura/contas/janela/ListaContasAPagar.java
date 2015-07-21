package br.com.alura.contas.janela;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
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
import br.com.alura.contas.modelo.ContaAPagar;

public class ListaContasAPagar extends JFrame {

	private JButton botaoRemover;
	private JTable tabelaContas;

	public ListaContasAPagar() {
		setTitle("Contas a Pagar");
		setLocationRelativeTo(null);
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
	}

	private JPanel criaPainelBotoes() {
		JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		painelBotoes.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		
		JButton botaoIncluir = new JButton("Incluir");
		botaoIncluir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evento) {
				new FormularioContasAPagar(ListaContasAPagar.this).mostra();
			}
		});
		painelBotoes.add(botaoIncluir);
		
		botaoRemover = new JButton("Remover");
		botaoRemover.setEnabled(false);
		botaoRemover.addActionListener(new RemoverContaAPagarListener(tabelaContas));
		
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
		String[] colunas = { "Id", "Categoria", "Descrição", "Valor",
				"Vencimento" };

		
		ContaAPagarDAO contasDAO = new ContaAPagarDAO();
		List<ContaAPagar> contas = contasDAO.getContas();
		Object[][] dados = converteContasParaTabela(contas);
		contasDAO.fecha();

		TableModel modelo = new DefaultTableModel(dados, colunas);
		JTable tabelaContas = new JTable(modelo);
		tabelaContas.setAutoCreateRowSorter(true);
		tabelaContas.setFillsViewportHeight(true);
		
		tabelaContas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent evento) {
				int linhasSelecionadas = tabelaContas.getSelectedRowCount();
				if (linhasSelecionadas > 0) {
					botaoRemover.setEnabled(true);
				} else {
					botaoRemover.setEnabled(false);
				}
			}
		});
		
		return tabelaContas;
	}

	private Object[][] converteContasParaTabela(List<ContaAPagar> contas) {
		Object[][] dados = new Object[contas.size()][];
		for (int i = 0; i < contas.size(); i++) {
			ContaAPagar conta = contas.get(i);
			Object[] dadosDaConta = new Object[5];
			dadosDaConta[0] = conta.getId();
			dadosDaConta[1] = conta.getCategoria();
			dadosDaConta[2] = conta.getDescricao();
			dadosDaConta[3] = conta.getValor();
			dadosDaConta[4] = conta.getVencimento();
			dados[i] = dadosDaConta;
		}
		return dados;
	}

	public void mostra() {
		setVisible(true);
	}
}