package br.com.alura.contas.formulario.cellrenderer;

import java.awt.Component;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class DataCellRenderer implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		JFormattedTextField campoData = new JFormattedTextField(new SimpleDateFormat(
				"dd/MM/yyyy"));
		campoData.setBorder(BorderFactory.createEmptyBorder());
		campoData.setValue(value);
		return campoData;
	}

	
}
