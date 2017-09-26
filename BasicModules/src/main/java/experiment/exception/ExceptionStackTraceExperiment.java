package experiment.exception;

/**
 * @author liangchuan
 */
public class ExceptionStackTraceExperiment {

    private static void foo() throws Exception {
        throw new Exception();
    }

    private static void bar() throws Exception {
        try {
            foo();
        } catch (Exception e) {
            throw e;
        }
    }

    public static void main(String[] args) {
            bar();
    }
}
