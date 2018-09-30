import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;

public class Hybrid {
	//Hybrid can be both client or server
	
	private Socket socket;
	private ServerSocket serverSocket;
	private String ip;
	private int port;
	private Scanner scan = new Scanner(System.in);
	
	public Hybrid() {
		System.out.println("Please enter IP : ");
		ip = scan.nextLine();
		System.out.println("Plese enter PORT : ");
		port = scan.nextInt();
		
		if(!connect()) {
			initServer();
		}
	}
	
	private boolean connect() {
		try {
			socket = new Socket(ip, port);
		}catch(IOException e){
			return false;
		}
		return true;
	}
	
	private void initServer() {
		try {
			serverSocket = new ServerSocket(port, 10, InetAddress.getByName(ip));
		}catch(IOException e) {
			System.out.println(e);
		}
	}
	
	public static void main(String[] args) {
		
	}

}
