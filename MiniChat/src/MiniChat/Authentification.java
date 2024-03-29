package MiniChat;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Authentification implements Runnable{
	
	private Socket socket;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private String login = "zero", pass = null;
	public boolean authentifier = false;
	public Thread t2;
	
	public Authentification(Socket s) {
		socket = s;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			
			while(!authentifier) {
				
				out.println("Entrez votre login :");
				out.flush();
				login = in.readLine();
				
				out.println("Entrez votre mot de passe :");
				out.flush();
				pass = in.readLine();
				
				if(isValid(login, pass)) {
					out.println("connecte");
					System.out.println(login+ " vient de se connecter");
					out.flush();
					authentifier = true;
				} else {
					out.println("erreur");
					out.flush();
				}
				
			}
			
			t2 = new Thread(new Chat_ClientServeur(socket, login));
			t2.start();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(login + " ne repend pas !");
		}
	
	}
	
	private static boolean isValid(String login, String pass) {
		boolean connexion = false;
		
		Scanner sc;
		try {
			
			sc = new Scanner(new File("C:\\Users\\Meriem\\Desktop\\javas\\MiniChat\\zero.txt"));
			while(sc.hasNext()) {
				if(sc.nextLine().equals(login+" "+pass)) {
					connexion = true;
					break;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Le fichier n'existe pas !");
		}
		
		
		return connexion;
		
	}
}
