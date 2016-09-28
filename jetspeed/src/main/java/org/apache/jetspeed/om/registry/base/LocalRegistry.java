/*
 * Copyright 2000-2001,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jetspeed.om.registry.base;

import org.apache.jetspeed.om.registry.Registry;
import org.apache.jetspeed.om.registry.RegistryEntry;
import org.apache.jetspeed.om.registry.InvalidEntryException;

/**
 * This interface declares the methods used by the RegistryService to
 * set entries within the registry without impacting the persistant state.
 *
 * @author <a href="mailto:raphael@apache.org">Rapha謖 Luta</a>
 * @version $Id: LocalRegistry.java,v 1.2 2004/02/23 03:08:26 jford Exp $
 */
public interface LocalRegistry extends Registry
{
    /**
     * This method is used  to only set the entry in the local
     * memory cache of the registry without any coherency check with
     * persistent storage
     *
     * @param entry the RegistryEntry to store
     */
    public void setLocalEntry( RegistryEntry entry ) throws InvalidEntryException;

    /**
     * This method is used to only add the entry in the local
     * memory cache of the registry without any coherency check with
     * persistent storage
     *
     * @param entry the RegistryEntry to store
     */
    public void addLocalEntry( RegistryEntry entry ) throws InvalidEntryException;

    /**
     * This method is used to only remove the entry from the local
     * memory cache of the registry without any coherency check with
     * persistent storage
     *
     * @param name the name of the RegistryEntry to remove
     */
    public void removeLocalEntry( String name );

    /**
     * This method is used to only remove the entry from the local
     * memory cache of the registry without any coherency check with
     * persistent storage
     *
     * @param entry the RegistryEntry to remove
     */
    public void removeLocalEntry( RegistryEntry entry );

}