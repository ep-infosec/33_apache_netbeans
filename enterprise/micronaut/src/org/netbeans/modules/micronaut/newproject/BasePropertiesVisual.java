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
package org.netbeans.modules.micronaut.newproject;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.MalformedURLException;
import java.net.URL;
import javax.lang.model.SourceVersion;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.AsyncGUIJob;
import org.openide.util.ImageUtilities;
import org.openide.util.RequestProcessor;

/**
 * Options panel for the Base Properties step in Micronaut project wizard.
 *
 * @author Dusan Balek
 */
public class BasePropertiesVisual extends JPanel implements DocumentListener, ActionListener, AsyncGUIJob {

    private final BasePropertiesWizardPanel panel;
    private boolean initialized = false;
    private boolean failed = false;

    public BasePropertiesVisual(BasePropertiesWizardPanel panel) {
        initComponents();
        this.panel = panel;
        this.defaultRadioButton.addActionListener(this);
        this.snapshotRadioButton.addActionListener(this);
        this.customRadioButton.addActionListener(this);
        this.customTextField.getDocument().addDocumentListener(this);
        this.groupTextField.getDocument().addDocumentListener(this);
        this.versionTextField.getDocument().addDocumentListener(this);
        this.customTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                customRadioButton.setSelected(true);
                versionTextField.setText("");
                applicationTypeComboBox.removeAllItems();
                javaVersionComboBox.removeAllItems();
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (customRadioButton.isSelected()) {
                    if (customURLValid()) {
                        refresh();
                    } else {
                        panel.fireChangeEvent();
                    }
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of
     * this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        serviceButtonGroup = new javax.swing.ButtonGroup();
        languageButtonGroup = new javax.swing.ButtonGroup();
        testFrameworkButtonGroup = new javax.swing.ButtonGroup();
        serviceURLLabel = new javax.swing.JLabel();
        defaultRadioButton = new javax.swing.JRadioButton();
        defaultURLLabel = new javax.swing.JLabel();
        snapshotRadioButton = new javax.swing.JRadioButton();
        snapshotURLLabel = new javax.swing.JLabel();
        customRadioButton = new javax.swing.JRadioButton();
        customTextField = new javax.swing.JTextField();
        versionLabel = new javax.swing.JLabel();
        versionTextField = new javax.swing.JTextField();
        groupLabel = new javax.swing.JLabel();
        groupTextField = new javax.swing.JTextField();
        artifactLabel = new javax.swing.JLabel();
        artifactTextField = new javax.swing.JTextField();
        applicationTypeLabel = new javax.swing.JLabel();
        applicationTypeComboBox = new javax.swing.JComboBox<>();
        javaVersionLabel = new javax.swing.JLabel();
        javaVersionComboBox = new javax.swing.JComboBox<>();
        languageLabel = new javax.swing.JLabel();
        javaRadioButton = new javax.swing.JRadioButton();
        kotlinRadioButton = new javax.swing.JRadioButton();
        groovyRadioButton = new javax.swing.JRadioButton();
        testFrameworkLabel = new javax.swing.JLabel();
        junitRadioButton = new javax.swing.JRadioButton();
        spockRadioButton = new javax.swing.JRadioButton();
        kotestRadioButton = new javax.swing.JRadioButton();
        executeDownloadedCodeWarning = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(serviceURLLabel, org.openide.util.NbBundle.getMessage(BasePropertiesVisual.class, "LBL_ChooseServiceURL")); // NOI18N

        serviceButtonGroup.add(defaultRadioButton);
        defaultRadioButton.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(defaultRadioButton, org.openide.util.NbBundle.getMessage(BasePropertiesVisual.class, "LBL_Default")); // NOI18N
        defaultRadioButton.setActionCommand("DEFAULT");

        defaultURLLabel.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(defaultURLLabel, MicronautLaunchService.defaultURL);

        serviceButtonGroup.add(snapshotRadioButton);
        org.openide.awt.Mnemonics.setLocalizedText(snapshotRadioButton, org.openide.util.NbBundle.getMessage(BasePropertiesVisual.class, "LBL_Snapshot")); // NOI18N
        snapshotRadioButton.setActionCommand("SNAPSHOT");

        snapshotURLLabel.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(snapshotURLLabel, MicronautLaunchService.snapshotURL);

        serviceButtonGroup.add(customRadioButton);
        org.openide.awt.Mnemonics.setLocalizedText(customRadioButton, org.openide.util.NbBundle.getMessage(BasePropertiesVisual.class, "LBL_Custom")); // NOI18N
        customRadioButton.setActionCommand("CUSTOM");

        org.openide.awt.Mnemonics.setLocalizedText(versionLabel, org.openide.util.NbBundle.getMessage(BasePropertiesVisual.class, "LBL_Version")); // NOI18N

        versionTextField.setEditable(false);
        versionTextField.setColumns(20);

        groupLabel.setLabelFor(groupTextField);
        org.openide.awt.Mnemonics.setLocalizedText(groupLabel, org.openide.util.NbBundle.getMessage(BasePropertiesVisual.class, "LBL_Group")); // NOI18N

        groupTextField.setColumns(20);

        artifactLabel.setLabelFor(artifactTextField);
        org.openide.awt.Mnemonics.setLocalizedText(artifactLabel, org.openide.util.NbBundle.getMessage(BasePropertiesVisual.class, "LBL_Artifact")); // NOI18N

        artifactTextField.setEditable(false);
        artifactTextField.setColumns(20);

        applicationTypeLabel.setLabelFor(applicationTypeComboBox);
        org.openide.awt.Mnemonics.setLocalizedText(applicationTypeLabel, org.openide.util.NbBundle.getMessage(BasePropertiesVisual.class, "LBL_ApplicationType")); // NOI18N

        javaVersionLabel.setLabelFor(javaVersionComboBox);
        org.openide.awt.Mnemonics.setLocalizedText(javaVersionLabel, org.openide.util.NbBundle.getMessage(BasePropertiesVisual.class, "LBL_JavaVersion")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(languageLabel, org.openide.util.NbBundle.getMessage(BasePropertiesVisual.class, "LBL_Language")); // NOI18N

        languageButtonGroup.add(javaRadioButton);
        javaRadioButton.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(javaRadioButton, org.openide.util.NbBundle.getMessage(BasePropertiesVisual.class, "LBL_Java")); // NOI18N
        javaRadioButton.setActionCommand("JAVA");

        languageButtonGroup.add(kotlinRadioButton);
        org.openide.awt.Mnemonics.setLocalizedText(kotlinRadioButton, org.openide.util.NbBundle.getMessage(BasePropertiesVisual.class, "LBL_Kotlin")); // NOI18N
        kotlinRadioButton.setActionCommand("KOTLIN");

        languageButtonGroup.add(groovyRadioButton);
        org.openide.awt.Mnemonics.setLocalizedText(groovyRadioButton, org.openide.util.NbBundle.getMessage(BasePropertiesVisual.class, "LBL_Groovy")); // NOI18N
        groovyRadioButton.setActionCommand("GROOVY");

        org.openide.awt.Mnemonics.setLocalizedText(testFrameworkLabel, org.openide.util.NbBundle.getMessage(BasePropertiesVisual.class, "LBL_TestFramework")); // NOI18N

        testFrameworkButtonGroup.add(junitRadioButton);
        junitRadioButton.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(junitRadioButton, org.openide.util.NbBundle.getMessage(BasePropertiesVisual.class, "LBL_JUnit")); // NOI18N
        junitRadioButton.setActionCommand("JUNIT");

        testFrameworkButtonGroup.add(spockRadioButton);
        org.openide.awt.Mnemonics.setLocalizedText(spockRadioButton, org.openide.util.NbBundle.getMessage(BasePropertiesVisual.class, "LBL_Spock")); // NOI18N
        spockRadioButton.setActionCommand("SPOCK");

        testFrameworkButtonGroup.add(kotestRadioButton);
        org.openide.awt.Mnemonics.setLocalizedText(kotestRadioButton, org.openide.util.NbBundle.getMessage(BasePropertiesVisual.class, "LBL_Kotest")); // NOI18N
        kotestRadioButton.setActionCommand("KOTEST");

        executeDownloadedCodeWarning.setBackground(new java.awt.Color(248, 184, 139));
        executeDownloadedCodeWarning.setForeground(Color.black);
        executeDownloadedCodeWarning.setIcon(ImageUtilities.loadImageIcon("org/netbeans/modules/micronaut/resources/warning.png", false));
        org.openide.awt.Mnemonics.setLocalizedText(executeDownloadedCodeWarning, org.openide.util.NbBundle.getMessage(BasePropertiesVisual.class, "MSG_executeDownloadedCodeWarning")); // NOI18N
        executeDownloadedCodeWarning.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(248, 184, 139), 5, true));
        executeDownloadedCodeWarning.setOpaque(true);
        executeDownloadedCodeWarning.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(executeDownloadedCodeWarning, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(javaVersionLabel)
                            .addComponent(groupLabel)
                            .addComponent(languageLabel)
                            .addComponent(customRadioButton)
                            .addComponent(artifactLabel)
                            .addComponent(versionLabel)
                            .addComponent(snapshotRadioButton)
                            .addComponent(defaultRadioButton)
                            .addComponent(serviceURLLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(testFrameworkLabel)
                            .addComponent(applicationTypeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(javaVersionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(javaRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(junitRadioButton))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(spockRadioButton)
                                            .addComponent(kotlinRadioButton))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(groovyRadioButton)
                                            .addComponent(kotestRadioButton))))
                                .addGap(0, 332, Short.MAX_VALUE))
                            .addComponent(defaultURLLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(versionTextField)
                            .addComponent(snapshotURLLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(customTextField)
                            .addComponent(applicationTypeComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(artifactTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(groupTextField))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(serviceURLLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(defaultRadioButton)
                    .addComponent(defaultURLLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(snapshotRadioButton)
                    .addComponent(snapshotURLLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customRadioButton)
                    .addComponent(customTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(versionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(versionLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(groupTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(groupLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(artifactTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(artifactLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(applicationTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(applicationTypeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(javaVersionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(javaVersionLabel))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(languageLabel)
                    .addComponent(javaRadioButton)
                    .addComponent(kotlinRadioButton)
                    .addComponent(groovyRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(testFrameworkLabel)
                    .addComponent(junitRadioButton)
                    .addComponent(spockRadioButton)
                    .addComponent(kotestRadioButton))
                .addGap(18, 18, 18)
                .addComponent(executeDownloadedCodeWarning, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<MicronautLaunchService.ApplicationType> applicationTypeComboBox;
    private javax.swing.JLabel applicationTypeLabel;
    private javax.swing.JLabel artifactLabel;
    private javax.swing.JTextField artifactTextField;
    private javax.swing.JRadioButton customRadioButton;
    private javax.swing.JTextField customTextField;
    private javax.swing.JRadioButton defaultRadioButton;
    private javax.swing.JLabel defaultURLLabel;
    private javax.swing.JLabel executeDownloadedCodeWarning;
    private javax.swing.JRadioButton groovyRadioButton;
    private javax.swing.JLabel groupLabel;
    private javax.swing.JTextField groupTextField;
    private javax.swing.JRadioButton javaRadioButton;
    private javax.swing.JComboBox<String> javaVersionComboBox;
    private javax.swing.JLabel javaVersionLabel;
    private javax.swing.JRadioButton junitRadioButton;
    private javax.swing.JRadioButton kotestRadioButton;
    private javax.swing.JRadioButton kotlinRadioButton;
    private javax.swing.ButtonGroup languageButtonGroup;
    private javax.swing.JLabel languageLabel;
    private javax.swing.ButtonGroup serviceButtonGroup;
    private javax.swing.JLabel serviceURLLabel;
    private javax.swing.JRadioButton snapshotRadioButton;
    private javax.swing.JLabel snapshotURLLabel;
    private javax.swing.JRadioButton spockRadioButton;
    private javax.swing.ButtonGroup testFrameworkButtonGroup;
    private javax.swing.JLabel testFrameworkLabel;
    private javax.swing.JLabel versionLabel;
    private javax.swing.JTextField versionTextField;
    // End of variables declaration//GEN-END:variables

    boolean valid(WizardDescriptor wizardDescriptor) {
        if (failed) {
            wizardDescriptor.putProperty(WizardDescriptor.PROP_ERROR_MESSAGE, "Problems in contacting service!");
            return false;
        }
        if (groupTextField.getText().isEmpty()) {
            wizardDescriptor.putProperty(WizardDescriptor.PROP_ERROR_MESSAGE, "Group can't be empty.");
            return false;
        }
        if (!SourceVersion.isName(groupTextField.getText())) {
            wizardDescriptor.putProperty(WizardDescriptor.PROP_ERROR_MESSAGE, "Not a valid Group name.");
            return false;
        }
        if (customRadioButton.isSelected()) {
            if (customTextField.getText().isEmpty()) {
                wizardDescriptor.putProperty(WizardDescriptor.PROP_ERROR_MESSAGE, "Service URL can't be empty.");
                return false;
            }
            if (!customURLValid()) {
                wizardDescriptor.putProperty(WizardDescriptor.PROP_ERROR_MESSAGE, "Not a valid Service URL.");
                return false;
            }
        }
        wizardDescriptor.putProperty(WizardDescriptor.PROP_ERROR_MESSAGE, "");
        return !versionTextField.getText().isEmpty();
    }

    void store(WizardDescriptor wd) {
        wd.putProperty(MicronautProjectWizardIterator.SERVICE_URL, getServiceUrl());
        wd.putProperty(MicronautProjectWizardIterator.MAVEN_GROUP, groupTextField.getText().trim());
        wd.putProperty(MicronautProjectWizardIterator.APPLICATION_TYPE, applicationTypeComboBox.getSelectedItem());
        wd.putProperty(MicronautProjectWizardIterator.JAVA_VERSION, javaVersionComboBox.getSelectedItem());
        wd.putProperty(MicronautProjectWizardIterator.LANGUAGE, languageButtonGroup.getSelection().getActionCommand());
        wd.putProperty(MicronautProjectWizardIterator.TEST_FRAMEWORK, testFrameworkButtonGroup.getSelection().getActionCommand());
    }

    void read(WizardDescriptor wd) {
        String serviceUrl = (String) wd.getProperty(MicronautProjectWizardIterator.SERVICE_URL);
        if (serviceUrl != null) {
            switch (serviceUrl) {
                case MicronautLaunchService.defaultURL:
                    this.defaultRadioButton.setSelected(true);
                    break;
                case MicronautLaunchService.snapshotURL:
                    this.snapshotRadioButton.setSelected(true);
                    break;
                default:
                    this.customTextField.setText(serviceUrl);
            }
        }
        String group = (String) wd.getProperty(MicronautProjectWizardIterator.MAVEN_GROUP);
        this.groupTextField.setText(group == null || group.isEmpty() ? "com.example" : group);
        this.artifactTextField.setText((String) wd.getProperty(MicronautProjectWizardIterator.MAVEN_ARTIFACT));
        javaVersionComboBox.setSelectedItem(MicronautProjectWizardIterator.JAVA_VERSION);
        applicationTypeComboBox.setSelectedItem(wd.getProperty(MicronautProjectWizardIterator.APPLICATION_TYPE));
    }

    void validate(WizardDescriptor d) throws WizardValidationException {
        // nothing to validate
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        panel.fireChangeEvent();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        panel.fireChangeEvent();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        panel.fireChangeEvent();
    }

    @Override
    public void construct() {
        try {
            panel.wizardDescriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Contacting service...");
            MicronautLaunchService service = MicronautLaunchService.getInstance();
            String serviceUrl = getServiceUrl();
            String micronautVersion = service.getMicronautVersion(serviceUrl);
            for (MicronautLaunchService.ApplicationType type : service.getApplicationTypes(serviceUrl)) {
                applicationTypeComboBox.addItem(type);
            }
            for (String version : service.getJdkVersions(serviceUrl)) {
                javaVersionComboBox.addItem(version);
            }
            versionTextField.setText(micronautVersion);
            initialized = true;
        } catch (Exception ex) {
            panel.wizardDescriptor.putProperty(WizardDescriptor.PROP_ERROR_MESSAGE, "Could not query Micronaut Launch service");
            failed = true;
            panel.fireChangeEvent();
        }
    }

    @Override
    public void finished() {
        if (initialized) {
            panel.fireChangeEvent();
            groupTextField.requestFocus();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        versionTextField.setText("");
        applicationTypeComboBox.removeAllItems();
        javaVersionComboBox.removeAllItems();
        if ("CUSTOM".equals(e.getActionCommand())) {
            customTextField.requestFocus();
        } else {
            refresh();
        }
    }

    private void refresh() {
        initialized = false;
        failed = false;
        RequestProcessor.getDefault().post(() -> {
            construct();
            SwingUtilities.invokeLater(() -> {
                finished();
            });
        });
    }

    private String getServiceUrl() {
        switch (serviceButtonGroup.getSelection().getActionCommand()) {
            case "DEFAULT":
                return MicronautLaunchService.defaultURL;
            case "SNAPSHOT":
                return MicronautLaunchService.snapshotURL;
            default:
                return customTextField.getText();
        }
    }

    private boolean customURLValid() {
        try {
            new URL(customTextField.getText());
        } catch (MalformedURLException ex) {
            return false;
        }
        return true;
    }
}