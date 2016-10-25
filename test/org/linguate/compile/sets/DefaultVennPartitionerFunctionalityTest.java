/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.sets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Assume;
import static org.junit.Assume.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author Phil Hutchinson
 */
@RunWith(Parameterized.class)
public class DefaultVennPartitionerFunctionalityTest {
    DefaultVennPartitioner<Character> instance;
    TestCase testCase;
    String testName;
    
    public DefaultVennPartitionerFunctionalityTest(TestCase testCase, String testName) {
        this.testCase = testCase;
        this.testName = testName;
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
        List<Set<Character>> sourceSets = testCase.getSourceSets();
        List<Boolean> sourceComplementedFlags = testCase.getSourceSetsAreComplementedFlag();
        
        for (int sourceIndex = 0; sourceIndex < sourceSets.size(); sourceIndex++) {
            Set<Character> currentSubset = sourceSets.get(sourceIndex);
            if (sourceComplementedFlags.get(sourceIndex)) {
                instance.addSubsetByComplement(currentSubset);
            } else {
                instance.addSubset(currentSubset);
            }
        }
    }

    /**
     * Indirect test for minimum number of partitions used: test that every
     * partition is necessary, by ensuring there are no pair of partitions
     * with the same set of source subsets. This test looks only at results,
     * not at the input, so will only be valid where other tests have 
     * demonstrated that the class is generally working. The complemented
     * partition is ignored for this test.
     */
    @Test
    public void allPartitionsRequired() {
        List<Set<Character>> sourceSets = testCase.getSourceSets();
        List<Boolean> sourceSetComplementedFlags = testCase.getSourceSetsAreComplementedFlag();
        int regularPartitionCount = instance.getRegularPartitionCount();
        // partitionsToSourceSubsets: an array of sets of integers. Each array
        // position corresponds to a partition, and the set of integers to the
        // indices of the subsets that contain this partition.
        Set<Integer>[] partitionsToSourceSubsets = new Set[regularPartitionCount];
        
        for(int i = 0; i < regularPartitionCount; i++) {
            partitionsToSourceSubsets[i] = new HashSet<>();
        }
        
        // get set of partitions for input subsets
        for (int sourceIndex = 0; sourceIndex < sourceSets.size(); sourceIndex++) {
            Set<Character> currentSubset = sourceSets.get(sourceIndex);
            Set<Integer> currentPartitions = sourceSetComplementedFlags.get(sourceIndex)
                    ? instance.getPartitionsForSubsetByComplement(currentSubset)
                    : instance.getPartitionsForSubset(currentSubset);
            for(int currentPartition : currentPartitions) {
                if (currentPartition != VennPartitioner.COMPLEMENTED_PARTITION_INDEX) {
                    partitionsToSourceSubsets[currentPartition].add(sourceIndex);
                }
            }
        }
        
        
        for (int partitionLeft = 0; partitionLeft < regularPartitionCount - 1; partitionLeft++) {
            for (int partitionRight = partitionLeft + 1; partitionRight < regularPartitionCount; partitionRight++) {
                String msg = String.format("Partitions %d and %d contain the same subsets, can be combined", partitionLeft, partitionRight);
                assertNotEquals(msg, partitionsToSourceSubsets[partitionLeft], partitionsToSourceSubsets[partitionRight]);
            }
        }
    }
    
    @Test
    public void allSubsetsEquivalentToUnionOfTheirPartitions() {
        List<Set<Character>> sourceSets = testCase.getSourceSets();
        List<Boolean> sourceSetComplementedFlags = testCase.getSourceSetsAreComplementedFlag();
        
        assumeTrue(sourceSets.size() > 0);
        
        for (int sourceSetIndex = 0; sourceSetIndex < sourceSets.size(); sourceSetIndex++) {
            Set<Character> currentSubset = sourceSets.get(sourceSetIndex);
            if (!sourceSetComplementedFlags.get(sourceSetIndex)) {
                regularSubsetEquivalentToUnionOfItsPartition(currentSubset);
            } else {
                complementedSubsetEquivalentToUnionOfItsPartition(currentSubset);
            }
        }
    }

