/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.sets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Phil Hutchinson
 */
public class DefaultVennPartitionerBaselineTest {
    
    DefaultVennPartitioner<Character> instance;
    
    public DefaultVennPartitionerBaselineTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new DefaultVennPartitioner<Character>();
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    @Test
    public void addRegularSubset_null_throws() {
        expectedException.expect(NullPointerException.class);
        instance.addSubset(null);
    }
    
    @Test
    public void addComplementedSubset_null_throws() {
        expectedException.expect(NullPointerException.class);
        instance.addSubsetByComplement(null);
    }
    
    @Test
    public void getRegularPartitionCount_valid() {
        Set<Character> subset = buildSet('a', 'b', 'c');
        instance.addSubset(subset);
        int expectedValue = 1;
        int actualValue = instance.getRegularPartitionCount();
        assertEquals(expectedValue, actualValue);
    }
    
    @Test
    public void getComplementedPartitionExists_true() {
        Set<Character> subset = buildSet('a', 'b', 'c');
        instance.addSubsetByComplement(subset);
        boolean expectedValue = true;
        boolean actualValue = instance.getComplementedPartitionExists();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void getComplementedPartitionExists_false() {
        Set<Character> subset = buildSet('a', 'b', 'c');
        instance.addSubset(subset);
        boolean expectedValue = false;
        boolean actualValue = instance.getComplementedPartitionExists();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void getPartition_negativeIndex_throws() {
        expectedException.expect(IndexOutOfBoundsException.class);
        instance.getPartition(-10);
    }
    
    @Test
    public void getPartition_invalidIndex_throws() {
        expectedException.expect(IndexOutOfBoundsException.class);
        instance.getPartition(3);
    }
    
    @Test
    public void getPartitionsForRegularSubset_null_throws() {
        expectedException.expect(IndexOutOfBoundsException.class);
        instance.getPartitionsForSubset(null);
    }
    
    @Test
    public void getPartitionsForComplementedSubset_null_throws() {
        expectedException.expect(IndexOutOfBoundsException.class);
        instance.getPartitionsForSubsetByComplement(null);
    }
            
    private Set<Character> buildSet(Character... chars)
    {
        return new HashSet<>(Arrays.asList(chars));
    }
}
