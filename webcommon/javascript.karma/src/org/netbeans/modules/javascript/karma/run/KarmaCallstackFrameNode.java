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

package org.netbeans.modules.javascript.karma.run;

import javax.swing.Action;
import org.netbeans.modules.gsf.testrunner.ui.api.CallstackFrameNode;

public class KarmaCallstackFrameNode extends CallstackFrameNode {

    private final JumpToCallStackAction.Callback callback;


    public KarmaCallstackFrameNode(String frameInfo, String displayName, JumpToCallStackAction.Callback callback) {
        super(frameInfo, displayName);
        this.callback = callback;
    }

    @Override
    public Action getPreferredAction() {
        return new JumpToCallStackAction(frameInfo, callback);
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[] {new JumpToCallStackAction(frameInfo, callback)};
    }

}
