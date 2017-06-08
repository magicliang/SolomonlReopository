import java.net.URL;

/**
 * Created by liangchuan on 2017/6/8.
 */
public class BootstrapTest {

    public static void main(String[] args){
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (int i = 0; i < urls.length; i++) {
            System.out.println(urls[i].toExternalForm());
        }
    }
}
