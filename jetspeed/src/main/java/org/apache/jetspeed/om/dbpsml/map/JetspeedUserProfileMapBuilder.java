package org.apache.jetspeed.om.dbpsml.map;

import java.util.Date;
import java.math.BigDecimal;

import org.apache.torque.Torque;
import org.apache.torque.TorqueException;
import org.apache.torque.map.MapBuilder;
import org.apache.torque.map.DatabaseMap;
import org.apache.torque.map.TableMap;

/**
  *  This class was autogenerated by Torque on:
  *
  * [Thu Jun 10 23:17:32 JST 2004]
  *
  */
public class JetspeedUserProfileMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "org.apache.jetspeed.om.dbpsml.map.JetspeedUserProfileMapBuilder";


    /**
     * The database map.
     */
    private DatabaseMap dbMap = null;

    /**
     * Tells us if this DatabaseMapBuilder is built so that we
     * don't have to re-build it every time.
     *
     * @return true if this DatabaseMapBuilder is built
     */
    public boolean isBuilt()
    {
        return (dbMap != null);
    }

    /**
     * Gets the databasemap this map builder built.
     *
     * @return the databasemap
     */
    public DatabaseMap getDatabaseMap()
    {
        return this.dbMap;
    }

    /**
     * The doBuild() method builds the DatabaseMap
     *
     * @throws TorqueException
     */
    public void doBuild() throws TorqueException
    {
        dbMap = Torque.getDatabaseMap("default");

        dbMap.addTable("JETSPEED_USER_PROFILE");
        TableMap tMap = dbMap.getTable("JETSPEED_USER_PROFILE");

        tMap.setPrimaryKeyMethod(TableMap.NATIVE);

        tMap.setPrimaryKeyMethodInfo("JETSPEED_USER_PROFILE");

              tMap.addPrimaryKey("JETSPEED_USER_PROFILE.PSML_ID", new Integer(0));
                    tMap.addColumn("JETSPEED_USER_PROFILE.USER_NAME", new String());
                    tMap.addColumn("JETSPEED_USER_PROFILE.MEDIA_TYPE", new String());
                    tMap.addColumn("JETSPEED_USER_PROFILE.LANGUAGE", new String());
                    tMap.addColumn("JETSPEED_USER_PROFILE.COUNTRY", new String());
                    tMap.addColumn("JETSPEED_USER_PROFILE.PAGE", new String());
                    tMap.addColumn("JETSPEED_USER_PROFILE.PROFILE", new Object());
          }
}
