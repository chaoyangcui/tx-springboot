package com.tx.txspringboot.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * <a href="http://zookeeper.apache.org/doc/r3.4.9/recipes.html#sc_recipes_Locks">Distributed Lock</a>
 *
 * <pre>
 *     1. Call create( ) with a pathname of "_locknode_/lock-" and the sequence and ephemeral flags set.
 *     2. Call getChildren( ) on the lock node without setting the watch flag (this is important to avoid the herd effect).
 *     3. If the pathname created in step 1 has the lowest sequence number suffix, the client has the lock and the client exits the protocol.
 *     4. The client calls exists( ) with the watch flag set on the path in the lock directory with the next lowest sequence number.
 *     5. if exists( ) returns false, go to step 2. Otherwise, wait for a notification for the pathname from the previous step before going to step 2.
 * </pre>
 *
 * Created by IntelliJ IDEA.
 *
 * @author Eric
 * @since 2018/4/3 13:20
 */
public class ZKLock {
    private ZooKeeper zk;

    {
        try {
            zk =
                    new ZooKeeper(
                            "www.cndotaer.com:2181",
                            300,
                            event -> {
                                Watcher.Event.EventType eventType = event.getType();
                                System.out.println("EventType   : " + eventType.name());
                                Watcher.Event.KeeperState keeperState = event.getState();
                                System.out.println("KeeperState : " + keeperState.name());
                            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final String ZK_LOCK_PREFIX = "/lock-";
    private static final String ZK_LOCK_ROOT = "/_locknode_";

    public static void main(String[] args) {
        ZKLock zkLock = new ZKLock();
        // zkLock.step1();
        try {
            zkLock.watchAndWait(ZK_LOCK_ROOT);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void step1() {
        try {
            ZooKeeper zooKeeper =
                    new ZooKeeper(
                            "www.cndotaer.com:2181",
                            300,
                            event -> {
                                Watcher.Event.EventType eventType = event.getType();
                                System.out.println("EventType   : " + eventType.name());
                                Watcher.Event.KeeperState keeperState = event.getState();
                                System.out.println("KeeperState : " + keeperState.name());
                            });

            Runnable otherClient =
                    () -> {
                        String result = null;
                        try {
                            result =
                                    zooKeeper.create(
                                            ZK_LOCK_ROOT + ZK_LOCK_PREFIX,
                                            new byte[0],
                                            ZooDefs.Ids.OPEN_ACL_UNSAFE,
                                            CreateMode.EPHEMERAL_SEQUENTIAL);
                        } catch (KeeperException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("otherClient's Result: " + result);
                    };
            Thread otherThread = new Thread(otherClient);
            otherThread.join();
            otherThread.start();

            // step 1
            String currClient =
                    zooKeeper.create(
                            ZK_LOCK_ROOT + ZK_LOCK_PREFIX,
                            new byte[0],
                            ZooDefs.Ids.OPEN_ACL_UNSAFE,
                            CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println("currClient's Result: " + currClient);

            List<String> children = zooKeeper.getChildren(ZK_LOCK_ROOT, false);
            System.out.println(children);

        } catch (IOException | InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }

    private void watchAndWait(String target) throws KeeperException, InterruptedException {
        Semaphore s = new Semaphore(0);
        try {
            Stat stat =
                    zk.exists(
                            target,
                            event -> {
                                if (event.getType().equals(Watcher.Event.EventType.NodeDeleted)) {
                                    s.release();
                                }
                            });

            if (null != stat) {
                s.acquire();
            }
        } catch (KeeperException e) {
            if (e.code().equals(KeeperException.Code.CONNECTIONLOSS)) {
                this.watchAndWait(target);
            } else {
                throw e;
            }
        }
    }
}
