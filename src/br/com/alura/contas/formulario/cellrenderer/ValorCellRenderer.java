package br.com.alura.contas.formulario.cellrenderer;

import java.awt.Component;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

public class ValorCellRenderer implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		JFormattedTextField campoValor = new JFormattedTextField(new DefaultFormatterFactory(
				new NumberFormatter(NumberFormat.getCurrencyInstance()),
				new NumberFormatter(NumberFormat.getCurrencyInstance()),
				new NumberFormatter()));
		campoValor.setBorder(BorderFactory.createEmptyBorder());
		campoValor.setValue(value);
		return campoValor;
	}

}
