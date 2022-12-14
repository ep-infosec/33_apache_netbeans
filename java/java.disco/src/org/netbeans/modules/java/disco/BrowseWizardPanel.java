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
package org.netbeans.modules.java.disco;

import org.openide.WizardDescriptor;

public class BrowseWizardPanel extends AbstractWizardPanel<BrowsePanel> {

    private final WizardState state;

    BrowseWizardPanel(WizardState state) {
        this.state = state;
    }

    @Override
    protected BrowsePanel createComponent() {
        return new BrowsePanel(this, state);
    }

    @Override
    public boolean isValid() {
        return getComponent().isOK();
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(DiscoPlatformIt.PROP_DOWNLOAD_FOLDER, getComponent().getUserDownloadFolder());
    }
}
