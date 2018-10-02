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
    
    private static ArrayList<Integer> num1 = new ArrayList<Integer>();
    private static ArrayList<Integer> num2 = new ArrayList<Integer>();
    
    private JFrame fr = new JFrame("Client");
    private JTextArea ta = new JTextArea("", 5, 50);
    private JTextField tf = new JTextField("");
    private JLabel countdown = new JLabel("Countdown");
    
    private int score = 0;
    private int no = 0;
    
    public Client() {
    	//Draw UI 
    	fr.add(countdown, BorderLayout.SOUTH);
    	fr.add(tf, BorderLayout.NORTH);
    	tf.requestFocus();
    	fr.add(ta);
    	fr.pack();
    	
    	tf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int res = Integer.parseInt(tf.getText());
				if(res == (num1.get(no) + num2.get(no))) {
					score += 1;
					out.println(score);
					tf.setText("");
					no += 1;
					ta.append(num1.get(no) + "+" + num2.get(no) + "\n");
				}
			}
		});
    }

	private void run() throws IOException {		
		String serverAddress = "127.0.0.1";
        Socket socket = new Socket(serverAddress, 8910);
        System.out.println("Client test");
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
        Object object;
        try {
			object = objIn.readObject();
			num1 = (ArrayList<Integer>)object;
			
			object = objIn.readObject();
			num2 = (ArrayList<Integer>)object;
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
        while(true) {
        	String line = in.readLine();
        	if(line.equals("ENTERNAME")) {
        		System.out.println("cl a");
        		out.println("A");
        		out.println("READY");
        	}else if(line.equals("START")) {
        		Timer t = new Timer();
        		t.start();
        		ta.append(num1.get(no) + "+" + num2.get(no)+"\n");
        	}else {
        		ta.append(line);        		
        	}
        }
	}

	class Timer extends Thread{
		public void run() {
			for(int j = 60; j >= 0; j--) {
				try {
					Thread.sleep(1000);
					countdown.setText("Time : " + j);
				}catch(InterruptedException e) {
					System.out.println(e);
				}
			}
		}
	}
	public static void main(String[] args) throws Exception {
		Client c = new Client();
		c.fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.fr.setVisible(true);
		c.run();

	}

}

