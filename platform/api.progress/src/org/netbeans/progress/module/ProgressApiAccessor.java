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
package org.netbeans.progress.module;

import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.modules.progress.spi.InternalHandle;
import org.openide.util.Exceptions;

/**
 *
 * @author sdedic
 */
public abstract class ProgressApiAccessor {
    private static volatile ProgressApiAccessor INSTANCE;

    public static void setInstance(ProgressApiAccessor inst) {
        assert inst != null;
        INSTANCE = inst;
    }

    public static synchronized ProgressApiAccessor getInstance() {
        if (INSTANCE == null) {
            try {
                Class.forName(ProgressHandle.class.getName(), true, ProgressApiAccessor.class.getClassLoader());
                assert INSTANCE != null;
            } catch (ClassNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return INSTANCE;
    }

    public abstract InternalHandle getInternalHandle(ProgressHandle h);

    public abstract ProgressHandle create(InternalHandle h);
}
