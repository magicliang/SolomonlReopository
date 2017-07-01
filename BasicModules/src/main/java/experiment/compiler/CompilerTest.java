package experiment.compiler;


import javax.tools.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangchuan on 2017/6/9.
 */
public class CompilerTest {

    public static void main(String[] args) {

        // Traverse Java compiler
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        final List<ByteArrayJavaClass> classFileObjects = new ArrayList<>();

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        JavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        fileManager = new ForwardingJavaFileManager<JavaFileManager>(fileManager) {
            public JavaFileObject getJavaFileForOutput(Location location, final String className,
                    JavaFileObject.Kind kind, FileObject sibling) throws IOException {
                if (className.startsWith("x.")) {
                    ByteArrayJavaClass fileObject = new ByteArrayJavaClass(className);
                    classFileObjects.add(fileObject);
                    return fileObject;
                } else
                    return super.getJavaFileForOutput(location, className, kind, sibling);
            }
        };

    }

}
