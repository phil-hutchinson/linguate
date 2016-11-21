/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.sets;

import java.util.Set;

/**
 * A VennPartitioner partitions a universal set based on a set of input subsets of
 * this set in such a way that "alike" elements are grouped together, where elements 
 * are "alike" iff they appear in the exact same set of input subsets. As a result
 * of this process, each input subset can be defined as a union of a set
 * of partitions from the partitioned set.
 * 
 * <p>A simple way to visualize this is to consider a Venn diagram. Each non-empty 
 * region of the Venn diagram will be a separate partition. So, for example, if 
 * the Venn diagram has subsets  A and B, then there will be a partition for the
 * region containing elements from only set A, a partition for the region containing
 * elements from only set B, a partition for the region where A and B intersect, and
 * a partition for the region outside of both A and B. If a third set C is added, this
 * creates four new partitions: its intersection with A, its intersection with B, its
 * intersection with both A and B, and the subset containing items only from C. (Note
 * that the VennPartitioner will only create partitions for non-empty regions.)
 * 
 * <p>The partitions themselves are referred to by integer index.
 * 
 * <p>Throughout this class, subsets can be defined either by a list of the elements 
 * they contain, or by their complement (i.e. by listing all elements they do not 
 * contain: so the elements the set does include in this case is the complement of 
 * the defining set with respect to the universal set. At most, one partition may 
 * be defined by its complement. A partition defined by its complement will exist iff
 * at least one 
 * 
 * <p>Generation methods and retrieval methods can be interspersed, but the results
 * of retrieval methods are invalidated by subsequent calls to generation methods.
 * 
 * <p>Generation methods include addSubset(), and retrieval methods
 include getRegularPartitionCount(), getComplementedPartitionExists(),
 getPartition(), and getPartitionsForSubset().
 
 <p>This class can be better understood by looking at an example use case. In
 * the case of a RegEx tree, the character class (i.e. the set of accepted characters) 
 * of each leaf node would be passed as a distinct subset. This process would create
 * a set of partitions, where all characters in the same partition are accepted by
 * the exact same set of partitions - so that all characters in the same partition
 * can be treated as "congruent", since they always behave the same.
 * 
 * @author Phil Hutchinson
 * @param <TElement> The type of element contained by the universal set.
 */
public interface VennPartitioner<TElement> {
    public final int COMPLEMENTED_PARTITION_INDEX = -1;
    
    /**
     * Specifies a subset that should be used for distinguishing elements
     * of the universal set.
     * 
     * <p>Once this method has been called, the VennPartitioner will be able to 
     * provide a set of partitions whose union is equal to this subset. May be
     * set to an empty set, but cannot be set to null.
     * 
     * @param subset: the subset to integrate into the Venn partition.
     */
    void addSubset(Set<TElement> subset);

    /**
     * Specifies a complemented subset that should be used for distinguishing elements
     * of the universal set - i.e. all items that are not a member of the subset are provided.
     * 
     * <p>Once this method has been called, the VennPartitioner will be able to 
     * provide a set of partitions whose union is equal to this subset.
     * 
     * @param complementOfSubset: the subset containing all elements that are not
     * part of the subset being added to the VennPartitioner. May be an empty set
     * (indicating the subset = the universal set) but cannot be set to null.
     */
    void addSubsetByComplement(Set<TElement> complementOfSubset);

    /**
     * Returns the number of partitions the universal set has been
     * separated into, excluding the complemented partition.
     * 
     * @return The number of regular partitions.
     */
    int getRegularPartitionCount();

    /**
     * Returns true when there is a a partition consisting of all items that are
     * not part of any other partition (i.e. the leftover items). If a complemented
     * partition exists, its index will be equal to 
     * 
     * @return True if complemented partition exists, its index will be
     * {@link #COMPLEMENTED_PARTITION_INDEX COMPLEMENTED_PARTITION_INDEX}.
     */
    boolean getComplementedPartitionExists();

    /**
     * Returns the contents of a partition. This method will either return all
     * of the elements in the partition.
     * 
     * <p>The value partitionId  must be a valid index i.e. between 0 and the value returned
     * by {@link #getRegularPartitionCount() getRegularPartitionCount} minus one.
     * 
     * @param partitionId : the index of the partition. The total number of 
     * partitions can be determined with {@link #getRegularPartitionCount() getRegularPartitionCount}.
     * @return The definition of the partition, as a set of elements either contained
     * or not contained in the partition.
     */
    Set<TElement> getPartition(int partitionId);

    /**
     * Returns the indices of partitions whose union is equal to the given subset.
     * 
     * <p>The parameter subset may not be null, and must refer to a regular subset that was
     * previously added using {@link #addSubset(java.util.Set) addSubset}.
     * It may be an empty set, if this set was previously passed as a regular subset.
     * 
     * @param subset The set of items to retrieve partitions for. Must have been previously
     * passed to {@link #addSubset(java.util.Set) addSubset}.
     * @return a set of indices for the partitions which can be used to compose 
     * the subset.
     */
    Set<Integer> getPartitionsForSubset(Set<TElement> subset);
    
    /**
     * Returns the indices of partitions whose union is equal to the given subset.
     * 
     * <p>The parameter complementedSubset may not be null, and must refer to a complemented subset that was
     * previously added using {@link #addSubset(java.util.Set) addSubset}.
     * It may be an empty set, if this set was previously passed as a complemented subset.
     * 
     * @param complementOfSubset The set of items to retrieve partitions for. Must have been
     * previously passed to {@link #addSubsetByComplement(java.util.Set) addSubsetByComplement}.
     * @return a set of indices for the partitions which compose the distinctSubset.
     */
    Set<Integer> getPartitionsForSubsetByComplement(Set<TElement> complementOfSubset);
}
