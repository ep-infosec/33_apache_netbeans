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
package org.netbeans.modules.java.project.ui;

import static java.awt.Component.CENTER_ALIGNMENT;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.StringTokenizer;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import org.netbeans.api.java.source.*;
import org.netbeans.api.java.source.ui.TypeElementFinder;
import org.netbeans.spi.project.ui.templates.support.Templates;
import org.openide.WizardDescriptor;
import org.openide.awt.Mnemonics;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

/**
 * @author Arthur Sadykov
 */
public class ExtensionAndImplementationVisualPanel extends JPanel implements DocumentListener {

    private static final String SUPERCLASS = "superclass"; // NOI18N
    private static final String INTERFACES = "interfaces"; // NOI18N
    private static final String NEW_LINE = "\n"; // NOI18N
    private static final String EMPTY_STRING = ""; // NOI18N
    private static final String EXCEPTION_TEMPLATE_NAME = "Exception"; // NOI18N
    private static final String INTERFACE_TEMPLATE_NAME = "Interface"; // NOI18N
    private final ExtensionAndImplementationWizardPanel wizardPanel;
    private JTextField superclassTextField;
    private JTextArea interfacesTextArea;

    private ExtensionAndImplementationVisualPanel(ExtensionAndImplementationWizardPanel wizardPanel) {
        initComponents();
        this.wizardPanel = wizardPanel;
    }

    public static ExtensionAndImplementationVisualPanelBuilder builder(ExtensionAndImplementationWizardPanel wizardPanel) {
        return new ExtensionAndImplementationVisualPanelBuilder(wizardPanel);
    }

    private void setSuperclassTextField(JTextField superclassTextField) {
        this.superclassTextField = superclassTextField;
    }

    private void setInterfacesTextArea(JTextArea interfacesTextArea) {
        this.interfacesTextArea = interfacesTextArea;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
    }// </editor-fold>//GEN-END:initComponents

    @NbBundle.Messages({
        "INFO_JavaTargetChooser_ProvideValidSuperclass=Provide a valid superclass.",
        "INFO_JavaTargetChooser_ProvideValidInterfaces=Provide valid interfaces."
    })
    boolean isValid(WizardDescriptor wizardDescriptor) {
        if (wizardDescriptor == null) {
            return false;
        }
        if (superclassTextField != null) {
            String superclass = superclassTextField.getText();
            if (!superclass.isEmpty() && !isValidSuperclassString(superclass)) {
                wizardDescriptor.putProperty(WizardDescriptor.PROP_ERROR_MESSAGE,
                        NbBundle.getMessage(ExtensionAndImplementationVisualPanel.class,
                                "INFO_JavaTargetChooser_ProvideValidSuperclass")); // NOI18N
                return false;
            }
        }
        if (interfacesTextArea != null) {
            String interfaces = interfacesTextArea.getText();
            if (!interfaces.isEmpty() && !isValidInterfacesString(interfaces)) {
                wizardDescriptor.putProperty(WizardDescriptor.PROP_ERROR_MESSAGE,
                        NbBundle.getMessage(ExtensionAndImplementationVisualPanel.class,
                                "INFO_JavaTargetChooser_ProvideValidInterfaces")); // NOI18N
                return false;
            }
        }
        return true;
    }

    private boolean isValidSuperclassString(String superclass) {
        if (!superclass.isEmpty()) {
            if (superclass.charAt(0) == '.' || superclass.charAt(superclass.length() - 1) == '.') {
                return false;
            }
        }
        return hasValidTokens(superclass);
    }

