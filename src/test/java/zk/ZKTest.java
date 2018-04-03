package zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Eric
 * @since 2018/4/3 10:47
 */
// @RunWith(SpringJUnit4ClassRunner.class)
// @SpringBootTest(classes = {ZKService.class})
@RunWith(JUnit4.class)
public class ZKTest {

    private static ZooKeeper zooKeeper;

    @Before
    public void before() {
        try {
            zooKeeper =
                    new ZooKeeper(
                            "www.cndotaer.com:2181",
                            300,
                            event -> {
                                System.out.println(event.getType().name());
                                System.out.println(event.getPath());
                            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void zk() {
        System.out.println("zk");
        String uuid = UUID.randomUUID().toString();
        try {
            String result =
                    zooKeeper.create(
                            "/zk-lock-",
                            uuid.getBytes(),
                            ZooDefs.Ids.OPEN_ACL_UNSAFE,
                            CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(result);
            System.out.println(zooKeeper.getState().isAlive());
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void zkDel() {
        try {
            zooKeeper.delete("/zk-lock0000000004", 0);
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void zkNode() {
        try {
            byte[] bytes = zooKeeper.getData("/zk-lock0000000004", false, new Stat());
            System.out.println(new String(bytes));
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void zkChildred() {
        try {
            List<String> children = zooKeeper.getChildren("/", false);
            Collections.sort(
                    children,
                    Comparator.reverseOrder());
            System.out.println(children);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
