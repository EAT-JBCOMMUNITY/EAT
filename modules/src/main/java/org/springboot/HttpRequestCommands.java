package org.springboot;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Wildfly/spring/src/main/java#23.0.0","modules/testcases/jdkAll/WildflyJakarta/spring/src/main/java#31.0.0","modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/spring/src/main/java","modules/testcases/jdkAll/ServerBeta/spring/src/main/java#21.0.0","modules/testcases/jdkAll/Eap7Plus/spring/src/main/java#7.3.5"})
public class HttpRequestCommands {
    private static String execute(final Callable<String> task, final long timeout, final TimeUnit unit) throws TimeoutException, ExecutionException {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Future<String> result = executor.submit(task);
        try {
            return result.get(timeout, unit);
        } catch (TimeoutException e) {
            result.cancel(true);
            throw e;
        } catch (InterruptedException e) {
            // should not happen
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw e;
        } finally {
            executor.shutdownNow();
            try {
                executor.awaitTermination(timeout, unit);
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    public static String get(final String spec, final long timeout, final TimeUnit unit) throws MalformedURLException, ExecutionException, TimeoutException {
        final URL url = new URL(spec);
        Callable<String> task = new Callable<String>() {
            @Override
            public String call() throws Exception {
                final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                return processResponse(conn);
            }
        };
        return execute(task, timeout, unit);
    }

    private static String read(final InputStream in) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        int b;
        while((b = in.read()) != -1) {
            out.write(b);
        }
        return out.toString();
    }

    private static String processResponse(HttpURLConnection conn) throws IOException {
        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            final InputStream err = conn.getErrorStream();
            try {
                throw new IOException(read(err));
            }
            finally {
                err.close();
            }
        }
        final InputStream in = conn.getInputStream();
        try {
            return read(in);
        }
        finally {
            in.close();
        }
    }

    public static String put(final String spec, final String message, final long timeout, final TimeUnit unit) throws MalformedURLException, ExecutionException, TimeoutException {
        final URL url = new URL(spec);
        Callable<String> task = new Callable<String>() {
            @Override
            public String call() throws Exception {
                final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                final OutputStream out = conn.getOutputStream();
                try {
                    write(out, message);
                    return processResponse(conn);
                }
                finally {
                    out.close();
                }
            }
        };
        return execute(task, timeout, unit);
    }

    private static void write(OutputStream out, String message) throws IOException {
        final OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
        writer.write(message);
        writer.flush();
    }
}

