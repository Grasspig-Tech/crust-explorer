package crust.explorer;


import crust.explorer.enums.ModuleEnum;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
public class CollectionsTests {

    private static volatile Set<String> setId = new HashSet<>();
    private static volatile Set<ExecutorService> executorSet = new HashSet<>();

    private static final int poolSize = 100;
    private static final int addNum = 10000;
    private static final int removeNum = 7000;

    private static List<String> modules = new ArrayList();

    static {
        Collections.initModule();
        ModuleEnum[] values = ModuleEnum.values();
        for (ModuleEnum e : values) {
            modules.add(e.getModule());
        }
        log.info("init, modules:{}", modules);
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        CountDownLatch count = new CountDownLatch(modules.size() * (addNum + removeNum));
        ExecutorService moduleExecutor = Executors.newFixedThreadPool(modules.size());
        addExecutorSet(moduleExecutor);
        for (int i = 0; i < modules.size(); i++) {
            final String module = modules.get(i);
            moduleExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    add(module, countDownLatch, count);
                    remove(module, countDownLatch, count);
                }
            });
        }
        countDownLatch.countDown();
        count.await();
        executorSet.forEach(e -> e.shutdown());
        showResult();
    }

    private static void showResult() {
        for (int i = 0; i < modules.size(); i++) {
            String module = modules.get(i);
            ConcurrentHashMap<String, Session> moduleSessions = Collections.getModuleSessions(module);
            log.info("showResult, module:{}", module);
            log.info("showResult, module.size:{}", moduleSessions.size());
            log.info("showResult, module.moduleSessions:{}", moduleSessions);
        }
    }

    @SneakyThrows
    private static void add(String module, CountDownLatch countDownLatch, CountDownLatch count) {
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        addExecutorSet(executor);
        countDownLatch.await();
        for (int i = 0; i < addNum; i++) {
            final int j = i + 1;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    Session session = Session.builder().id(String.valueOf(j)).module(module).build();
                    Collections.addSession(module, session);
                    count.countDown();
                }
            });
        }
    }

    @SneakyThrows
    private static void remove(String module, CountDownLatch countDownLatch, CountDownLatch count) {
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        addExecutorSet(executor);
        countDownLatch.await();
        for (int i = 0; i < removeNum; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    Session session = getSession(module);
                    Collections.removeSession(session);
                    count.countDown();
                }
            });
        }
    }

    private static synchronized Session getSession(String module) {
        for (; ; ) {
            Session session = getSession(module, setId);
            if (Objects.nonNull(session)) {
                return session;
            }
        }
    }

    public static Session getSession(String module, Set<String> set) {
        ConcurrentHashMap<String, Session> moduleSessions = Collections.getModuleSessions(module);
        Iterator<Map.Entry<String, Session>> iterator = moduleSessions.entrySet().iterator();
        while (iterator.hasNext()) {
            Session session = iterator.next().getValue();
            String id = module.concat(session.getId());
            if (!set.contains(id)) {
                set.add(id);
                return session;
            }
        }
        return null;
    }

    private static synchronized void addExecutorSet(ExecutorService executor) {
        executorSet.add(executor);
    }
}