    /** 
     * Helper for {@link #allSubsetsEquivalentToUnionOfTheirPartitions() 
     * allSubsetsEquivalentToUnionOfTheirPartitions} */
    private void regularSubsetEquivalentToUnionOfItsPartition(Set<Character> subset) {
        Set<Integer> partitionIndices = instance.getPartitionsForSubset(subset);
        
        // step 1: ensure complemented set is not included in returned partition indices

        assert(!partitionIndices.contains(VennPartitioner.COMPLEMENTED_PARTITION_INDEX));
        
        // step 2: ensure union of partitions equals original subset.
        
        Set<Character> expectedResult = subset;
        Set<Character> actualResult = new HashSet<>();
        
        partitionIndices.forEach(pi -> {
            Set<Character> partition = instance.getPartition(pi);
            actualResult.addAll(partition);
        });
        
       assertEquals(expectedResult, actualResult);
    }
    
    /** 
     * Helper for {@link #allSubsetsEquivalentToUnionOfTheirPartitions() 
     * allSubsetsEquivalentToUnionOfTheirPartitions} */
    private void complementedSubsetEquivalentToUnionOfItsPartition(Set<Character> complementOfSubset) {
        Set<Integer> partitionIndices = instance.getPartitionsForSubsetByComplement(complementOfSubset);
        
        // step 1: ensure complemented set is included in returned partition indices
        assert(partitionIndices.contains(VennPartitioner.COMPLEMENTED_PARTITION_INDEX));
        
        // step 2: find all elements that are in complementOfSubset as well as the returned partitions
        int expectedIntersectionSize = 0;
        partitionIndices.remove(VennPartitioner.COMPLEMENTED_PARTITION_INDEX);
        Set<Character> unionOfRegularPartitions = new HashSet<>();
        
        partitionIndices.forEach(pi -> {
            Set<Character> partition = instance.getPartition(pi);
            unionOfRegularPartitions.addAll(partition);
        });
        
        Set<Character> intersection = unionOfRegularPartitions; // re-use unionOfRegularPartitions - no longer needed.
        intersection.retainAll(complementOfSubset);
        int actualIntersectionSize = intersection.size();
        assertEquals(expectedIntersectionSize, actualIntersectionSize);
    }
    
    /**
     * Partitioned set property: No partition is the empty set.
     */
    @Test
    public void noPartitionContainsEmptySet() {
       int numberOfPartitions = instance.getRegularPartitionCount();
       for (int partitionIndex = 0; partitionIndex < numberOfPartitions; partitionIndex++) {
           Set<Character> partition = instance.getPartition(partitionIndex);
           Assert.assertNotEquals(0, partition.size());
       }
    }
        
    /**
     * Partitioned set property: union of partitions must be equal
     * to the universal set. This property cannot be fully tested given
     * the use of complemented sets, but it can effectively tested by
     * ensuring all elements supplied in source set are in a partition,
     * and that the class correctly reports whether there is a complemented
     * partition, tested {@link #complementedPartitionExistenceAccurate 
     * reportsComplementedPartitionExistenceAccurately).
     */
    @Test
    public void allSuppliedElementsContainedInARegularPartition() {
        // there is no need to treat regular sets and defined-by-complement
        // sets separately here: any elements introduced by either means
        // should be contained in one of the regular partitions.
        Set<Character> expectedResult = new HashSet<>();
        List<Set<Character>> sourceSets = testCase.getSourceSets();
        sourceSets.forEach(s -> {
           expectedResult.addAll(s);
        });
        
        Set<Character> actualResult = new HashSet<>();
       int numberOfPartitions = instance.getRegularPartitionCount();
       for (int partitionIndex = 0; partitionIndex < numberOfPartitions; partitionIndex++) {
           Set<Character> partition = instance.getPartition(partitionIndex);
           actualResult.addAll(partition);
       }
       
       assertEquals(expectedResult, actualResult);
    }
    
    @Test
    public void complementedPartitionExistenceAccurate() {
        // a complemented partition is required iff there has been at least one
        // subset added by complement.
        boolean expectedResult = hasComplimentedSourceSet();
        boolean actualResult = instance.getComplementedPartitionExists();
        assertEquals(expectedResult, actualResult);
    }
            
    /**
     * Partitioned set property: intersection of any two distinct sets is empty.
     * Tested indirectly by ensuring no element appears in two different partitions.
     */
    @Test
    public void onlyOnePartitionPerElement() {
        int expectedDuplicateCount = 0;
        Set<Character> duplicateElements = new HashSet<>();
        Set<Character> usedElements = new HashSet<>();
        
        int numberOfPartitions = instance.getRegularPartitionCount();
        for (int partitionIndex = 0; partitionIndex < numberOfPartitions; partitionIndex++) {
           Set<Character> partition = instance.getPartition(partitionIndex);
           Set<Character> intersection = new HashSet<>(partition);
           intersection.retainAll(usedElements);
           duplicateElements.addAll(intersection);
           usedElements.addAll(partition);
       }
       String msg = "Duplicate Elements" + duplicateElements.toString();
       int actualDuplicateCount = duplicateElements.size();
       assertEquals(msg, expectedDuplicateCount, actualDuplicateCount);
    }
    
