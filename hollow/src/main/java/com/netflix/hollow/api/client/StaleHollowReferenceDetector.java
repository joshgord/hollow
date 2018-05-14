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
package com.netflix.hollow.api.client;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.api.custom.HollowAPI;

/**
 * Detect stale Hollow references and USAGE of stale hollow references.
 *
 * <p>When obtaining a reference to a hollow object, this reference is not intended to be held on to
 * indefinitely. This class detects whether references are held and/or used beyond some expected
 * lifetime.
 *
 * <p>If objects are detected as held beyond some grace period but not used beyond that period, then
 * they will be detached, so they do not hang on to the entire historical data store beyond some
 * length of time.
 *
 * <p>This class is also responsible for notifying the HollowUpdateListener if stale references or
 * usage is detected.
 */
public class StaleHollowReferenceDetector {

  public StaleHollowReferenceDetector(
      HollowConsumer.ObjectLongevityConfig config,
      HollowConsumer.ObjectLongevityDetector detector) {}

  static boolean isKnownAPIHandle(HollowAPI api) {
    return true;
  }

  void newAPIHandle(HollowAPI api) {}

  public void startMonitoring() {}
}
