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

package org.netbeans.modules.project.ui.groups;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Panel to configure state of an existing ad-hoc group.
 * Applicable in advanced mode.
 * @author Jesse Glick
 */
public class AdHocGroupEditPanel extends GroupEditPanel {

    private final AdHocGroup g;

    public AdHocGroupEditPanel(AdHocGroup g) {
        this.g = g;
        initComponents();
        DocumentListener l = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) {
                firePropertyChange(PROP_READY, null, null);
            }
            @Override public void removeUpdate(DocumentEvent e) {
                firePropertyChange(PROP_READY, null, null);
            }
            @Override public void changedUpdate(DocumentEvent e) {}
        };
        nameField.setText(g.getName());
        nameField.getDocument().addDocumentListener(l);
        autoSynchCheckbox.setSelected(g.isAutoSynch());
        updateSynchButton();
    }

    @Override
    public void applyChanges() {
        g.setName(nameField.getText().trim());
        g.setAutoSynch(autoSynchCheckbox.isSelected());
    }

    private void updateSynchButton() {
        synchButton.setEnabled(!autoSynchCheckbox.isSelected() && !g.isPristine());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        autoSynchCheckbox = new javax.swing.JCheckBox();
        synchButton = new javax.swing.JButton();

        nameLabel.setLabelFor(nameField);
        org.openide.awt.Mnemonics.setLocalizedText(nameLabel, org.openide.util.NbBundle.getMessage(AdHocGroupEditPanel.class, "AdHocGroupEditPanel.nameLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(autoSynchCheckbox, org.openide.util.NbBundle.getMessage(AdHocGroupEditPanel.class, "AdHocGroupEditPanel.autoSynchCheckbox.text")); // NOI18N
        autoSynchCheckbox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        autoSynchCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoSynchCheckboxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(synchButton, org.openide.util.NbBundle.getMessage(AdHocGroupEditPanel.class, "AdHocGroupEditPanel.synchButton.text")); // NOI18N
        synchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                synchButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameField, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(autoSynchCheckbox)
                            .addComponent(synchButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(autoSynchCheckbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(synchButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        nameLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(AdHocGroupEditPanel.class, "AdHocGroupEditPanel.nameLabel.AccessibleContext.accessibleDescription")); // NOI18N
        nameField.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(AdHocGroupEditPanel.class, "AdHocGroupEditPanel.nameField.AccessibleContext.accessibleName")); // NOI18N
        nameField.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(AdHocGroupEditPanel.class, "AdHocGroupEditPanel.nameField.AccessibleContext.accessibleDescription")); // NOI18N
        autoSynchCheckbox.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(AdHocGroupEditPanel.class, "AdHocGroupEditPanel.autoSynchCheckbox.AccessibleContext.accessibleDescription")); // NOI18N
        synchButton.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(AdHocGroupEditPanel.class, "AdHocGroupEditPanel.synchButton.AccessibleContext.accessibleDescription")); // NOI18N

        getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(AdHocGroupEditPanel.class, "AdHocGroupEditPanel.AccessibleContext.accessibleName")); // NOI18N
        getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(AdHocGroupEditPanel.class, "AdHocGroupEditPanel.AccessibleContext.accessibleDescription")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

    private void synchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_synchButtonActionPerformed
        g.synch();
        updateSynchButton();
    }//GEN-LAST:event_synchButtonActionPerformed

    private void autoSynchCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoSynchCheckboxActionPerformed
        updateSynchButton();
    }//GEN-LAST:event_autoSynchCheckboxActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox autoSynchCheckbox;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton synchButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public boolean isReady() {
        return doCheckExistingGroups(nameField, g);
    }
    
}
