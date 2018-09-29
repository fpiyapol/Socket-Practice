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
				new Handler(listener.accept()).start();
			}
		}finally {
			listener.close();
		}
		
	}
	
	//Handler thread class
	private static class Handler extends Thread{
		private String name;
		private Socket socket;
		private BufferedReader in;
        private PrintWriter out;
        
        //Constructs set thread socket
        public Handler(Socket socket) {
        	this.socket = socket;
        }
        
        //Running process
        public void run(){
        	//set communicate i/o
        	try {
        		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        	}catch (IOException e){
        		 System.out.println(e);
        	}finally {
				
			}
        }
	}

}
