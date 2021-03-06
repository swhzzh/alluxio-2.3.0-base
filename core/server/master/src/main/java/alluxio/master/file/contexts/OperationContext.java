/*
 * The Alluxio Open Foundation licenses this work under the Apache License, version 2.0
 * (the "License"). You may not use this work except in compliance with the License, which is
 * available at www.apache.org/licenses/LICENSE-2.0
 *
 * This software is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied, as more fully set forth in the License.
 *
 * See the NOTICE file distributed with this work for information regarding copyright ownership.
 */

package alluxio.master.file.contexts;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * Used as a base class for wrapping context around proto messages.
 *
 * @param <T> Proto message type
 */
@NotThreadSafe
public class OperationContext<T extends com.google.protobuf.GeneratedMessageV3.Builder<?>> {
  // Proto message that is being wrapped
  private T mOptionsBuilder;
  // Used to track client call status.
  private CallTracker mCallTracker;

  /**
   * Creates an instance with given proto message.
   *
   * @param optionsBuilder Internal proto message builder instance
   */
  public OperationContext(T optionsBuilder) {
    this(optionsBuilder, null);
    mOptionsBuilder = optionsBuilder;
    mCallTracker = CallTracker.NOOP_TRACKER;
  }

  /**
   * Creates an instance with given proto message.
   *
   * @param optionsBuilder Internal proto message builder instance
   * @param callTracker client call tracker, or {@code null} if no tracking is desired
   */
  public OperationContext(T optionsBuilder, CallTracker callTracker) {
    mOptionsBuilder = optionsBuilder;
    mCallTracker = callTracker;
  }

  /**
   * @return underlying proto message instance
   */
  public T getOptions() {
    return mOptionsBuilder;
  }

  /**
   * TODO(ggezer): Make the call-tracker infra note the source of cancellation.
   * @return {@code true} if the call is cancelled by the client
   */
  public boolean isCancelled() {
    return mCallTracker.isCancelled();
  }
}
