package dev.perryplaysmc.dynamicenchantments.compiler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;


/**
 * Creator: PerryPlaysMC
 * Created: 02/2021
 **/
public class ExtendedStandardJavaFileManager extends
		ForwardingJavaFileManager<JavaFileManager> {

	private final List<CompiledCode> compiledCode = new ArrayList<>();
	private final DynamicClassLoader cl;

	/**
	 * Creates a new instance of ForwardingJavaFileManager.
	 *
	 * @param fileManager
	 *            delegate to this file manager
	 * @param cl
	 */
	protected ExtendedStandardJavaFileManager(JavaFileManager fileManager,
			DynamicClassLoader cl) {
		super(fileManager);
		this.cl = cl;
	}

	@Override
	public JavaFileObject getJavaFileForOutput(
			Location location, String className,
			JavaFileObject.Kind kind, FileObject sibling) throws IOException {

		try {
			CompiledCode innerClass = new CompiledCode(className);
			compiledCode.add(innerClass);
			cl.addCode(innerClass);
			return innerClass;
		} catch (Exception e) {
			throw new RuntimeException(
					"Error while creating in-memory output file for "
							+ className, e);
		}
	}

	@Override
	public ClassLoader getClassLoader(Location location) {
		return cl;
	}
}
