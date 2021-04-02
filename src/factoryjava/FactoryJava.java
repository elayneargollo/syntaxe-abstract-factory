package factoryjava;

import java.io.File;

import interfaces.IBuilder;
import interfaces.IFactory;
import interfaces.ISyntaxHighlighter;


public class FactoryJava implements IFactory {

	@Override
	public IBuilder createCompiler(File file) {
		return new BuilderJava();
	}

	@Override
	public ISyntaxHighlighter create(File file) {
		return new SyntaxHighlighterJava();
	}

}
