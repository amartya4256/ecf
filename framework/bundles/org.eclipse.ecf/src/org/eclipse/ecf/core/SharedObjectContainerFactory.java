/*******************************************************************************
 * Copyright (c) 2004 Composent, Inc. and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Composent, Inc. - initial API and implementation
 ******************************************************************************/

package org.eclipse.ecf.core;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.ecf.core.provider.ISharedObjectContainerInstantiator;
import org.eclipse.ecf.core.util.AbstractFactory;
import org.eclipse.ecf.internal.core.Trace;

/**
 * Factory for creating {@link ISharedObjectContainer} instances. This
 * class provides ECF clients an entry point to constructing {@link ISharedObjectContainer}
 * instances.  
 * <br>
 * <br>
 * Here is an example use of the SharedObjectContainerFactory to construct an instance
 * of the 'standalone' container (has no connection to other containers):
 * <br><br>
 * <code>
 * 	    ISharedObjectContainer container = <br>
 * 			SharedObjectContainerFactory.makeSharedObjectContainer('standalone');
 *      <br><br>
 *      ...further use of container variable here...
 * </code>
 * 
 */
public class SharedObjectContainerFactory {

    private static Trace debug = Trace.create("containerfactory");
    
    private static Hashtable containerdescriptions = new Hashtable();
    protected static SharedObjectContainerFactory instance = null;
    
    static {
    	instance = new SharedObjectContainerFactory();
    }
    protected SharedObjectContainerFactory() {
    }
    public static SharedObjectContainerFactory getDefault() {
    	return instance;
    }
    private static void trace(String msg) {
        if (Trace.ON && debug != null) {
            debug.msg(msg);
        }
    }

