package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.alee.laf.WebLookAndFeel;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.beans.PropertyChangeEvent;
import javax.swing.UIManager;

public class MainWindow {

	private JFrame frmIngInversa;
	
	private String projectDirectory = null;
	private String cmodelxmi = null;
	private String haxemodelxmi = null;
	
	private JButton btnGenerarModeloC = null;
	private JButton btnGenerarModeloHaxe = null;
	private JButton btnGenerarCodigoHaxe = null;
	private JTextField txtProjectFolder;
	private JTextField txtPName;
	private JLabel lblEsperandoC;
	private JLabel lblEsperandoSource;
	private JLabel lblEsperandoHaxe;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WebLookAndFeel.install ();
					MainWindow window = new MainWindow();
					window.frmIngInversa.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmIngInversa = new JFrame();
		frmIngInversa.setResizable(false);
		frmIngInversa.setTitle("Migraci\u00F3n C++ a Haxe");
		frmIngInversa.setBounds(100, 100, 450, 249);
		frmIngInversa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		txtProjectFolder = new JTextField();
		txtProjectFolder.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				if(txtProjectFolder.getText().isEmpty()){
					projectDirectory = null;
					setButtonsActive(false);
				}
			}
		});
		txtProjectFolder.setForeground(Color.GRAY);
		txtProjectFolder.setToolTipText("");
		txtProjectFolder.setColumns(10);
		
		JButton btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = selectFolder("Seleccionar carpeta del proyecto");
				if(path != null){
					projectDirectory = path;
					txtProjectFolder.setText(projectDirectory);
					btnGenerarModeloC.setEnabled(true);
				}
			}
		});
		
		JLabel lblSeleccionarProyectoCc = new JLabel("Seleccionar proyecto C/C++");
		
		btnGenerarModeloC = new JButton("Generar modelo C++");
		btnGenerarModeloC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateCModel();
			}
		});
		
		btnGenerarModeloHaxe = new JButton("Generar modelo Haxe");
		btnGenerarModeloHaxe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateHaxeModel();
			}
		});
		
		btnGenerarCodigoHaxe = new JButton("Generar c\u00F3digo Haxe");
		btnGenerarCodigoHaxe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateHaxeSource();
			}
		});
		
		this.setButtonsActive(false);
		
		JLabel lblNombreDelProyecto = new JLabel("Nombre del proyecto");
		
		txtPName = new JTextField();
		txtPName.setForeground(Color.GRAY);
		txtPName.setColumns(10);
		
		lblEsperandoC = new JLabel("");
		
		lblEsperandoHaxe = new JLabel("");
		
		lblEsperandoSource = new JLabel("");
		
		GroupLayout groupLayout = new GroupLayout(frmIngInversa.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(txtProjectFolder, GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSeleccionar))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNombreDelProyecto))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnGenerarCodigoHaxe)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblEsperandoSource))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(btnGenerarModeloC)
										.addComponent(btnGenerarModeloHaxe))
									.addGap(7)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblEsperandoHaxe)
										.addComponent(lblEsperandoC)))))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(txtPName, GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblSeleccionarProyectoCc)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblSeleccionarProyectoCc)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtProjectFolder, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSeleccionar))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNombreDelProyecto)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtPName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnGenerarModeloC)
						.addComponent(lblEsperandoC))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnGenerarModeloHaxe)
						.addComponent(lblEsperandoHaxe))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnGenerarCodigoHaxe)
						.addComponent(lblEsperandoSource))
					.addContainerGap(21, Short.MAX_VALUE))
		);
		frmIngInversa.getContentPane().setLayout(groupLayout);
	}
	
	private String selectFolder(String title){
		String path = null;
		JFileChooser chooser = new JFileChooser("./");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if(title != null) chooser.setDialogTitle(title);
		int returnVal = chooser.showOpenDialog(frmIngInversa);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			path = chooser.getSelectedFile().getAbsolutePath();
			String filter = File.separator+"."+File.separator;
			if(path.contains(filter)) path = path.replace(filter, File.separator);
		}
		return path;
	}
	
	private void setButtonsActive(boolean value){
		if( this.btnGenerarCodigoHaxe != null && this.btnGenerarModeloC != null && this.btnGenerarModeloHaxe != null ){
			this.btnGenerarCodigoHaxe.setEnabled(value);
			this.btnGenerarModeloC.setEnabled(value);
			this.btnGenerarModeloHaxe.setEnabled(value);
		}
	}
	
	private String savePathFile(String title) {
		String path = null;
		JFileChooser chooser = new JFileChooser( this.projectDirectory );
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Metadata Interchange (*.xmi)", "xmi");
		chooser.setFileFilter(filter);
		if(title != null) chooser.setDialogTitle(title);
		int returnVal = chooser.showSaveDialog(frmIngInversa);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			path = chooser.getSelectedFile().getAbsolutePath();
			if(!path.endsWith(".xmi")){
				path += ".xmi";
			}
		}
		return path;
	}
	
	private String openPathFile(String title) {
		String path = null;
		JFileChooser chooser = new JFileChooser( this.projectDirectory );
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Metadata Interchange (*.xmi)", "xmi");
		chooser.setFileFilter(filter);
		if(title != null) chooser.setDialogTitle(title);
		int returnVal = chooser.showOpenDialog(frmIngInversa);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			path = chooser.getSelectedFile().getAbsolutePath();
			if(!path.endsWith(".xmi")){
				path += ".xmi";
			}
		}
		return path;
	}
	
	private void generateCModel(){
		if(this.txtPName.getText().isEmpty()) this.txtPName.setText("Unknown project");
		lblEsperandoC.setText("Generando...");
		String path = this.savePathFile("Seleccionar archivo de salida");
		if(path != null){
			Process ps;
			try {
				ps = Runtime.getRuntime().exec(new String[]{"java","-jar","lib/IngInversa.jar", this.txtPName.getText(), this.projectDirectory, path});
				ps.waitFor();
		        java.io.InputStream is=ps.getInputStream();
		        byte b[]=new byte[is.available()];
		        is.read(b,0,b.length);
		        System.out.println(new String(b));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JOptionPane.showMessageDialog(null, "Modelo generado exitosamente");
				lblEsperandoC.setText("Generado");
				this.cmodelxmi = path;
				this.btnGenerarModeloHaxe.setEnabled(true);
			}
		}else{
			lblEsperandoC.setText("Error: Ruta indefinida");
		}
	}
	
	private void generateHaxeModel(){
		JOptionPane.showMessageDialog(null, "Función sin implementar.\r\nSeleccionar modelo haxe generado.");
		String path = this.openPathFile("Seleccionar archivo de modelo haxe");
		if(path != null){
			lblEsperandoHaxe.setText("Seleccionado");
			this.haxemodelxmi = path;
			this.btnGenerarCodigoHaxe.setEnabled(true);
		}
		/*String path = null;
		boolean cancel = false;
		do {
			path = this.savePathFile();
			if(path != null && path.equals(this.cmodelxmi)) {
				JOptionPane.showMessageDialog(null, "El archivo no puede llamarse igual");
				path = null;
			}else{
				cancel = true;
			}
		} while (path == null && !cancel);
		lblEsperandoHaxe.setText("Generando...");
		if(path != null){
			ATLLauncher l = new ATLLauncher();
			l.launch(cmodelxmi, path);
			JOptionPane.showMessageDialog(null, "Modelo generado exitosamente");
			lblEsperandoHaxe.setText("Generado");
			this.haxemodelxmi = path;
			this.btnGenerarCodigoHaxe.setEnabled(true);
		}else{
			lblEsperandoHaxe.setText("Error: Ruta indefinida");
		}*/
	}
	
	private void generateHaxeSource(){
		lblEsperandoSource.setText("Generando...");
		String path = this.selectFolder("Seleccionar carpeta de salida");
		if(path != null){
			Process ps;
			try {
				ps = Runtime.getRuntime().exec(new String[]{"java","-jar","lib/IngDirecta.jar", this.haxemodelxmi,path});
				ps.waitFor();
		        java.io.InputStream is=ps.getInputStream();
		        byte b[]=new byte[is.available()];
		        is.read(b,0,b.length);
		        System.out.println(new String(b));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JOptionPane.showMessageDialog(null, "Codigo generado exitosamente");
				lblEsperandoSource.setText("Generado");
			}
		}else{
			lblEsperandoSource.setText("Error: Ruta indefinida");
		}
	}
}
