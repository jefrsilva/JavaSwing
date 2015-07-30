package br.com.alura.contas.formulario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import br.com.alura.contas.dao.ContaAPagarDAO;
import br.com.alura.contas.formulario.cellrenderer.list.ContasAPagarListCellRenderer;
import br.com.alura.contas.modelo.ContaAPagar;

public class JanelaInicial extends JFrame {

	public JanelaInicial() {
		super("Controle de Contas a Pagar e a Receber");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel painelConteudo = new JPanel();
		painelConteudo.setLayout(new BoxLayout(painelConteudo,
				BoxLayout.PAGE_AXIS));
		
		JMenuBar barraDeMenu = criaMenu();
		this.setJMenuBar(barraDeMenu);

		JPanel painelContasAPagar = criaPainelContasAPagar();
		painelConteudo.add(painelContasAPagar);

		setContentPane(painelConteudo);
		pack();
		setLocationRelativeTo(null);
	}

	private JMenuBar criaMenu() {
		JMenuBar barraDeMenu = new JMenuBar();
		
		JMenu menuTelas = new JMenu("Telas");
		menuTelas.setMnemonic(KeyEvent.VK_T);	
		barraDeMenu.add(menuTelas);
		
		JMenuItem itemContasAPagar = new JMenuItem("Contas a Pagar", KeyEvent.VK_P);
		menuTelas.add(itemContasAPagar);
		itemContasAPagar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ListaContasAPagar(JanelaInicial.this).mostra();
			}
		});
		
		menuTelas.addSeparator();
		
		JMenuItem itemSair = new JMenuItem("Sair", KeyEvent.VK_S);
		menuTelas.add(itemSair);
		itemSair.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		return barraDeMenu;
	}

	private JPanel criaPainelContasAPagar() {
		JPanel painelContasAPagar = new JPanel();

		Border borda = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder bordaComTitulo = BorderFactory.createTitledBorder(borda,
				"Contas a pagar");
		bordaComTitulo.setTitleJustification(TitledBorder.LEADING);
		painelContasAPagar
				.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createEmptyBorder(10, 10, 10, 10),
						bordaComTitulo));

		ContaAPagarDAO dao = new ContaAPagarDAO();
		List<ContaAPagar> contas = dao.getContasAPagar();
		dao.fecha();

		JList<ContaAPagar> listaContasAPagar = new JList<>();
		
		ContaAPagar[] contasArray = contas.toArray(new ContaAPagar[0]);
		listaContasAPagar.setListData(contasArray);
		listaContasAPagar.setLayoutOrientation(JList.VERTICAL);
		listaContasAPagar.setCellRenderer(new ContasAPagarListCellRenderer());
		painelContasAPagar.add(listaContasAPagar);
		return painelContasAPagar;
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
