package fr.pe.domaine.peactions.config;

import fr.pe.domaine.peactions.emploistoredev.ressources.RomeESD;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class RomeCacheListener implements CacheEventListener<String, List<RomeESD>> {

    private static final Logger LOG = LoggerFactory.getLogger(RomeCacheListener.class);

    @Override
    public void onEvent(CacheEvent cacheEvent) {
        LOG.info("Mise en caches des romes {} {} {} {} ", cacheEvent.getType(), cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
    }
}
