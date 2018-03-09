package ee.ttu.iti0202.factory;

import ee.ttu.iti0202.orb.Orb;
import ee.ttu.iti0202.oven.Oven;
import ee.ttu.iti0202.storage.ResourceStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrbFactory {
    private ResourceStorage storage;
    private List<Oven> ovens = new ArrayList<>();
    private List<Orb> producedOrbs = new ArrayList<>();

    public OrbFactory(ResourceStorage resourceStorage) {
        storage = resourceStorage;
    }

    public void addOven(Oven oven) {
        // oven isn't in list and oven has same storage as factory
        if (!ovens.contains(oven) && oven.getResourceStorage() == storage) {
            ovens.add(oven);
        }
    }

    public List<Oven> getOvens() {
        return ovens;
    }

    public int produceOrbs() {
        int start = producedOrbs.size();

        for (Oven o : ovens) {
            Optional<Orb> orbOptional = o.craftOrb();
            if (orbOptional.isPresent()) {
                producedOrbs.add(orbOptional.get());
            }
        }
        return producedOrbs.size() - start;
    }

    public int produceOrbs(int cycles) {
        int counter = 0;
        for (int i = 0; i < cycles; i++) {
            counter += produceOrbs();
        }
        return counter;
    }

    public List<Orb> getAndClearProducedOrbsList() {
        if (producedOrbs.size() > 0) {
            List<Orb> result = new ArrayList<>(producedOrbs);
            producedOrbs.clear();
            return result;
        }
        return producedOrbs;
    }
}
