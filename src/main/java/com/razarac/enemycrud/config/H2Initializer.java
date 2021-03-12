package com.razarac.enemycrud.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.razarac.enemycrud.entities.*;
import com.razarac.enemycrud.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class H2Initializer implements ApplicationRunner {

    @Autowired
    private EnemyCrudRepository enemyCrudRepository;

    @Autowired
    private ElementCrudRepository elementCrudRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // ------------------------------------------------------ //
        // ---------------- DARK SOULS ELEMENTS ----------------- //
        // ------------------------------------------------------ //
        List<String> elementNames = new ArrayList<String>();
        elementNames.addAll(List.of("Magic", "Fire", "Lightning", "None", "Dark", "Poison", "Toxic", "Bleed", "Standard", "Strike", "Thrust", "Slash"));

        // Add special weapons/items/cheese stuff here
        var specialNames = new ArrayList<String>();
        specialNames.addAll(List.of("Ledge", "Backstab", "Parry", "Bracelets", "Farron Greatsword", "Plunge Attack", "Choas Blade", "Lifehunt Scythe"));

        elementNames.addAll(specialNames);

        List<EEnemyElement> elements = buildElements(elementNames);
        elementCrudRepository.saveAll(elements);
        
        // ------------------ BOSS ATTRIBUTES ------------------- //
        String name = "";
        String image = "";
        String description = "";
        List<String> weak = Collections.<String> emptyList();
        List<String> resist = Collections.<String> emptyList();
        List<String> immune = Collections.<String> emptyList();
        EEnemy enemy;

        // ------------------------------------------------------ //
        // ------------------- DARK SOULS ONE ------------------- //
        // ------------------------------------------------------ //
        /* ASYLUM DEMON */
        name = "Asylum Demon";
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTGuSM9kU9gkhj3qTm7sRCpY7NAJ3nIRA3fkw&usqp=CAU";
        description = "Either run through the door on the left to fight it later, or attack without locking on.";
        weak = List.of("Fire", "Bleed");
        resist = List.of("None");
        immune = List.of("None");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* BELL GARGOYLE */
        name = "Bell Gargoyle";
        image = "https://i.pinimg.com/originals/7e/f5/04/7ef5043586728dc2bdf39d957bf84760.jpg";
        description = "There are two. Cut off the tail of one of them.";
        weak = List.of("Fire, Lightning");
        resist = List.of("None");
        immune = List.of("None");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* CAPRA DEMON */
        name = "Capra Demon";
        image = "https://i.pinimg.com/originals/df/ba/de/dfbade1cf0472d71ded6141668327d6a.jpg";
        description = "Focus entirely on killing the dogs, then deal with the demon. Careful at the back, it\'s hard to see.";
        weak = List.of("Fire", "Plunge Attack");
        resist = List.of("None");
        immune = List.of("None");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* CEASELESS DISCHARGE */
        name = "Ceaseless Discharge";
        image = "https://farm4.static.flickr.com/3919/14399720551_32a2d17935.jpg";
        description = "Loot the corpse, run all the way back to the start, and slap him after he jumps over the chasm and grabs hold of the ledge.";
        weak = List.of("Ledge");
        resist = List.of("Fire");
        immune = List.of("None");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* CENTIPEDE DEMON */
        name = "Centipede Demon";
        image = "https://static1.thegamerimages.com/wordpress/wp-content/uploads/2020/06/Centipede-Demon.jpg?q=50&fit=crop&w=740&h=370";
        description = "Cut off the tail to get the Orange Charred Ring and beat him up, or expect to use a bow or magic.";
        weak = List.of("Magic, Lightning");
        resist = List.of("Fire");
        immune = List.of("None");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* CHAOS WITCH QUELAAG */
        name = "Chaos Witch Quelaag";
        image = "http://soulslore.wdfiles.com/local--resized-images/data:chaos-witch-quelaag/2015-09-24_00082.jpg/medium.jpg";
        description = "Lots of fire moves, be careful of the lava as it lingers and the explosion AOE. Hit the female body to stagger her.";
        weak = List.of("Lightning");
        resist = List.of("None");
        immune = List.of("Fire");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* CROSSBREED PRISCILLA */
        name = "Crossbreed Priscilla";
        image = "http://soulslore.wdfiles.com/local--resized-images/data:crossbreed-priscilla/Untitled-1.jpg/medium.jpg";
        description = "She turns invisible if you hurt her (a sin btw). Cut off her tail asap. Look for footprints and attack there. Avoid OHKO moves as that softlocks the game.";
        weak = List.of("Poison");
        resist = List.of("None");
        immune = List.of("None");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* DARK SUN GWYNDOLIN */
        name = "Dark Sun Gwyndolin";
        image = "http://vignette1.wikia.nocookie.net/darksouls/images/1/1f/Gwyndolin_Ingame.jpg/revision/latest?cb=20160630212846";
        description = "This is a marathon where you run, get in some hits, and then avoid getting shot after he teleports.";
        weak = List.of("Poison");
        resist = List.of("None");
        immune = List.of("None");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* DEMON FIRESAGE */
        name = "Demon Firesage";
        image = "https://static.wikia.nocookie.net/darksouls/images/a/aa/Demon_Firesage.jpg/revision/latest?cb=20160608105549";
        description = "Asylum Demon but red tbh. Staying behind him is the name of the game.";
        weak = List.of("Magic", "Bleed");
        resist = List.of("None");
        immune = List.of("None");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* FOUR KINGS */
        name = "Four Kings";
        image = "https://farm6.static.flickr.com/5560/14379826436_9cd9f916b9.jpg";
        description = "There\'s actually 5 of them lol, appearing 1 at a time every 45 seconds. Stay close when attacking and focus on 1 at a time. It\'s all magic damage.";
        weak = List.of("Chaos Blade", "Lifehunt Scythe");
        resist = List.of("Bleed");
        immune = List.of("None");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* GAPING DRAGON */
        name = "Gaping Dragon";
        image = "http://darksouls.wikidot.com/local--files/bosses/gaping-dragon-large.jpg";
        description = "Sprinting and rolling is good here. Avoid the acid. Kill the Channeler before starting this fight. Cut the tail.";
        weak = List.of("None");
        resist = List.of("None");
        immune = List.of("None");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

    }

    private void saveEnemy(EEnemy enemy) {
        for (EEnemyElement element : enemy.getWeaknesses()) {
            element.getWeakEnemies().add(enemy);
        }
        for (EEnemyElement element : enemy.getResistances()) {
            element.getResistEnemies().add(enemy);
        }
        for (EEnemyElement element : enemy.getImmunities()) {
            element.getImmuneEnemies().add(enemy);
        }
        enemyCrudRepository.save(enemy);
    }

    private EEnemy buildEnemy(EEnemy enemy, List<String> weak, List<String> resist, List<String> immune) {
        List<EEnemyElement> result = new ArrayList<EEnemyElement>();

        for (String elementName : weak) {
            result.addAll(elementCrudRepository.findByName(elementName));
            enemy.setWeaknesses(result);
            result.clear();
        }
        for (String elementName : resist) {
            result.addAll(elementCrudRepository.findByName(elementName));
            enemy.setResistances(result);
            result.clear();
        }
        for (String elementName : immune) {
            result.addAll(elementCrudRepository.findByName(elementName));
            enemy.setImmunities(result);
            result.clear();
        }

        return enemy;
    }

    private List<EEnemyElement> buildElements(List<String> elements) {
        List<EEnemyElement> result = new ArrayList<EEnemyElement>(); 

        for (String name : elements) {
            EEnemyElement element = EEnemyElement.builder().name(name).build(); 
            element.setWeakEnemies(new ArrayList<EEnemy>());
            element.setResistEnemies(new ArrayList<EEnemy>());
            element.setImmuneEnemies(new ArrayList<EEnemy>());
            
            result.add(element);
        }

        return result;
    }
    
}
