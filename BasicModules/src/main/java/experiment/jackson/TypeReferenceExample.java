package experiment.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个例子证明了一件事，jackson 无法用 TypeReference 转化哪怕一级泛型成员
 */
public class TypeReferenceExample {
    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> Entity<T> deserialize(String message) throws IOException {

        /**
         * 少了这个壳类型就不行。这个壳如果不存在，则 jackson会选用 LinkHashMap作壳
         */
        Entity<T> entity = OBJECT_MAPPER.readValue(message, new TypeReference<Entity<T>>() {
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

        System.out.println("entity2 is:" + entity2);

    }
}

@Data
class A {
    private String a;

}

