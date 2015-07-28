package br.com.alura.contas.formulario.cellrenderer;

import java.awt.Component;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.NumberFormatter;

public class ValorCellRenderer extends DefaultTableCellRenderer implements
		TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);

		try {
			NumberFormatter format = new NumberFormatter(NumberFormat.getCurrencyInstance());
			String valor = format.valueToString(value);
			setText(valor);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return this;
	}

}
