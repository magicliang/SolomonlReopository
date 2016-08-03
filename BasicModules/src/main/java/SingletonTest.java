/**
 * Created by magicliang on 2016/8/2.
 */
//use enum to represent singleton
public class SingletonTest {
    public static void main(String[] args){
        //These 2 instances are the same instances
        System.out.println(getSingletonByStaticHolder());
        System.out.println(getSingletonByStaticHolder());
        //These 2 instances are the same instances
        System.out.println(getSingletonByHungeryMan());
        System.out.println(getSingletonByHungeryMan());
    }

    private SingletonTest() {
    }

    private static volatile SingletonTest instance;

    // Approach 1: use static holder
    private static class SingletonHolder {
        // This is loaded only at first time it is accessed.
        // 一个小技巧，因为类已经是私有的了，所以这个变量就可以是公有的
        public static SingletonTest instance = new SingletonTest();
    }
    public static SingletonTest getSingletonByStaticHolder() {
        return SingletonHolder.instance;
    }
    //Approach 2: use double check
    public static SingletonTest getSingletonByHungeryMan() {
        if(instance == null){
            //Must lock class
            synchronized (SingletonTest.class){
                if(instance == null){
                    System.out.println("Only initialize instance once");
                    instance = new SingletonTest();
                }
            }
        }
        return  instance;
    }
}
