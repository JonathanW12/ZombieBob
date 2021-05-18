package org.netbeans.modules.autoupdate.silentupdate;

/**
 *
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.openide.modules.ModuleInstall;
import org.openide.util.Exceptions;
/**
 * Manages a module's lifecycle. Remember that an installer is optional and
 * often not needed at all.
 */
public class UpdateActivator extends ModuleInstall {

    private final ScheduledExecutorService exector = Executors.newScheduledThreadPool(1);

    @Override
    public void restored() {
        try {
            setBundleProperties();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        exector.scheduleAtFixedRate(doCheck, 5000, 5000, TimeUnit.MILLISECONDS);
    }

        private void setBundleProperties() throws IOException {
        File filehandle;
        //Getting bundle.properties
        String bundleRelativeLocation = "SilentUpdate\\src\\main\\resources\\org\\netbeans\\modules\\autoupdate\\silentupdate\\resources\\Bundle.properties";
        String userDir = System.getProperty("user.dir");
        String bundleLocation = userDir.replace("application\\target\\zombie", bundleRelativeLocation);
        //Getting xml
        String updatesRelativeLocation = "application\\netbeans_site\\updates.xml";
        String updatesLocation = userDir.replace("application\\target\\zombie", updatesRelativeLocation);
        updatesLocation = updatesLocation.replaceAll("\\\\", "/");
        updatesLocation = "file"+":///"+updatesLocation;
        
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            filehandle = new File(bundleLocation);
        } else {
            //maybe doesnt work on linux :-)
            filehandle = new File(bundleLocation);
        }
        FileInputStream in;
        try {
        in = new FileInputStream(filehandle);
        Properties props = new Properties();
        props.load(in);
        in.close();

        FileOutputStream out = new FileOutputStream(filehandle);
        props.setProperty("org_netbeans_modules_autoupdate_silentupdate_update_center", updatesLocation);
        props.store(out, null);
        out.close();
        }catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
    private static final Runnable doCheck = new Runnable() {
        @Override
        public void run() {
            if (UpdateHandler.timeToCheck()) {
                UpdateHandler.checkAndHandleUpdates();
            }
        }

    };

    @Override
    public void uninstalled() {
        super.uninstalled(); //To change body of generated methods, choose Tools | Templates.
    }

}
