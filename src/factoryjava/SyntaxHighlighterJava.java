package factoryjava;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.sun.tools.javac.Main;

import interfaces.ISyntaxHighlighter;

public class SyntaxHighlighterJava extends JFrame implements ISyntaxHighlighter {

	private static final long serialVersionUID = 1L;

	private JPanel panelSyntaxe;
	private RSyntaxTextArea syntaxTextArea;
	private JButton buttonUpdateFile;
	private GroupLayout layout;
	private File fileOperation;

	public void initComponent() {

		panelSyntaxe = new JPanel();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Syntax Highlighter Java");
		getContentPane().setEnabled(false);
		setAlwaysOnTop(true);
		setVisible(true);

		panelSyntaxe.setLayout(new java.awt.CardLayout());
		buttonUpdateFile = new JButton("Update File");

		layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(panelSyntaxe, GroupLayout.PREFERRED_SIZE, 909, GroupLayout.PREFERRED_SIZE)
								.addComponent(buttonUpdateFile, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
				.addContainerGap().addComponent(panelSyntaxe, GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(buttonUpdateFile, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE).addGap(6)));
		getContentPane().setLayout(layout);

		getContentPane().setLayout(layout);
		pack();

	}

	public String createRSyntaxTextArea() throws IOException {

		FileReader fileReader = null;
		fileReader = new FileReader(fileOperation);

		BufferedReader reader = new BufferedReader(fileReader);

		syntaxTextArea = new RSyntaxTextArea(20, 90);
		syntaxTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		syntaxTextArea.setCodeFoldingEnabled(true);
		syntaxTextArea.setFont(new Font("Monospace", Font.PLAIN, 20));

		RTextScrollPane sp = new RTextScrollPane(syntaxTextArea);

		panelSyntaxe.add(sp);
		syntaxTextArea.revalidate();
		syntaxTextArea.read(reader, null);

		return syntaxTextArea.getText();

	}

	public void actionButtonUpdateFile() {

		buttonUpdateFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Object[] options = { "Yes, please", "No, thanks", "Cancel" };

				int optionStatusFile = JOptionPane.showOptionDialog(panelSyntaxe,
						"Do you really want to change this file?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

				switch (optionStatusFile) {

				case 0:
					try {

						Writer arquivo = new FileWriter(fileOperation);
						PrintWriter saida = new PrintWriter(arquivo);
						arquivo.write(syntaxTextArea.getText());
						arquivo.close();

					} catch (IOException e) {
						e.printStackTrace();
					}

					dispose();
					break;
				case 1:
				case 2:
					dispose();
					break;
				}
			}
		});

	}

	@Override
	public JFrame createSyntaxe(File file) throws IOException {
		this.fileOperation = file;

		initComponent();
		createRSyntaxTextArea();
		actionButtonUpdateFile();

		return null;
	}

}
