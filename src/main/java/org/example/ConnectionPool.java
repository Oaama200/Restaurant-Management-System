package org.example;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static ConcurrentLinkedQueue<Connection> connectionPool;
    private static final int POOL_SIZE = 5;
    private static final int THREAD_POOL_SIZE = 7;

    private ConnectionPool() {}

    public static synchronized ConcurrentLinkedQueue<Connection> getConnectionPool() {
        if (connectionPool == null) {
            connectionPool = new ConcurrentLinkedQueue<>();
            for (int i = 0; i < POOL_SIZE; i++) {
                connectionPool.offer(new Connection(i));
            }
        }
        return connectionPool;
    }

    public static void initializeConnectionPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            executorService.submit(() -> {
                Connection connection = getConnectionPool().poll();
                if (connection != null) {
                    try {
                        connection.open();
                        Thread.sleep(1000);
                        logger.info(Thread.currentThread().getName() + " acquired connection " + connection.getId());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        connection.close();
                        getConnectionPool().offer(connection);
                    }
                } else {
                    logger.info(Thread.currentThread().getName() + " is waiting for a connection");
                }
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void initializeConnectionPoolWithFutures() {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        CompletableFuture<?>[] futures = new CompletableFuture[THREAD_POOL_SIZE];

        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            futures[i] = CompletableFuture.runAsync(() -> {
                Connection connection = getConnectionPool().poll();
                if (connection != null) {
                    try {
                        connection.open();
                        Thread.sleep(1000);
                        logger.info(Thread.currentThread().getName() + " acquired connection " + connection.getId());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        connection.close();
                        getConnectionPool().offer(connection);
                    }
                } else {
                    logger.info(Thread.currentThread().getName() + " is waiting for a connection");
                }
            }, executorService);
        }

        CompletableFuture.allOf(futures).join();
        executorService.shutdown();
    }

    public static Connection acquireConnection() {
        return getConnectionPool().poll();
    }

    public static void releaseConnection(Connection connection) {
        if (connection != null) {
            getConnectionPool().offer(connection);
        }
    }
}