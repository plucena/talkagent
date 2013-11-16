package br.usp.talkagent.ucl.converter.parser;

import java.awt.*;
import java.awt.event.*;
/**
 * Insert the type's description here.
 * Creation date: (07/05/2001 %r)
 * @author: 
 */
public class UclLanguageGI extends javax.swing.JApplet implements WindowListener {
	private javax.swing.JPanel ivjJAppletContentPane = null;
	private javax.swing.JLabel ivjJLabel1 = null;
	private Choice ivjChoice1 = null;
	private javax.swing.JButton ivjJButton1 = null;
	private javax.swing.JButton ivjJButton2 = null;
	private javax.swing.JList ivjJList1 = null;
	private javax.swing.JScrollPane ivjJScrollPane1 = null;
	private javax.swing.JTabbedPane ivjJTabbedPane1 = null;
	private javax.swing.JPanel ivjPage = null;
/**
 * Returns information about this applet.
 * @return a string of information about this applet
 */
public String getAppletInfo() {
	return "UclLanguageGI\n" + 
		"\n" + 
		"Insert the type's description here.\n" + 
		"Creation date: (07/05/2001 %r)\n" + 
		"@author: Carlos A. Estombelo Montesco\n" + 
		"";
}
/**
 * Return the Choice1 property value.
 * @return java.awt.Choice
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Choice getChoice1() {
	if (ivjChoice1 == null) {
		try {
			ivjChoice1 = new java.awt.Choice();
			ivjChoice1.setName("Choice1");
			ivjChoice1.setFont(new java.awt.Font("dialog", 0, 10));
			ivjChoice1.setBounds(22, 32, 281, 40);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjChoice1;
}








/**
 * Called whenever the part throws an exception.
 * @param exception java.lang.Throwable
 */
private void handleException(java.lang.Throwable exception) {

	/* Uncomment the following lines to print uncaught exceptions to stdout */
	System.out.println("--------- UNCAUGHT EXCEPTION ---------");
	exception.printStackTrace(System.out);
}

/**
 * Initializes the applet.
 * 
 * @see #start
 * @see #stop
 * @see #destroy
 */
public void init() {
	try {
		setName("UclLanguageGI");
		setSize(834, 471);
		setContentPane(getJAppletContentPane());
		// user code begin {1}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {2}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * Starts the applet when it is run as an application
 * @param args an array of command-line arguments
 */
public static void main() 
{
	UclLanguageGI applet = new UclLanguageGI();
	javax.swing.JFrame frame = new javax.swing.JFrame("Applet");

	frame.addWindowListener(applet);
	frame.getContentPane().add("Center", applet);
	frame.setSize(350, 250);
	frame.show();

	applet.init();
	applet.start();
}
/**
 * Paints the applet.
 * If the applet does not need to be painted (e.g. if it is only a container for other
 * awt components) then this method can be safely removed.
 * 
 * @param g  the specified Graphics window
 * @see #update
 */
public void paint(Graphics g) {
	super.paint(g);

	// insert code to paint the applet here
}
/**
 * Invoked when a window is activated.
 * @param e the received event
 */
public void windowActivated(WindowEvent e) {
	// Do nothing.
	// This method is required to comply with the WindowListener interface.
}
/**
 * Invoked when a window has been closed.
 * @param e the received event
 */
public void windowClosed(WindowEvent e) {
	// Do nothing.
	// This method is required to comply with the WindowListener interface.
}
/**
 * Invoked when a window is in the process of being closed.
 * The close operation can be overridden at this point.
 * @param e the received event
 */
public void windowClosing(WindowEvent e) {
	// The window is being closed.  Shut down the system.
	System.exit(0);
}
/**
 * Invoked when a window is deactivated.
 * @param e the received event
 */
public void windowDeactivated(WindowEvent e) {
	// Do nothing.
	// This method is required to comply with the WindowListener interface.
}
/**
 * Invoked when a window is de-iconified.
 * @param e the received event
 */
public void windowDeiconified(WindowEvent e) {
	// Do nothing.
	// This method is required to comply with the WindowListener interface.
}
/**
 * Invoked when a window is iconified.
 * @param e the received event
 */
public void windowIconified(WindowEvent e) {
	// Do nothing.
	// This method is required to comply with the WindowListener interface.
}
/**
 * Invoked when a window has been opened.
 * @param e the received event
 */
public void windowOpened(WindowEvent e) {
	// Do nothing.
	// This method is required to comply with the WindowListener interface.
}
/**
 * Return the JAppletContentPane property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPanel getJAppletContentPane() {
	if (ivjJAppletContentPane == null) {
		try {
			ivjJAppletContentPane = new javax.swing.JPanel();
			ivjJAppletContentPane.setName("JAppletContentPane");
			ivjJAppletContentPane.setLayout(null);
			getJAppletContentPane().add(getJLabel1(), getJLabel1().getName());
			getJAppletContentPane().add(getChoice1(), getChoice1().getName());
			getJAppletContentPane().add(getJScrollPane1(), getJScrollPane1().getName());
			getJAppletContentPane().add(getJButton1(), getJButton1().getName());
			getJAppletContentPane().add(getJButton2(), getJButton2().getName());
			getJAppletContentPane().add(getJTabbedPane1(), getJTabbedPane1().getName());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJAppletContentPane;
}/**
 * Return the JButton1 property value.
 * @return javax.swing.JButton
 */  
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JButton getJButton1() {
	if (ivjJButton1 == null) {
		try {
			ivjJButton1 = new javax.swing.JButton();
			ivjJButton1.setName("JButton1");
			ivjJButton1.setText("JButton1");
			ivjJButton1.setBounds(24, 67, 93, 23);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJButton1;
}/**
 * Return the JButton2 property value.
 * @return javax.swing.JButton
 */  
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JButton getJButton2() {
	if (ivjJButton2 == null) {
		try {
			ivjJButton2 = new javax.swing.JButton();
			ivjJButton2.setName("JButton2");
			ivjJButton2.setText("JButton2");
			ivjJButton2.setBounds(373, 186, 93, 23);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJButton2;
}/**
 * Return the JLabel1 property value.
 * @return javax.swing.JLabel
 */  
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JLabel getJLabel1() {
	if (ivjJLabel1 == null) {
		try {
			ivjJLabel1 = new javax.swing.JLabel();
			ivjJLabel1.setName("JLabel1");
			ivjJLabel1.setText("Sentence");
			ivjJLabel1.setBounds(21, 17, 132, 13);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJLabel1;
}/**
 * Return the JList1 property value.
 * @return javax.swing.JList
 */  
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JList getJList1() {
	if (ivjJList1 == null) {
		try {
			ivjJList1 = new javax.swing.JList();
			ivjJList1.setName("JList1");
			ivjJList1.setBounds(0, 0, 160, 120);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJList1;
}/**
 * Return the JScrollPane1 property value.
 * @return javax.swing.JScrollPane
 */  
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JScrollPane getJScrollPane1() {
	if (ivjJScrollPane1 == null) {
		try {
			ivjJScrollPane1 = new javax.swing.JScrollPane();
			ivjJScrollPane1.setName("JScrollPane1");
			ivjJScrollPane1.setBounds(370, 31, 445, 149);
			getJScrollPane1().setViewportView(getJList1());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJScrollPane1;
}/**
 * Return the JTabbedPane1 property value.
 * @return javax.swing.JTabbedPane
 */  
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JTabbedPane getJTabbedPane1() {
	if (ivjJTabbedPane1 == null) {
		try {
			ivjJTabbedPane1 = new javax.swing.JTabbedPane();
			ivjJTabbedPane1.setName("JTabbedPane1");
			ivjJTabbedPane1.setBounds(33, 227, 762, 229);
			ivjJTabbedPane1.insertTab("Page", null, getPage(), null, 0);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJTabbedPane1;
}/**
 * Return the Page property value.
 * @return javax.swing.JPanel
 */  
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPanel getPage() {
	if (ivjPage == null) {
		try {
			ivjPage = new javax.swing.JPanel();
			ivjPage.setName("Page");
			ivjPage.setLayout(null);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjPage;
}}