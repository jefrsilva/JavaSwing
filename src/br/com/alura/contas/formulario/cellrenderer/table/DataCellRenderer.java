package br.com.alura.contas.formulario.cellrenderer.table;

import java.awt.Component;
import java.text.Format;
import java.text.SimpleDateFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class DataCellRenderer extends DefaultTableCellRenderer implements
		TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);

		Format format = new SimpleDateFormat("dd/MM/yyyy");
		String data = format.format(value);
		setText(data);
		return this;
	}

}
