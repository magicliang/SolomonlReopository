import java.net.URL;

/**
 * Created by liangchuan on 2017/6/8.
 */
public class BootstrapTest {

    public static void main(String[] args){
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
    }
}
