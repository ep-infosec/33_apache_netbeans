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
package org.netbeans.modules.css.lib;

import org.antlr.runtime.CommonToken;
import org.netbeans.modules.css.lib.api.CssTokenId;
import org.netbeans.modules.css.lib.api.NodeType;

/**
 *
 * @author marekfukala
 */
public class TokenNode extends AbstractParseTreeNode {

    private final int from, to;
    private CssTokenId tokenId;

    public TokenNode(CharSequence source, CommonToken token) {
        super(source); 
        int[] range = CommonTokenUtil.getCommonTokenOffsetRange(token);
        from = range[0];
        to = range[1];
        tokenId = CssTokenId.forTokenTypeCode(token.getType());
    }

    @Override
    public String name() {
        return image().toString();
    }
    
    @Override
    public NodeType type() {
        return NodeType.token;
    }

    /**
     * Gets the kind of the token encapsulated by this TokenNode. Please notice
     * that it's not possible to get the token itself since the parser source
     * is not a stream of the netbeans tokens, but tokens generated by the antlr lexer.
     */
    public CssTokenId getTokenId() {
        return tokenId;
    }

    @Override
    public int from() {
        return from;
    }

    @Override
    public int to() {
        return to;
    }

    @Override
    public String toString() {
        return new StringBuilder(super.toString())
                .append(' ')
                .append('\'')
                .append(escapeNL(image()))
                .append('\'')
                .append(' ')
                .append('[')
                .append(getTokenId())
                .append(']')
                .toString();
    }
    
    private CharSequence escapeNL(CharSequence text) {
        return text.toString().replace("\n", "\\n");
    }
}
