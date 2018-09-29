import java.io.*;
import java.net.*;
import java.util.*;


//Create Server that communicate with many clients
//Server can Generate A Set of Number and send to all clients

public class Server {
	
	//Set port for Server
	private static final int port = 8910;
	
	
	//Handler many clients with Thread
	public static void main(String[] args) throws Exception {
		System.out.println("Server is Running...");
		
		//Create Socket Listener
		ServerSocket listener = new ServerSocket(port);
		
		try {
			while(true) {
				//new Client
				new Handler(listener.accept()).start();
			}
		}finally {
			listener.close();
		}
		
	}
	
	//Handler thread class
	private static class Handler extends Thread{
		private Socket socket;
		private BufferedReader in;
        private PrintWriter out;
        
        //Constructs set thread socket
        public Handler(Socket socket) {
        	this.socket = socket;
        }
        
        //Running process
        public void run(){
        	try {
        		//set communicate i/o
        		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        		out = new PrintWriter(socket.getOutputStream(), true);
        		out.println("Hello client i am server");
        		while(true) {
        			String input = in.readLine();
        			if(input == null) {
        				return;
        			}
        			System.out.println(input);
        		}
        	}catch (IOException e){
        		 System.out.println(e);
        	}finally {
				try {
					//this client is going down close socket
					socket.close();
				}catch(IOException e){
					System.out.println(e);
				}
			}
        }
	}

}
