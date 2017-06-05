package downloader;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;


public class Downloading implements ActionListener{

	private ProgramFrame ramka;
	
	public void setRamka(ProgramFrame ramka){
		this.ramka = ramka;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	
		if(!ramka.txt.getText().equals("")&&!ramka.folder.getText().equals("")){
			
				SwingWorker<Object, Object> sw = new SwingWorker<Object, Object>() {
			    	 
			    	 @Override
			    	 protected Object doInBackground() throws Exception{
				
			    		 ramka.cTxt.setEnabled(false);
			    		 ramka.cFolder.setEnabled(false);
			    		 ramka.pobierz.setEnabled(false);
			    		 ramka.pobierz.setText("WAIT...");
			    		 
						String txtP = ramka.txt.getText();
						String folderP = ramka.folder.getText();
						
						File plikTXT = new File(txtP);
						
						try (BufferedReader br = new BufferedReader(new FileReader(plikTXT))) {
							
							int udane = 0;
							int nieudane = 0;
						    String line;
						    Image image;
						    String nazwa;
						    String sciezka;
						    MediaTracker mt;
						    
						    File fout = new File(folderP+"//list.txt");
							FileOutputStream fos = new FileOutputStream(fout);
						 
							BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
						    
						    while ((line = br.readLine()) != null) {
						       if(ramka.names.isSelected()){
						    	   String[] podzielone = line.split("\t");
					    		   	mt = new MediaTracker(ramka);
						    	   
						    	   		nazwa = podzielone[1]+".jpg";
								    	sciezka = folderP+"\\"+nazwa;
								    	int numerek = 0;
								    	String newName = nazwa;
								    	
								    	URL url = new URL(podzielone[0]);
								        image = Toolkit.getDefaultToolkit().getImage(url);mt.addImage(image, 0);
									    try{
									        	
									        mt.waitForID(0);
											    
									    }catch(InterruptedException e){
										    
									        System.out.println("Przerwano");
									        	
									    }

								        if(image.getHeight(null)>0&&image.getWidth(null)>0){
									        File outputfile = new File(sciezka);
									        if(outputfile.exists() && !outputfile.isDirectory()){
									        	while(outputfile.exists()){
											        numerek++;
									        		String[] temp = nazwa.split("\\.");
									        		newName = temp[0]+"("+numerek+")."+temp[1];
									        		sciezka = folderP+"\\"+newName;
									        		outputfile = new File(sciezka);
									        	}
									        }
									        nazwa = newName;
								    		String[] t = nazwa.split("\\.");
								    		nazwa = t[0]+".jpg";
								        	BufferedImage bi = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_RGB);
								        	bi.getGraphics().drawImage(image, 0, 0, null);
								        	ImageIO.write(bi, "jpg", outputfile);
								        	udane++;
									        bw.write(nazwa);
									        bw.newLine();
								        }else{
								        	nieudane++;
								        	System.out.println("NOT FOUND!");
								        }
								        
						       }else{
						    	   if(line.indexOf("\t") < 1){
						    		   	mt = new MediaTracker(ramka);
								    	nazwa = line.substring(line.lastIndexOf('/')+1);
							    		String[] t = nazwa.split("\\.");
							    		nazwa = t[0]+".jpg";
								    	sciezka = folderP+"\\"+nazwa;
								    	
								    	URL url = new URL(line);
								        image = Toolkit.getDefaultToolkit().getImage(url);
								        mt.addImage(image, 0);
								        try{
								        	
								        	mt.waitForID(0);
										    
								        }catch(InterruptedException e){
									    
								        	System.out.println("Przerwano");
								        	
								        }
								        if(image.getHeight(null)>0&&image.getWidth(null)>0){
								        	
											    File outputfile = new File(sciezka);
											    BufferedImage bi = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_RGB);
										        bi.getGraphics().drawImage(image, 0, 0, null);
										        ImageIO.write(bi, "jpg", outputfile);
										        udane++;
											    bw.write(nazwa);
											    bw.newLine();
											    
								        }else{

								        	nieudane++;
								        	System.out.println("NOT FOUND!");
								        	
								        }
						    	   }else{
						    		   mt = new MediaTracker(ramka);
						    		   String[] split = line.split("\t");
						    		   nazwa = split[0].substring(line.lastIndexOf('/')+1);
						    		   String[] t = nazwa.split("\\.");
						    		   nazwa = t[0]+".jpg";
						    		   sciezka = folderP+"\\"+nazwa;
								    	
								    	URL url = new URL(split[0]);
								        image = Toolkit.getDefaultToolkit().getImage(url);
								        mt.addImage(image, 0);
								        try{
								        	
								        	mt.waitForID(0);
										    
								        }catch(InterruptedException e){
									    
								        	System.out.println("Przerwano");
								        	
								        }
								        if(image.getHeight(null)>0&&image.getWidth(null)>0){
								        	
									        File outputfile = new File(sciezka);
								        	BufferedImage bi = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_RGB);
								        	bi.getGraphics().drawImage(image, 0, 0, null);
								        	ImageIO.write(bi, "jpg", outputfile);
								        	udane++;
									        bw.write(nazwa);
									        bw.newLine();
								        }else{
								        	nieudane++;
								        	System.out.println("NOT FOUND!");
								        } 
						    	   }
						    	   
						       }
						       
						    }
						    
						    bw.write("Completed: "+udane+", not found: "+nieudane+".");
						    bw.close();
						    br.close();
						    fos.close();
						    
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						return null;
			    	 }
			    	 @Override
			    	 public void done(){
			    		 
			    		 ramka.cTxt.setEnabled(true);
			    		 ramka.cFolder.setEnabled(true);
			    		 ramka.pobierz.setEnabled(true);
			    		 ramka.pobierz.setText("DOWNLOAD");
			    		 
			    		 JOptionPane.showMessageDialog(null, "Done! List of downloaded images is located in 'list.txt' file in choosen directory.","OK!", JOptionPane.INFORMATION_MESSAGE);
			    		 
			    	 }
			    	 
			};
		
		sw.execute();
			
		}
		
	}
	
}
