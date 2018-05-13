/*
 *
 *  Copyright 2016 Netflix, Inc.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */
package com.netflix.hollow.tools.history;

import com.netflix.hollow.api.sampling.DisabledSamplingDirector;
import com.netflix.hollow.api.sampling.HollowSetSampler;
import com.netflix.hollow.core.index.key.PrimaryKey;
import com.netflix.hollow.core.memory.encoding.HashCodes;
import com.netflix.hollow.core.read.dataaccess.HollowSetTypeDataAccess;
import com.netflix.hollow.core.read.engine.HollowTypeReadState;
import com.netflix.hollow.core.read.engine.SetMapKeyHasher;
import com.netflix.hollow.core.read.engine.set.HollowSetTypeReadState;
import com.netflix.hollow.core.read.iterator.HollowOrdinalIterator;
import com.netflix.hollow.core.schema.HollowSetSchema;

public class HollowHistoricalSetDataAccess extends HollowHistoricalTypeDataAccess implements HollowSetTypeDataAccess {

    private HistoricalPrimaryKeyMatcher keyMatcher;
    
    public HollowHistoricalSetDataAccess(HollowHistoricalStateDataAccess dataAccess, HollowTypeReadState typeState) {
        super(dataAccess, typeState, new HollowSetSampler(typeState.getSchema().getName(), DisabledSamplingDirector.INSTANCE));
    }

    @Override
    public HollowSetSchema getSchema() {
        return ((HollowSetTypeReadState) removedRecords).getSchema();
    }

    @Override
    public int size(int ordinal) {
        if(!ordinalIsPresent(ordinal))
            return ((HollowSetTypeDataAccess)dataAccess.getTypeDataAccess(getSchema().getName(), ordinal)).size(ordinal);
        return ((HollowSetTypeReadState) removedRecords).size(getMappedOrdinal(ordinal));
    }
    
    @Override
    public boolean contains(int ordinal, int value) {
        if(!ordinalIsPresent(ordinal))
            return ((HollowSetTypeDataAccess)dataAccess.getTypeDataAccess(getSchema().getName(), ordinal)).contains(ordinal, value);
        return ((HollowSetTypeReadState) removedRecords).contains(getMappedOrdinal(ordinal), value);
    }
    
    @Override
    public int findElement(int ordinal, Object... hashKey) {
        if(keyMatcher == null)
            return -1;
        
        if(!ordinalIsPresent(ordinal))
            return ((HollowSetTypeDataAccess)dataAccess.getTypeDataAccess(getSchema().getName(), ordinal)).findElement(ordinal, hashKey);
        
        ordinal = ordinalRemap.get(ordinal);
        
        HollowSetTypeReadState removedRecords = (HollowSetTypeReadState)getRemovedRecords();
        
        int hashTableSize = HashCodes.hashTableSize(removedRecords.size(ordinal));
        int hash = SetMapKeyHasher.hash(hashKey, keyMatcher.getFieldTypes());
        
        int bucket = hash & (hashTableSize - 1);
        int bucketOrdinal = removedRecords.relativeBucketValue(ordinal, bucket);
        while(bucketOrdinal != -1) {
            if(keyMatcher.keyMatches(bucketOrdinal, hashKey))
                return bucketOrdinal;
         
            bucket++;
            bucket &= (hashTableSize - 1);
            bucketOrdinal = removedRecords.relativeBucketValue(ordinal, bucket);
        }
        
        return -1;
    }

    @Override
    public boolean contains(int ordinal, int value, int hashCode) {
        if(!ordinalIsPresent(ordinal))
            return ((HollowSetTypeDataAccess)dataAccess.getTypeDataAccess(getSchema().getName(), ordinal)).contains(ordinal, value, hashCode);
        return ((HollowSetTypeReadState) removedRecords)
            .contains(getMappedOrdinal(ordinal), value, hashCode);
    }

    @Override
    public int relativeBucketValue(int ordinal, int bucketIndex) {
        if(!ordinalIsPresent(ordinal))
            return ((HollowSetTypeDataAccess)dataAccess.getTypeDataAccess(getSchema().getName(), ordinal)).relativeBucketValue(ordinal, bucketIndex);
        return ((HollowSetTypeReadState) removedRecords)
            .relativeBucketValue(getMappedOrdinal(ordinal), bucketIndex);
    }

    @Override
    public HollowOrdinalIterator potentialMatchOrdinalIterator(int ordinal, int hashCode) {
        if(!ordinalIsPresent(ordinal))
            return ((HollowSetTypeDataAccess)dataAccess.getTypeDataAccess(getSchema().getName(), ordinal)).potentialMatchOrdinalIterator(ordinal, hashCode);
        return ((HollowSetTypeReadState) removedRecords)
            .potentialMatchOrdinalIterator(getMappedOrdinal(ordinal), hashCode);
    }

    @Override
    public HollowOrdinalIterator ordinalIterator(int ordinal) {
        if(!ordinalIsPresent(ordinal))
            return ((HollowSetTypeDataAccess)dataAccess.getTypeDataAccess(getSchema().getName(), ordinal)).ordinalIterator(ordinal);
        return ((HollowSetTypeReadState) removedRecords).ordinalIterator(getMappedOrdinal(ordinal));
    }

    void buildKeyMatcher() {
        PrimaryKey hashKey = getSchema().getHashKey();
        if(hashKey != null)
            this.keyMatcher = new HistoricalPrimaryKeyMatcher(getDataAccess(), hashKey);
    }

}
