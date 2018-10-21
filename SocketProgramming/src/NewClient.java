import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class NewClient {
	
	BufferedReader in;
    PrintWriter out;
    
    private static String input;
    
	private static ArrayList<Integer> num1 = new ArrayList<Integer>();
	private static ArrayList<Integer> num2 = new ArrayList<Integer>();
	
	private static JFrame frame = new JFrame("Client");
	private static JTextField display = new JTextField(50);
	private static JTextField tf = new JTextField(50);
	private static JTextArea ta = new JTextArea("", 5, 50);
	private static JLabel time = new JLabel("Time : -- ");
	private static JPanel pn = new JPanel(new FlowLayout());
	
	private static int no = 0;
	private static int score = 0;
	
	public NewClient() {
		//Draw GUI
		display.setEditable(false);
		frame.add(display, BorderLayout.NORTH);
		frame.add(ta);
		pn.add(time);
		pn.add(tf);
		frame.add(pn, BorderLayout.SOUTH);
		
		frame.pack();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		tf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int res = Integer.parseInt(tf.getText());
				if(res == (num1.get(no) + num2.get(no))) {
					score += 1;
					tf.setText("");
					no += 1;
					display.setText(num1.get(no) + " + " + num2.get(no));
					out.println(score);
				}
				
			}
		});
	}
	
	class Timer extends Thread{
		public void run() {
			for(int j = 60; j >= 0; j--) {
				try {
					Thread.sleep(1000);
					time.setText("Time : " + j);
				}catch(InterruptedException e) {
					System.out.println(e);
				}
			}
		}
	}
	
	public void getObj(Socket socket, ObjectInputStream objIn) throws IOException {
		Object object;
        try {
			object = objIn.readObject();
			num1 = (ArrayList<Integer>)object;
			
			for(int n: num1) {
				System.out.println(n);
			}
			
			object = objIn.readObject();
			num2 = (ArrayList<Integer>)object;
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void run() throws IOException {
		//Address Hard code
		String serverAddress = "127.0.0.1";
        Socket socket = new Socket(serverAddress, 8910);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        
        ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
        
        
        while(true) {
        	input = in.readLine();
        	
        	System.out.println("client " + input + "\n");
        	
        	if(input.equals("ENTERNAME")) {
        		System.out.println("En name");
        		out.println("A");
        	}else if(input.equals("NAMEACCEPTED")) {
        		System.out.println("Obj");
        		getObj(socket, objIn);
        		out.println("RECVED");
        		//Hard code
        		out.println("READY");
        	}else if(input.equals("START")) {
        		Timer t = new Timer();
        		t.start();
        		display.setText(num1.get(no) + "+" + num2.get(no)+"\n");
        	}
        }
	}
	
	public static void main(String[] args) throws IOException {
		NewClient cl = new NewClient();
		cl.run();
	}

}
