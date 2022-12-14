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
package org.netbeans.modules.localhistory.ui.view;

import java.io.File;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

import javax.swing.*;
import org.netbeans.modules.versioning.core.api.VCSFileProxy;
import org.netbeans.modules.versioning.ui.history.HistoryComponent;

/**
 * Top component which displays something.
 * 
 * @author Tomas Stupka
 */
public final class HistoryTopComponent extends TopComponent {

    static final String PREFERRED_ID = "text.history";
    private HistoryComponent hc;
    
    private HistoryTopComponent() {
        initComponents();
        if( "Aqua".equals( UIManager.getLookAndFeel().getID() ) ) {             // NOI18N
            setBackground(UIManager.getColor("NbExplorerView.background"));     // NOI18N
        }
        setToolTipText(NbBundle.getMessage(HistoryTopComponent.class, "HINT_LocalHistoryTopComponent")); // NOI18N
    }

    HistoryTopComponent(VCSFileProxy[] files) {
        this();
        hc = new HistoryComponent(files);
        setLayout(new java.awt.BorderLayout());
        add(hc, java.awt.BorderLayout.CENTER);
        add(hc.getToolbarRepresentation(), java.awt.BorderLayout.PAGE_START);
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_NEVER;
    }

    @Override
    public void componentOpened() {
        super.componentOpened();
        hc.componentOpened();
    }

    @Override
    public void componentClosed() {
        super.componentClosed();
        hc.componentClosed();
    }

    @Override
    protected void componentActivated() {
        super.componentActivated();
        hc.componentActivated();
    }

    @Override
    protected void componentDeactivated() {
        super.componentDeactivated();
        hc.componentDeactivated();
    }

    @Override
    protected void componentHidden() {
        super.componentHidden();
        hc.componentHidden();
    }

    @Override
    protected void componentShowing() {
        super.componentShowing();
        hc.componentShowing();
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return new HelpCtx("org.netbeans.modules.localhistory.ui.view.LHHistoryTab");
    }

}
