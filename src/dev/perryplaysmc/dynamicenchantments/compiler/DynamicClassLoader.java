package dev.perryplaysmc.dynamicenchantments.compiler;

import java.util.HashMap;
import java.util.Map;

/**
 * Creator: PerryPlaysMC
 * Created: 02/2021
 **/
public class DynamicClassLoader extends ClassLoader {

	private final Map<String, CompiledCode> customCompiledCode = new HashMap<>();

	public DynamicClassLoader(ClassLoader parent) {
		super(parent);
	}

	public void addCode(CompiledCode cc) {
		customCompiledCode.put(cc.getName(), cc);
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		CompiledCode cc = customCompiledCode.get(name);
		if (cc == null) {
			return super.findClass(name);
		}
		byte[] byteCode = cc.getByteCode();
		return defineClass(name, byteCode, 0, byteCode.length);
	}
}
