/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.sets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Default implementation of VennPartitioner.
 * 
 * 
 * @author Phil Hutchinson
 */
public class DefaultVennPartitioner<TElement> implements VennPartitioner<TElement> {
    private Map<TElement,Long> elementToRawPartition = new HashMap<>();
    private Map<TElement,Integer> elementToPublicPartition = new HashMap<>();
    private Set<TElement>[] publicPartitions = new Set[0];
    int regularPartitionCount = 0;
    boolean publicPartitionsBuilt = false;
    private long nextRawPartitionId = 0;
    boolean hasSubsetByComplement = false;
    
    @Override
    public void addSubset(Set<TElement> subset) {
        if (subset == null) {
            throw new NullPointerException("Parameter subset cannot be null.");
        }
        
        Map<Long,Long> rawPartitionUpdates = new HashMap<>();
        long newElementRawPartition = getNextRawPartitionId();
        for(TElement element : subset) {
            if (!elementToRawPartition.containsKey(element)) {
                // first time this element has been seen.
                elementToRawPartition.put(element, newElementRawPartition);
            } else {
                // element seen before. Check whether replacement partition has been 
                // created for partition it belongs to.
                long rawPartitionId = elementToRawPartition.get(element);
                long replacementRawPartitionId;
                if (rawPartitionUpdates.containsKey(rawPartitionId)) {
                    replacementRawPartitionId = rawPartitionUpdates.get(rawPartitionId);
                } else {
                    replacementRawPartitionId = getNextRawPartitionId();
                    rawPartitionUpdates.put(rawPartitionId, replacementRawPartitionId);
                }
                elementToRawPartition.put(element, replacementRawPartitionId);
            }
        }
    }

    @Override
    public void addSubsetByComplement(Set<TElement> complementOfSubset) {
        if (complementOfSubset == null) {
            throw new NullPointerException("Parameter complementOfSubset cannot be null.");
        }
        
        addSubset(complementOfSubset);
        hasSubsetByComplement = true;
    }

    @Override
    public int getRegularPartitionCount() {
        if (!publicPartitionsBuilt) {
            buildPublicPartitions();
        }
        
        return regularPartitionCount;
    }

    @Override
    public boolean getComplementedPartitionExists() {
        return hasSubsetByComplement;
    }

    @Override
    public Set<TElement> getPartition(int partitionId) {
        if (!publicPartitionsBuilt) {
            buildPublicPartitions();
        }

        if (partitionId < 0 || partitionId >= this.regularPartitionCount) {
            throw new IndexOutOfBoundsException();
        }
        
        return Collections.unmodifiableSet(publicPartitions[partitionId]);
    }

    @Override
    public Set<Integer> getPartitionsForSubset(Set<TElement> subset) {
        if (subset == null) {
            throw new NullPointerException("Parameter subset cannot be null.");
        }
        if (!publicPartitionsBuilt) {
            buildPublicPartitions();
        }
        Set<Integer> result = new HashSet<>();
        Set<TElement> remainingItems = new HashSet<TElement>(subset);
        
        while(remainingItems.size() > 0) {
            // take any element
            TElement element = remainingItems.iterator().next();
            if (!elementToPublicPartition.containsKey(element)) {
                throw new IllegalStateException("Please ensure the set passed to getPartitionsForSubset has previously been passed to addSubset.");
            }
            
            int publicPartitionId = elementToPublicPartition.get(element);
            result.add(publicPartitionId);
            Set<TElement> fullPartition = publicPartitions[publicPartitionId];
            remainingItems.removeAll(fullPartition);
        }
        
        return result;
    }
    
    @Override
    public Set<Integer> getPartitionsForSubsetByComplement(Set<TElement> complementOfSubset) {
        if (complementOfSubset == null) {
            throw new NullPointerException("Parameter complementOfSubset cannot be null.");
        }
        
        Set<Integer> excludedPartitions = getPartitionsForSubset(complementOfSubset);
        
        Set<Integer> result = new HashSet<>();
        for (int i = 0; i < regularPartitionCount; i++) {
            if (!excludedPartitions.contains(i)) {
                result.add(i);
            }
        }
        
        result.add(VennPartitioner.COMPLEMENTED_PARTITION_INDEX);
        
        return result;
    }
    
    private long getNextRawPartitionId() {
        long result = nextRawPartitionId;
        nextRawPartitionId++;
        return result;
    }

    private void buildPublicPartitions() {
        resetPublicPartitions();
        int nextPublicPartition = 0;
        
        Map<Long, Integer> rawToPublic = new HashMap<>();
        
        for(Map.Entry<TElement, Long> rawEntry: elementToRawPartition.entrySet() ) {
            TElement element = rawEntry.getKey();
            long rawPartition = rawEntry.getValue();
            
            int publicPartition;
            if (rawToPublic.containsKey(rawPartition)) {
                publicPartition = rawToPublic.get(rawPartition);
            } else {
                publicPartition = nextPublicPartition;
                nextPublicPartition++;
                rawToPublic.put(rawPartition, publicPartition);
            }
            
            elementToPublicPartition.put(element, publicPartition);
        }
        regularPartitionCount = nextPublicPartition;
        
        publicPartitions = new Set[regularPartitionCount];
        for(int i = 0; i < regularPartitionCount; i++) {
            publicPartitions[i] = new HashSet<>();
        }
        
        for(Map.Entry<TElement, Integer> publicEntry: elementToPublicPartition.entrySet() ) {
            TElement element = publicEntry.getKey();
            int publicPartition = publicEntry.getValue();
            
            publicPartitions[publicPartition].add(element);
        }
        
        publicPartitionsBuilt = true;
    }

    private void resetPublicPartitions() {
        elementToPublicPartition.clear();
        publicPartitions = new Set[0];
    }
}
