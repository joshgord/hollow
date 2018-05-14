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
package com.netflix.hollow.core.read.dataaccess.proxy;

import com.netflix.hollow.core.read.dataaccess.HollowMapTypeDataAccess;
import com.netflix.hollow.core.read.dataaccess.HollowTypeDataAccess;
import com.netflix.hollow.core.read.engine.map.PotentialMatchHollowMapEntryOrdinalIteratorImpl;
import com.netflix.hollow.core.read.iterator.HollowMapEntryOrdinalIterator;
import com.netflix.hollow.core.read.iterator.HollowMapEntryOrdinalIteratorImpl;
import com.netflix.hollow.core.schema.HollowMapSchema;

/**
 * A {@link HollowTypeProxyDataAccess} for a MAP type.
 * 
 * @see HollowProxyDataAccess
 */
public class HollowMapProxyDataAccess extends HollowTypeProxyDataAccess implements HollowMapTypeDataAccess{

    public HollowMapProxyDataAccess(HollowProxyDataAccess dataAccess) {
        super(dataAccess);
    }

    public void setCurrentDataAccess(HollowTypeDataAccess currentDataAccess) {
        this.currentDataAccess = currentDataAccess;
    }

    @Override
    public HollowMapSchema getSchema() {
        return ((HollowMapTypeDataAccess) currentDataAccess).getSchema();
    }

    @Override
    public int size(int ordinal) {
        return ((HollowMapTypeDataAccess) currentDataAccess).size(ordinal);
    }

    @Override
    public int get(int ordinal, int keyOrdinal) {
        return ((HollowMapTypeDataAccess) currentDataAccess).get(ordinal, keyOrdinal);
    }

    @Override
    public int get(int ordinal, int keyOrdinal, int hashCode) {
        return ((HollowMapTypeDataAccess) currentDataAccess).get(ordinal, keyOrdinal, hashCode);
    }
    
    @Override
    public int findKey(int ordinal, Object... hashKey) {
        return ((HollowMapTypeDataAccess) currentDataAccess).findKey(ordinal, hashKey);
    }

    @Override
    public int findValue(int ordinal, Object... hashKey) {
        return ((HollowMapTypeDataAccess) currentDataAccess).findValue(ordinal, hashKey);
    }

    @Override
    public long findEntry(int ordinal, Object... hashKey) {
        return ((HollowMapTypeDataAccess) currentDataAccess).findEntry(ordinal, hashKey);
    }

    @Override
    public HollowMapEntryOrdinalIterator potentialMatchOrdinalIterator(int ordinal, int hashCode) {
        return new PotentialMatchHollowMapEntryOrdinalIteratorImpl(ordinal, this, hashCode);
    }

    @Override
    public HollowMapEntryOrdinalIterator ordinalIterator(int ordinal) {
        return new HollowMapEntryOrdinalIteratorImpl(ordinal, this);
    }

    @Override
    public long relativeBucket(int ordinal, int bucketIndex) {
        return ((HollowMapTypeDataAccess) currentDataAccess).relativeBucket(ordinal, bucketIndex);
    }
}
