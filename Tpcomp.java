/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author acer
 */
public class Tpcomp extends javax.swing.JFrame {
       
    /**
     * Creates new form Tpcomp
     */
    static ArrayList<String> m = new ArrayList<String>();
	static ArrayList<String> l = new ArrayList<String>();
	static ArrayList<String> s = new ArrayList<String>();
	static String[] mot;
	
    public Tpcomp() {
        initComponents();
    }
public void charger() throws FileNotFoundException  {
              JFileChooser fc = new JFileChooser("C:\\Documents");
	      FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers text", "Compila");
		fc.addChoosableFileFilter(filter);
		if(fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
			File file = fc.getSelectedFile();
			Scanner sc_l = new Scanner(file);
			Scanner sc_m = new Scanner(file);
			m.clear();
			l.clear();
				while(sc_l.hasNextLine()){
					l.add(sc_l.nextLine());
				}
				while(sc_m.hasNext()){
					m.add(sc_m.next());
					}

			sc_m.close();
			sc_l.close();
			}
	}

	
	public boolean chiffre(String chaine, int i) {
		char[] nombre = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
		int j = 0;
		while (j < nombre.length) {
			if (chaine.charAt(i) == nombre[j]) {
				return true;
			}
			j++;
		}

		return false;
	}

	public String type_numero(String chaine) {
		int i = 0;
		int token_pos = 0;
		boolean point_unique = true;
		while (i < chaine.length()) {
			if (chiffre(chaine, i)) token_pos++;
			else if(point_unique & chaine.charAt(token_pos)=='.') {
				token_pos++;
				point_unique = false;
			}
			i++;
		}
		
		if (token_pos == chaine.length() && !chaine.contains(".")) return "Nombre entier";
		else if (token_pos == chaine.length() && !point_unique) return "Nombre reel";
		return null;

	}
	

	public boolean isChar(String chaine, int i) {
		char[] alphabet = { 'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e', 'F', 'f', 'G', 'g', 'H', 'h', 'I', 'i',
				'J', 'j', 'K', 'k', 'L', 'l', 'M', 'm', 'N', 'n', 'O', 'o', 'P', 'p', 'Q', 'q', 'R', 'r', 'S', 's', 'T',
				't', 'U', 'u', 'V', 'v', 'W', 'w', 'X', 'x', 'Y', 'y', 'Z', 'z' };
		int k = 0;
		while (k < alphabet.length) {
			if (chaine.charAt(i) == alphabet[k]) {
				return true;
			}
			k++;
		}
		return false;

	}

	public String ident(String chaine) {
		boolean verifier_Premier = false;
		boolean tiret_unique = true;
		int token_pos = 0;
		int i = 0;
		if (isChar(chaine, 0)) {
			token_pos++;
			verifier_Premier = true;
		}
		if (verifier_Premier == true && chaine.length() == 1)
			return "identificateur";
		
		else if (chaine.length() > 1) {
			i = 1;
			while (i < chaine.length()) {
				
				if (isChar(chaine, i))
					{token_pos++;
					tiret_unique=true;
					}
				else if (chiffre(chaine, i))
					{token_pos++;
					tiret_unique=true;
					}
				else if (chaine.charAt(i) == '_' && tiret_unique) {
					tiret_unique=false;
					token_pos++;
				}
				i++;
			}
			if (token_pos == chaine.length())
				return "Identificateur";
		}
		return null;
	}
	


