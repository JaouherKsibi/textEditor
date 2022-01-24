package textEditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TextEditor extends JFrame implements ActionListener {
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private JSpinner fontSizeSpinner;
	private JLabel fontLabel;
	private JButton fontColorButton;
	private JComboBox<String> fontBox;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem openItem;
	private JMenuItem saveItem;
	private JMenuItem exitItem;
	public TextEditor() {
		//menu Bar 
		menuBar=new JMenuBar();
		
		fileMenu=new JMenu("File");
		openItem=new JMenuItem("Open");
		openItem.addActionListener(this);
		saveItem=new JMenuItem("Save");
		saveItem.addActionListener(this);
		exitItem=new JMenuItem("Exit");
		exitItem.addActionListener(this);
		
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);
		
		menuBar.add(fileMenu);
		//Font Box 
		String[] fonts=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		fontBox=new JComboBox<String>(fonts);
		
		fontBox.setSelectedItem("Arial");
		fontBox.addActionListener(this);
		
		
		
		//Color Button
		fontColorButton=new JButton("Color");
		fontColorButton.addActionListener(this);
		
		
		//FontSizeSpinner
		fontLabel=new JLabel("Font:");
		fontSizeSpinner=new JSpinner();
		fontSizeSpinner.setPreferredSize(new Dimension(50,25));
		fontSizeSpinner.setValue(20);
		fontSizeSpinner.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int)fontSizeSpinner.getValue()));
				
			}
		});
		//textArea
		textArea=new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Arial",Font.PLAIN,20));
		
		//Scrollpane
		scrollPane=new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(450,450));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		//Adding the componenets to the frame
		this.setJMenuBar(menuBar);
		this.add(fontBox);
		this.add(fontLabel);
		this.add(fontSizeSpinner);
		this.add(fontColorButton);
		this.add(scrollPane);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Jawher's text Editor");
		this.setSize(500, 500);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==fontColorButton) {
			JColorChooser colorChooser=new JColorChooser();
			Color color=colorChooser.showDialog(null, "choose a Color", Color.black);
			textArea.setForeground(color);
		}
		else if(e.getSource()==fontBox) {
			textArea.setFont(new Font((String)fontBox.getSelectedItem(),Font.PLAIN,(int)fontSizeSpinner.getValue()));
		}
		else if(e.getSource()==openItem) {
			JFileChooser fileChooser=new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			FileNameExtensionFilter filter=new FileNameExtensionFilter("Text Files", "txt");
			fileChooser.setFileFilter(filter);
			int response =fileChooser.showOpenDialog(null);
			if(response==JFileChooser.APPROVE_OPTION) {
				File file=new File(fileChooser.getSelectedFile().getAbsolutePath());
				Scanner fileIn=null;
				try {
					fileIn=new Scanner(file);
					if(file.isFile()) {
						while(fileIn.hasNextLine()) {
							String line=fileIn.nextLine()+"\n";
							textArea.append(line);
						}
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
				finally {
					fileIn.close();
				}
			}
		}
		else if(e.getSource()==saveItem) {
			JFileChooser fileChooser=new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			int response=fileChooser.showSaveDialog(null);
			if(response==JFileChooser.APPROVE_OPTION) {
				File file=new File(fileChooser.getSelectedFile().getAbsolutePath());
				PrintWriter fileOut=null;
				try {
					fileOut=new PrintWriter(file);
					fileOut.println(textArea.getText());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				finally {
					fileOut.close();
				}
			}
		}
		else if(e.getSource()==exitItem) {
			System.exit(0);
		}
		
	}
	
}
