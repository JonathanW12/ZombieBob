package dk.sdu.mmmi.cbse.zombiebobmodules;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import junit.framework.Test;
import org.netbeans.junit.NbModuleSuite;
import org.netbeans.junit.NbTestCase;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
/**
 *
 * @author jonaw
 */
public class ApplicationTest extends NbTestCase{
    
    private static final String addCollision = "src\\test\\resources\\wcollision\\updates.xml";
    private static final String noCollision = "src\\test\\resources\\nocollision\\updates.xml";
    private final String updates = getxmlPath();
    
    public static Test suite(){
        return NbModuleSuite.createConfiguration(ApplicationTest.class).
                gui(false).
                enableClasspathModules(false).
                clusters(".*").
                suite();
    }
    
    public ApplicationTest(String name) throws IOException {
        super(name);
    }
    
    public void testApplication() throws InterruptedException, IOException {
        List<IEntityProcessingService> processors = new CopyOnWriteArrayList<>();
        List<IGamePluginService> plugins = new CopyOnWriteArrayList<>();
        
        //Getting start ammount of modules
        copy(get(addCollision),get(updates),REPLACE_EXISTING);
        waitForUpdate(processors, plugins);
        int startingAmmountProcessors = processors.size();
        int startingAmmountPlugins = plugins.size();
        
        //System.out.println("Processors : "+ startingAmmountProcessors + "\n Plugings : " + startingAmmountPlugins);
        assertFalse("Updates.xml is setup incorrectly", 0 == startingAmmountProcessors );
        System.out.println("Ammount of Processors1 : "+processors.size() + "\n"+processors );
        
        //Testing no collision
        copy(get(noCollision),get(updates),REPLACE_EXISTING);
        waitForUpdate(processors, plugins);
        System.out.println("Ammount of Processors2 : "+processors.size() + "\n"+processors );
        //assertEquals("Collision Not removed from Plugins",startingAmmountPlugins-1 ,plugins.size());
        assertEquals("Collision Not removed from Processors",startingAmmountProcessors-1 ,processors.size());

        //Adding collision again
        copy(get(addCollision),get(updates),REPLACE_EXISTING);
        waitForUpdate(processors, plugins);
        System.out.println("Ammount of Processors3 : "+processors.size() + "\n"+processors );
        assertEquals("Collision Added incorectly to Processors",startingAmmountProcessors ,processors.size());
        assertEquals("Collision Added correctly to Plugins",startingAmmountPlugins ,plugins.size());

    }
    
    private void waitForUpdate(List<IEntityProcessingService> processors, List<IGamePluginService> plugins) throws InterruptedException{
        Thread.sleep(5000);
        processors.clear();
        processors.addAll(Lookup.getDefault().lookupAll(IEntityProcessingService.class));
        plugins.clear();
        plugins.addAll(Lookup.getDefault().lookupAll(IGamePluginService.class));
    }
    private String getxmlPath(){
        String userDir = System.getProperty("user.dir");
        String updatesRelativeLocation = "application\\netbeans_site\\updates.xml";
        String path = userDir.replace("application", updatesRelativeLocation);
        return path;
    }
}
