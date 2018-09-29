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
    private JFrame fr = new JFrame("Client");
    private JTextArea ta = new JTextArea("", 5, 50);
    private JTextField tf = new JTextField("");
    
    public Client() {
    	//Draw UI 
    	fr.add(tf, BorderLayout.NORTH);
    	tf.requestFocus();
    	fr.add(ta);
    	fr.pack();
    	
    	tf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				out.println(tf.getText());
				tf.setText("");
			}
		});
    }
    
	private void run() throws IOException {		
		String serverAddress = "127.0.0.1";
        Socket socket = new Socket(serverAddress, 8910);
        System.out.println("Client test");
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ta.append(in.readLine());
        out = new PrintWriter(socket.getOutputStream(), true);
        
        while(true) {
        	String line = in.readLine();
        	ta.append(line);
        }
	}
	public static void main(String[] args) throws Exception {
		Client c = new Client();
		c.fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.fr.setVisible(true);
		c.run();

	}

}
