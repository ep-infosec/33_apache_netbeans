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

package org.netbeans.modules.tomcat5.customizer;
import java.awt.Font;
import java.io.File;
import javax.accessibility.AccessibleContext;
import javax.swing.JFileChooser;
import javax.swing.JSpinner;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

/**
 * Customizer Startup tab.
 *
 * @author  sherold
 */
public class CustomizerStartup extends javax.swing.JPanel {

    private CustomizerDataSupport custData;

    private final File catalinaHome;
    
    /** Creates new form CustomizerStartup */
    public CustomizerStartup(CustomizerDataSupport custData, File catalinaHome) {
        this.custData = custData;
        this.catalinaHome = catalinaHome;
        
        initComponents();
        if (Utilities.isWindows()) {
            // force shutdown not supported on Windows
            jCheckBox4.setEnabled(false);
        } else {
            // shared memory debugging transport supported only on Windows
            jRadioButton1.setEnabled(false);
            jTextField4.setEnabled(false);
        }
        
        updateCustomScriptComponents();
        
        JTextField jSpinner1TextField = ((JSpinner.NumberEditor)jSpinner1.getEditor()).getTextField();        
        
        AccessibleContext ac = jSpinner1TextField.getAccessibleContext();
        ac.setAccessibleName(NbBundle.getMessage(CustomizerStartup.class, "ACSN_SocketPortNum"));
        ac.setAccessibleDescription(NbBundle.getMessage(CustomizerStartup.class, "ACSD_SocketPortNum"));
        
        // work-around for jspinner incorrect fonts
        Font font = jTextField1.getFont();
        jSpinner1TextField.setFont(font);
        
        /*
         * mnemonics generated in the guarded block do not work
         * because of change of the model after mnemonic setting
         * remove the workaround after completion of issue 111094
         */
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox1, org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "TXT_CustomScript")); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox4, org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "TXT_ForceShutdown")); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton1, org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "TXT_SharedMemName")); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton2, org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "TXT_SocketPort")); // NOI18N
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jCheckBox4 = new javax.swing.JCheckBox();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jTextField4 = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        timeoutsLabel = new javax.swing.JLabel();
        startupTimeoutLabel = new javax.swing.JLabel();
        startupTimeoutSpinner = new javax.swing.JSpinner();
        shutdownTimeoutLabel = new javax.swing.JLabel();
        shutdownTimeoutSpinner = new javax.swing.JSpinner();

        setLayout(new java.awt.GridBagLayout());

        jLabel1.setLabelFor(jTextField1);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "TXT_StartupScript")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 17, 0, 0);
        add(jLabel1, gridBagConstraints);
        jLabel1.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "ASCN_CatalinaScript")); // NOI18N
        jLabel1.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "ASCD_CatalinaScript")); // NOI18N

        jTextField1.setColumns(20);
        jTextField1.setDocument(custData.getScriptPathModel());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(jTextField1, gridBagConstraints);
        jTextField1.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "ASCN_Script")); // NOI18N
        jTextField1.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "ASCD_Script")); // NOI18N

        jCheckBox1.setModel(custData.getCustomScriptModel());
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox1, org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "TXT_CustomScript")); // NOI18N
        jCheckBox1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox1StateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 0);
        add(jCheckBox1, gridBagConstraints);
        jCheckBox1.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "ASCN_CustomScript")); // NOI18N
        jCheckBox1.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "ASCD_CustomScript")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "TXT_DebugTransport")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 12, 0, 0);
        add(jLabel4, gridBagConstraints);
        jLabel4.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "ACSN_DebugTrans")); // NOI18N
        jLabel4.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "ACSN_DebugTrans")); // NOI18N

        jCheckBox4.setModel(custData.getForceStopModel());
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox4, org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "TXT_ForceShutdown")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 0);
        add(jCheckBox4, gridBagConstraints);
        jCheckBox4.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "ASCN_ForceStop")); // NOI18N
        jCheckBox4.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "ASCD_ForceStop")); // NOI18N

        jRadioButton1.setModel(custData.getSharedMemModel());
        buttonGroup1.add(jRadioButton1);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton1, org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "TXT_SharedMemName")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 0);
        add(jRadioButton1, gridBagConstraints);
        jRadioButton1.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "ACSN_SharedMem")); // NOI18N
        jRadioButton1.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "ACSD_SharedMem")); // NOI18N

        jRadioButton2.setModel(custData.getSocketModel());
        buttonGroup1.add(jRadioButton2);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton2, org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "TXT_SocketPort")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 0);
        add(jRadioButton2, gridBagConstraints);
        jRadioButton2.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "ACSN_SocketPort")); // NOI18N
        jRadioButton2.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "ACSD_SocektPort")); // NOI18N

        jTextField4.setColumns(15);
        jTextField4.setDocument(custData.getSharedMemNameModel());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(jTextField4, gridBagConstraints);
        jTextField4.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "ACSD_SharedMemName")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(browseButton, org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "TXT_BrowseCatalinaScript")); // NOI18N
        browseButton.setEnabled(false);
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 12);
        add(browseButton, gridBagConstraints);
        browseButton.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "ASCN_Browse")); // NOI18N
        browseButton.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "ASCD_Browse")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "TXT_NoteChangesTakeAffect")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 12, 0);
        add(jLabel2, gridBagConstraints);
        jLabel2.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "ASCN_Note")); // NOI18N
        jLabel2.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "ASCD_Note")); // NOI18N

        jSpinner1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jSpinner1.setModel(custData.getDebugPortModel());
        jSpinner1.setEditor(new JSpinner.NumberEditor(jSpinner1, "#"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(jSpinner1, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(timeoutsLabel, org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "CustomizerStartup.timeoutsLabel.txt")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 12, 0, 0);
        add(timeoutsLabel, gridBagConstraints);

        startupTimeoutLabel.setLabelFor(startupTimeoutSpinner);
        org.openide.awt.Mnemonics.setLocalizedText(startupTimeoutLabel, org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "CustomizerStartup.startupTimeout.label")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 34, 0, 0);
        add(startupTimeoutLabel, gridBagConstraints);

        startupTimeoutSpinner.setModel(custData.getStartupTimeoutModel());
        startupTimeoutSpinner.setEditor(new NumberEditor(startupTimeoutSpinner, "#"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(startupTimeoutSpinner, gridBagConstraints);

        shutdownTimeoutLabel.setLabelFor(shutdownTimeoutSpinner);
        org.openide.awt.Mnemonics.setLocalizedText(shutdownTimeoutLabel, org.openide.util.NbBundle.getMessage(CustomizerStartup.class, "CustomizerStartup.shutdownTimeout.label")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 34, 0, 0);
        add(shutdownTimeoutLabel, gridBagConstraints);

        shutdownTimeoutSpinner.setModel(custData.getShutdownTimeoutModel());
        shutdownTimeoutSpinner.setEditor(new NumberEditor(shutdownTimeoutSpinner, "#"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(shutdownTimeoutSpinner, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        JFileChooser chooser;
        File script = new File(jTextField1.getText().trim());
        File currentFolder = script.isDirectory() ? script
                                                  : script.getParentFile();

        if (currentFolder != null && currentFolder.exists()) {
            chooser = new JFileChooser(currentFolder);
        } else if (catalinaHome != null && catalinaHome.exists()) {
            chooser = new JFileChooser(catalinaHome);
        } else {
            chooser = new JFileChooser();
        }
        
        int returnVal = chooser.showOpenDialog(SwingUtilities.getWindowAncestor(this));
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            jTextField1.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_browseButtonActionPerformed

    private void jCheckBox1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox1StateChanged
        updateCustomScriptComponents();
    }//GEN-LAST:event_jCheckBox1StateChanged
    
    private void updateCustomScriptComponents() {
        boolean enabled = jCheckBox1.isSelected();
        jLabel1.setEnabled(enabled);
        jTextField1.setEnabled(enabled);
        browseButton.setEnabled(enabled);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JLabel shutdownTimeoutLabel;
    private javax.swing.JSpinner shutdownTimeoutSpinner;
    private javax.swing.JLabel startupTimeoutLabel;
    private javax.swing.JSpinner startupTimeoutSpinner;
    private javax.swing.JLabel timeoutsLabel;
    // End of variables declaration//GEN-END:variables
    
}