    private boolean hasComplimentedSourceSet() {
        return testCase.getSourceSetsAreComplementedFlag()
                .stream()
                .anyMatch(b -> b);
    }
    @Parameters( name = "{1}" )
    public static List<Object[]> getParameters() {
        List <TestCase> testCases = TestCase.getAllTests();

        List<Object[]> data = new ArrayList<>();
        testCases.forEach(tc -> {
            Object[] dataItem = new Object[] {
                tc,
                tc.name
            };
            data.add(dataItem);
        });
        
        return data;
    }
    
    private static class TestCase {

        String name;
        List<Set<Character>> sourceSets;
        List<Boolean> sourceSetsComplemented;

        // Lists are re-used (referenced): should not be edited after passing to this list.
        public TestCase(String name, List<Set<Character>> sourceSets, List<Boolean> complementedFlags) {
            if (sourceSets == null || complementedFlags == null || sourceSets.size() != complementedFlags.size()) {
                throw new IllegalArgumentException("Invalid data for test case.");
            }
            
            this.name = name;
            this.sourceSets = Collections.unmodifiableList(sourceSets);
            this.sourceSetsComplemented = Collections.unmodifiableList(complementedFlags);
        }
        
        public List<Set<Character>> getSourceSets() {
            return sourceSets;
        }

        public List<Boolean> getSourceSetsAreComplementedFlag() {
            return sourceSetsComplemented;
        }

        public static List<TestCase> getAllTests() {
            List<TestCase> result = new ArrayList<>();
            
            result.add(noSubsets());
            result.add(oneRegularSubset());
            result.add(oneComplementedSubset());
            
            result.add(twoDisjointSubsetsRR());
            result.add(twoOverlappingSubsetsRR());
            result.add(twoIdenticalSubsetsRR());
            result.add(setAndProperSubsetRR());
            
            result.add(twoDisjointSubsetsRC());
            result.add(twoOverlappingSubsetsRC());
            result.add(twoIdenticalSubsetsRC());
            result.add(setAndProperSubsetRC());
            
            result.add(twoDisjointSubsetsCR());
            result.add(twoOverlappingSubsetsCR());
            result.add(twoIdenticalSubsetsCR());
            result.add(setAndProperSubsetCR());
            
            result.add(twoDisjointSubsetsCC());
            result.add(twoOverlappingSubsetsCC());
            result.add(twoIdenticalSubsetsCC());
            result.add(setAndProperSubsetCC());
            
            result.add(manySubsets());

            return result;
        }
        
        public static TestCase noSubsets() {
            List<Set<Character>> sourceSets = new ArrayList<>();
            List<Boolean> complementedFlags = new ArrayList<>();
            
            return  new TestCase("No Subsets", sourceSets, complementedFlags);
        }
        
        public static TestCase oneRegularSubset() {
            List<Set<Character>> sourceSets = new ArrayList<>();
            List<Boolean> complementedFlags = new ArrayList<>();
            
            sourceSets.add(buildSet('a', 'b', 'c'));
            complementedFlags.add(false);
            
            return  new TestCase("One Regular Subset", sourceSets, complementedFlags);
        }
        
        public static TestCase oneComplementedSubset() {
            List<Set<Character>> sourceSets = new ArrayList<>();
            List<Boolean> complementedFlags = new ArrayList<>();
            
            sourceSets.add(buildSet('a', 'b', 'c'));
            complementedFlags.add(true);
            
            return  new TestCase("One Complemented Subset", sourceSets, complementedFlags);
        }
        
        public static TestCase twoDisjointSubsetsRR() {
            List<Set<Character>> sourceSets = new ArrayList<>();
            List<Boolean> complementedFlags = new ArrayList<>();
            
            sourceSets.add(buildSet('a', 'b', 'c'));
            complementedFlags.add(false);
            sourceSets.add(buildSet('d', 'e', 'f'));
            complementedFlags.add(false);
            
            return  new TestCase("Disjoint Subsets RR", sourceSets, complementedFlags);
        }
        
