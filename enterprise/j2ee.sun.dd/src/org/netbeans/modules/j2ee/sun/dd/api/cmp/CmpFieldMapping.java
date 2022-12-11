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
package org.netbeans.modules.j2ee.sun.dd.api.cmp;

public interface CmpFieldMapping extends org.netbeans.modules.j2ee.sun.dd.api.CommonDDBean {

    public static final String FIELD_NAME = "FieldName"; // NOI18N
    public static final String COLUMN_NAME = "ColumnName"; // NOI18N
    public static final String READ_ONLY = "ReadOnly"; // NOI18N
    public static final String FETCHED_WITH = "FetchedWith"; // NOI18N

    public void setFieldName(String value);
    public String getFieldName();

    public void setColumnName(int index, String value);
    public String getColumnName(int index);
    public int sizeColumnName();
    public void setColumnName(String[] value);
    public String[] getColumnName();
    public int addColumnName(String value);
    public int removeColumnName(String value);

    public void setReadOnly(boolean value);
    public boolean isReadOnly();

    public void setFetchedWith(FetchedWith value);
    public FetchedWith getFetchedWith();
    public FetchedWith newFetchedWith();

}
