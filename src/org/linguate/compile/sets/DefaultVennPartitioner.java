/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.sets;

import java.util.Set;

/**
 * Default implementation of VennPartitioner.
 * 
 * 
 * @author Phil Hutchinson
 */
public class DefaultVennPartitioner<TElement> implements VennPartitioner {

    @Override
    public void addSubset(Set subset) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addSubsetByComplement(Set complementOfSubset) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getRegularPartitionCount() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean getComplementedPartitionExists() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set getPartition(int partitionId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set getPartitionsForSubset(Set subset) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Set getPartitionsForSubsetByComplement(Set complementOfSubset) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
