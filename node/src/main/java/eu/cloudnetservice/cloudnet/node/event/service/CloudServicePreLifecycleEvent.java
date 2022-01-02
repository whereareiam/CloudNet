/*
 * Copyright 2019-2022 CloudNetService team & contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.cloudnetservice.cloudnet.node.event.service;

import eu.cloudnetservice.cloudnet.driver.event.Cancelable;
import eu.cloudnetservice.cloudnet.driver.service.ServiceLifeCycle;
import eu.cloudnetservice.cloudnet.node.service.CloudService;
import lombok.NonNull;

public final class CloudServicePreLifecycleEvent extends CloudServiceEvent implements Cancelable {

  private final ServiceLifeCycle targetLifecycle;
  private volatile boolean cancelled;

  public CloudServicePreLifecycleEvent(@NonNull CloudService service, @NonNull ServiceLifeCycle targetLifecycle) {
    super(service);
    this.targetLifecycle = targetLifecycle;
  }

  public @NonNull ServiceLifeCycle targetLifecycle() {
    return this.targetLifecycle;
  }

  @Override
  public boolean cancelled() {
    return this.cancelled;
  }

  @Override
  public void cancelled(boolean value) {
    this.cancelled = value;
  }
}