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
package com.netflix.hollow.api.sampling;

import com.netflix.hollow.core.read.filter.HollowFilterConfig;
import com.netflix.hollow.core.schema.HollowObjectSchema;
import java.util.Collection;
import java.util.Collections;

public class HollowObjectSampler implements HollowSampler {

  public static final HollowObjectSampler NULL_SAMPLER =
      new HollowObjectSampler(new HollowObjectSchema("", 0), DisabledSamplingDirector.INSTANCE);

  public HollowObjectSampler(HollowObjectSchema schema, HollowSamplingDirector director) {}

  public void setSamplingDirector(HollowSamplingDirector director) {}

  @Override
  public void setFieldSpecificSamplingDirector(
      HollowFilterConfig fieldSpec, HollowSamplingDirector director) {}

  public void setUpdateThread(Thread t) {}

  public void recordFieldAccess(int fieldPosition) {}

  public boolean hasSampleResults() {
    return false;
  }

  @Override
  public Collection<SampleResult> getSampleResults() {
    return Collections.emptyList();
  }

  @Override
  public void reset() {}
}
