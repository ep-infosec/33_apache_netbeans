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

package org.netbeans.modules.php.symfony2.ui.customizer;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.modules.php.api.util.UiUtils;
import org.netbeans.modules.php.symfony2.ui.options.SymfonyOptionsPanelController;
import org.openide.awt.Mnemonics;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.ChangeSupport;
import org.openide.util.NbBundle;

public class SymfonyCustomizerPanel extends JPanel {

    private static final long serialVersionUID = -464365465761315L;

    private final FileObject sources;
    private final ChangeSupport changeSupport = new ChangeSupport(this);


    public SymfonyCustomizerPanel(FileObject sources) {
        this.sources = sources;

        initComponents();
        init();
        setFieldsEnabled(enabledCheckBox.isSelected());
    }

    private void init() {
        enabledCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                fireChange();
                setFieldsEnabled(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        appDirTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                processChange();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                processChange();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                processChange();
            }
            private void processChange() {
                fireChange();
            }
        });
    }

    public boolean isSupportEnabled() {
        return enabledCheckBox.isSelected();
    }

    public void setSupportEnabled(boolean enabled) {
        enabledCheckBox.setSelected(enabled);
    }

    public String getAppDirectory() {
        return appDirTextField.getText().replace(File.separatorChar, '/'); // NOI18N
    }

    public void setAppDirectory(String appDir) {
        appDirTextField.setText(appDir.replace('/', File.separatorChar)); // NOI18N
    }

    public boolean isIgnoreCacheDirectory() {
        return ignoreCacheDirectoryCheckBox.isSelected();
    }

    public void setIgnoreCacheDirectory(boolean ignore) {
        ignoreCacheDirectoryCheckBox.setSelected(ignore);
    }

    public void addChangeListener(ChangeListener changeListener) {
        changeSupport.addChangeListener(changeListener);
    }

    public void removeChangeListener(ChangeListener changeListener) {
        changeSupport.removeChangeListener(changeListener);
    }

    final void setFieldsEnabled(boolean enabled) {
        appDirTextField.setEnabled(enabled);
        appDirBrowseButton.setEnabled(enabled);
        ignoreCacheDirectoryCheckBox.setEnabled(enabled);
    }

    void fireChange() {
        changeSupport.fireChange();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        infoLabel = new JLabel();
        optionsLabel = new JLabel();
        enabledCheckBox = new JCheckBox();
        enabledInfoLabel = new JLabel();
        appDirLabel = new JLabel();
        appDirTextField = new JTextField();
        appDirBrowseButton = new JButton();
        ignoreCacheDirectoryCheckBox = new JCheckBox();

        Mnemonics.setLocalizedText(infoLabel, NbBundle.getMessage(SymfonyCustomizerPanel.class, "SymfonyCustomizerPanel.infoLabel.text")); // NOI18N

        Mnemonics.setLocalizedText(optionsLabel, NbBundle.getMessage(SymfonyCustomizerPanel.class, "SymfonyCustomizerPanel.optionsLabel.text")); // NOI18N
        optionsLabel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                optionsLabelMouseEntered(evt);
            }
            public void mousePressed(MouseEvent evt) {
                optionsLabelMousePressed(evt);
            }
        });

        Mnemonics.setLocalizedText(enabledCheckBox, NbBundle.getMessage(SymfonyCustomizerPanel.class, "SymfonyCustomizerPanel.enabledCheckBox.text")); // NOI18N

        Mnemonics.setLocalizedText(enabledInfoLabel, NbBundle.getMessage(SymfonyCustomizerPanel.class, "SymfonyCustomizerPanel.enabledInfoLabel.text")); // NOI18N

        Mnemonics.setLocalizedText(appDirLabel, NbBundle.getMessage(SymfonyCustomizerPanel.class, "SymfonyCustomizerPanel.appDirLabel.text")); // NOI18N

        Mnemonics.setLocalizedText(appDirBrowseButton, NbBundle.getMessage(SymfonyCustomizerPanel.class, "SymfonyCustomizerPanel.appDirBrowseButton.text")); // NOI18N
        appDirBrowseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                appDirBrowseButtonActionPerformed(evt);
            }
        });

        ignoreCacheDirectoryCheckBox.setSelected(true);
        Mnemonics.setLocalizedText(ignoreCacheDirectoryCheckBox, NbBundle.getMessage(SymfonyCustomizerPanel.class, "SymfonyCustomizerPanel.ignoreCacheDirectoryCheckBox.text")); // NOI18N

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(appDirLabel)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(appDirTextField)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(appDirBrowseButton))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                    .addComponent(ignoreCacheDirectoryCheckBox)
                    .addComponent(enabledCheckBox))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                    .addComponent(enabledInfoLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(infoLabel)
                        .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(optionsLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
            .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(enabledCheckBox)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(enabledInfoLabel)
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(appDirLabel)
                    .addComponent(appDirTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(appDirBrowseButton))
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(ignoreCacheDirectoryCheckBox)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(infoLabel)
                    .addComponent(optionsLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void optionsLabelMouseEntered(MouseEvent evt) {//GEN-FIRST:event_optionsLabelMouseEntered
        evt.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_optionsLabelMouseEntered

    private void optionsLabelMousePressed(MouseEvent evt) {//GEN-FIRST:event_optionsLabelMousePressed
        UiUtils.showOptions(SymfonyOptionsPanelController.OPTIONS_SUBPATH);
    }//GEN-LAST:event_optionsLabelMousePressed

    @NbBundle.Messages("SymfonyCustomizerPanel.browseAppDir.title=Select \"app\" directory")
    private void appDirBrowseButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_appDirBrowseButtonActionPerformed
        File appDir = new FileChooserBuilder(SymfonyCustomizerPanel.class)
                .setTitle(Bundle.SymfonyCustomizerPanel_browseAppDir_title())
                .setDirectoriesOnly(true)
                .setDefaultWorkingDirectory(FileUtil.toFile(sources))
                .forceUseOfDefaultWorkingDirectory(true)
                .showOpenDialog();
        if (appDir != null) {
            appDir = FileUtil.normalizeFile(appDir);
            FileObject fo = FileUtil.toFileObject(appDir);
            if (FileUtil.isParentOf(sources, fo)) {
                // it is ok
                String relativePath = FileUtil.getRelativePath(sources, fo);
                assert relativePath != null : sources + " not parent of " + fo;
                setAppDirectory(relativePath);
            } else {
                // not ok
                appDirTextField.setText(appDir.getAbsolutePath());
            }
        }
    }//GEN-LAST:event_appDirBrowseButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton appDirBrowseButton;
    private JLabel appDirLabel;
    private JTextField appDirTextField;
    private JCheckBox enabledCheckBox;
    private JLabel enabledInfoLabel;
    private JCheckBox ignoreCacheDirectoryCheckBox;
    private JLabel infoLabel;
    private JLabel optionsLabel;
    // End of variables declaration//GEN-END:variables

}
