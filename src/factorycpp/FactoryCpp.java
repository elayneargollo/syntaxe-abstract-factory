package factorycpp;

import java.io.File;

import interfaces.IBuilder;
import interfaces.IFactory;
import interfaces.ISyntaxHighlighter;

public class FactoryCpp implements IFactory{

	@Override
	public IBuilder createCompiler(File file) {
		return new BuilderCpp();
	}

	@Override
	public ISyntaxHighlighter create(File file) {
		return new SyntaxHighlighterCpp();
	}

}
