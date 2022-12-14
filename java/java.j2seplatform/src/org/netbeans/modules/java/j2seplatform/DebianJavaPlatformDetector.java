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
package org.netbeans.modules.java.j2seplatform;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.netbeans.modules.java.j2seplatform.platformdefinition.J2SEPlatformImpl;
import org.netbeans.spi.java.platform.JavaPlatformFactory;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Lookup;

/**
 *
 * @author lkishalmi
 */
public class DebianJavaPlatformDetector implements Runnable {
    static final File DEBIAN_JAVA_DIR = new File("/usr/lib/jvm"); //NOI18N
    static final String JAVA_DIR_MATCHER = "java-(\\d+)-openjdk-(amd64|arm64|i386)"; //NOI18N

    @Override
    public void run() {
        if (DEBIAN_JAVA_DIR.isDirectory()) {
            File[] platformDirs = DEBIAN_JAVA_DIR.listFiles((File f) -> f.isDirectory() && f.getName().matches(JAVA_DIR_MATCHER));
            Collection<? extends JavaPlatformFactory.Provider> providers = Lookup.getDefault().lookupAll(JavaPlatformFactory.Provider.class);
            for (JavaPlatformFactory.Provider provider : providers) {
                JavaPlatformFactory platformFactory = provider.forType(J2SEPlatformImpl.PLATFORM_J2SE);
                if (platformFactory != null) {
                    for (File platformDir : platformDirs) {
                        try {
                            FileObject installFolder = FileUtil.toFileObject(platformDir);
                            platformFactory.create(installFolder, getDisplayName(installFolder), true);
                        } catch (IOException ex) {
                            //It seems we was not suceeding to add this
                        } catch(IllegalArgumentException ex) {
                            //Thrown if the platform is already persisted and added to the system.
                        }
                    }
                    break;
                }
            }
        }
    }

    private static String getDisplayName(FileObject installFolder) {
        Matcher m = Pattern.compile(JAVA_DIR_MATCHER).matcher(installFolder.getNameExt());
        return m.find() ? "JDK " + m.group(1) : installFolder.getNameExt();
    }
}
