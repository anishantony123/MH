package com.test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class SearchTest
{
JTable table;
JTextField textField;

public SearchTest()
{
table = getTable();
textField = new JTextField();
textField.addActionListener(new ActionListener()
{
public void actionPerformed(ActionEvent e)
{
search();
}
});
}

private JTable getTable()
{
int rows = 32, cols = 3;
String[] colNames = { "column 1", "column 2", "column 3" };
Object[][] data = new Object[rows][cols];
for(int row = 0; row < rows; row++)
for(int col = 0; col < cols; col++)
data[row][col] = "item " + (row*cols + col + 1);
DefaultTableModel model = new DefaultTableModel(data, colNames)
{
public Class getColumnClass(int col)
{
Object o = getValueAt(0, col);
if(o == null)
return Object.class;
else
return o.getClass();
}
};
JTable table = new JTable(model);
table.setDefaultRenderer(String.class, new CustomRenderer());
table.setRowSelectionAllowed(true);
table.setColumnSelectionAllowed(true);
table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
return table;
}

private void search()
{
String target = textField.getText();
for(int row = 0; row < table.getRowCount(); row++)
for(int col = 0; col < table.getColumnCount(); col++)
{
String next = (String)table.getValueAt(row, col);
if(next.equals(target))
{
showSearchResults(row, col);
return;
}
}

// reset table display after failed search
// > target null/not found <
CustomRenderer renderer =
(CustomRenderer)table.getDefaultRenderer(String.class);
renderer.setTargetCell(-1, -1);
table.repaint();
}

private void showSearchResults(int row, int col)
{
CustomRenderer renderer = (CustomRenderer)table.getCellRenderer(row, col);
renderer.setTargetCell(row, col);
Rectangle r = table.getCellRect(row, col, false);
table.scrollRectToVisible(r);
table.repaint();
}

public static void main(String[] args)
{
SearchTest test = new SearchTest();
JFrame f = new JFrame();
f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
f.getContentPane().add(new JScrollPane(test.table));
f.getContentPane().add(test.textField, "South");
f.setSize(400,400);
f.setLocation(200,200);
f.setVisible(true);
test.textField.requestFocusInWindow();
}
}

class CustomRenderer implements TableCellRenderer
{
JLabel label;
int targetRow, targetCol;

public CustomRenderer()
{
label = new JLabel();
label.setHorizontalAlignment(JLabel.CENTER);
label.setOpaque(true);
targetRow = -1;
targetCol = -1;
}

public Component getTableCellRendererComponent(JTable table,
Object value,
boolean isSelected,
boolean hasFocus,
int row, int column)
{
if(isSelected)
{
label.setBackground(table.getSelectionBackground());
label.setForeground(table.getSelectionForeground());
}
else
{
label.setBackground(table.getBackground());
label.setForeground(table.getForeground());
}
if(row == targetRow && column == targetCol)
{
label.setBorder(BorderFactory.createLineBorder(Color.red));
label.setFont(table.getFont().deriveFont(Font.BOLD));
}
else
{
label.setBorder(null);
label.setFont(table.getFont());
}
label.setText((String)value);
return label;
}

public void setTargetCell(int row, int col)
{
targetRow = row;
targetCol = col;
}
} 