    private boolean hasValidTokens(String fqn) {
        StringTokenizer tokenizer = new StringTokenizer(fqn, "."); // NOI18N
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (token.isEmpty()) {
                return false;
            }
            if (!Utilities.isJavaIdentifier(token)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidInterfacesString(String interfaces) {
        if (!interfaces.isEmpty()) {
            if (interfaces.charAt(0) == '.' || interfaces.charAt(interfaces.length() - 1) == '.') {
                return false;
            }
        }
        if (interfacesTextArea != null) {
            for (int i = 0; i < interfacesTextArea.getLineCount(); i++) {
                try {
                    int lineStartOffset = interfacesTextArea.getLineStartOffset(i);
                    int lineEndOffset = interfacesTextArea.getLineEndOffset(i);
                    if (lineEndOffset > lineStartOffset) {
                        String implementationClass =
                                interfacesTextArea.getText(lineStartOffset, lineEndOffset - lineStartOffset - 1);
                        if (!hasValidTokens(implementationClass)) {
                            return false;
                        }
                    }
                } catch (BadLocationException ex) {
                    Exceptions.printStackTrace(ex);
                    return false;
                }
            }
        }
        return true;
    }

    void readSettings(WizardDescriptor wizardDescriptor) {
        Object superclassProperty = wizardDescriptor.getProperty(SUPERCLASS);
        String superclass = superclassProperty != null ? (String) superclassProperty : EMPTY_STRING;
        if (!superclass.isEmpty() && superclassTextField != null) {
            superclassTextField.setText(superclass);
        }
        Object interfacesProperty = wizardDescriptor.getProperty(INTERFACES);
        String interfaces = interfacesProperty != null ? (String) interfacesProperty : EMPTY_STRING;
        if (!interfaces.isEmpty() && interfacesTextArea != null) {
            interfacesTextArea.setText(interfaces);
        }
    }

    void storeSettings(WizardDescriptor wizardDescriptor) {
        if (superclassTextField != null) {
            wizardDescriptor.putProperty(SUPERCLASS, superclassTextField.getText());
        }
        if (interfacesTextArea != null) {
            wizardDescriptor.putProperty(INTERFACES, interfacesTextArea.getText());
        }
    }

    @Override
    public void insertUpdate(DocumentEvent event) {
        wizardPanel.fireChangeEvent();
    }

    @Override
    public void removeUpdate(DocumentEvent event) {
        wizardPanel.fireChangeEvent();
    }

    @Override
    public void changedUpdate(DocumentEvent event) {
        wizardPanel.fireChangeEvent();
    }

    private void browseSuperclassButtonActionPerformed(ActionEvent evt) {
        ElementHandle<TypeElement> handle = TypeElementFinder.find(null, new TypeElementFinder.Customizer() {
            @Override
            public Set<ElementHandle<TypeElement>> query(ClasspathInfo classpathInfo, String textForQuery,
                    ClassIndex.NameKind nameKind, Set<ClassIndex.SearchScope> searchScopes) {
                return classpathInfo.getClassIndex().getDeclaredTypes(textForQuery, nameKind, searchScopes);
            }

            @Override
            public boolean accept(ElementHandle<TypeElement> typeHandle) {
                WizardDescriptor wizardDescriptor = wizardPanel.getWizardDescriptor();
                FileObject template = Templates.getTemplate(wizardDescriptor);
                if (template != null) {
                    String templateName = template.getName();
                    switch (templateName) {
                        case EXCEPTION_TEMPLATE_NAME:
                            return isNotFinalExceptionType(typeHandle);
                        case INTERFACE_TEMPLATE_NAME:
                            return isInterface(typeHandle);
                        default:
                            return isNotFinalClass(typeHandle);
                    }
                }
                return false;
            }
        });
        if (handle != null) {
            superclassTextField.setText(handle.getQualifiedName());
        }
    }

    private boolean isNotFinalExceptionType(ElementHandle<TypeElement> typeHandle) {
        // TODO: this should not use Class.forName!
        try {
            Class<?> clazz = Class.forName(typeHandle.getQualifiedName());
            return typeHandle.getKind() == ElementKind.CLASS
                    && Exception.class.isAssignableFrom(clazz)
                    && !Modifier.isFinal(clazz.getModifiers());
        } catch (ClassNotFoundException ex) {
            return true; // we don't know
        }
    }

    private boolean isInterface(ElementHandle<TypeElement> typeHandle) {
        return typeHandle.getKind() == ElementKind.INTERFACE;
    }

    private boolean isNotFinalClass(ElementHandle<TypeElement> typeHandle) {
        // TODO: this should not use Class.forName!
        try {
            Class<?> clazz = Class.forName(typeHandle.getQualifiedName());
            return typeHandle.getKind() == ElementKind.CLASS && !Modifier.isFinal(clazz.getModifiers());
        } catch (ClassNotFoundException ex) {
            return true; // we don't know
        }
    }

    private void browseInterfacesButtonActionPerformed(ActionEvent evt) {
        ElementHandle<TypeElement> handle = TypeElementFinder.find(null, new TypeElementFinder.Customizer() {
            @Override
            public Set<ElementHandle<TypeElement>> query(ClasspathInfo classpathInfo, String textForQuery,
                    ClassIndex.NameKind nameKind, Set<ClassIndex.SearchScope> searchScopes) {
                return classpathInfo.getClassIndex().getDeclaredTypes(textForQuery, nameKind, searchScopes);
            }

            @Override
            public boolean accept(ElementHandle<TypeElement> typeHandle) {
                return isInterface(typeHandle);
            }
        });
        if (handle != null && interfacesTextArea != null) {
            String fqn = handle.getQualifiedName();
            if (interfacesTextArea.getText().isEmpty()) {
                interfacesTextArea.setText(fqn);
            } else {
                String interfaces = interfacesTextArea.getText();
                if (!interfaces.contains(fqn)) {
                    interfacesTextArea.append(NEW_LINE + fqn);
                }
            }
        }
    }

    public static class ExtensionAndImplementationVisualPanelBuilder {

        private static final int SMALL_VERTICAL_SPACING = 8;
        private static final int SMALL_HORIZONTAL_SPACING = 8;
        private static final int MINIMUM_COMPONENT_HEIGHT = 25;
        private static final int MINIMUM_COMPONENT_WIDTH = 0;
        private static final int DEFAULT_ROW_COUNT = 5;
        private static final int DEFAULT_COLUMN_COUNT = 20;
        private static final int MINIMUM_LABEL_WIDTH = 85;
        private static final int MINIMUM_TEXT_AREA_HEIGHT = 100;
        private final ExtensionAndImplementationWizardPanel wizardPanel;
        private final ExtensionAndImplementationVisualPanel visualPanel;
        private JTextArea interfacesTextArea;
        private JTextField superclassTextField;

        public ExtensionAndImplementationVisualPanelBuilder(ExtensionAndImplementationWizardPanel wizardPanel) {
            this.wizardPanel = wizardPanel;
            visualPanel = new ExtensionAndImplementationVisualPanel(wizardPanel);
        }

        @NbBundle.Messages({
            "ExtensionAndImplementationVisualPanel.superclassLabel.text=&Superclass:",
            "ExtensionAndImplementationVisualPanel.superinterfaceLabel.text=&Superinterface:",
            "ExtensionAndImplementationVisualPanel.browseSuperclassButton.text=Browse..."
        })
        public ExtensionAndImplementationVisualPanelBuilder withExtensionBox() {
            Box extensionBox = new Box(BoxLayout.X_AXIS);
            JLabel superclassLabel = new JLabel();
            FileObject template = Templates.getTemplate(wizardPanel.getWizardDescriptor());
            if (template != null) {
                String templateName = template.getName();
                if (templateName.equals(INTERFACE_TEMPLATE_NAME)) {
                    Mnemonics.setLocalizedText(superclassLabel,
                            NbBundle.getMessage(ExtensionAndImplementationVisualPanel.class,
                                    "ExtensionAndImplementationVisualPanel.superinterfaceLabel.text")); // NOI18N
                } else {
                    Mnemonics.setLocalizedText(superclassLabel,
                            NbBundle.getMessage(ExtensionAndImplementationVisualPanel.class,
                                    "ExtensionAndImplementationVisualPanel.superclassLabel.text")); // NOI18N
                }
            }
            superclassLabel.setAlignmentX(LEFT_ALIGNMENT);
            superclassLabel.setAlignmentY(CENTER_ALIGNMENT);
            superclassLabel.setMinimumSize(new Dimension(MINIMUM_LABEL_WIDTH, MINIMUM_COMPONENT_HEIGHT));
            superclassLabel.setPreferredSize(new Dimension(MINIMUM_LABEL_WIDTH, MINIMUM_COMPONENT_HEIGHT));
            superclassTextField = new JTextField();
            superclassTextField.setEditable(false);
            superclassTextField.setMinimumSize(new Dimension(MINIMUM_COMPONENT_WIDTH, MINIMUM_COMPONENT_HEIGHT));
            superclassTextField.setAlignmentX(LEFT_ALIGNMENT);
            superclassTextField.setAlignmentY(CENTER_ALIGNMENT);
            superclassTextField.getDocument().addDocumentListener(visualPanel);
            superclassLabel.setLabelFor(superclassTextField);
            JButton browseSuperclassButton = new JButton();
            Mnemonics.setLocalizedText(browseSuperclassButton,
                    NbBundle.getMessage(ExtensionAndImplementationVisualPanel.class,
                            "ExtensionAndImplementationVisualPanel.browseSuperclassButton.text")); // NOI18N
            browseSuperclassButton.addActionListener(visualPanel::browseSuperclassButtonActionPerformed);
            browseSuperclassButton.setAlignmentX(LEFT_ALIGNMENT);
            browseSuperclassButton.setAlignmentY(CENTER_ALIGNMENT);
            extensionBox.add(superclassLabel);
            extensionBox.add(superclassTextField);
            extensionBox.add(Box.createHorizontalStrut(SMALL_HORIZONTAL_SPACING));
            extensionBox.add(browseSuperclassButton);
            extensionBox.add(Box.createHorizontalStrut(SMALL_HORIZONTAL_SPACING));
            visualPanel.add(extensionBox);
            visualPanel.add(Box.createVerticalStrut(SMALL_VERTICAL_SPACING));
            return this;
        }

        @NbBundle.Messages({
            "ExtensionAndImplementationVisualPanel.interfacesLabel.text=&Interfaces:",
            "ExtensionAndImplementationVisualPanel.browseInterfacesButton.text=Browse..."
        })
        public ExtensionAndImplementationVisualPanelBuilder withImplementationBox() {
            Box implementationBox = new Box(BoxLayout.X_AXIS);
            JLabel interfacesLabel = new JLabel();
            Mnemonics.setLocalizedText(interfacesLabel,
                    NbBundle.getMessage(ExtensionAndImplementationVisualPanel.class,
                            "ExtensionAndImplementationVisualPanel.interfacesLabel.text")); // NOI18N
            interfacesLabel.setAlignmentX(LEFT_ALIGNMENT);
            interfacesLabel.setAlignmentY(TOP_ALIGNMENT);
            interfacesLabel.setMinimumSize(new Dimension(MINIMUM_LABEL_WIDTH, MINIMUM_COMPONENT_HEIGHT));
            interfacesLabel.setPreferredSize(new Dimension(MINIMUM_LABEL_WIDTH, MINIMUM_COMPONENT_HEIGHT));
            JScrollPane interfacesScrollPane = new JScrollPane();
            interfacesTextArea = new JTextArea();
            interfacesTextArea.setEditable(false);
            interfacesTextArea.setColumns(DEFAULT_COLUMN_COUNT);
            interfacesTextArea.setRows(DEFAULT_ROW_COUNT);
            interfacesTextArea.getDocument().addDocumentListener(visualPanel);
            interfacesLabel.setLabelFor(interfacesTextArea);
            interfacesScrollPane.setViewportView(interfacesTextArea);
            interfacesScrollPane.setAlignmentX(LEFT_ALIGNMENT);
            interfacesScrollPane.setAlignmentY(TOP_ALIGNMENT);
            interfacesScrollPane.setMinimumSize(new Dimension(MINIMUM_COMPONENT_WIDTH, MINIMUM_TEXT_AREA_HEIGHT));
            JButton browseInterfacesButton = new JButton();
            Mnemonics.setLocalizedText(browseInterfacesButton,
                    NbBundle.getMessage(ExtensionAndImplementationVisualPanel.class,
                            "ExtensionAndImplementationVisualPanel.browseInterfacesButton.text")); // NOI18N
            browseInterfacesButton.addActionListener(visualPanel::browseInterfacesButtonActionPerformed);
            browseInterfacesButton.setAlignmentX(LEFT_ALIGNMENT);
            browseInterfacesButton.setAlignmentY(TOP_ALIGNMENT);
            implementationBox.add(interfacesLabel);
            implementationBox.add(interfacesScrollPane);
            implementationBox.add(Box.createHorizontalStrut(SMALL_HORIZONTAL_SPACING));
            implementationBox.add(browseInterfacesButton);
            implementationBox.add(Box.createHorizontalStrut(SMALL_HORIZONTAL_SPACING));
            visualPanel.add(implementationBox);
            return this;
        }

        public ExtensionAndImplementationVisualPanel build() {
            visualPanel.setSuperclassTextField(superclassTextField);
            visualPanel.setInterfacesTextArea(interfacesTextArea);
            visualPanel.add(Box.createRigidArea(new Dimension(MINIMUM_COMPONENT_WIDTH, Integer.MAX_VALUE)));
            return visualPanel;
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
