package factorycpp;

import java.io.File;

import interfaces.IBuilder;
import interfaces.IFactory;
import interfaces.ISyntaxe;

public class FactoryCpp implements IFactory{

	@Override
	public IBuilder createCompiler(File file) {
		return new BuilderCpp();
	}

	@Override
	public ISyntaxe create(File file) {
		return new SyntaxeCpp();
	}

}
