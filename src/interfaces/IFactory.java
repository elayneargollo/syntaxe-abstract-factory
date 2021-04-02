package interfaces;

import java.io.File;

import javax.swing.JFrame;

public interface IFactory {
	public abstract IBuilder createCompiler(File file);
	public abstract ISyntaxHighlighter create(File file);
}
