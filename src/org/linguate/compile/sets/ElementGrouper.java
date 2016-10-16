/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.sets;

import java.util.Set;

/**
 * An ElementGrouper creates a partition on a set based on initial subsets of
 * this set, so that alike elements are grouped together, where elements are
 * alike iff they appear in the exact same set of initial subsets. As a result
 * of this process, each initial subset can be defined as a union of a set
 * of partitions from the partitioned set.
 * 
 * <p>The partitions themselves are referred to by integer index.
 * 
 * <p>Throughout this class, subsets can be defined either by a list of the elements 
 * they contain, or by their complement (i.e. by listing all elements they do not 
 * contain: so the elements the set does include in this case is the complement of 
 * the defining set with respect to the universal set. At most, one partition may 
 * be defined by its complement.
 * 
 * <p>Generation methods and retrieval methods can be interspersed, but the results
 * of retrieval methods are invalidated by subsequent calls to generation methods.
 * 
 * <p>Generation methods include addDistinctSubset(), and retrieval methods
 include getPartitionCount(), getPartitionIsDefinedByComplement(),
 getPartition(), and getPartitionsForDistinctSubset().
 
 <p>This class can be better understood by looking at an example use case. In
 * the case of a RegEx tree, the character class (i.e. the set of accepted characters) 
 * of each leaf node would be passed as a distinct subset. This process would create
 * a set of partitions, where all characters in the same partition are accepted by
 * the exact same set of partitions - so that 
 * 
 * @author Phil Hutchinson
 * @param <TElement> The type of element contained by the universal set.
 */
public interface ElementGrouper<TElement> {

    /**
     * Specifies a subset that should be used for distinguishing elements
     * of the universal set.
     * 
     * <p>Once this method has been called, the SetParititioner will be able to 
     * provide a set of partitions set of partitions whose union is equal to 
     * this subset.
     * 
     * @param newSubset: the subset to integrate into the partition.
     * @param definedByComplement: true if the subset consists of elements
     * not in the set, rather than elements in the set.
     */
    void addDistinctSubset(Set<TElement> newSubset, boolean definedByComplement);

    /**
     * Returns the number of partitions the universal set has been
     * separated into.
     * 
     * @return The number of partition.
     */
    int getPartitionCount();

    /**
     * Returns true where the contents of a partition are defined by elements
     * that do not exist in the set, rather than listing the elements that
     * do exist in the set.
     * 
     * <p>This method provides context to calls to {@link #getPartition(int) getPartition}.
     * The value of partitionId must be a valid index i.e. between 0 and the value returned
     * by {@link #getPartitionCount() getPartitionCount} minus one.
     * 
     * @param partitionId : the index of the partition. The total number of 
     * partitions can be determined with {@link #getPartitionCount() getPartitionCount}.
     * @return True if partition is described by its complement, else false.
     */
    boolean getPartitionIsDefinedByComplement(int partitionId);

    /**
     * Returns the contents of a partition. This method will either return all
     * of the elements that are in the partition, or all elements that are not,
     * so it requires a call to {@link #getPartitionIsDefinedByComplement(int) getPartitionIsDefinedByComplement}
     * to give it context.
     * 
     * <p>The value partitionId  must be a valid index i.e. between 0 and the value returned
     * by {@link #getPartitionCount() getPartitionCount} minus one.
     * 
     * @param partitionId : the index of the partition. The total number of 
     * partitions can be determined with {@link #getPartitionCount() getPartitionCount}.
     * @return The definition of the partition, as a set of elements either contained
     * or not contained in the partition.
     */
    Set<TElement> getPartition(int paritionId);

    /**
     * Returns the indices of partitions whose union is equal to the given distinct subset.
     * 
     * <p>The parameter distinctSubset may not be null, and must refer to a distinct subset that was
     * previously added using {@link #addDistinctSubset(java.util.Set, boolean) addDistinctSubset}.
     * It may be an empty set, if this set was previously passed as a distinct subset.
     * 
     * 
     * @param distinctSubset The set of items to retrieve partitions for.
     * @param definedByComplement True if distinctSubset defines all items that are not part
     * of the subset
     * @return a set of indices for the partitions which compose the distinctSubset.
     */
    Set<Integer> getPartitionsForDistinctSubset(Set<TElement> distinctSubset, boolean definedByComplement);
}
