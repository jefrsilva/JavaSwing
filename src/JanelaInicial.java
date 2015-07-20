import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class JanelaInicial extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public JanelaInicial() {
		super("Controle de Contas a Pagar e a Receber");		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JLabel rotulo = new JLabel("Olá mundo!");
		add(rotulo);
		
		JButton botaoCadastroCliente = new JButton("Cadastro de clientes");
		add(botaoCadastroCliente);
		
		botaoCadastroCliente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new FormularioContasAPagar().mostra();
			}
		});
		
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
