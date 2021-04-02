

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import interfaces.IBuilder;
import interfaces.IFactory;
import interfaces.ISyntaxHighlighter;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class main extends JFrame {

	private JPanel contentPane;
	private JTextField pathFile;
	private JButton buttonCompile;
	private File file;
	private String factoryName;
	private ArrayList<String> extensionsValid = new ArrayList<>();
	private IFactory factory;
	private IBuilder builder;
	private ISyntaxHighlighter syntaxe;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main frame = new main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void createProductFactory(IFactory factory) throws IOException {

		syntaxe = factory.create(file);
		syntaxe.createSyntaxe(file);

		builder = factory.createCompiler(file);

	}

	public main() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 361, 211);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		pathFile = new JTextField();
		pathFile.setColumns(10);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);

		JButton btnNewButton = new JButton("Send File");

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				file = new File(pathFile.getText());

				if (file.exists()) {

					try {
						plugin();
						createProductFactory(factory);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Error !");
					}
					buttonCompile.setEnabled(true);
				}
			}
		});

		buttonCompile = new JButton("Compile File");
		buttonCompile.setEnabled(false);

		buttonCompile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String sucess = builder.compiler(file);

				if (sucess.equals("Successfully compiled")) {
					JOptionPane.showMessageDialog(null, sucess);
				} else {
					JOptionPane.showMessageDialog(null, sucess);
				}
			}
		});

		JLabel lblSistemaVisual = new JLabel("Visual file system - Abstract Factory");

		JLabel lblFile = new JLabel("File");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addContainerGap(31, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup().addComponent(lblFile)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(pathFile, GroupLayout.PREFERRED_SIZE, 238, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 40, Short.MAX_VALUE).addComponent(
										buttonCompile, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)))
				.addGap(29))
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup().addContainerGap(64, Short.MAX_VALUE)
						.addComponent(lblSistemaVisual).addGap(51)));
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
						gl_panel.createSequentialGroup().addContainerGap(49, Short.MAX_VALUE)
								.addComponent(lblSistemaVisual).addGap(32)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(pathFile, GroupLayout.PREFERRED_SIZE, 27,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblFile))
								.addGap(18).addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(buttonCompile).addComponent(btnNewButton))
								.addContainerGap()));
		panel.setLayout(gl_panel);

	}

	@SuppressWarnings({ "deprecation", "unused" })
	public void plugin() {

		String extensionFile[] = pathFile.getText().split("\\.");
		File file = new File("./plugins");

		if (file.exists()) {

			String[] plugins = file.list();
			URL[] jars = new URL[plugins.length];

			for (int position = 0; position < plugins.length; position++) {

				try {

					jars[position] = (new File("./plugins/" + plugins[position])).toURL();
				} catch (MalformedURLException e) {
					JOptionPane.showMessageDialog(null, "Error verifying .jar file !");
				}
			}

			URLClassLoader ulc = new URLClassLoader(jars);

			extensionFactorySuport(plugins);
			int positionInstance = getPositionFactoryInPluginCompatible(plugins, extensionFile[1], extensionsValid);

			if (positionInstance >= 0) {
				factoryName = plugins[positionInstance].split("\\.")[0];

				try {
					factory = (IFactory) Class.forName(factoryName.toLowerCase() + "." + factoryName, true, ulc).newInstance();
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
					JOptionPane.showMessageDialog(null, "It was not possible to instantiate the factory !");
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"This program does not have a plugin to support this file extension !");

			}

		} else {
			JOptionPane.showMessageDialog(null, "File plugins not found !");
		}

	}

	public int getPositionFactoryInPluginCompatible(String[] plugins, String extensionFile, ArrayList<String> extensionsValid) {
		String extension;
		String factoryName;

		for (int position = 0; position < plugins.length; position++) {
			extension = extensionsValid.get(position);
			factoryName = plugins[position].split("\\.")[0].toLowerCase();

			if (factoryName.contains(extension) && extension.equals(extensionFile)) {
				return position;
			}
		}

		return -1;

	}

	public void extensionFactorySuport(String[] plugins) {

		String extension;

		for (int position = 0; position < plugins.length; position++) {
			extension = plugins[position].split("\\.")[0];
			extension = extension.replace("Factory", "").toLowerCase();
			extensionsValid.add(extension);
		}
	}
}
