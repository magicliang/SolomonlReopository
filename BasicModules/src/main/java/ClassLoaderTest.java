import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;

/**
 * Created by liangchuan on 2017/6/8.
 */
public class ClassLoaderTest {

    public static void hello() {
        System.out.println("Hello, world");
    }

    public static void main(String[] args) throws Exception {
        /**
         * 样例输出是：
         * file:/Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre/lib/resources.jar
         * file:/Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre/lib/rt.jar
         * file:/Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre/lib/sunrsasign.jar
         * file:/Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre/lib/jsse.jar
         * file:/Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre/lib/jce.jar
         * file:/Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre/lib/charsets.jar
         * file:/Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre/lib/jfr.jar
         * file:/Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre/classes
         *
         * 即使我们的环境变量里面没有对 CLASS_PATH 指向 rt.jar。我们依然可以用 bootstrap 扫描器获得和加载这些包。
         * 值得注意的是， Bootstrap Classloader 本身是 JVM 自己实现的，和我们的根 ClassLoader是不一样的。
         *
         */
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (int i = 0; i < urls.length; i++) {
            System.out.println(urls[i].toExternalForm());
        }
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        ClassLoader extClassLoader = systemClassLoader.getParent();
        ClassLoader bootstrapClassLoader = extClassLoader.getParent();

        System.out.println("systemClassLoader: " + systemClassLoader);
        System.out.println("extClassLoader: " + extClassLoader);
        // 这一行返回 null，也就是说 bootstrap classloader  本身不能由 getParent 得到。
        System.out.println("bootstrapClassLoader: " + bootstrapClassLoader);

        /**
         * output:
         * systemClassLoader: sun.misc.Launcher$AppClassLoader@18b4aac2
         * extClassLoader: sun.misc.Launcher$ExtClassLoader@1d44bcfa
         * bootstrapClassLoader: null
         * */
        try {
            // 这里就是类加载器的相对路径了。如果系统里有配置过 classpaht，就可以看到 classes 或者 libs，否则就是当前路径为类加载器的起点。
            Enumeration<URL> em1 = systemClassLoader.getResources("");
            while (em1.hasMoreElements()) {
                System.out.println(em1.nextElement());
            }
            // Output: file:/Users/magicliang/IdeaProjects/SolomonlReopository/BasicModules/target/classes/
            System.out.println("extClassLoader's loading path: " + System.getProperty("java.ext.dirs"));
            //output: extClassLoader's loading path: /Users/magicliang/Library/Java/Extensions:/Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre/lib/ext:/Library/Java/Extensions:/Network/Library/Java/Extensions:/System/Library/Java/Extensions:/usr/lib/java


            Enumeration<URL> em2 = extClassLoader.getResources("");
            while (em2.hasMoreElements()) {
                System.out.println(em1.nextElement());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        // Try to use CompileClassLoader to load myself's hello method.

        CompileClassLoader compileClassLoader = new CompileClassLoader();
        Class<?> clazz = compileClassLoader.loadClass("ClassLoaderTest");

        //这是寻找无参方法和调用无参方法的一个例子。
        Method hello = clazz.getMethod("hello");
        hello.invoke(null, null);

        // test URLClassLoader, use current class path to find this class.
        URL[] urLs = {new URL("file:.")};// Keep in mind the url must have a protocol format as prefix.
        URLClassLoader urlClassLoader = new URLClassLoader(urls);
        clazz = urlClassLoader.loadClass("ClassLoaderTest");

        hello = clazz.getMethod("hello");
        hello.invoke(null, null);
    }
}


/**
 * 可能可以实现的需求：
 *   执行代码前验证数字签名
 *   根据用户提供的密码解密代码，从而实现代码混淆器来反编译 class 文件。我的理解是，用户提供一个网络传输的加密过的字符串，实际上可以通过反混淆得到一个真正的 .class 文件，然后再加载。
 *   根据用户需求来动态加载类
 *   根据用户需求，让其他数据以字节码的形式加载到应用中
 *   我自己的理解：
 *      可以自己实现自己的 eval api 了！
 *   所有的 ExtentionClassLoader 和 SystemClassLoader 都是 URLClassLoader 的子类。
 */
class CompileClassLoader extends ClassLoader {

    private byte[] getBytes(String fileName) throws IOException {
        // Prefer path to file in Java later in 7.
        File file = new File(fileName);
        long len = file.length();
        byte[] raw = new byte[(int) len];
        try (FileInputStream fin = new FileInputStream(file)) {
            int r = fin.read(raw);
            if (r != len) {
                throw new IOException("无法读取文件： " + r + " != " + len);
            }
            return raw;
        }
    }

    private boolean compile(String javaFile) throws IOException {
        System.out.println("CompileClassLoader：正在编译 " + javaFile + "...");
        Process p = Runtime.getRuntime().exec("javac " + javaFile);
        try {
            // 这个方法会让本线程停止在此处直到这个子进程完成。
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int ret = p.exitValue();
        return ret == 0;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class clazz = null;
        String fileStub = name.replace(".", "/");
        String javaFileName = fileStub + ".java";
        String classFileName = fileStub + ".class";
        File javaFile = new File(javaFileName);
        File classFile = new File(classFileName);

        // 1 Java 文件存在 2 类文件不存在或者 Java 文件的修改日期比类文件要晚
        if (javaFile.exists() && (!classFile.exists() || javaFile.lastModified() > classFile.lastModified())) {
            try {
                // 短路阻塞了？
                if (!compile(javaFileName) || !classFile.exists()) {
                    throw new ClassNotFoundException(" find class failed: " + name);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (classFile.exists()) {
            try {
                byte[] raw = getBytes(classFileName);
                // defineClass 虽然不能直接改写，但我们有了 bytes 依然可以在内存中动态定义类，不过是从 java 文件定义起，而且借助 javac 的帮助了。
                clazz = defineClass(name, raw, 0, raw.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (clazz == null) {
            throw new ClassNotFoundException(name);
        }
        return clazz;
    }
}
