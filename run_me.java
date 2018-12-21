package program;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class run_me {
	private JFrame main_frame;
	private String Complete_Path;
	private static int key_code_1;
	private static int key_code_2;
	private static File save_doc;
	private static boolean allow_hook;
	 // boolean to end global hook. this is initialized early 
	private static boolean HookRunning = true;
	private static final int shut_down_key = GlobalKeyEvent.VK_F12;
	
	public run_me() {
		main_frame = new JFrame("1keyS&L -- press "+KeyEvent.getKeyText(shut_down_key)+" to terminate");
		main_frame.setSize(450, 300);
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main_frame.setResizable(false);
		Complete_Path = null;		
		key_code_1 = KeyEvent.VK_F1; //default hotkey
		key_code_2 = KeyEvent.VK_F2;
		allow_hook = false; // the hook won't change file permission unless we allow it.
		
	}
	
	private void gui() {
		// define fonts, border
		Font courier_bold_16 = new Font("Courier New",Font.BOLD,16);
		Font arial_italic_16 = new Font("Arial", Font.ITALIC,16);
		Font arial_plain_16 = new Font("Arial",Font.PLAIN,16);
		Border myborder_thick = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);
		Border myborder_thin = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
		
		JPanel control_panel = new JPanel();
		control_panel.setLayout(null); // arrange components by coordinates.
		
		JLabel enable_ro = new JLabel("Enable Read Only");
		enable_ro.setFont(courier_bold_16);
		enable_ro.setBounds(40, 30, 180, 30);
		
		JLabel disable_ro= new JLabel("Disable Read Only");
		disable_ro.setFont(courier_bold_16);
		disable_ro.setBounds(40, 70, 180, 30);
		
		JTextField customKey_1 = new JTextField("F1");
		customKey_1.setFont(arial_italic_16);
		customKey_1.setForeground(Color.BLUE); // set text color
		customKey_1.setHorizontalAlignment(SwingConstants.CENTER); // set text alignment
		customKey_1.setBounds(240, 35, 100, 25);
		customKey_1.setFocusTraversalKeysEnabled(false);  // prevent focus traversal via "TAB"
		customKey_1.setEditable(false);		
		customKey_1.setBorder(myborder_thick);
		
		customKey_1.addFocusListener(new FocusListener() { // show caret when focus is gained
			@Override
			public void focusLost(FocusEvent e) {
				return;
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				customKey_1.getCaret().setVisible(true);
			}
		});
		
		customKey_1.addKeyListener(new KeyListener() {		
			@Override
			public void keyTyped(KeyEvent e) {
				return;// TODO Auto-generated method stub
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				return;// TODO Auto-generated method stub				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				key_code_1 = e.getKeyCode();
				customKey_1.setText(KeyEvent.getKeyText(e.getKeyCode()));
			}
		});
		 
		
		JTextField customKey_2 = new JTextField("F2");
		customKey_2.setFont(arial_italic_16);
		customKey_2.setForeground(Color.BLUE);
		customKey_2.setHorizontalAlignment(SwingConstants.CENTER);
		customKey_2.setBounds(240, 75, 100, 25);
		customKey_2.setFocusTraversalKeysEnabled(false); 
		customKey_2.setEditable(false);
		customKey_2.setBorder(myborder_thick);
		
		customKey_2.addFocusListener(new FocusListener() {	
			@Override
			public void focusLost(FocusEvent e) {
				return;
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				customKey_2.getCaret().setVisible(true);
			}
		});
		
		customKey_2.addKeyListener(new KeyListener() {	
			@Override
			public void keyTyped(KeyEvent e) {
				return;// TODO Auto-generated method stub				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				return;// TODO Auto-generated method stub			
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				key_code_2 = e.getKeyCode();
				customKey_2.setText(KeyEvent.getKeyText(e.getKeyCode()));
			}
		});
		
		JTextField path_field= new JTextField("C:\\");
		path_field.setBounds(20,150,400,30);
		path_field.setFont(arial_plain_16);
		path_field.setOpaque(false);
		path_field.setBackground(new Color(0,0,0,0));
		path_field.setBorder(null);
		path_field.setEditable(false);
		path_field.setBorder(myborder_thin);
		
		JButton select_file = new JButton("Select File");
		select_file.setBounds(90, 200, 100, 30);
		select_file.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				file_chooser FC = new file_chooser();
				try {
					Complete_Path = FC.pickFile();
					save_doc = new File(Complete_Path);
					String short_path = shorten_path(Complete_Path, 50);
					path_field.setText(short_path);
				}
				catch (Exception e){
					e.printStackTrace();
				}			
			}			
		});
		
		JButton AllowGlobalHook = new JButton("ALLOW");
		AllowGlobalHook.setBounds(260, 200, 100, 30);
		AllowGlobalHook.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Complete_Path == null)
				{
					allow_hook = false;
					JOptionPane.showMessageDialog(main_frame, "You must select a file !!!");
					return;
				}
				if (AllowGlobalHook.getText().equals("ALLOW")){ // note that in anonymous/private class you can't access local variable
					allow_hook = true;
					System.out.println("Hook is allowed now");
					AllowGlobalHook.setText("DENY");
					AllowGlobalHook.setForeground(Color.RED);
				}	
				else {
					allow_hook = false;
					AllowGlobalHook.setText("ALLOW");
					AllowGlobalHook.setForeground(Color.BLACK);				
				}
			}
		});
				
		control_panel.add(enable_ro);
		control_panel.add(disable_ro);
		control_panel.add(customKey_1);
		control_panel.add(customKey_2);
		control_panel.add(path_field);
		control_panel.add(select_file);
		control_panel.add(AllowGlobalHook);
		
		main_frame.add(control_panel);
		
		//main_frame.pack();
		main_frame.setLocationRelativeTo(null);
		main_frame.setVisible(true);
		
	}
	
	public static void main(String[] args)
	{
		
		run_me window = new run_me();
		window.gui();

		/** 
		 * global keyboard hook uses multi-thread, can't be paused or reopened.
		 * If the boolean in constructor is true, the GlobalKeyAdapter will shield the KeyListener in GUI.
		 * If the boolean is false, both GlobalKeyAdapter and GUI KeyListener can consume the keystroke.
		**/
		GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(false);
		
		keyboardHook.addKeyListener(new GlobalKeyAdapter() {
			@Override 
			public void keyPressed(GlobalKeyEvent event) {
				//System.out.println(event);
				if(event.getVirtualKeyCode()==shut_down_key) // the key to end global hook			
				{
					HookRunning = false;
				}
				else if(allow_hook && event.getVirtualKeyCode()==key_code_1)
				{
					save_doc.setWritable(false);
					System.out.println("write  "+save_doc.canWrite());
				}
				else if(allow_hook && event.getVirtualKeyCode()==key_code_2)
				{
					save_doc.setWritable(true);
					System.out.println("write  "+save_doc.canWrite());
				}
				else 
				{	
				}	
			}
			@Override 
			public void keyReleased(GlobalKeyEvent event) {
				//System.out.println(event); 
				}
		});
		
		try {
			while(HookRunning) Thread.sleep(3000);
		}
		catch(InterruptedException e) {
			// nothing to do here 
		}
		finally { 
			keyboardHook.shutdownHook(); 
			System.exit(0);
		} 

	}
	
	
	private static String shorten_path(String str, int limit)
	{
		if(limit<10 || limit>50){
			return shorten_path(str,50);
		}
		if(str.length()<=limit){
			return str;
		}
		else {
			int end_length = (limit - 6)/2;   
			return str.substring(0, end_length)+"......"+
					str.substring(str.length()-end_length, str.length());
		}
	}
}