        public static TestCase twoOverlappingSubsetsRR() {
            List<Set<Character>> sourceSets = new ArrayList<>();
            List<Boolean> complementedFlags = new ArrayList<>();
            
            sourceSets.add(buildSet('a', 'b', 'c'));
            complementedFlags.add(false);
            sourceSets.add(buildSet('c', 'd', 'e'));
            complementedFlags.add(false);
            
            return  new TestCase("Overlapping subsets RR", sourceSets, complementedFlags);
        }
        
        public static TestCase twoIdenticalSubsetsRR() {
            List<Set<Character>> sourceSets = new ArrayList<>();
            List<Boolean> complementedFlags = new ArrayList<>();
            
            sourceSets.add(buildSet('a', 'b', 'c'));
            complementedFlags.add(false);
            sourceSets.add(buildSet('a', 'b', 'c'));
            complementedFlags.add(false);
            
            return  new TestCase("Identical Subsets RR", sourceSets, complementedFlags);
        }
        
        public static TestCase setAndProperSubsetRR() {
            List<Set<Character>> sourceSets = new ArrayList<>();
            List<Boolean> complementedFlags = new ArrayList<>();
            
            sourceSets.add(buildSet('a', 'b', 'c', 'd', 'e', 'f'));
            complementedFlags.add(false);
            sourceSets.add(buildSet('b', 'c', 'd'));
            complementedFlags.add(false);
            
            return  new TestCase("Set and Proper Subset RR", sourceSets, complementedFlags);
        }
        
        public static TestCase twoDisjointSubsetsRC() {
            List<Set<Character>> sourceSets = new ArrayList<>();
            List<Boolean> complementedFlags = new ArrayList<>();
            
            sourceSets.add(buildSet('a', 'b', 'c'));
            complementedFlags.add(false);
            sourceSets.add(buildSet('d', 'e', 'f'));
            complementedFlags.add(true);
            
            return  new TestCase("Disjoint Subsets RC", sourceSets, complementedFlags);
        }
        
        public static TestCase twoOverlappingSubsetsRC() {
            List<Set<Character>> sourceSets = new ArrayList<>();
            List<Boolean> complementedFlags = new ArrayList<>();
            
            sourceSets.add(buildSet('a', 'b', 'c'));
            complementedFlags.add(false);
            sourceSets.add(buildSet('c', 'd', 'e'));
            complementedFlags.add(true);
            
            return  new TestCase("Overlapping subsets RC", sourceSets, complementedFlags);
        }
        
        public static TestCase twoIdenticalSubsetsRC() {
            List<Set<Character>> sourceSets = new ArrayList<>();
            List<Boolean> complementedFlags = new ArrayList<>();
            
            sourceSets.add(buildSet('a', 'b', 'c'));
            complementedFlags.add(false);
            sourceSets.add(buildSet('a', 'b', 'c'));
            complementedFlags.add(true);
            
            return  new TestCase("Identical Subsets RC", sourceSets, complementedFlags);
        }
        
        public static TestCase setAndProperSubsetRC() {
            List<Set<Character>> sourceSets = new ArrayList<>();
            List<Boolean> complementedFlags = new ArrayList<>();
            
            sourceSets.add(buildSet('a', 'b', 'c', 'd', 'e', 'f'));
            complementedFlags.add(false);
            sourceSets.add(buildSet('b', 'c', 'd'));
            complementedFlags.add(true);
            
            return  new TestCase("Set and Proper Subset RC", sourceSets, complementedFlags);
        }
        
        public static TestCase twoDisjointSubsetsCR() {
            List<Set<Character>> sourceSets = new ArrayList<>();
            List<Boolean> complementedFlags = new ArrayList<>();
            
            sourceSets.add(buildSet('a', 'b', 'c'));
            complementedFlags.add(true);
            sourceSets.add(buildSet('d', 'e', 'f'));
            complementedFlags.add(false);
            
            return  new TestCase("Disjoint Subsets CR", sourceSets, complementedFlags);
        }
        
        public static TestCase twoOverlappingSubsetsCR() {
            List<Set<Character>> sourceSets = new ArrayList<>();
            List<Boolean> complementedFlags = new ArrayList<>();
            
            sourceSets.add(buildSet('a', 'b', 'c'));
            complementedFlags.add(true);
            sourceSets.add(buildSet('c', 'd', 'e'));
            complementedFlags.add(false);
            
            return  new TestCase("Overlapping subsets CR", sourceSets, complementedFlags);
        }
        
        public static TestCase twoIdenticalSubsetsCR() {
            List<Set<Character>> sourceSets = new ArrayList<>();
            List<Boolean> complementedFlags = new ArrayList<>();
            
            sourceSets.add(buildSet('a', 'b', 'c'));
            complementedFlags.add(true);
            sourceSets.add(buildSet('a', 'b', 'c'));
            complementedFlags.add(false);
            
            return  new TestCase("Identical Subsets CR", sourceSets, complementedFlags);
        }
        
