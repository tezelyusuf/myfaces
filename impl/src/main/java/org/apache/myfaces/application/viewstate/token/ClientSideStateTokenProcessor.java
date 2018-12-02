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
package org.apache.myfaces.application.viewstate.token;

import javax.faces.context.FacesContext;
import org.apache.myfaces.util.StateUtils;

/**
 *
 * @author Thomas Andraschko
 */
public class ClientSideStateTokenProcessor extends StateTokenProcessor
{
    @Override
    public Object decode(FacesContext facesContext, String token)
    {
        if (STATELESS_TOKEN.equals(token))
        {
            // Should not happen, because ResponseStateManager.isStateless(context,viewId) should
            // catch it first
            return null;
        }
        Object savedStateObject = StateUtils.reconstruct((String)token, facesContext.getExternalContext());
        return savedStateObject;
    }

    @Override
    public String encode(FacesContext facesContext, Object savedStateObject)
    {
        if (facesContext.getViewRoot().isTransient())
        {
            return STATELESS_TOKEN;
        }
        String serializedState = StateUtils.construct(savedStateObject, facesContext.getExternalContext());
        return serializedState;
    }
}