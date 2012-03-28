package com.numericalactivity.dktxtools.pool;

import static org.junit.Assert.*;

import org.junit.Test;

public class PoolTest {

    protected static final int NUMBER_OF_SLOTS = 2;

    @Test
    public void testPoolRetrieving() {
        TestPoolObject poolObject1 = TestPoolObject.getNew();
        TestPoolObject poolObject2 = TestPoolObject.getNew();
        assertEquals("Wrong number of slots", NUMBER_OF_SLOTS, TestPoolObject._pool._numberOfSlots);
        assertEquals("Wrong number of slots", NUMBER_OF_SLOTS, TestPoolObject._pool._pool.length);
        assertEquals("Wrong index", -1, TestPoolObject._pool._index);
        assertFalse("Same hash codes", poolObject1.toString().contentEquals(poolObject2.toString()));

        poolObject1.recycle();
        assertEquals("Wrong index", 0, TestPoolObject._pool._index);

        TestPoolObject poolObject3 = TestPoolObject.getNew();
        assertEquals("Wrong index", -1, TestPoolObject._pool._index);
        assertTrue("Different hash codes", poolObject1.toString().contentEquals(poolObject3.toString()));
    }

    @Test
    public void testPoolIndex() {
        TestPoolObject poolObject1 = TestPoolObject.getNew();
        TestPoolObject poolObject2 = TestPoolObject.getNew();
        TestPoolObject poolObject3 = TestPoolObject.getNew();
        assertEquals("Wrong index", -1, TestPoolObject._pool._index);

        poolObject1.recycle();
        assertEquals("Wrong index", 0, TestPoolObject._pool._index);

        poolObject2.recycle();
        assertEquals("Wrong index", 1, TestPoolObject._pool._index);

        poolObject3.recycle();
        assertEquals("Wrong index", 1, TestPoolObject._pool._index);

        poolObject1 = TestPoolObject.getNew();
        assertEquals("Wrong index", 0, TestPoolObject._pool._index);

        poolObject1 = TestPoolObject.getNew();
        assertEquals("Wrong index", -1, TestPoolObject._pool._index);

        poolObject1 = TestPoolObject.getNew();
        assertEquals("Wrong index", -1, TestPoolObject._pool._index);
    }

    @Test
    public void testReset() {
        TestPoolObject poolObject1  = TestPoolObject.getNew();
        poolObject1._string         = "azerty";
        poolObject1.recycle();

        poolObject1                 = TestPoolObject.getNew();
        assertEquals("Object not resetted", null, poolObject1._string);
    }

    @Test
    public void testRecycleOnce() {
        TestPoolObject poolObject1  = TestPoolObject.getNew();
        assertEquals("Wrong index", -1, TestPoolObject._pool._index);

        poolObject1.recycle();
        assertEquals("Wrong index", 0, TestPoolObject._pool._index);

        poolObject1.recycle();
        assertEquals("Wrong index", 0, TestPoolObject._pool._index);
    }

    static class TestPoolObject implements PoolInterface {
        static final Pool<TestPoolObject> _pool = new Pool<TestPoolObject>(NUMBER_OF_SLOTS, new TestFactory());
        boolean _recyclable                     = false;
        String _string;

        public static TestPoolObject getNew() {
            return _pool.get();
        }

        @Override
        public void reset() {
            _recyclable = false;
            _string     = null;
        }

        @Override
        public void recycle() {
            if (_recyclable) {
                return;
            }

            _pool.add(this);
            _recyclable = true;
        }

        protected static class TestFactory implements PoolFactoryInterface<TestPoolObject> {

            @Override
            public TestPoolObject factory() {
                return new TestPoolObject();
            }
            
        }
    }
}
