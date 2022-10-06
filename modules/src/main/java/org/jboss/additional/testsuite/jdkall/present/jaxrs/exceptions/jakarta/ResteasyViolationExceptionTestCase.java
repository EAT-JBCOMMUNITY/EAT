package org.jboss.additional.testsuite.jdkall.present.jaxrs.exceptions;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ElementKind;
import jakarta.validation.Path;
import jakarta.validation.metadata.ConstraintDescriptor;
import jakarta.ws.rs.core.MediaType;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.resteasy.api.validation.ConstraintType;
import org.jboss.resteasy.spi.validation.ConstraintTypeUtil;
import org.jboss.resteasy.api.validation.SimpleViolationsContainer;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@RunWith(Arquillian.class)
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#27.0.0.Beta1"})
public class ResteasyViolationExceptionTestCase {

    private final static String WARNAME = "ResteasyViolationExceptionTestCase.war";
    
    @Deployment(name = "war")
    public static Archive<?> createWar() {
        WebArchive war = ShrinkWrap.create(WebArchive.class,WARNAME);
        war.addClasses(ResteasyViolationExceptionTestCase.class,ResteasyViolationExceptionImpl.class,ConstraintTypeUtil11.class,ConstraintType.class,ConstraintTypeUtil.class);
        return war;
    }

    @Test
    @OperateOnDeployment("war")
    public void testViolationException() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        AtomicBoolean run = new AtomicBoolean(true);
        AtomicReference<ResteasyViolationExceptionImpl> exceptionToUse = new AtomicReference<>();

        List<Future<Boolean>> runnerResults = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            runnerResults.add(executor.submit(new ExceptionToStringCaller(run, exceptionToUse)));
        }

        for (int i = 0; i < 1_000; i++) {
            Set<ConstraintViolation<Object>> violations = new HashSet<>();
            violations.add(new DummyConstraintViolation());
            violations.add(new DummyConstraintViolation());
            violations.add(new DummyConstraintViolation());
            final SimpleViolationsContainer container = new SimpleViolationsContainer(violations);
            final List<MediaType> accept = new ArrayList<>();
            ResteasyViolationExceptionImpl violationException = new ResteasyViolationExceptionImpl(container, accept);
            exceptionToUse.set(violationException);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                //ignore
            }
            if (!run.get()) {
                break;
            }
        }

        run.set(false);
        executor.shutdownNow();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        boolean anyExceptions = false;
        for (Future<Boolean> result : runnerResults) {
            anyExceptions |= result.get();
        }
        assertFalse("Didn't expect any exceptions", anyExceptions);
    }

    static class ExceptionToStringCaller implements Callable<Boolean> {

        final AtomicReference<ResteasyViolationExceptionImpl> exceptionToUse;
        final AtomicBoolean run;

        ExceptionToStringCaller(AtomicBoolean run,
                AtomicReference<ResteasyViolationExceptionImpl> exceptionToUse) {
            this.run = run;
            this.exceptionToUse = exceptionToUse;
        }

        @Override
        public Boolean call() throws Exception {
            while (run.get()) {
                try {
                    if (exceptionToUse.get() != null) {
                        //EXCEPTION OCCURS HERE
                        String s = exceptionToUse.get().toString();
                        if (s == "") {
                            throw new RuntimeException("No to String value");
                        }
                    }
                } catch (ConcurrentModificationException e) {
                    e.printStackTrace();
                    run.set(false);
                    return true;
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    //ignore
                }
            }
            return false;
        }
    }

    static class DummyConstraintViolation implements ConstraintViolation<Object> {

        @Override
        public String getMessage() {
            return "message";
        }

        @Override
        public String getMessageTemplate() {
            return "";
        }

        @Override
        public String getRootBean() {
            return "Root";
        }

        @Override
        public Class getRootBeanClass() {
            return String.class;
        }

        @Override
        public Object getLeafBean() {
            return "leaf";
        }

        @Override
        public Object[] getExecutableParameters() {
            return new Object[0];
        }

        @Override
        public Object getExecutableReturnValue() {
            return null;
        }

        @Override
        public Path getPropertyPath() {
            return new Path() {
                @Override
                public Iterator<Node> iterator() {
                    Node node = new Node() {
                        @Override
                        public String getName() {
                            return "name";
                        }

                        @Override
                        public boolean isInIterable() {
                            return false;
                        }

                        @Override
                        public Integer getIndex() {
                            return 0;
                        }

                        @Override
                        public Object getKey() {
                            return "key";
                        }

                        @Override
                        public ElementKind getKind() {
                            return ElementKind.BEAN;
                        }

                        @Override
                        public <T extends Node> T as(Class<T> nodeType) {
                            return (T) this;
                        }
                    };
                    return Collections.singleton(node).iterator();
                }
            };
        }

        @Override
        public Object getInvalidValue() {
            return "invalid";
        }

        @Override
        public ConstraintDescriptor<?> getConstraintDescriptor() {
            return null;
        }

        @Override
        public Object unwrap(Class type) {
            return this;
        }
    }
}
