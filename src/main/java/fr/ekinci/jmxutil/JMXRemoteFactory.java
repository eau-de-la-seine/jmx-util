package fr.ekinci.jmxutil;

import java.lang.management.ManagementFactory;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import org.apache.log4j.Logger;


/**
 * <p>Static class for adding MBean/MXBean</p>
 * Before launching use those parameters :
    -Dlog4j.configuration={path to file} or file
    -Dcom.sun.management.jmxremote.port=9999
    -Dcom.sun.management.jmxremote.authenticate=false
    -Dcom.sun.management.jmxremote.ssl=false
 * @author Gokan EKINCI
 */
public class JMXRemoteFactory {
    private final static Logger LOG = Logger.getLogger(JMXRemoteFactory.class);
    
    /**
     * <p>A method which add an MBean/MXBean object</p>
     * <p>The object must respect MBean or MXBean conventions</p>
     * @param mbean
     */
    public static <T> void addMBean(T mbean) {
        try {
            String packageName = mbean.getClass().getPackage().getName();
            String className = mbean.getClass().getSimpleName();
            ObjectName on = new ObjectName(packageName + ":type=" + className);
            MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
            mbeanServer.registerMBean(mbean, on);
        } catch (MalformedObjectNameException | InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException e) {            
            LOG.fatal("A fatal error happened during MBean/MXBean adding", e);
            System.exit(-1);
        }
    }
}
