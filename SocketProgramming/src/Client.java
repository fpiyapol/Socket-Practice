import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client {
	BufferedReader in;
    PrintWriter out;
    
	private void run() throws IOException {
		String serverAddress = "127.0.0.1";
        Socket socket = new Socket(serverAddress, 8910);
        System.out.println("Client test");
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println(in.readLine());
        out = new PrintWriter(socket.getOutputStream(), true);
		
        while(true) {
        	String line = in.readLine();
        	System.out.println(line);
        }
	}
	public static void main(String[] args) throws Exception {
		Client c = new Client();
		c.run();

	}

}
