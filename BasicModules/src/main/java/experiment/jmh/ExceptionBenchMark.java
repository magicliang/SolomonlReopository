package experiment.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author liangchuan
 */

@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class ExceptionBenchMark {

    @Param({"500"})
    private int recursiveDepth;

    private Object returnMethod(int recursiveDepth) {
        if ( recursiveDepth > 0 ) {
            return returnMethod(recursiveDepth - 1);
        } else {
            return new Object();
        }
    }


    private Object throwMethod(int recursiveDepth) throws Exception {
        if ( recursiveDepth > 0 ) {
            return throwMethod(recursiveDepth - 1);
        } else {
            throw new RuntimeException();
        }
    }

    // 一定要是公开方法
    @Benchmark
    public void benchMarkReturn() {
        try {
            throwMethod(recursiveDepth);
        } catch (Exception e) {

        }
    }

    @Benchmark
    public void benchMarkThrow() {
        try {
            throwMethod(recursiveDepth);
        } catch (Exception e) {

        }
    }

    @Setup
    public void prepare() {

    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ExceptionBenchMark.class.getSimpleName())
                .forks(2)
                .warmupIterations(1)
                .measurementIterations(1)
                .build();

        new Runner(opt).run();
    }
}
