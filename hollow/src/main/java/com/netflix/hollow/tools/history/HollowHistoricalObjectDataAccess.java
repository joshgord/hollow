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
import com.netflix.hollow.api.sampling.HollowObjectSampler;
import com.netflix.hollow.core.read.dataaccess.HollowObjectTypeDataAccess;
import com.netflix.hollow.core.read.engine.HollowTypeReadState;
import com.netflix.hollow.core.read.engine.object.HollowObjectTypeReadState;
import com.netflix.hollow.core.schema.HollowObjectSchema;

public class HollowHistoricalObjectDataAccess extends HollowHistoricalTypeDataAccess implements HollowObjectTypeDataAccess {

    public HollowHistoricalObjectDataAccess(HollowHistoricalStateDataAccess dataAccess, HollowTypeReadState removedRecords) {
        super(dataAccess, removedRecords, new HollowObjectSampler((HollowObjectSchema)removedRecords.getSchema(), DisabledSamplingDirector.INSTANCE));
    }

    @Override
    public HollowObjectSchema getSchema() {
        return ((HollowObjectTypeReadState) removedRecords).getSchema();
    }

    @Override
    public boolean isNull(int ordinal, int fieldIndex) {
        if(!ordinalIsPresent(ordinal))
            return ((HollowObjectTypeDataAccess)dataAccess.getTypeDataAccess(getSchema().getName(), ordinal)).isNull(ordinal, fieldIndex);
        return ((HollowObjectTypeReadState) removedRecords)
            .isNull(getMappedOrdinal(ordinal), fieldIndex);
    }

    @Override
    public int readOrdinal(int ordinal, int fieldIndex) {
        if(!ordinalIsPresent(ordinal))
            return ((HollowObjectTypeDataAccess)dataAccess.getTypeDataAccess(getSchema().getName(), ordinal)).readOrdinal(ordinal, fieldIndex);
        return ((HollowObjectTypeReadState) removedRecords)
            .readOrdinal(getMappedOrdinal(ordinal), fieldIndex);
    }

    @Override
    public int readInt(int ordinal, int fieldIndex) {
        if(!ordinalIsPresent(ordinal))
            return ((HollowObjectTypeDataAccess)dataAccess.getTypeDataAccess(getSchema().getName(), ordinal)).readInt(ordinal, fieldIndex);
        return ((HollowObjectTypeReadState) removedRecords)
            .readInt(getMappedOrdinal(ordinal), fieldIndex);
    }

    @Override
    public float readFloat(int ordinal, int fieldIndex) {
        if(!ordinalIsPresent(ordinal))
            return ((HollowObjectTypeDataAccess)dataAccess.getTypeDataAccess(getSchema().getName(), ordinal)).readFloat(ordinal, fieldIndex);
        return ((HollowObjectTypeReadState) removedRecords)
            .readFloat(getMappedOrdinal(ordinal), fieldIndex);
    }

    @Override
    public double readDouble(int ordinal, int fieldIndex) {
        if(!ordinalIsPresent(ordinal))
            return ((HollowObjectTypeDataAccess)dataAccess.getTypeDataAccess(getSchema().getName(), ordinal)).readDouble(ordinal, fieldIndex);
        return ((HollowObjectTypeReadState) removedRecords)
            .readDouble(getMappedOrdinal(ordinal), fieldIndex);
    }

    @Override
    public long readLong(int ordinal, int fieldIndex) {
        if(!ordinalIsPresent(ordinal))
            return ((HollowObjectTypeDataAccess)dataAccess.getTypeDataAccess(getSchema().getName(), ordinal)).readLong(ordinal, fieldIndex);
        return ((HollowObjectTypeReadState) removedRecords)
            .readLong(getMappedOrdinal(ordinal), fieldIndex);
    }

    @Override
    public Boolean readBoolean(int ordinal, int fieldIndex) {
        if(!ordinalIsPresent(ordinal))
            return ((HollowObjectTypeDataAccess)dataAccess.getTypeDataAccess(getSchema().getName(), ordinal)).readBoolean(ordinal, fieldIndex);
        return ((HollowObjectTypeReadState) removedRecords)
            .readBoolean(getMappedOrdinal(ordinal), fieldIndex);
    }

    @Override
    public byte[] readBytes(int ordinal, int fieldIndex) {
        if(!ordinalIsPresent(ordinal))
            return ((HollowObjectTypeDataAccess)dataAccess.getTypeDataAccess(getSchema().getName(), ordinal)).readBytes(ordinal, fieldIndex);
        return ((HollowObjectTypeReadState) removedRecords)
            .readBytes(getMappedOrdinal(ordinal), fieldIndex);
    }

    @Override
    public String readString(int ordinal, int fieldIndex) {
        if(!ordinalIsPresent(ordinal))
            return ((HollowObjectTypeDataAccess)dataAccess.getTypeDataAccess(getSchema().getName(), ordinal)).readString(ordinal, fieldIndex);
        return ((HollowObjectTypeReadState) removedRecords)
            .readString(getMappedOrdinal(ordinal), fieldIndex);
    }

    @Override
    public boolean isStringFieldEqual(int ordinal, int fieldIndex, String testValue) {
        if(!ordinalIsPresent(ordinal))
            return ((HollowObjectTypeDataAccess)dataAccess.getTypeDataAccess(getSchema().getName(), ordinal)).isStringFieldEqual(ordinal, fieldIndex, testValue);
        return ((HollowObjectTypeReadState) removedRecords)
            .isStringFieldEqual(getMappedOrdinal(ordinal), fieldIndex, testValue);
    }

    @Override
    public int findVarLengthFieldHashCode(int ordinal, int fieldIndex) {
        if(!ordinalIsPresent(ordinal))
            return ((HollowObjectTypeDataAccess)dataAccess.getTypeDataAccess(getSchema().getName(), ordinal)).findVarLengthFieldHashCode(ordinal, fieldIndex);
        return ((HollowObjectTypeReadState) removedRecords)
            .findVarLengthFieldHashCode(getMappedOrdinal(ordinal), fieldIndex);
    }
}
