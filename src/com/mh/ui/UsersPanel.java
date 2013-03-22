package com.mh.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.Main;
import com.mh.model.HMConstants;
import com.mh.model.User;
import com.mh.service.UserService;
import com.mh.ui.images.ImageUtil;
import com.mh.ui.util.Utils;

public class UsersPanel extends JPanel{
	private JButton addNewButton;
	private JButton nextButton;
	private JButton previousButton;
	private JButton refreshButton;
	private JLabel searchResult;
	private JTextField searchText;
	private JTable table;
	private Main main;
	protected AddUserPanel addUsers;
	public int ID_COL_INDEX= 5;
	private DefaultTableModel dm;
	private Rectangle r;
	private int lastIndex = 0;
	private List<TableIndex> searchIndexList = new ArrayList<UsersPanel.TableIndex>();
	private Set<User> searchedUserList = new HashSet<User>();//list of unique users after search
	private Set<Integer> searchSelColset = new HashSet<Integer>();
	String mode=HMConstants.VIEW;
		public UsersPanel(Main main) {
		try {
		this.main=main;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{10, 100, 40, 40 , 20,20, 20, 0, 10};
		gridBagLayout.rowHeights = new int[]{0, 0, 39,30,94,0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.5,0.5,0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		addNewButton = new JButton(ImageUtil.getIcon(HMConstants.ADDNEW_IMG));
		addNewButton.setContentAreaFilled(false);
		addNewButton.setBorderPainted( false );
		addNewButton.setToolTipText("<html>Add new Patient</html>");
		//addNewButton = new JButton("Add New");
		addNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				addUsers = new AddUserPanel(getMain(),getMain().getSplitPane().getRightComponent());
				addUsers.setVisible(true);
				getMain().getSplitPane().setRightComponent(addUsers);
				
			}
		});
		
		
		
