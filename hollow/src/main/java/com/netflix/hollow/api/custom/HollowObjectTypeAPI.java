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
package com.netflix.hollow.api.custom;

import com.netflix.hollow.api.sampling.DisabledSamplingDirector;
import com.netflix.hollow.api.sampling.HollowObjectSampler;
import com.netflix.hollow.api.sampling.HollowSampler;
import com.netflix.hollow.api.sampling.HollowSamplingDirector;
import com.netflix.hollow.core.read.dataaccess.HollowDataAccess;
import com.netflix.hollow.core.read.dataaccess.HollowObjectTypeDataAccess;
import com.netflix.hollow.core.read.dataaccess.missing.HollowObjectMissingDataAccess;
import com.netflix.hollow.core.read.filter.HollowFilterConfig;
import com.netflix.hollow.core.read.missing.MissingDataHandler;
import com.netflix.hollow.core.schema.HollowObjectSchema;
import java.util.Arrays;

/**
 * This is the Hollow Type API interface for OBJECT type records.
 * <p>
 * In a Generated Hollow API, this will be extended for each OBJECT type with specific methods to retrieve each field.
 * 
 * @see HollowTypeAPI
 */
public abstract class HollowObjectTypeAPI extends HollowTypeAPI {

    protected final String fieldNames[];
    protected final int fieldIndex[];

    protected final HollowObjectSampler boxedFieldAccessSampler = new HollowObjectSampler(null, DisabledSamplingDirector.INSTANCE);

    protected HollowObjectTypeAPI(HollowAPI api, HollowObjectTypeDataAccess typeDataAccess, String fieldNames[]) {
        super(api, typeDataAccess);
        this.fieldNames = fieldNames;
        this.fieldIndex = new int[fieldNames.length];

        if(!(typeDataAccess instanceof HollowObjectMissingDataAccess)) {
            HollowObjectSchema schema = typeDataAccess.getSchema();
            for(int i=0;i<fieldNames.length;i++) {
                int fieldPosition = schema.getPosition(fieldNames[i]);
                fieldIndex[i] = fieldPosition;
            }
        } else {
            Arrays.fill(fieldIndex, -1);
        }
    }

    @Override
    public HollowObjectTypeDataAccess getTypeDataAccess() {
        return (HollowObjectTypeDataAccess) typeDataAccess;
    }

    public HollowDataAccess getDataAccess() {
        return typeDataAccess.getDataAccess();
    }

    public HollowSampler getBoxedFieldAccessSampler() {
        return boxedFieldAccessSampler;
    }

    @Override
    public void setSamplingDirector(HollowSamplingDirector samplingDirector) {

    }
    
    @Override
    public void setFieldSpecificSamplingDirector(HollowFilterConfig fieldSpec, HollowSamplingDirector director) {

    }
    
    @Override
    public void ignoreUpdateThreadForSampling(Thread t) {

    }

    protected MissingDataHandler missingDataHandler() {
        return api.getDataAccess().getMissingDataHandler();
    }

}