    private static void dumpStack(String msg, Throwable e) {
        if (Trace.ON && debug != null) {
            debug.dumpStack(e, msg);
        }
    }
    /*
     * Add a SharedObjectContainerDescription to the set of known
     * SharedObjectContainerDescriptions.
     * 
     * @param scd the SharedObjectContainerDescription to add to this factory
     * @return SharedObjectContainerDescription the old description of the same
     * name, null if none found
     */
    public SharedObjectContainerDescription addDescription(
            SharedObjectContainerDescription scd) {
        trace("addDescription("+scd+")");
        return addDescription0(scd);
    }
    /**
     * Get a collection of the SharedObjectContainerDescriptions currently known to
     * this factory.  This allows clients to query the factory to determine what if
     * any other SharedObjectContainerDescriptions are currently registered with
     * the factory, and if so, what they are.
     * 
     * @return List of SharedObjectContainerDescription instances
     */
    public List getDescriptions() {
        return getDescriptions0();
    }
    protected List getDescriptions0() {
        return new ArrayList(containerdescriptions.values());
    }
    protected SharedObjectContainerDescription addDescription0(
            SharedObjectContainerDescription n) {
        if (n == null)
            return null;
        return (SharedObjectContainerDescription) containerdescriptions.put(n
                .getName(), n);
    }
    /**
     * Check to see if a given named description is already contained by this
     * factory
     * 
     * @param scd
     *            the SharedObjectContainerDescription to look for
     * @return true if description is already known to factory, false otherwise
     */
    public boolean containsDescription(
            SharedObjectContainerDescription scd) {
        return containsDescription0(scd);
    }
    protected boolean containsDescription0(
            SharedObjectContainerDescription scd) {
        if (scd == null)
            return false;
        return containerdescriptions.containsKey(scd.getName());
    }
    protected SharedObjectContainerDescription getDescription0(
            SharedObjectContainerDescription scd) {
        if (scd == null)
            return null;
        return (SharedObjectContainerDescription) containerdescriptions.get(scd
                .getName());
    }
    protected SharedObjectContainerDescription getDescription0(
            String name) {
        if (name == null)
            return null;
        return (SharedObjectContainerDescription) containerdescriptions.get(name);
    }
    /**
     * Get the known SharedObjectContainerDescription given it's name.
     * 
     * @param name
     * @return SharedObjectContainerDescription found
     * @throws SharedObjectContainerInstantiationException
     */
    public SharedObjectContainerDescription getDescriptionByName(
            String name) throws SharedObjectContainerInstantiationException {
        trace("getDescriptionByName("+name+")");
        SharedObjectContainerDescription res = getDescription0(name);
        if (res == null) {
            throw new SharedObjectContainerInstantiationException(
                    "SharedObjectContainerDescription named '" + name
                            + "' not found");
        }
        return res;
    }
    /**
     * Make ISharedObjectContainer instance. Given a
     * SharedObjectContainerDescription object, a String [] of argument types,
     * and an Object [] of parameters, this method will
     * <p>
     * <ul>
     * <li>lookup the known SharedObjectContainerDescriptions to find one of
     * matching name</li>
     * <li>if found, will retrieve or create an
     * ISharedObjectContainerInstantiator for that description</li>
     * <li>Call the ISharedObjectContainerInstantiator.makeInstance method to
     * return an instance of ISharedObjectContainer</li>
     * </ul>
     * 
     * @param desc
     *            the SharedObjectContainerDescription to use to create the
     *            instance
     * @param argTypes
     *            a String [] defining the types of the args parameter
     * @param args
     *            an Object [] of arguments passed to the makeInstance method of
     *            the ISharedObjectContainerInstantiator
     * @return a valid instance of ISharedObjectContainer
     * @throws SharedObjectContainerInstantiationException
     */
    public ISharedObjectContainer makeSharedObjectContainer(
            SharedObjectContainerDescription desc, String[] argTypes,
            Object[] args) throws SharedObjectContainerInstantiationException {
        trace("makeSharedObjectContainer("+desc+","+Trace.convertStringAToString(argTypes)+","+Trace.convertObjectAToString(args)+")");
        if (desc == null)
            throw new SharedObjectContainerInstantiationException(
                    "SharedObjectContainerDescription cannot be null");
        SharedObjectContainerDescription cd = getDescription0(desc);
        if (cd == null)
            throw new SharedObjectContainerInstantiationException(
                    "SharedObjectContainerDescription named '" + desc.getName()
                            + "' not found");
        Class clazzes[] = null;
        ISharedObjectContainerInstantiator instantiator = null;
        try {
            instantiator = (ISharedObjectContainerInstantiator) cd
            .getInstantiator();
            clazzes = AbstractFactory.getClassesForTypes(argTypes, args, cd.getClassLoader());
        } catch (Exception e) {
            SharedObjectContainerInstantiationException newexcept = new SharedObjectContainerInstantiationException(
                    "makeSharedObjectContainer exception with description: "+desc+": "+e.getClass().getName()+": "+e.getMessage());
            newexcept.setStackTrace(e.getStackTrace());
            dumpStack("Exception in makeSharedObjectContainer",newexcept);
            throw newexcept;
        }
        if (instantiator == null)
            throw new SharedObjectContainerInstantiationException(
                    "Instantiator for SharedObjectContainerDescription "
                            + cd.getName() + " is null");
        // Ask instantiator to actually create instance
        return (ISharedObjectContainer) instantiator
                .makeInstance(desc,clazzes, args);
    }
    /**
     * Make ISharedObjectContainer instance. Given a
     * SharedObjectContainerDescription name, this method will
     * <p>
     * <ul>
     * <li>lookup the known SharedObjectContainerDescriptions to find one of
     * matching name</li>
     * <li>if found, will retrieve or create an
     * ISharedObjectContainerInstantiator for that description</li>
     * <li>Call the ISharedObjectContainerInstantiator.makeInstance method to
     * return an instance of ISharedObjectContainer</li>
     * </ul>
     * 
     * @param descriptionName
     *            the SharedObjectContainerDescription name to lookup
     * @return a valid instance of ISharedObjectContainer
     * @throws SharedObjectContainerInstantiationException
     */
    public ISharedObjectContainer makeSharedObjectContainer(
            String descriptionName)
            throws SharedObjectContainerInstantiationException {
        return makeSharedObjectContainer(
                getDescriptionByName(descriptionName), null, null);
    }
    /**
     * Make ISharedObjectContainer instance. Given a
     * SharedObjectContainerDescription name, this method will
     * <p>
     * <ul>
     * <li>lookup the known SharedObjectContainerDescriptions to find one of
     * matching name</li>
     * <li>if found, will retrieve or create an
     * ISharedObjectContainerInstantiator for that description</li>
     * <li>Call the ISharedObjectContainerInstantiator.makeInstance method to
     * return an instance of ISharedObjectContainer</li>
     * </ul>
     * 
     * @param descriptionName
     *            the SharedObjectContainerDescription name to lookup
     * @param args
     *            the Object [] of arguments passed to the
     *            ISharedObjectContainerInstantiator.makeInstance method
     * @return a valid instance of ISharedObjectContainer
     * @throws SharedObjectContainerInstantiationException
     */
    public ISharedObjectContainer makeSharedObjectContainer(
            String descriptionName, Object[] args)
            throws SharedObjectContainerInstantiationException {
        return makeSharedObjectContainer(
                getDescriptionByName(descriptionName), null, args);
    }
    /**
     * Make ISharedObjectContainer instance. Given a
     * SharedObjectContainerDescription name, this method will
     * <p>
     * <ul>
     * <li>lookup the known SharedObjectContainerDescriptions to find one of
     * matching name</li>
     * <li>if found, will retrieve or create an
     * ISharedObjectContainerInstantiator for that description</li>
     * <li>Call the ISharedObjectContainerInstantiator.makeInstance method to
     * return an instance of ISharedObjectContainer</li>
     * </ul>
     * 
     * @param descriptionName
     *            the SharedObjectContainerDescription name to lookup
     * @param argsTypes
     *            the String [] of argument types of the following args
     * @param args
     *            the Object [] of arguments passed to the
     *            ISharedObjectContainerInstantiator.makeInstance method
     * @return a valid instance of ISharedObjectContainer
     * @throws SharedObjectContainerInstantiationException
     */
    public ISharedObjectContainer makeSharedObjectContainer(
            String descriptionName, String[] argsTypes, Object[] args)
            throws SharedObjectContainerInstantiationException {
        return makeSharedObjectContainer(
                getDescriptionByName(descriptionName), argsTypes, args);
    }
    /**
     * Remove given description from set known to this factory.
     * 
     * @param scd
     *            the SharedObjectContainerDescription to remove
     * @return the removed SharedObjectContainerDescription, null if nothing
     *         removed
     */
    public SharedObjectContainerDescription removeDescription(
            SharedObjectContainerDescription scd) {
        trace("removeDescription("+scd+")");
        return removeDescription0(scd);
    }
    protected SharedObjectContainerDescription removeDescription0(
            SharedObjectContainerDescription n) {
        if (n == null)
            return null;
        return (SharedObjectContainerDescription) containerdescriptions.remove(n
                .getName());
    }

}