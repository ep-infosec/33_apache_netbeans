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

package org.netbeans.modules.profiler.heapwalk;

import javax.swing.BoundedRangeModel;
import javax.swing.SwingUtilities;
import org.netbeans.lib.profiler.heap.*;
import org.netbeans.modules.profiler.heapwalk.model.AbstractHeapWalkerNode;
import org.netbeans.modules.profiler.heapwalk.model.BrowserUtils;
import org.netbeans.modules.profiler.heapwalk.model.HeapWalkerNode;
import org.netbeans.modules.profiler.heapwalk.model.HeapWalkerNodeFactory;
import org.netbeans.modules.profiler.heapwalk.model.InstanceNode;
import org.netbeans.modules.profiler.heapwalk.ui.ReferencesBrowserControllerUI;
import org.netbeans.modules.profiler.ui.NBSwingWorker;
import org.openide.DialogDescriptor;
import org.openide.util.NbBundle;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.TreePath;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.modules.profiler.api.ProfilerDialogs;
import org.openide.DialogDisplayer;


/**
 *
 * @author Tomas Hurka
 * @author Jiri Sedlacek
 */
@NbBundle.Messages({
    "ReferencesBrowserController_NoInstanceSelectedString=<No Instance Selected>",
    "ReferencesBrowserController_NoneString=<none>",
    "ReferencesBrowserController_ProgressDialogCaption=Progress...",
    "ReferencesBrowserController_ProgressMsg=Computing nearest GC root...",
    "ReferencesBrowserController_SelfGcRootMsg=The instance is a GC root.",
    "ReferencesBrowserController_NoGcRootMsg=No GC root found."
})
public class ReferencesBrowserController extends AbstractController {
    //~ Inner Interfaces ---------------------------------------------------------------------------------------------------------

    public static interface Handler {
        //~ Methods --------------------------------------------------------------------------------------------------------------

        public HeapFragmentWalker getHeapFragmentWalker();

        public void showClass(JavaClass javaClass);

        public void showInstance(Instance instance);
    }

    //~ Inner Classes ------------------------------------------------------------------------------------------------------------

//    private class FieldsComparator implements Comparator {
//        //~ Instance fields ------------------------------------------------------------------------------------------------------
//
//        private boolean sortingOrder;
//        private int sortingColumn;
//
//        //~ Constructors ---------------------------------------------------------------------------------------------------------
//
//        public FieldsComparator(int sortingColumn, boolean sortingOrder) {
//            this.sortingColumn = sortingColumn;
//            this.sortingOrder = sortingOrder;
//        }
//
//        //~ Methods --------------------------------------------------------------------------------------------------------------
//
//        public int compare(Object o1, Object o2) {
//            FieldValue field1 = sortingOrder ? (FieldValue) o1 : (FieldValue) o2;
//            FieldValue field2 = sortingOrder ? (FieldValue) o2 : (FieldValue) o1;
//
//            switch (sortingColumn) {
//                case 0: // Name
//                    return field1.getField().getName().compareTo(field2.getField().getName());
//                case 1: // Type
//                    return field1.getField().getType().getName().compareTo(field2.getField().getType().getName());
//                case 2: // Value
//                    return field1.getValue().compareTo(field2.getValue());
//                default:
//                    throw new RuntimeException("Unsupported compare operation for " + o1 + ", " + o2); // NOI18N
//            }
//        }
//    }


    // --- Public interface ------------------------------------------------------
    public static final AbstractHeapWalkerNode EMPTY_INSTANCE_NODE = new AbstractHeapWalkerNode(null) {
        protected String computeName() {
            return Bundle.ReferencesBrowserController_NoInstanceSelectedString();
        }

        protected String computeType() {
            return Bundle.ReferencesBrowserController_NoneString();
        }

        protected String computeValue() {
            return Bundle.ReferencesBrowserController_NoneString();
        }
        
        protected String computeSize() {
            return ""; // NOI18N
        }
        
        protected String computeRetainedSize() {
            return ""; // NOI18N
        }

        protected Icon computeIcon() {
            return null;
        }

        public boolean isLeaf() {
            return true;
        }
    };

    private static final int DEFAULT_WIDTH = 350;
    private static final int DEFAULT_HEIGHT = 100;

    //~ Instance fields ----------------------------------------------------------------------------------------------------------

    private Handler referencesControllerHandler;
    private Instance instance;

    //~ Constructors -------------------------------------------------------------------------------------------------------------

    public ReferencesBrowserController(Handler referencesControllerHandler) {
        this.referencesControllerHandler = referencesControllerHandler;
    }

    //~ Methods ------------------------------------------------------------------------------------------------------------------

    // --- Internal interface ----------------------------------------------------

    public Handler getReferencesControllerHandler() {
        return referencesControllerHandler;
    }

    public HeapWalkerNode getFilteredSortedReferences(String filterValue, int sortingColumn, boolean sortingOrder) {
        if (instance == null) {
            return EMPTY_INSTANCE_NODE;
        }

        return getSortedReferences(getFilteredReferences(getReferences(instance), filterValue), sortingColumn, sortingOrder);
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
        update();
    }

    public void createNavigationHistoryPoint() {
        referencesControllerHandler.getHeapFragmentWalker().createNavigationHistoryPoint();
    }

    public void navigateToClass(JavaClass javaClass) {
        referencesControllerHandler.showClass(javaClass);
    }

    public void navigateToInstance(Instance instance) {
        referencesControllerHandler.showInstance(instance);
    }
    
    public void navigateToRootNearestGCRoot() {
        ((ReferencesBrowserControllerUI)getPanel()).showRootGCRoot();
    }

