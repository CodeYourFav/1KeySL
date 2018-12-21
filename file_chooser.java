package program;
import java.io.File;

import javax.swing.JFileChooser;

public class file_chooser {
	private JFileChooser chooser;
	private StringBuilder sb;
	
	public file_chooser() {
	    chooser = new JFileChooser();
		sb = new StringBuilder();
	}
	
	protected String pickFile() throws Exception{
		if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
			File chosen_file = chooser.getSelectedFile();
			sb.append(chosen_file.getAbsolutePath());
		}
		else{
			sb.append("No file selected");
		}
		return sb.toString();
	}

}
