package dev.perryplaysmc.dynamicenchantments.compiler;

import java.util.*;

import javax.tools.*;


/**
 * Creator: PerryPlaysMC
 * Created: 02/2021
 **/
public class DynamicJavaCompiler {
    private final JavaCompiler javac;
    private DynamicClassLoader classLoader;
    private Iterable<String> options;

    private final Map<String, SourceCode> sourceCodes = new HashMap<>();

    public static DynamicJavaCompiler newInstance(ClassLoader loader) {
        return new DynamicJavaCompiler(loader);
    }

    private DynamicJavaCompiler(ClassLoader loader) {
        this.javac = ToolProvider.getSystemJavaCompiler();
        if(loader == null) this.classLoader = new DynamicClassLoader(ClassLoader.getSystemClassLoader());
        else this.classLoader = new DynamicClassLoader(loader);
    }

    public DynamicJavaCompiler useOptions(String... options) {
        this.options = Arrays.asList(options);
        return this;
    }

    public Map<String, Class<?>> compileAll() throws Exception {
        if(sourceCodes.size() == 0) throw new CompilationException("No source code to compile");
        Collection<SourceCode> compilationUnits = sourceCodes.values();
        DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
        ExtendedStandardJavaFileManager fileManager = new ExtendedStandardJavaFileManager(javac.getStandardFileManager((null), (null), (null)), classLoader);
        JavaCompiler.CompilationTask task = javac.getTask(null, fileManager, collector, options, null, compilationUnits);
        boolean result = task.call();
        if(!result || collector.getDiagnostics().size() > 0) {
            StringBuilder exceptionMsg = new StringBuilder();
            exceptionMsg.append("Unable to compile the source");
            boolean hasErrors = false;
            for(Diagnostic<? extends JavaFileObject> d : collector.getDiagnostics()) {
                switch(d.getKind()) {
                    case OTHER:
                    case ERROR:
                        hasErrors = true;
                        break;
                }
                if(d.getKind()== Diagnostic.Kind.NOTE||d.getKind()== Diagnostic.Kind.MANDATORY_WARNING||d.getKind()== Diagnostic.Kind.WARNING)continue;
                exceptionMsg.append("\n").append("{\n  kind>:").append(d.getKind());
                exceptionMsg.append("\n  ").append("line>:").append(d.getLineNumber());
                exceptionMsg.append("\n  ").append("column>:").append(d.getColumnNumber());
                exceptionMsg.append("\n  ").append("message>:").append(d.getMessage(Locale.US));
                exceptionMsg.append("\n}");
            }
            if(hasErrors) {
                throw new CompilationException(exceptionMsg.toString());
            }
        }

        Map<String, Class<?>> classes = new HashMap<>();
        for(String className : sourceCodes.keySet())
            classes.put(className, classLoader.loadClass(className));
        return classes;
    }

    public Class<?> compile(String className, String sourceCode) throws Exception {
        return addSource(className, sourceCode).compileAll().get(className);
    }

    public DynamicJavaCompiler addSource(String className, String sourceCode) throws Exception {
        sourceCodes.put(className, new SourceCode(className, sourceCode));
        return this;
    }
}
