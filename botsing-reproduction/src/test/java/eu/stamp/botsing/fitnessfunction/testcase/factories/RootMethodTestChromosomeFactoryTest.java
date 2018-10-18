package eu.stamp.botsing.fitnessfunction.testcase.factories;

import ch.qos.logback.classic.Level;
import eu.stamp.botsing.ga.strategy.operators.GuidedSearchUtility;
import org.evosuite.runtime.System;
import org.evosuite.setup.TestCluster;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.utils.generic.GenericClass;
import org.evosuite.utils.generic.GenericConstructor;
import org.evosuite.utils.generic.GenericMethod;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RootMethodTestChromosomeFactoryTest {

    @Test(expected = System.SystemExitException.class)
    public void testChromosomeMethod() throws NoSuchMethodException {
        setLoggingLevel(Level.INFO);
        Object obj = new String();
        Class<?>[] classes = new Class<?>[1];
        classes[0] = String.class;
        GenericClass gc = Mockito.mock(GenericClass.class);
        Mockito.when(gc.hasWildcardOrTypeVariables()).thenReturn(false);
        Method m  = obj.getClass().getMethod("equals", Object.class);
        GenericMethod call =  Mockito.mock(GenericMethod.class);
        Mockito.when(call.getName()).thenReturn("equals");
        Mockito.when(call.getOwnerClass()).thenReturn(gc);
        Mockito.when(call.isMethod()).thenReturn(true);
        Mockito.when(call.getOwnerType()).thenReturn(String.class);
        Mockito.when(call.getMethod()).thenReturn(m);
        Mockito.when(call.getParameterTypes()).thenReturn(m.getParameterTypes());
        Mockito.when(call.getReturnType()).thenReturn(Boolean.TYPE);
        TestCluster.getInstance().addTestCall(call);

        GuidedSearchUtility utility = Mockito.mock(GuidedSearchUtility.class);
        Set<String> publicCalls = new HashSet<>();
        publicCalls.add("byteValue");
        publicCalls.add("doubleValue");
        publicCalls.add("equal");
        Mockito.when(utility.getPublicCalls()).thenReturn(publicCalls);
        RootMethodTestChromosomeFactory rm =  new RootMethodTestChromosomeFactory(utility);
        TestChromosome generatedChromosome = rm.getChromosome();
        assertFalse(generatedChromosome.getTestCase().isEmpty());
        assertTrue(generatedChromosome.getTestCase().isValid());



        ///
        Object obj2 = new Boolean(true);
        Class<?>[] classes2 = new Class<?>[1];
        classes2[0] = String.class;
        GenericClass gc2 = Mockito.mock(GenericClass.class);
        Mockito.when(gc2.hasWildcardOrTypeVariables()).thenReturn(false);
        Constructor c  = obj2.getClass().getConstructors()[0];
        ///

        GenericConstructor call2 =  Mockito.mock(GenericConstructor.class);
        Mockito.when(call2.getName()).thenReturn(c.getName());
        Mockito.when(call2.getOwnerClass()).thenReturn(gc2);
        Mockito.when(call2.isMethod()).thenReturn(false);
        Mockito.when(call2.isConstructor()).thenReturn(true);
        Mockito.when(call2.getOwnerType()).thenReturn(String.class);
        Mockito.when(call2.getConstructor()).thenReturn(c);
        Mockito.when(call2.getRawGeneratedType()).thenReturn(c.getParameterTypes()[0]);
        Mockito.when(call2.getReturnType()).thenReturn(Boolean.TYPE);
        TestCluster.getInstance().addTestCall(call2);

        publicCalls.add(c.getName());

        rm =  new RootMethodTestChromosomeFactory(utility);

        generatedChromosome = rm.getChromosome();

    }

    public static void setLoggingLevel(ch.qos.logback.classic.Level level) {
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(level);
    }
}
