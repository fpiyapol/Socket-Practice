import java.io.*;
import java.net.*;
import java.util.*;


//Create Server that communicate with many clients
//Server can Generate A Set of Number and send to all clients

public class Server {
	
	//Set port for Server
	private static final int port = 8910;
	private static ArrayList<Integer> num1 = new ArrayList<Integer>();
	private static ArrayList<Integer> num2 = new ArrayList<Integer>();
	
	private static Random ran = new Random();
	
	
	//Handler many clients with Thread
	public static void main(String[] args) throws Exception {
		System.out.println("Server is Running...");
		
		//Create Socket Listener
		ServerSocket listener = new ServerSocket(port);
		
		Thread s1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int i=1; i<=5; i++) {
					int  n = ran.nextInt(9) + 1;
					num1.add(n);
				}
				for(int i=1; i<=7; i++) {
					int  n = ran.nextInt(99) + 10;
					num1.add(n);
				}
			}
		});
		
		Thread s2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int i=1; i<=5; i++) {
					int  n = ran.nextInt(9) + 1;
					num2.add(n);
				}
				for(int i=1; i<=7; i++) {
					int  n = ran.nextInt(99) + 10;
					num2.add(n);
				}
			}
		});
			
		s1.start();
		s2.start();
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
        private OutputStream lout;
        
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
        		lout = socket.getOutputStream();
        		out.println("Hello client i am server");
        		ObjectOutputStream objOut = new ObjectOutputStream(lout);
        		objOut.writeObject(num1);
        		objOut.writeObject(num2);
        		
        		out.println("start");
        		
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
