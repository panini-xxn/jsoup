package org.jsoup.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidateTest {
    @Test
    public void testNotNull() {
        Validate.notNull("foo");
        boolean threw = false;
        try {
            Validate.notNull(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        Assertions.assertTrue(threw);
    }

    @Test void stacktraceFiltersOutValidateClass() {
        boolean threw = false;
        try {
            Validate.notNull(null);
        } catch (ValidationException e) {
            threw = true;
            assertEquals("Object must not be null", e.getMessage());
            StackTraceElement[] stackTrace = e.getStackTrace();
            for (StackTraceElement trace : stackTrace) {
                assertNotEquals(trace.getClassName(), Validate.class.getName());
            }
            assertTrue(stackTrace.length >= 1);
        }
        Assertions.assertTrue(threw);
    }

    @Test void nonnullParam() {
        boolean threw = true;
        try {
            Validate.notNullParam(null, "foo");
        } catch (ValidationException e) {
            assertEquals("The parameter 'foo' must not be null.", e.getMessage());
        }
        assertTrue(threw);
    }

    @Test
    public void testMap() throws NoSuchFieldException {
        HttpConnection connection = new HttpConnection();
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("key1", "value1");
        dataMap.put("key2", "value2");

        connection.data(dataMap);

        HttpConnection connectionTwo = new HttpConnection();

        assertThrows(ValidationException.class, () -> {
            connectionTwo.data((Map<String, String>) null);
        });
    }
}