    public void navigateToNearestGCRoot(final InstanceNode instanceNode) {
        new NBSwingWorker(true) {
            private ProgressHandle progress = null;
            private HeapWalkerNode gcRootNode = null;
            private BoundedRangeModel progressModel = null;
            private ChangeListener cl = null;
            private boolean done = false;

            @Override
            public void doInBackground() {
                progressModel = HeapProgress.getProgress();
                cl = new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        if (progress != null) {
                            progress.progress(progressModel.getValue());
                        }
                    }
                };
                progressModel.addChangeListener(cl);
                try {
                    gcRootNode = BrowserUtils.computeChildrenToNearestGCRoot(instanceNode);
                } finally {
                    HeapProgress.getProgress().removeChangeListener(cl);
                }                    
            }

            @Override
            public void nonResponding() {
                SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            if (!done) {
                                progress = ProgressHandle.createHandle(Bundle.ReferencesBrowserController_ProgressMsg());
                                progress.start(HeapProgress.PROGRESS_MAX);
                            }
                        }
                    });
            }

            @Override
            public void done() {
                done = false;
                if (progress != null) {
                    progress.finish();
                }
                
                ReferencesBrowserControllerUI controlerUI = (ReferencesBrowserControllerUI) getPanel();
                HeapWalkerNode selNode = controlerUI.getSelectedNode();
                if (selNode == null) {
                    selNode = controlerUI.getSelectedNode(0);
                }
                if (instanceNode.equals(selNode)) {
                    if (gcRootNode != null) {
                        if (instanceNode == gcRootNode) {
                            ProfilerDialogs.displayInfo(Bundle.ReferencesBrowserController_SelfGcRootMsg());
                        } else {
                            controlerUI.selectNode(gcRootNode);
                        }
                    } else {
                        ProfilerDialogs.displayInfo(Bundle.ReferencesBrowserController_NoGcRootMsg());
                    }
                }
            }
        }.execute();
    }

    public void showInstance(Instance instance) {
        if (this.instance != instance) {
            setInstance(instance);
        }
    }

    public void showInThreads(Instance instance) {
        HeapFragmentWalker heapFragmentWalker = referencesControllerHandler.getHeapFragmentWalker();
        heapFragmentWalker.switchToSummaryView();
        heapFragmentWalker.getSummaryController().getOverViewController().showInThreads(instance);
    }

    // --- Private implementation ------------------------------------------------
    public void update() {
        ((ReferencesBrowserControllerUI) getPanel()).update();
    }

    protected AbstractButton createControllerPresenter() {
        return ((ReferencesBrowserControllerUI) getPanel()).getPresenter();
    }
    
    public List<TreePath> getExpandedPaths() {
        return ((ReferencesBrowserControllerUI)getPanel()).getExpandedPaths();
    }
    
    public TreePath getSelectedRow() {
        return ((ReferencesBrowserControllerUI)getPanel()).getSelectedRow();
    }
    
    public void restoreState(List expanded, TreePath selected) {
        ((ReferencesBrowserControllerUI)getPanel()).restoreState(expanded, selected);
    }

    // --- Protected implementation ----------------------------------------------
    protected JPanel createControllerUI() {
        return new ReferencesBrowserControllerUI(this);
    }

    Dialog createProgressPanel(final String message, BoundedRangeModel model) {
        Dialog dialog;
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.add(new JLabel(message), BorderLayout.NORTH);

        final Dimension ps = panel.getPreferredSize();
        ps.setSize(Math.max(ps.getWidth(), DEFAULT_WIDTH), Math.max(ps.getHeight(), DEFAULT_HEIGHT));
        panel.setPreferredSize(ps);

        final JProgressBar progress = new JProgressBar();
        if (model == null) {
            progress.setIndeterminate(true);
        } else {
            progress.setStringPainted(true);
            progress.setModel(model);
        }
        panel.add(progress, BorderLayout.SOUTH);
        dialog = DialogDisplayer.getDefault().createDialog(new DialogDescriptor(panel, Bundle.ReferencesBrowserController_ProgressDialogCaption(), true, new Object[] {  },
                                                           DialogDescriptor.CANCEL_OPTION, DialogDescriptor.RIGHT_ALIGN,
                                                           null, null));

        return dialog;
    }

    private HeapWalkerNode getReferences(final Instance instance) {
        return HeapWalkerNodeFactory.createRootInstanceNode(instance, "this", // NOI18N
                new Runnable() { public void run() { ((ReferencesBrowserControllerUI) getPanel()).refreshView(); } },
                new Runnable() { public void run() { getPanel().repaint(); } },
                HeapWalkerNode.MODE_REFERENCES, referencesControllerHandler.getHeapFragmentWalker().getHeapFragment());
    }

    private HeapWalkerNode getFilteredReferences(HeapWalkerNode references, String filterValue) {
//            ArrayList filteredReferences = new ArrayList();
//
//            Iterator referencesIterator = references.iterator();
//            while (referencesIterator.hasNext()) {
//              FieldValue reference = (FieldValue)referencesIterator.next();
//              if (matchesFilter(reference)) filteredReferences.add(reference);
//            }
//
//            return filteredReferences;
        return references;
    }

    private HeapWalkerNode getSortedReferences(HeapWalkerNode filteredReferences, int sortingColumn, boolean sortingOrder) {
        //Collections.sort(filteredReferences, new FieldsComparator(sortingColumn, sortingOrder));
        return filteredReferences;
    }

//    private boolean matchesFilter(FieldValue reference) {
//        return true;
//    }
}
