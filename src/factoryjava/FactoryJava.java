package factoryjava;

import java.io.File;

import interfaces.IBuilder;
import interfaces.IFactory;
import interfaces.ISyntaxe;


public class FactoryJava implements IFactory {

	@Override
	public IBuilder createCompiler(File file) {
		return new BuilderJava();
	}

	@Override
	public ISyntaxe create(File file) {
		return new SyntaxeJava();
	}

}
