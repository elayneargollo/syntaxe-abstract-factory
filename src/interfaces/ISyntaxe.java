package interfaces;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

public interface ISyntaxe {
	public abstract JFrame createSyntaxe(File file)  throws IOException;
}
