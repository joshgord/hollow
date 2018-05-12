package com.netflix.hollow.api.sampling;

import com.netflix.hollow.core.read.filter.HollowFilterConfig;
import java.util.Collection;
import java.util.Collections;

public class DisabledSampler implements HollowSampler {

  private static final HollowSampler INSTANCE = new DisabledSampler();

  public static HollowSampler getInstance() {
    return INSTANCE;
  }

  private DisabledSampler() {

  }

  @Override
  public void setSamplingDirector(HollowSamplingDirector director) {

  }

  @Override
  public void setFieldSpecificSamplingDirector(HollowFilterConfig fieldSpec,
      HollowSamplingDirector director) {

  }

  @Override
  public void setUpdateThread(Thread t) {

  }

  @Override
  public boolean hasSampleResults() {
    return false;
  }

  @Override
  public Collection<SampleResult> getSampleResults() {
    return Collections.emptyList();
  }

  @Override
  public void reset() {

  }
}
