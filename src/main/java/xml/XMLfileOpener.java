package xml;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.*;

/**
 * Utility to load xml files
 * 
 * @author Christine SOLNON (from PlaCo sources)
 * @version 1.0
 */
public class XMLfileOpener extends FileFilter {// Singleton
	
	private static XMLfileOpener instance = null;
	private XMLfileOpener(){}
	public static XMLfileOpener getInstance(){
		if (instance == null) instance = new XMLfileOpener();
		return instance;
	}

 	public File open() throws ExceptionXML{
 		int returnVal;
 		JFileChooser jFileChooserXML = new JFileChooser();
        jFileChooserXML.setFileFilter(this);
        jFileChooserXML.setFileSelectionMode(JFileChooser.FILES_ONLY);
         returnVal = jFileChooserXML.showOpenDialog(null);
        if (returnVal != JFileChooser.APPROVE_OPTION) 
        	throw new ExceptionXML("Problem when opening file");
        return new File(jFileChooserXML.getSelectedFile().getAbsolutePath());
 	}
 	
 	@Override
    public boolean accept(File f) {
    	if (f == null) return false;
    	if (f.isDirectory()) return true;
    	String extension = getExtension(f);
    	if (extension == null) return false;
    	return extension.contentEquals("xml");
    }

	@Override
	public String getDescription() {
		return "XML file";
	}

    private String getExtension(File f) {
	    String filename = f.getName();
	    int i = filename.lastIndexOf('.');
	    if (i>0 && i<filename.length()-1) 
	    	return filename.substring(i+1).toLowerCase();
	    return null;
   }
}
