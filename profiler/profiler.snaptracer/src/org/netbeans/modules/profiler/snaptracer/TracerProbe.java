/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.netbeans.modules.profiler.snaptracer;

import org.netbeans.modules.profiler.snaptracer.impl.IdeSnapshot;


/**
 * Tracer probe represents one monitored unit in the Timeline chart. The probe
 * may provide one or several values displayed in the chart, for example several
 * numeric values of an MBean.
 *
 * @author Jiri Sedlacek
 */
public abstract class TracerProbe {

    private final ProbeItemDescriptor[] itemDescriptors;


    /**
     * Creates new instance of TracerProbe.
     *
     * @param itemDescriptors descriptors of UI appearance of items provided by the probe
     */
    public TracerProbe(ProbeItemDescriptor[] itemDescriptors) {
        if (itemDescriptors == null || itemDescriptors.length == 0)
            throw new IllegalArgumentException("Invalid ProbeItemDescriptors"); // NOI18N
        this.itemDescriptors = itemDescriptors;
    }


    /**
     * Returns descriptors of UI appearance of items provided by the probe.
     *
     * @return descriptors of UI appearance of items provided by the probe
     */
    public final ProbeItemDescriptor[] getItemDescriptors() { return itemDescriptors; };


    /**
     * Returns number of items provided by the probe.
     *
     * @return number of items provided by the probe
     */
    public int getItemsCount() { return itemDescriptors.length; }

    /**
     * Returns values of items provided by the probe at the defined time. It's up
     * to the probe implementation whether the values will be read in this method
     * or retrieved from a private data model prefetching the values asynchronously.
     * The timestamp is the same for a single data-read event for all selected
     * probes of the actual Tracer session.
     * <br><br>
     * <b>Note: current Tracer version doesn't support negative values. Negative
     * values returned by the probes will be treated as '0' and the user will be
     * notified by a warning dialog.</b>
     *
     * @param timestamp timestamp of the data-read event
     * @return values of items provided by the probe
     */
    public abstract long[] getItemValues(int sampleIndex);


    /**
     * Optionally returns ProbeStateHandler instance which obtains notifications
     * about the Tracer session status. Default implementation returns null.
     * You may use StateAware subclass instead of implementing this method to
     * obtain the notifications.
     *
     * @return ProbeStateHandler instance which obtains status notifications,
     * default implementation returns null
     */
    public ProbeStateHandler getStateHandler() { return null; }


    /**
     * An abstract adapter class for receiving Tracer session state notifications.
     * See ProbeStateHandler for details.
     */
    public abstract static class SessionAware extends TracerProbe {

        private ProbeStateHandler stateHandler;

        /**
         * Creates new instance of TracerProbe.SessionAware.
         *
         * @param itemDescriptors descriptors of UI appearance of items provided by the probe
         */
        public SessionAware(ProbeItemDescriptor[] itemDescriptors) {
            super(itemDescriptors);
        }


        /**
         * Returns a predefined ProbeStateHandler which obtains notifications
         * about the Tracer session state.
         *
         * @return predefined ProbeStateHandler which obtains status notifications
         */
        public final synchronized ProbeStateHandler getStateHandler() {
            if (stateHandler == null) stateHandler = new ProbeStateHandler() {
                public void probeAdded(IdeSnapshot snapshot) {
                    SessionAware.this.probeAdded(snapshot);
                }
                public void probeRemoved(IdeSnapshot snapshot) {
                    SessionAware.this.probeRemoved(snapshot);
                }
                public TracerProgressObject sessionInitializing(IdeSnapshot snapshot, int refresh) {
                    return SessionAware.this.sessionInitializing(snapshot, refresh);
                }
                public void sessionStarting(IdeSnapshot snapshot)
                        throws SessionInitializationException {
                    SessionAware.this.sessionStarting(snapshot);
                }
                public void sessionRunning(IdeSnapshot snapshot) {
                    SessionAware.this.sessionRunning(snapshot);
                }
                public void sessionStopping(IdeSnapshot snapshot) {
                    SessionAware.this.sessionStopping(snapshot);
                }
                public void sessionFinished(IdeSnapshot snapshot) {
                    SessionAware.this.sessionFinished(snapshot);
                }
                public void refreshRateChanged(IdeSnapshot snapshot, int refresh) {
                    SessionAware.this.refreshRateChanged(snapshot, refresh);
                }
            };
            return stateHandler;
        }


        /**
         * Invoked when the is added into the Timeline view.
         *
         * @param snapshot profiler snapshot
         */
        protected void probeAdded(IdeSnapshot snapshot) {}

        /**
         * Invoked when the probe is removed from the Timeline view.
         *
         * @param snapshot profiler snapshot
         */
        protected void probeRemoved(IdeSnapshot snapshot) {}


        /**
         * Invoked when setting up a new Tracer session. This method allows a
         * Probe to notify the user about initialization progress. The actual
         * initialization (and updating the TracerProgressObject) should be
         * performed in the sessionStarting() method. Useful for example for
         * messaging a delay during instrumention of classes in target application.
         *
         * @param snapshot profiler snapshot
         * @param refresh session refresh rate in miliseconds
         * @return TracerProgressObject to track initialization progress
         */
        protected TracerProgressObject sessionInitializing(IdeSnapshot snapshot, int refresh) {
            return null;
        }

        /**
         * Invoked when starting a new Tracer session. Any probe initialization
         * should be performed in this method. If provided by the
         * sessionInitializing method, a TracerProgressObject should be updated to
         * reflect the initialization progress. This method may throw a
         * SessionInitializationException in case of initialization failure. Any
         * packages/probes initialized so far will be correctly finished, however the
         * probe throwing the SessionInitializationException is responsible for
         * cleaning up any used resources and restoring its state without any
         * following events.
         *
         * @param snapshot profiler snapshot
         * @throws SessionInitializationException in case of initialization failure
         */
        protected void sessionStarting(IdeSnapshot snapshot)
                throws SessionInitializationException {}

        /**
         * Invoked when all packages/probes have been started and the Tracer session
         * is running and collecting data.
         *
         * @param snapshot profiler snapshot
         */
        protected void sessionRunning(IdeSnapshot snapshot) {}

        /**
         * Invoked when stopping the Tracer session. Any probe cleanup should be
         * performed in this method. Any long-running cleanup code should preferably
         * be invoked in a separate worker thread to allow the Tracer session to
         * finish as fast as possible. Be sure to check/wait for the cleanup thread
         * when starting a new Tracer session in sessionStarting().
         *
         * @param snapshot profiler snapshot
         */
        protected void sessionStopping(IdeSnapshot snapshot) {}

        /**
         * Invoked when the Tracer session has finished.
         *
         * @param snapshot profiler snapshot
         */
        protected void sessionFinished(IdeSnapshot snapshot) {}

        /**
         * Invoked when refresh rate of the Tracer session has been changed.
         *
         * @param snapshot profiler snapshot
         * @param refresh session refresh rate in miliseconds
         */
        protected void refreshRateChanged(IdeSnapshot snapshot, int refresh) {}

    }

}
