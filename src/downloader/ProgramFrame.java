package downloader;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class ProgramFrame extends JFrame{
	
	JButton pobierz = new JButton("DOWNLOAD");
	JButton cTxt = new JButton("...");
	JButton cFolder = new JButton("...");
	
	JTextField txt = new JTextField();
	JTextField folder = new JTextField();
	
	JCheckBox names = new JCheckBox();
	
	private JFileChooser chooser;
	private Downloading listener = new Downloading();
	
	private ActionListener wczytywanie = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==cTxt){
				chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
				chooser.setDialogTitle("CHOOSE TXT WITH URL's");
				chooser.setDragEnabled(true);
				chooser.setFileFilter(new FileNameExtensionFilter("TXT", "txt"));
				int returnVal = chooser.showOpenDialog(ProgramFrame.this);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            txt.setText(chooser.getSelectedFile().getPath());
		        }
			}else if(e.getSource()==cFolder){
				chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
				chooser.setDialogTitle("CHOOSE DIRECTORY TO SAVE");
				chooser.setDragEnabled(true);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);
		        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		            folder.setText(chooser.getSelectedFile().getPath());
		         }
			}
		}
	};
	
	public ProgramFrame(){
		super("Image downloader");
		listener.setRamka(this);
		txt.setEditable(false);
		folder.setEditable(false);
		cTxt.addActionListener(wczytywanie);
		cFolder.addActionListener(wczytywanie);
		pobierz.addActionListener(listener);
		setSize(450, 200);
        setResizable(false);
        setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		dodajSrodek();
		dodajDol();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void dodajSrodek(){
		JPanel srodek = new JPanel(new GridLayout(0,2));
		JPanel srodekL = new JPanel(new GridLayout(0, 2));
		JPanel srodekP = new JPanel(new GridLayout(5,1));
		JPanel srodekLL = new JPanel(new GridLayout(5, 1));
		JPanel srodekLR = new JPanel(new GridLayout(5, 2));
		
		srodekLL.add(new JLabel("TXT:"));
		srodekLL.add(new JLabel());
		srodekLL.add(new JLabel("DIRECTORY:"));
		srodekLL.add(new JLabel());
		srodekLL.add(new JLabel("CHANGE NAME"));
		srodekL.add(srodekLL);
		
		srodekLR.add(new JLabel());
		srodekLR.add(cTxt);
		srodekLR.add(new JLabel());
		srodekLR.add(new JLabel());
		srodekLR.add(new JLabel());
		srodekLR.add(cFolder);
		srodekLR.add(new JLabel());
		srodekLR.add(new JLabel());
		srodekLR.add(new JLabel());
		srodekLR.add(names);
		srodekL.add(srodekLR);
		
		srodekP.add(txt);
		srodekP.add(new JLabel());
		srodekP.add(folder);
		
		srodek.add(srodekL);
		srodek.add(srodekP);
		add(srodek,BorderLayout.CENTER);
	}
	
	private void dodajDol(){
		add(pobierz,BorderLayout.SOUTH);
	}
	
}