	public String reserve(String chaine) {
		String[] mot_reserve = { "String", "\"", "<", ">", ":", ",", "Start_Program", "Int_Number", ";;", "Give", "Real_Number", "If", "--", "Else",
				"Start", "Affect", "to", "Finish", "ShowMes :", "ShowVal", "//.", "End_Program" };

		String[] Affichage = { "Declaration d'une chaine de caractere", "mot reserve pour une chaine de caractere",
				"symbol inferieur", "symbol superieur", "caractere reserv� deux points", "caractere reserv� virgule",
				"Mot reserve debut du programme", " Mot reserve debut declaration d'un entier",
				"Mot reserve fin instruction", "Mot reserve pour affectation entre variables", " Mot reserve debut declaration d'un Real",
				" Mot reserve pour condition SI", "Mot reserve pour condition", "Mot reserve pour condition SINON", "Debut d'un sous programme",
				"Mot reserve pour affectation", "Mot reserve pour affectation", "Fin d'un sous programme", "Mot reserv� pour afficher un message", 
				"Mot reserv� pour afficher une valeur", "Mot reserv� pour un commentaire", "Mot reserve Fin du programme" };
		int i = 0;
		while (i < mot_reserve.length) {
			if (chaine.equals(mot_reserve[i])) {
				return Affichage[i];
			}
			i++;
		}
		return null;
	}
       

public void lexicale(List<String> liste) {
		int i = 0;
		
		while (i < m.size()) {
			if (reserve(m.get(i)) != null) {
				s.add(reserve(m.get(i)));
			} else if (ident(m.get(i)) != null) {
				s.add(ident(m.get(i)));
			} else if (type_numero(m.get(i)) != null) {
				s.add(type_numero(m.get(i)));
			} 
			else l.add("Erreur");

			i++;
		}

	}
	public String syntaxique(String chaine){
		if(chaine.equals("Start_Program")) return "D�but du programme";
		else if(chaine.equals("Else ")) return "SINON";
		else if(chaine.equals("Start ")) return "D�but d'un bloc";
		else if(chaine.equals("Finish ")) return "Fin d'un bloc";
		else if(chaine.startsWith("//.")) return "un commentaire";
		else if(chaine.equals("End_Program")) return "Fin du programme";
		else if(chaine.startsWith("ShowMes : \" ") && chaine.endsWith(" \" ;; ")) return "Affichage d'un message � l'ecran";
		else if(chaine.contains(" ")) {
			mot = chaine.split(" ");
			int i=0, k=1;
			
				if(mot[i].equals("Int_Number")){ 
					i++;
					if(mot[i].equals(":")){
						i++;
						if(ident(mot[i]) != null){
							i++;
							while(mot[i].equals(",")){
								i++;
								k++;
								if(ident(mot[i]) != null) i++;
							}
							if(mot[i].equals(";;")) return "D�claration de "+k+" variables int";
						}
					}
					
				}
				else if(mot[i].equals("Give")){
					i++;
					if(ident(mot[i]) != null){
						i++;
					if(mot[i].equals(":")){
						i++;
						if(type_numero(mot[i]) == "Nombre entier") {
							i++;
							if(mot[i].equals(";;")) return "affectation dune valeur d'entier � "+mot[i-3];
						}
						else if(type_numero(mot[i]) == "Nombre reel"){
							i++;
							if(mot[i].equals(";;")) return "affectation dune valeur du reel � "+mot[i-3];
						}
						
					}
				}
				
				}
			
				
				else if(mot[i].equals("If")){
					i++;
					if(mot[i].equals("--")){
						i++;
					if(ident(mot[i]) != null){
						i++;
						if(mot[i].equals("<") || mot[i].equals(">") || mot[i].equals("==")){
						i++;
						if(ident(mot[i]) != null){
							i++;
						if(mot[i].equals("--")) return "condition if"; 
							}}}}
				}
				
				
				
				else if(mot[i].equals("Affect")){
					i++;
					if(ident(mot[i]) != null){
						i++;
					if(mot[i].equals("to")){
						i++;
						if(ident(mot[i]) != null) {
							i++;
							if(mot[i].equals(";;")) return "affectation de "+mot[i-3]+" a "+mot[i-1];
						}

					}

				}
				}
				
				else if(mot[i].equals("Real_Number")){
					i++;
					if(mot[i].equals(" ")) i++;
						if(ident(mot[i]) != null) i++;
							if(mot[i].equals(";;")) return "D�claration de  var float";
						}
					
				
				else if(mot[i].equals("ShowVal")){
					i++;
					if(mot[i].equals(":")){
						i++;
						if(ident(mot[i]) != null){
							i++;
							if(mot[i].equals(";;")) return "affichage de valeur de "+mot[i-1];
						}
						

				}}
				
				}
		return "erreur de syntaxe";
	}
public String semantique(String chaine){
		if(chaine.equals("Start_Program")) return "public static void main(String[] args) {";
		else if(chaine.equals("Else ")) return "else";
		else if(chaine.equals("Start ")) return "{";
		else if(chaine.equals("Finish ")) return "}";
		else if(chaine.startsWith("//.")) return "/*un commentaire*/";
		else if(chaine.equals("End_Program")) return "}";
		else if(chaine.startsWith("ShowMes : \" ") && chaine.endsWith(" \" ;; ")) return "System.out.println(\"Affichage d'un message\");";
		else if(chaine.contains(" ")) {
			mot = chaine.split(" ");
			int i=0;

				if(mot[i].equals("Int_Number")){
					i++;
					if(mot[i].equals(":"))
						i++;
						if(ident(mot[i]) != null){
							i++;
							while(mot[i].equals(",")){
								i++;
								if(ident(mot[i]) != null) i++;
							}
							if(mot[i].equals(";;"))return "int"+mot[i-5]+","+mot[i-3]+","+mot[i-1]+";";
						}
					

				}
				
				else if(mot[i].equals("Give")){
					i++;
					if(ident(mot[i]) != null){
						i++;
					if(mot[i].equals(":"))i++;
						if(type_numero(mot[i]) == "Nombre entier") {
							i++;
							if(mot[i].equals(";;")) return mot[i-3]+"="+mot[i-1]+";";
						}
						else if(type_numero(mot[i]) == "Nombre reel"){
							i++;
							if(mot[i].equals(";;")) return mot[i-3]+"="+mot[i-1]+";";
						}

					
				}

				}
				
				else if(mot[i].equals("Real_Number")){
					i++;
					if(mot[i].equals(" "))i++;
						if(ident(mot[i]) != null)i++;
							if(mot[i].equals(";;")) return "float "+mot[i-1]+";";
						}


				
				
				else if(mot[i].equals("If")){
					i++;
					
					if(mot[i].equals("--")){
						i++;
					if(ident(mot[i]) != null){
						i++;
						if(mot[i].equals("<") || mot[i].equals(">") || mot[i].equals("==")){
						i++;
						if(ident(mot[i]) != null){
							i++;
						if(mot[i].equals("--")) return "if"+"("+mot[i-3]+mot[i-2]+mot[i-1]+")"; 
							}}}}
					else i++;
				}
				
				
				
				else if(mot[i].equals("Affect")){
					i++;
					if(ident(mot[i]) != null){
						i++;
					if(mot[i].equals("to")){
						i++;
						if(ident(mot[i]) != null) {
							i++;
							if(mot[i].equals(";;")) return  mot[i-3]+"="+mot[i-1]+";";
						}

					}

				}

				}
				
				
				else if(mot[i].equals("ShowVal")){
					i++;
					if(mot[i].equals(":"))i++;
						if(ident(mot[i]) != null)i++;
							if(mot[i].equals(";;")) return "System.out.println("+mot[i-1]+");";
						}

				

				
								}
		return "erreur symantique";
		
	}




	
					

	


	
	

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Ta = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(44, 62, 80));

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(240, 240, 240));
        jLabel1.setText("Commandes:");

        jButton1.setBackground(new java.awt.Color(34, 167, 240));
        jButton1.setFont(new java.awt.Font("Segoe UI Historic", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(240, 240, 240));
        jButton1.setText("Analyse Syntaxique");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(34, 167, 240));
        jButton3.setFont(new java.awt.Font("Segoe UI Historic", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(240, 240, 240));
        jButton3.setText("Analyse Lexical");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(34, 167, 240));
        jButton4.setFont(new java.awt.Font("Segoe UI Historic", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(240, 240, 240));
        jButton4.setText("Charger un fichier");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        Ta.setBackground(new java.awt.Color(228, 241, 254));
        Ta.setColumns(20);
        Ta.setFont(new java.awt.Font("Segoe UI Historic", 1, 14)); // NOI18N
        Ta.setRows(5);
        jScrollPane1.setViewportView(Ta);

        jButton2.setBackground(new java.awt.Color(34, 167, 240));
        jButton2.setFont(new java.awt.Font("Segoe UI Historic", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(240, 240, 240));
        jButton2.setText("Analyse Semantique");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton2MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 597, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   
    
     
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try{
             Ta.setText("");
       charger();
       int i = 0;
	while (i < l.size()) {
          Ta.setText(Ta.getText()+l.get(i)+"\n");
	i++;  }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        
       
      

         

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Ta.setText("");
	lexicale(m);
	int i = 0;
	while (i < m.size()) {
	Ta.setText(Ta.getText()+m.get(i) + " -:- " + s.get(i)+"\n");
							i++;}
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Ta.setText("");
	int i = 0;										
	while(i < l.size()) {
	Ta.setText(Ta.getText()+l.get(i) + " -:- " +syntaxique(l.get(i))+"\n");
							
	i++;}
						
	
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MousePressed
          Ta.setText("");
	int i = 0;										
	while(i < l.size()) {
	Ta.setText(Ta.getText()+l.get(i) + " -:- " +semantique(l.get(i))+"\n");
							
	i++;}
		
    }//GEN-LAST:event_jButton2MousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Tpcomp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tpcomp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tpcomp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tpcomp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tpcomp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Ta;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
