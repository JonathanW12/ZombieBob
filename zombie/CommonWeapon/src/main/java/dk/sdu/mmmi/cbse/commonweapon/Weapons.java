package dk.sdu.mmmi.cbse.commonweapon;

import dk.sdu.mmmi.cbse.common.services.IWeaponCreatorService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.openide.util.Lookup;

public class Weapons {
    private final Lookup lookup = Lookup.getDefault();
    private List<IWeaponCreatorService> weapons;
    private static Weapons instance;
    
    private Weapons() {
        weapons = new ArrayList<>(getWeaponModules());
    }
    
    public static Weapons getInstance() {
        if (instance == null) {
            instance = new Weapons();
        }
        
        return instance;
    }
    
    public List<IWeaponCreatorService> getWeapons() {
        return weapons;
    }   
    
    private Collection<? extends IWeaponCreatorService> getWeaponModules() {
        return lookup.lookupAll(IWeaponCreatorService.class);
    }
}
