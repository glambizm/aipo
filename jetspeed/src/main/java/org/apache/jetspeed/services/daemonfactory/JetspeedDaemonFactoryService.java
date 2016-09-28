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

package org.apache.jetspeed.services.daemonfactory;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.ServletConfig;

import org.apache.jetspeed.daemon.Daemon;
import org.apache.jetspeed.daemon.DaemonConfig;
import org.apache.jetspeed.daemon.DaemonContext;
import org.apache.jetspeed.daemon.DaemonEntry;
import org.apache.jetspeed.daemon.DaemonException;
import org.apache.jetspeed.daemon.DaemonNotFoundException;
import org.apache.jetspeed.daemon.DaemonThread;
import org.apache.jetspeed.services.logging.JetspeedLogFactoryService;
import org.apache.jetspeed.services.logging.JetspeedLogger;
import org.apache.jetspeed.services.resources.JetspeedResources;
import org.apache.turbine.services.TurbineBaseService;

/**
 * 
 * @author <a href="mailto:burton@apache.org">Kevin A. Burton</a>
 * @author <a href="mailto:sgala@hisitech.com">Santiago Gala</a>
 */
public class JetspeedDaemonFactoryService extends TurbineBaseService implements
    DaemonFactoryService {
  /**
   * Static initialization of the logger for this class
   */
  private static final JetspeedLogger logger = JetspeedLogFactoryService
      .getLogger(JetspeedDaemonFactoryService.class.getName());

  // BEGIN define the keys for various/default Daemons
  public final static String FEEDDAEMON_KEY = "org.apache.jetspeed.daemon.impl.FeedDaemon";

  public final static String DISKCACHEDAEMON_KEY = "org.apache.jetspeed.daemon.impl.DiskCacheDaemon";

  public final static String BADURLMANAGERDAEMON_KEY = "org.apache.jetspeed.daemon.impl.BadURLManagerDaemon";

  // END

  private DaemonContext context = null;

  /**
   * Stores mappings of DaemonEntry -> DaemonThreads
   */
  protected Hashtable daemons = new Hashtable();

  protected Hashtable threads = new Hashtable();

  private DaemonEntry[] entries = null;

  /**
   * Late init. Don't return control until early init says we're done.
   */
  public void init() {
    logger.info("Late init for DaemonFactory called");
    while (!getInit()) {
      try {
        Thread.sleep(500);
      } catch (InterruptedException ie) {
        logger.info("DaemonFactory service: Waiting for init()...");
      }
    }

  }

  /**
   * Perform initialization of the DaemonFactory. Note that this should return
   * right away so that processing can continue (IE thread off everything)
   */
  public synchronized void init(ServletConfig config) {

    // already initialized
    if (getInit())
      return;

    logger.info("Early init for DaemonFactory called...");

    this.context = new DaemonContext();

    // init daemons from config file
    Vector raw = JetspeedResources.getVector(JetspeedResources.DAEMON_ENTRY);
    this.entries = new DaemonEntry[raw.size()];

    for (int i = 0; i < raw.size(); ++i) {

      String name = (String) raw.elementAt(i);
      String classname = JetspeedResources.getString("daemon." + name
          + ".classname");
      long interval = JetspeedResources.getLong("daemon." + name + ".interval");
      boolean onstartup = JetspeedResources.getBoolean("daemon." + name
          + ".onstartup");

      entries[i] = new DaemonEntry(name, interval, classname, onstartup);

    }

    setInit(true);
    logger.info("Early init for DaemonFactory done");

    // Finish by starting requested Daemons...
    this.start();
  }

  /**
   * <p>
   * Starts any daemons that need processing.
   * </p>
   * 
   * <p>
   * This should be called right after init() so that any daemons that need to
   * be started will be. If you need to do any per-daemon initialization then do
   * so before calling start()
   * </p>
   */
  public void start() {

    logger.info("DaemonFactory:  Starting up necessary daemons.");

    // get all the entries..
    DaemonEntry[] entries = this.getDaemonEntries();

    for (int i = 0; i < entries.length; ++i) {

      // create Daemon threads for them and pa
      if (entries[i].onStartup()) {
        start(entries[i]);
      }

    }

  }

  /**
   * Starts a daemon entry
   * 
   * @param entry
   */
  private void start(DaemonEntry entry) {
    logger.info("DaemonFactory:  start(): starting daemon -> "
        + entry.getName());
    DaemonThread dt = new DaemonThread(entry);
    this.threads.put(entry, dt);
    dt.start();
  }

  /**
   * Stop all daemon thread
   * 
   * @param entry
   */
  public void stop() {
    logger.info("DaemonFactory:  stop(): stop all daemons");
    try {
      super.shutdown();
      Collection threadsValues = threads.values();
      Iterator threadsiter = threadsValues.iterator();
      while (threadsiter.hasNext()) {
        Object obj = threadsiter.next();
        // ((DaemonThread) obj).stopThread();
        ((DaemonThread) obj).interrupt();

      }
      threads.clear();
      daemons.clear();
      Collection daemonsValues = daemons.values();
      Iterator daemonsIter = daemonsValues.iterator();
      while (daemonsIter.hasNext()) {
        Object obj = daemonsIter.next();
        Daemon daemon = (Daemon) this.daemons.get(obj.getClass());
        daemon.wait();

      }

    } catch (Exception e) {
      logger.error("Error instantiating DaemonThread", e);
    }
  }

  /**
   * Allows a Daemon to define its Thread priority through a factory. The Thread
   * that this object should return should be an implementation of itself.
   */
  public Daemon getDaemon(DaemonEntry entry) throws DaemonException {

    // FIX ME: before instantiating a daemon ... find out if it is already setup

    Daemon daemon = (Daemon) this.daemons.get(entry);

    if (daemon != null) {
      return daemon;
    } else {
      logger.info("Creating daemon: " + entry.getName());
    }

    try {

      daemon = (Daemon) Class.forName(entry.getClassname()).newInstance();

      DaemonConfig dc = new DaemonConfig();

      daemon.init(dc, entry);

      this.daemons.put(entry, daemon);

      return daemon;

    } catch (ClassNotFoundException e) {
      logger.error("Exception", e);
      throw new DaemonException("daemon not found: " + e.getMessage());
    } catch (InstantiationException e) {
      logger.error("Exception", e);
      throw new DaemonException("couldn't instantiate daemon: "
          + e.getMessage());
    } catch (IllegalAccessException e) {
      logger.error("Exception", e);
      throw new DaemonException(e.getMessage());
    }

  }

  /**
   * Get a daemon with the given classname.
   * 
   * @see #getDaemon( DaemonEntry entry )
   */
  public Daemon getDaemon(String classname) throws DaemonException {

    DaemonEntry[] entries = this.getDaemonEntries();

    for (int i = 0; i < entries.length; ++i) {
      if (entries[i].getClassname().equals(classname)) {
        return getDaemon(entries[i]);
      }
    }

    throw new DaemonException("daemon not found: " + classname);

  }

  /**
   */
  public DaemonContext getDaemonContext() {
    return this.context;
  }

  /**
   * Kicks of processing of a Daemon. Does the same thing as getDaemon() but
   * also creates a thread and runs the daemon.
   */
  public void process(DaemonEntry entry) throws DaemonException {

    DaemonThread dt = (DaemonThread) this.threads.get(entry);

    if (dt == null) {
      start(entry);
      dt = (DaemonThread) this.threads.get(entry);
    }

    // FIX ME: get the status of this daemon before kicking it off again.
    int status = this.getStatus(entry);

    if (status != Daemon.STATUS_PROCESSING && status != Daemon.STATUS_UNKNOWN
        && dt != null) {
      // tell this thread to stop waiting and process immediately
      synchronized (dt) {
        dt.notify();
      }

    }

    if (dt != null && dt.isAlive() == false) {
      dt.start();
    }

  }

  /**
   */
  public int getStatus(DaemonEntry entry) {

    try {
      Daemon daemon = this.getDaemon(entry);
      return daemon.getStatus();
    } catch (DaemonException e) {
      logger.error("Exception", e);
      return Daemon.STATUS_UNKNOWN;
    }
  }

  /**
   * Get the last known result of the given DaemonEntry's processing
   */
  public int getResult(DaemonEntry entry) {

    try {
      Daemon daemon = this.getDaemon(entry);
      return daemon.getResult();
    } catch (DaemonException e) {
      logger.error("Exception", e);
      return Daemon.RESULT_UNKNOWN;
    }

  }

  /**
   * Get the last known message of the given DaemonEntry's processing
   */
  public String getMessage(DaemonEntry entry) {

    try {
      Daemon daemon = this.getDaemon(entry);
      return daemon.getMessage();
    } catch (DaemonException e) {
      logger.error("Exception", e);
      return null;
    }
  }

  /**
   * Get the current known DaemonEntries within the DaemonFactory
   */
  public DaemonEntry[] getDaemonEntries() {
    return this.entries;
  }

  /**
   * Given the name of a DaemonEntry... get it from the DaemonFactory
   */
  public DaemonEntry getDaemonEntry(String name) throws DaemonNotFoundException {

    DaemonEntry[] entries = this.getDaemonEntries();
    for (int i = 0; i < entries.length; ++i) {
      if (entries[i].getName().equals(name)) {
        return entries[i];
      }
    }

    throw new DaemonNotFoundException("Could not find daemon named: " + name);
  }

}
