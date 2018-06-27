package experiment.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个例子证明了一件事，jackson 无法用 TypeReference 转化哪怕一级泛型成员
 */
public class TypeReferenceExample {
    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> Entity<T> deserialize(String message) throws IOException {
        Object o = OBJECT_MAPPER.readValue(message, new TypeReference<T>() {
        });
        /**
         * o is: {data=[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]1}
         */
        System.out.println("o is: " + o);
        /**
         * o's type is: class java.util.LinkedHashMap
         */
        System.out.println("o's type is: " + o.getClass());

        /**
         * o's generic type is: java.util.HashMap<K, V>
         */
        System.out.println("o's generic type is: " + o.getClass().getGenericSuperclass());

        /**
         * 到这一层已经说明都运行时读不出来
         * o's generic type's generic type is: class java.lang.Object
         */
        System.out.println("o's generic type's generic type is: " + o.getClass().getGenericSuperclass().getClass().getGenericSuperclass());


        /**
         * 在这一步就会报错，换言之，TypeReference 确实只能推导到 Collection/Map一类类型里面：
         *  Exception in thread "main" java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to experiment.jackson.Entity
         at experiment.jackson.TypeReferenceExample.deserialize(TypeReferenceExample.java:17)
         at experiment.jackson.TypeReferenceExample.main(TypeReferenceExample.java:39)
         */
        Entity<T> entity = OBJECT_MAPPER.readValue(message, new TypeReference<T>() {
        });
        return entity;
    }

    public static <T> String serialize(T data) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(data);
    }

    public static void main(String[] args) throws IOException {
        TypeReferenceExample typeReferenceExample = new TypeReferenceExample();
        Entity<List<List<String>>> entity = new Entity<>();
        List<List<String>> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        list1.add(list2);

        for (int i = 0; i < 10; i++) {
            list2.add("" + i);
        }

        entity.setData(list1);

        String msg = serialize(entity);

        System.out.println("message is: " + msg);

        // 使用成员方法来使用类型见证帮助推导也于事无补。
        Entity<List<String>> entity2 = TypeReferenceExample.deserialize(msg);

        System.out.println(entity2);
    }
}