		searchText= new RoundJTextField(10);
		
		
		searchText.setFont(new Font("Arial",Font.BOLD,15));
		/*Border roundedBorder = new LineBorder(new Color(210,210,0), 14, true);*/
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 2;
		add(searchText, gbc_comboBox);
		
		
		//JButton btnSearch = new JButton("Search");
		JButton btnSearch = new JButton(ImageUtil.getIcon(HMConstants.SEARCH_IMG));
		btnSearch.setContentAreaFilled(false);
		btnSearch.setBorderPainted( false );
		btnSearch.setToolTipText("<html>Search(checked columns)</html>");
		btnSearch.setMinimumSize(new Dimension(100, 40));
		btnSearch.setPreferredSize(new Dimension(110, 40));
		btnSearch.setMaximumSize(new Dimension(110,Short.MAX_VALUE));
		//btnSearch.setToolTipText("Search");
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearch.gridx = 2;
		gbc_btnSearch.gridy = 2;
		gbc_btnSearch.anchor = GridBagConstraints.WEST;
		add(btnSearch, gbc_btnSearch);
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(Main.session.isValid()){
					searchIndexList.clear();
					String target = searchText.getText();
					if(target!=null && !target.trim().equals("")){			
						search(target);
						setSearchVisibility(true);					
						navigateSearch("");
					}else{
						r=null;
						table.repaint();
						setSearchVisibility(false);
				//		setTable(HMConstants.EDIT); //enable if blank search need refresh table
					}
					
				}
			}
		});
		refreshButton = new JButton(ImageUtil.getIcon(HMConstants.REFRESH_ICON));
		refreshButton.setContentAreaFilled(false);
		refreshButton.setBorderPainted( false );
		refreshButton.setMinimumSize(new Dimension(30, 30));
		refreshButton.setPreferredSize(new Dimension(40, 40));
		refreshButton.setMaximumSize(new Dimension(50,Short.MAX_VALUE));
		refreshButton.setToolTipText("<html>Reset</html>");
		GridBagConstraints gbc_btnRefresh = Utils.getConStraints(3, 2, null);
		gbc_btnRefresh.anchor = GridBagConstraints.WEST;
		add(refreshButton, gbc_btnRefresh);
		
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(Main.session.isValid()){
					setTable(HMConstants.EDIT);
				}
			}
		});
		
		
		GridBagConstraints gbc_addNewButton = new GridBagConstraints();
		gbc_addNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_addNewButton.gridx = 5;
		gbc_addNewButton.gridy = 2;
		add(addNewButton, gbc_addNewButton);
		
		GridBagConstraints gbc_loginNewButton = new GridBagConstraints();
		gbc_loginNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_loginNewButton.gridx = 5;
		gbc_loginNewButton.gridy = 0;
		
		add(main.getLoginButton(), gbc_loginNewButton);
		
	//search result  start
		
		previousButton = new JButton("<");
		GridBagConstraints gbc_previousButton = new GridBagConstraints();
		gbc_previousButton.insets = new Insets(0, 0, 5, 5);
		gbc_previousButton.gridx = 1;
		gbc_previousButton.gridy = 3;
		gbc_previousButton.anchor = GridBagConstraints.LINE_START;
		add(previousButton, gbc_previousButton);
		
		searchResult= new JLabel("0 of 0");
		GridBagConstraints gbc_searchResult = new GridBagConstraints();
		gbc_searchResult.insets = new Insets(0, 0, 5, 5);
		gbc_searchResult.gridx = 1;
		gbc_searchResult.gridy = 3;
		gbc_searchResult.anchor = GridBagConstraints.CENTER;
		add(searchResult, gbc_searchResult);
		
		nextButton = new JButton(">");
		GridBagConstraints gbc_nextButton = new GridBagConstraints();
		gbc_nextButton.insets = new Insets(0, 0, 5, 5);
		gbc_nextButton.gridx = 1;
		gbc_nextButton.gridy = 3;
		gbc_nextButton.anchor = GridBagConstraints.LINE_END;
		add(nextButton, gbc_nextButton);
		
		setSearchVisibility(false);
		
		previousButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				navigateSearch(HMConstants.SEARCH_PREVIOUS);
			}
		});
		
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				navigateSearch(HMConstants.SEARCH_NEXT);
			}
		});
		
	//search result  end	
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 7;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 4;
		add(scrollPane, gbc_scrollPane);
		
		//table + styling
		
		table = new JTable(){
			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				if(r!=null){
					Graphics2D g2d= (Graphics2D) g;
					g2d.setColor(Color.red);
					//r.setFrame(r.getX()-2, r.getY()-2, r.getWidth()+2, r.getHeight()+2);
					g2d.setStroke(new BasicStroke(3F)); 
					g2d.draw(r);
				}
				
			}
		};
		
		dm= new DefaultTableModel(){
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return (column==0 || column==8);
		    }
		};
		JButton edit=new JButton("Edit");
		
		table.setModel(dm);
		table.setFont(new Font("Arial",Font.PLAIN,12));
		table.getTableHeader().setFont(new Font("Time New Roman",Font.BOLD,12));
		table.getTableHeader().setBackground(new Color(143,188,255));		
		table.getTableHeader().setDefaultRenderer(new CheckBoxHeader());
		table.getTableHeader().setReorderingAllowed(false);
		//table.getTableHeader().setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray), table.getBorder()));
		
		dm.addColumn("Edit");
		dm.addColumn("Reg No");
		dm.addColumn("Name");
		dm.addColumn("Age");
		dm.addColumn("Sex");
		dm.addColumn("Center");
		dm.addColumn("Care of");
		dm.addColumn("Informer");
		dm.addColumn("Delete");
		dm.addColumn("hidden1");
		dm.addColumn("hidden2");
		
		//initially checked all columns
		for(int i=1;i<8;i++)
			searchSelColset.add(i);
	/*	TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(dm);
		table.setRowSorter(sorter);*/
		
		
	   table.getColumnModel().getColumn(9).setMinWidth(0);
	   table.getColumnModel().getColumn(9).setMaxWidth(0);
	   table.getColumnModel().getColumn(9).setWidth(0);
	
	   table.getColumnModel().getColumn(10).setMinWidth(0);
	   table.getColumnModel().getColumn(10).setMaxWidth(0);
	   table.getColumnModel().getColumn(10).setWidth(0);
		 	// add styles
	   
	   Color b1= new Color(186,179,255);
	   Color b2= new Color(179,209,255);
	   
	   Color f1= new Color(26,0,51);
	   Color f2= new Color(26,0,51);
	   
	   table.getColumn("Reg No").setCellRenderer(new CellRenderer(b1,f1));
	   table.getColumn("Name").setCellRenderer(new CellRenderer(b2,f2));
	   table.getColumn("Age").setCellRenderer(new CellRenderer(b1,f1));
	   table.getColumn("Sex").setCellRenderer(new CellRenderer(b2,f2));
	   table.getColumn("Center").setCellRenderer(new CellRenderer(b1,f1));
	   table.getColumn("Care of").setCellRenderer(new CellRenderer(b2,f2));
	   table.getColumn("Informer").setCellRenderer(new CellRenderer(b1,f1));
	   
	   
	   
	    table.getColumn("Edit").setMinWidth(40);
	    table.getColumn("Edit").setMaxWidth(40);
		table.getColumn("Edit").setCellRenderer(new ButtonRenderer(mode));
	    table.getColumn("Edit").setCellEditor(
	        new ButtonEditor(new JCheckBox(),mode));
	    
	    table.getColumn("Delete").setMinWidth(40);
		table.getColumn("Delete").setMaxWidth(40);
		table.getColumn("Delete").setCellRenderer(new ButtonRenderer(HMConstants.DELETE));
		table.getColumn("Delete").setCellEditor(
		        new ButtonEditor(new JCheckBox(),HMConstants.DELETE));
	    
	    
	    
		table.setAutoscrolls(true);	
		table.setRowHeight(25);
		table.setIntercellSpacing(new Dimension(3, 3));
		table.setShowGrid(false);
		
		
		scrollPane.setViewportView(table);
		
		
		dm.addTableModelListener(table);
		
		addNewButton.setVisible(true);
		
		if(Main.session.isValid()){
			addNewButton.setVisible(true);
		}else{
			addNewButton.setVisible(false);
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		//setTable(mode);
		
		// end table
	
	}
		
	class CellRenderer extends DefaultTableCellRenderer {
		
		  public CellRenderer(Color backGrndColor,Color foreGrndColor) {
		    setOpaque(true);
		    super.setBackground(backGrndColor);
		    super.setForeground(foreGrndColor);
		  }

		  public Component getTableCellRendererComponent(JTable table, Object value,
		      boolean isSelected, boolean hasFocus, int row, int column) {
			  isSelected= false;
			  return super.getTableCellRendererComponent(table,value, isSelected, hasFocus,row,column);
		}
	}
					
	class ButtonRenderer extends JButton implements TableCellRenderer {
		String tooltip;
		  public ButtonRenderer(String tooltip) {
		    setOpaque(true);
		    this.tooltip = tooltip;
		    setContentAreaFilled(true);
			setBorderPainted( false );
		  }

		  public Component getTableCellRendererComponent(JTable table, Object value,
		      boolean isSelected, boolean hasFocus, int row, int column) {
			  setIcon((Icon)value);
			  setToolTipText("<html>"+tooltip+"</html>");		    
		    return this;
		  }
		}
	
	
	class ButtonEditor extends DefaultCellEditor {
		 
		protected JButton button;

		  //private String label;
		  private Icon icon;

		  private boolean isPushed;

		  private Long userId;
		  private User user;
		  String tooltip;

		  public ButtonEditor(JCheckBox checkBox,String tooltip) {
		    super(checkBox);
		    this.tooltip = tooltip;
		    button = new JButton(ImageUtil.getIcon(HMConstants.EDIT_ICON));
		    button.setOpaque(true);
		    button.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        fireEditingStopped();
		      }
		    });
		  }

		  public Component getTableCellEditorComponent(JTable table, Object value,
		      boolean isSelected, int row, int column) {
			  icon = (Icon) value;
		      button.setIcon(icon);		   
		      //button.setToolTipText("<html>"+mode+"</html>");
		      // label = (value==null)?"":value.toString();		      
		   // button.setText(label);
		    isPushed = true;
		    userId = (Long) table.getModel().getValueAt(row, 9);
		    user = (User) table.getValueAt(row, 10);
		    return button;
		  }
		  
		  public Object getCellEditorValue() {
		    if (isPushed) {
		     //perform button actionof this row
		    	if(HMConstants.DELETE.equals(tooltip)){
		    		if( Main.session.isValid()){
		    			int option = JOptionPane.showConfirmDialog(UsersPanel.this, "Do you want to delete this record ?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		    			System.out.println(option);
		    			if(option == 0)// for yes
		    				new UserService().deactivateUser(user);
		    		}else{
		    			JOptionPane.showMessageDialog(UsersPanel.this, "You are not logged in !");
		    		}
		    		
		    	}else{
			    	//get user id
			    	AddUserPanel panel= new AddUserPanel(main,main.getSplitPane().getRightComponent());
			    	if(Main.session.isValid()){
			    		panel.setOperation(HMConstants.EDIT);
			    	}else{
			    		panel.setOperation(HMConstants.VIEW);
			    	}
			    	
			    	panel.setUser(user);
			    	main.getSplitPane().setRightComponent(panel);
		    	}
		    	
		    	
		    }
		    isPushed = false;
		    return icon;
		  }

		  public boolean stopCellEditing() {
		    isPushed = false;
		    boolean stopped = super.stopCellEditing();
		    if(HMConstants.DELETE.equals(tooltip) && Main.session.isValid()){
				 setTable(mode); 
			 }
		    return stopped;		    
		  }

		  protected void fireEditingStopped() {
			  super.fireEditingStopped();
			 if(HMConstants.DELETE.equals(tooltip) && Main.session.isValid()){
				 setTable(mode); 
			 }
		  }
		}
	  
	class CheckBoxHeader extends JCheckBox  
		    implements TableCellRenderer, MouseListener {  
		  protected CheckBoxHeader rendererComponent;  
		  protected int column;  
		  protected boolean mousePressed = false;  
		  
		  public CheckBoxHeader(ItemListener itemListener) {  
		    rendererComponent = this;  
		    rendererComponent.addItemListener(itemListener);  
		  }  
		  public CheckBoxHeader() {  
			    rendererComponent = this;  
		 }  
		  public Component getTableCellRendererComponent(  
		      JTable table, Object value,  
		      boolean isSelected, boolean hasFocus, int row, int column) {  
		    if (table != null && column>0 && column< table.getColumnCount()-3) {  
		      JTableHeader header = table.getTableHeader();  
		      if (header != null) {  
		        rendererComponent.setForeground(header.getForeground());  
		        rendererComponent.setBackground(header.getBackground());  
		        rendererComponent.setFont(header.getFont());  
		        if(searchSelColset.contains(column))
		        	rendererComponent.setSelected(true);
		        else
		        	rendererComponent.setSelected(false);
		        header.addMouseListener(rendererComponent);  
		      }  
		      setColumn(column);  
			    rendererComponent.setText(table.getColumnName(column));  
			    setBorder(UIManager.getBorder("TableHeader.cellBorder"));  
			    return rendererComponent;  
		    }else if(table!=null){
		    	return new JLabel(table.getColumnName(column));
		    }else{
		    	return null;
		    }
		    
		  }  
		  protected void setColumn(int column) {  
		    this.column = column;  
		  }  
		  public int getColumn() {  
		    return column;  
		  }  
		  protected void handleClickEvent(MouseEvent e) {  
		    if (mousePressed) {  
		      mousePressed=false;  
		      JTableHeader header = (JTableHeader)(e.getSource());  
		      JTable tableView = header.getTable();  
		      TableColumnModel columnModel = tableView.getColumnModel();  
		      int viewColumn = columnModel.getColumnIndexAtX(e.getX());  
		      if(searchSelColset.contains(viewColumn)){
		    	  searchSelColset.remove(viewColumn);
		      }else{
		    	  searchSelColset.add(viewColumn);
		      }
		      
		        doClick();  
		    }  
		  }  
		  public void mouseClicked(MouseEvent e) {  
		    handleClickEvent(e);  
		    ((JTableHeader)e.getSource()).repaint();  
		  }  
		  public void mousePressed(MouseEvent e) {  
		    mousePressed = true;  
		  }  
		  public void mouseReleased(MouseEvent e) {  
		  }  
		  public void mouseEntered(MouseEvent e) {  
		  }  
		  public void mouseExited(MouseEvent e) {  
		  }  
}  
	
	public void setTable(String mode) {
		// TODO Auto-generated method stub
		clearSearch();
		List<User> users= new UserService().getUsers();	
		
			//dm.setRowCount(0);
		
		
		setTableValues(mode, users);
		
	}
	/**
	 * will populate with list of users table
	 * 
	 * @param mode
	 * @param users
	 */
	private void setTableValues(String mode, List<User> users) {
		
		dm.getDataVector().removeAllElements();
		if(!mode.equals(HMConstants.VIEW)){
			mode = HMConstants.EDIT;
		}
		this.table.getColumnModel().getColumn(0).setHeaderValue(mode);
		
		if(users!=null && !users.isEmpty()){
			table.setRowHeight(25);
			for(User user :users){
				Object[ ] row={
						ImageUtil.getIcon(mode+"Icon"),
						//mode,
						user.getRegNo(),
						user.getName(),
						user.getAge(),
						//user.getDob(),
						user.getSex(),
						user.getCenter(),
						user.getCareOff(),
						user.getInformer(),
						ImageUtil.getIcon(HMConstants.DELETE_ICON),
						user.getId(),
						user
				};
			dm.addRow(row);	
			}
		}else{
			table.setRowHeight(1);//if last user is deleted , row still shown as first row
		}
	}
	private void clearSearch() {
		setSearchVisibility(false);
		getSearchText().setText("");
		r=null;
		searchIndexList.clear();
		lastIndex =0;
	}
	public void clearTable(){
		clearSearch();
		dm.getDataVector().removeAllElements();
		table.setRowHeight(1);
	}
	public Main getMain() {
		return main;
	}
	public void setMain(Main main) {
		this.main = main;
	}
	public JButton getAddNewButton() {
		return addNewButton;
	}
	public void setAddNewButton(JButton addNewButton) {
		this.addNewButton = addNewButton;
	}
	public JTable getTable() {
		return table;
	}
	public void setTable(JTable table) {
		this.table = table;
	}
	public AddUserPanel getAddUsers() {
		return addUsers;
	}
	public void setAddUsers(AddUserPanel addUsers) {
		this.addUsers = addUsers;
	}
	public int getID_COL_INDEX() {
		return ID_COL_INDEX;
	}
	public void setID_COL_INDEX(int iD_COL_INDEX) {
		ID_COL_INDEX = iD_COL_INDEX;
	}
	public DefaultTableModel getDm() {
		return dm;
	}
	public void setDm(DefaultTableModel dm) {
		this.dm = dm;
	}
	private void search(String searchText){
		searchIndexList.clear();
		searchedUserList.clear();
			for(int row = 0; row < table.getRowCount(); row++){
				for(int col = 1; col < table.getColumnCount()-3; col++){
					if(searchSelColset.contains(col)){
						String next = (table.getValueAt(row, col)!=null)?table.getValueAt(row, col).toString() : "";
						if(matchSearchText(next,searchText)){
							if(table.getValueAt(row, 10) instanceof User){
								searchedUserList.add((User)table.getValueAt(row, 10));
							}
							searchIndexList.add(new TableIndex(row, col));
						}
					}
				}
			}
			setTableValues(HMConstants.EDIT,new ArrayList<User>(searchedUserList));
	}
	private boolean matchSearchText(String currentText,String searchText){
		boolean matched = false;
		if(currentText !=null && searchText != null){
			 if(currentText.toLowerCase(Locale.ENGLISH).startsWith(searchText.toLowerCase(Locale.ENGLISH))){
				 matched = true;
			 }
		}
		return matched;
	}
	private void showSearchResults(int row, int col){
		r = table.getCellRect(row, col, false);
		table.scrollRectToVisible(r);
		
		table.repaint();
	}
	
	public JTextField getSearchText() {
		return searchText;
	}
	public void setSearchText(JTextField searchText) {
		this.searchText = searchText;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	class TableIndex{
		int row=0;
		int col=0;
		public TableIndex(int row,int col){
			this.row = row;
			this.col = col;
		}
		public int getRow() {
			return row;
		}
		public void setRow(int row) {
			this.row = row;
		}
		public int getCol() {
			return col;
		}
		public void setCol(int col) {
			this.col = col;
		}
		
	}
	public void setSearchVisibility(boolean falg){
		previousButton.setVisible(falg);
		searchResult.setVisible(falg);
		nextButton.setVisible(falg);
	}
	public void navigateSearch(String navigation){
		if(searchIndexList.size()>0){
			if(HMConstants.SEARCH_NEXT.equals(navigation)){
				lastIndex++;
				if(lastIndex >= searchIndexList.size()){
					lastIndex=0;
				}
			}else if(HMConstants.SEARCH_PREVIOUS.equals(navigation)){
				lastIndex--;
				if(lastIndex < 0){
					lastIndex= searchIndexList.size() - 1;
				}
			}else if(navigation.equals("")){
				lastIndex = 0;
			}
			TableIndex idx = searchIndexList.get(lastIndex);
			showSearchResults(idx.getRow(),idx.getCol());
			searchResult.setText((lastIndex+1)+" of "+searchIndexList.size());
		}else{
			searchResult.setText("0 of 0");
			r=null;
			table.repaint();
		}
	}
	
}
class RoundJTextField extends JTextField {
    private Shape shape;
    public RoundJTextField(int size) {
        super(size);
        setOpaque(false); // As suggested by @AVD in comment.
    }
    protected void paintComponent(Graphics g) {
         g.setColor(getBackground());
         g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
         super.paintComponent(g);
    }
    protected void paintBorder(Graphics g) {
         g.setColor(getForeground());
         g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
    }
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
         }
         return shape.contains(x, y);
    }
    
}
