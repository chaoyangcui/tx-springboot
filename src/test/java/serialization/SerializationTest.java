package serialization;

import com.tx.txspringboot.entitys.ExampleEntity;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;

/**
 * 测试Java序列化
 *
 * <p>Created by Intellij IDEA
 *
 * @author Eric Cui
 * @since 2018/3/20 22:39
 */
@RunWith(JUnit4.class)
public class SerializationTest {

    @Test
    public void serial() throws IOException, ClassNotFoundException {
        ExampleEntity exampleEntity = ExampleEntity.builder().build();
        exampleEntity.setId(1);
        exampleEntity.setDesc("desc");
        exampleEntity.setCommon("common");

        File serialObj = new File("example.obj");
        if (!serialObj.exists()) serialObj.createNewFile();

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(serialObj));
        out.writeObject(exampleEntity);
        IOUtils.closeQuietly(out);

        ObjectInputStream in = new ObjectInputStream(new FileInputStream(serialObj));
        Object object = in.readObject();
        IOUtils.closeQuietly(in);

        System.out.println(((ExampleEntity) object).getCommon());
    }
}