        public static TestCase setAndProperSubsetCR() {
            List<Set<Character>> sourceSets = new ArrayList<>();
            List<Boolean> complementedFlags = new ArrayList<>();
            
            sourceSets.add(buildSet('a', 'b', 'c', 'd', 'e', 'f'));
            complementedFlags.add(true);
            sourceSets.add(buildSet('b', 'c', 'd'));
            complementedFlags.add(false);
            
            return  new TestCase("Set and Proper Subset CR", sourceSets, complementedFlags);
        }
        
        public static TestCase twoDisjointSubsetsCC() {
            List<Set<Character>> sourceSets = new ArrayList<>();
            List<Boolean> complementedFlags = new ArrayList<>();
            
            sourceSets.add(buildSet('a', 'b', 'c'));
            complementedFlags.add(true);
            sourceSets.add(buildSet('d', 'e', 'f'));
            complementedFlags.add(true);
            
            return  new TestCase("Disjoint Subsets CC", sourceSets, complementedFlags);
        }
        
        public static TestCase twoOverlappingSubsetsCC() {
            List<Set<Character>> sourceSets = new ArrayList<>();
            List<Boolean> complementedFlags = new ArrayList<>();
            
            sourceSets.add(buildSet('a', 'b', 'c'));
            complementedFlags.add(true);
            sourceSets.add(buildSet('c', 'd', 'e'));
            complementedFlags.add(true);
            
            return  new TestCase("Overlapping Subsets CC", sourceSets, complementedFlags);
        }
        
        public static TestCase twoIdenticalSubsetsCC() {
            List<Set<Character>> sourceSets = new ArrayList<>();
            List<Boolean> complementedFlags = new ArrayList<>();
            
            sourceSets.add(buildSet('a', 'b', 'c'));
            complementedFlags.add(true);
            sourceSets.add(buildSet('a', 'b', 'c'));
            complementedFlags.add(true);
            
            return  new TestCase("Identical Subsets CC", sourceSets, complementedFlags);
        }
        
        public static TestCase setAndProperSubsetCC() {
            List<Set<Character>> sourceSets = new ArrayList<>();
            List<Boolean> complementedFlags = new ArrayList<>();
            
            sourceSets.add(buildSet('a', 'b', 'c', 'd', 'e', 'f'));
            complementedFlags.add(true);
            sourceSets.add(buildSet('b', 'c', 'd'));
            complementedFlags.add(true);
            
            return  new TestCase("Set and Proper Subset CC", sourceSets, complementedFlags);
        }
        
        public static TestCase manySubsets() {
            List<Set<Character>> sourceSets = new ArrayList<>();
            List<Boolean> complementedFlags = new ArrayList<>();
            
            sourceSets.add(buildSet('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'));
            complementedFlags.add(true);
            sourceSets.add(buildSet('b', 'd'));
            complementedFlags.add(false);
            sourceSets.add(buildSet('e', 'f', 'g', 'h', 'i', 'j', 'k', 'l'));
            complementedFlags.add(true);
            sourceSets.add(buildSet('e', 'f', 'g', 'h', 'i', 'j', 'k', 'l'));
            complementedFlags.add(false);
            sourceSets.add(buildSet('b'));
            complementedFlags.add(false);
            sourceSets.add(buildSet('m', 'n', 'o','p'));
            complementedFlags.add(true);
            sourceSets.add(buildSet('a', 'c', 'e', 'g', 'i', 'k', 'l'));
            complementedFlags.add(false);
            sourceSets.add(buildSet('a', 'd', 'g', 'j', 'm'));
            complementedFlags.add(true);
            sourceSets.add(buildSet('b', 'd', 'f', 'h'));
            complementedFlags.add(true);
            sourceSets.add(buildSet('b'));
            complementedFlags.add(true);
            sourceSets.add(buildSet('b', 'd'));
            complementedFlags.add(false);
            sourceSets.add(buildSet('m', 'f', 'r', 'a'));
            complementedFlags.add(true);
            sourceSets.add(buildSet('b', 'd'));
            complementedFlags.add(false);
            
            
            return  new TestCase("Many Subsets", sourceSets, complementedFlags);
        }
        
        private static Set<Character> buildSet(Character... chars)
        {
            return new HashSet<>(Arrays.asList(chars));
        }

    }
}
