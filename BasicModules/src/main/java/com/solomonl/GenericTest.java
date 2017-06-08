package com.solomonl;



import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * 获取泛型的具体类型，似乎只有反射成员才能做到？普通的局部变量看来是不行了？
 * Created by liangchuan on 2017/6/8.
 */
public class GenericTest {

    private Map<String, Integer> score;

    public static void main(String[] args) throws Exception {
        Class<GenericTest> clazz = GenericTest.class;
        Field field = clazz.getDeclaredField("score");
        // 此处 filed getType 都是返回 class。
        Class<?> type = field.getType();
        System.out.println("score 的实际类型是： " + type);

        System.out.println("score 的泛型类型是： " + field.toGenericString());

        Type gType = field.getGenericType();

        if (gType instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) gType;
            Type rType = pType.getRawType();
            System.out.println("原始类型是：" + rType);
            Type[] tArgs = pType.getActualTypeArguments();
            System.out.println("具体的类型实参为：");
            for (int i = 0; i < tArgs.length; i++) {
                System.out.println(String.format("第 %d 个泛型实参类型是： %s", i, tArgs[i]));
            }
        }

    }

}
