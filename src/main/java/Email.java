import java.io.BufferedReader;
import java.io.FileReader;
import java.net.PasswordAuthentication;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Email {
	
	private static BufferedReader br;

	public static void main(String args[])throws Exception{

		anniv();
	}
	
	/**
	 * Fonction qui retourne un tableau contenant les données d'un fichier CSV
	 * @return data -> contient les données du CSV
	 * @throws Exception
	 */
	public static String[] CSVFile() throws Exception{
		  
	    br = new BufferedReader(new FileReader("/home/arrigoj/eclipse-workspace/Info/src/Data.csv"));
	    String ligne = null;
	    while ((ligne = br.readLine()) != null)
	     {
	    	
	      String[] data = ligne.split(",");
	      return data;
	      
	      }
	    
	    br.close();
		return null;
	  }
	
	/**
	 * Méthode qui vérifie si les données dans le CSV et la date d'aujourd'hui sont les mêmes
	 * Si oui, apppelle a la méthode sendMessage
	 * @throws Exception
	 */
	public static void anniv() throws Exception{
		
		SimpleDateFormat formater = null;
	    Date aujourdhui = new Date();
	    
	    formater = new SimpleDateFormat("d");	    
	    String jour = formater.format(aujourdhui);
	    
	    formater = new SimpleDateFormat("M");	    
	    String mois = formater.format(aujourdhui);
	    
		String[] tab = CSVFile();
	    
		//test pour savoir si c'est l'anniversaire
		//Je pars du principe que dans le fichier CVS les données sont : @,jours,mois,année
		//Et qu'il n'y en manque jamais un seul
	    if (Integer.parseInt(tab[1]) == Integer.parseInt(jour)){;
	    	if (Integer.parseInt(tab[2]) == Integer.parseInt(mois)) {
	    		String subject = "Anniversaire";
	    		String text = "Bonne Anniversaire ! Cordialement ARRIGO Jordan";
	    		sendMessage(subject, text, tab[0]);
	    	}
	    }
	    else {
	    	System.out.println("Pas encore");
	    }
	    
	}
	
	/**
	 * Méthode qui gère l'envoie du mail via gmail
	 * @param subject -> sujet du mail
	 * @param text -> test à écrire dans le mail
	 * @param destinataire -> le destinataire du mail
	 * @throws NoSuchProviderException
	 */
	public static void sendMessage(String subject, String text, String destinataire) throws NoSuchProviderException {
		
		String userName = "bite";
		java.lang.String password = "pute";
		
	    try{
	         
	        //ouverture d'une session. la session gére les informations de configuration (nom d'utilisateur, mot de passe, hôte) nécessaires pour utiliser les fonctionnalités de JavaMail
	    	Properties props = new Properties();
	    	props.put("mail.smtp.auth", "true");
	    	props.put("mail.smtp.starttls.enable", "true");
	    	props.put("mail.smtp.host", "smtp.gmail.com");
	    	props.put("mail.smtp.port", "587");
	        props.setProperty("mail.from", "ddomofwar@gmail"); // @ expediteur
	        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
	            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
	                return new javax.mail.PasswordAuthentication(userName, password);
	            }
	        });

	        //Le message
	        Message     message     = new MimeMessage(session);
	        InternetAddress recipient   = new InternetAddress(destinataire);//***
	        message.setRecipient(Message.RecipientType.TO, recipient);
	        message.setSubject(subject);
	        message.setText(text);
	         
	        //Transport
	        Transport.send(message);
	            }catch(AddressException e) {
	                System.err.println("Adresse invalide");
	                System.err.println(e);
	            }
	            catch(MessagingException e) {
	                System.err.println("Erreur dans le message");
	                System.err.println(e);
	            }
	}
}