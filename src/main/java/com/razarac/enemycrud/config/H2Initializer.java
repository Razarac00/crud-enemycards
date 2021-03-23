package com.razarac.enemycrud.config;

import java.util.ArrayList;
// import java.util.Collections;
import java.util.List;

import com.razarac.enemycrud.entities.*;
import com.razarac.enemycrud.repository.*;

import org.hibernate.collection.internal.PersistentBag;
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
        specialNames.addAll(List.of("Ledge", "Backstab", "Parry", "Bracelets", "Farron Greatsword", "Plunge Attack", "Chaos Blade", "Lifehunt Scythe",
            "Divine", "Blessed"));

        elementNames.addAll(specialNames);

        List<EEnemyElement> elements = buildElements(elementNames);
        elementCrudRepository.saveAll(elements);
        
        // ------------------ BOSS ATTRIBUTES ------------------- //
        String name = "";
        String image = "";
        String description = "";
        List<String> weak = new ArrayList<String>();
        List<String> resist = new ArrayList<String>();
        List<String> immune = new ArrayList<String>(); //Collections.<String> emptyList();
        EEnemy enemy;

        // ------------------------------------------------------ //
        // ------------------- DARK SOULS ONE ------------------- //
        // ------------------------------------------------------ //
        /* ASYLUM DEMON */
        name = "Asylum Demon";
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTGuSM9kU9gkhj3qTm7sRCpY7NAJ3nIRA3fkw&usqp=CAU";
        description = "Either run through the door on the left to fight it later, or attack without locking on.";
        weak = List.of("Fire", "Bleed", "Poison", "Toxic");
        resist = List.of("None");
        immune = List.of("None");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* BELL GARGOYLE */
        name = "Bell Gargoyle";
        image = "https://i.pinimg.com/originals/7e/f5/04/7ef5043586728dc2bdf39d957bf84760.jpg";
        description = "There are two. Cut off the tail of one of them.";
        weak = List.of("Fire, Lightning", "Poison", "Toxic");
        resist = List.of("Bleed");
        immune = List.of("None");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* CAPRA DEMON */
        name = "Capra Demon";
        image = "https://i.pinimg.com/originals/df/ba/de/dfbade1cf0472d71ded6141668327d6a.jpg";
        description = "Focus entirely on killing the dogs, then deal with the demon. Careful at the back, it\'s hard to see.";
        weak = List.of("Fire", "Plunge Attack", "Poison", "Toxic");
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
        weak = List.of("Magic, Lightning", "Poison", "Toxic");
        resist = List.of("Fire");
        immune = List.of("None");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* CHAOS WITCH QUELAAG */
        name = "Chaos Witch Quelaag";
        image = "http://soulslore.wdfiles.com/local--resized-images/data:chaos-witch-quelaag/2015-09-24_00082.jpg/medium.jpg";
        description = "Lots of fire moves, be careful of the lava as it lingers and the explosion AOE. Hit the female body to stagger her.";
        weak = List.of("Lightning");
        resist = List.of("Poison");
        immune = List.of("Fire");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* CROSSBREED PRISCILLA */
        name = "Crossbreed Priscilla";
        image = "http://soulslore.wdfiles.com/local--resized-images/data:crossbreed-priscilla/Untitled-1.jpg/medium.jpg";
        description = "She turns invisible if you hurt her (a sin btw). Cut off her tail asap. Look for footprints and attack there. Avoid OHKO moves as that softlocks the game.";
        weak = List.of("Poison", "Toxic");
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
        weak = List.of("Magic", "Bleed", "Poison", "Toxic");
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
        resist = List.of("Poison");
        immune = List.of("None");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* GREAT GREY WOLF SIF */
        name = "Great Grey Wolf Sif";
        image = "http://darksouls.wikidot.com/local--files/npcs/sif-the-great-grey-wolf-large.jpg";
        description = "I won\'t ask why you\'re killing the dog. Melee builds get under him, Ranged builds kite and time your shots.";
        weak = List.of("Poison");
        resist = List.of("None");
        immune = List.of("None");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* GWYN LORD OF CINDER */
        name = "Gwyn Lord of Cinder";
        image = "https://cdn.jwplayer.com/v2/media/gVp051iT/poster.jpg?width=720";
        description = "Learn his moves. Do not hesitate, do not panic. He is fast.";
        weak = List.of("Parry");
        resist = List.of("Lightning", "Bleed");
        immune = List.of("Poison", "Toxic");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* IRON GOLEM */
        name = "Iron Golem";
        image = "http://darksouls.wikidot.com/local--files/bosses/iron-golem.jpg";
        description = "Kill the firebombing giant first. Other than that, go for the legs until you can go for the head, or ledge him.";
        weak = List.of("Ledge");
        resist = List.of("None");
        immune = List.of("Poison", "Toxic", "Bleed");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* MOONLIGHT BUTTERFLY */
        name = "Moonlight Butterfly";
        image = "https://i.redd.it/3uerx40y4wr51.jpg";
        description = "Expect a ranged fight unless you keep it in front of you as much as possible. Then it may land and you can melee.";
        weak = List.of("Magic", "Lightning", "Fire");
        resist = List.of("None");
        immune = List.of("Poison", "Toxic", "Bleed");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* NITO */
        name = "Nito";
        image = "https://static.wikia.nocookie.net/darksouls/images/6/63/Nito_intro.jpg/revision/latest/top-crop/width/220/height/220?cb=20130206001123";
        description = "Easily strafed. Either stay out of aggro range of his skeleton minions, or kill em with divine weapons.";
        weak = List.of("Fire");
        resist = List.of("None");
        immune = List.of("Poison", "Toxic");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* ORNSTEIN AND SMOUGH */
        name = "Ornstein and Smough";
        image = "https://i.ytimg.com/vi/ywoEbgdDfqI/maxresdefault.jpg";
        description = "You\'re gonna have to get good. Use the pillars. Kill the 1 you don\'t want to fight twice first.";
        weak = List.of("Fire", "Poison", "Toxic");
        resist = List.of("None");
        immune = List.of("None");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* PINWHEEL */
        name = "Pinwheel";
        image = "https://static.wikia.nocookie.net/darksouls/images/2/2f/Pinwheel_Ingame.jpg/revision/latest/top-crop/width/300/height/300?cb=20160614065326";
        description = "It shoots fireballs and clones itself, but if you hit it, it dies lol.";
        weak = List.of("Fire");
        resist = List.of("None");
        immune = List.of("None");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* SEATH THE SCALELESS */
        name = "Seath the Scaleless";
        image = "https://static.wikia.nocookie.net/darksouls/images/1/1f/Seath_the_scaleless.jpg/revision/latest?cb=20121112141611";
        description = "First encounter is guaranteed death. Second time go after his crystal and his tail and give him the run around.";
        weak = List.of("Lightning", "Magic", "Fire");
        resist = List.of("None");
        immune = List.of("Poison", "Toxic");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* STRAY DEMON */
        name = "Stray Demon";
        image = "http://darksouls.wikidot.com/local--files/bosses/stray-demon-large.jpg";
        description = "You\'ll take damage falling down into his arena. Avoid locking on and attack his backside.";
        weak = List.of("Fire", "Bleed", "Poison", "Toxic", "Lifehunt Scythe");
        resist = List.of("None");
        immune = List.of("None");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* TAURUS DEMON */
        name = "Taurus Demon";
        image = "http://darksouls.wikidot.com/local--files/bosses/taurus-demon-large.jpg";
        description = "Draw him near the tower and then plunge attack him. Note he will jump onto the tower if you stay up there too long.";
        weak = List.of("Fire", "Lightning", "Plunge Attack", "Poison", "Toxic");
        resist = List.of("None");
        immune = List.of("None");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* THE BED OF CHAOS */
        name = "The Bed of Chaos";
        image = "https://static.wikia.nocookie.net/darksouls/images/7/75/BOC.jpg/revision/latest?cb=20121122165225";
        description = "Destroy the glowing roots, enjoy leaping into the bed and dealing with the bug.";
        weak = List.of("None");
        resist = List.of("None");
        immune = List.of("Fire");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        // ----------------- DARK SOULS ONE DLC ----------------- //

        /* ARTORIAS THE ABYSSWALKER */
        name = "Artorias the Abysswalker";
        image = "http://darksouls.wikidot.com/local--files/bosses/knight-artorias-large.jpg";
        description = "Time your dodges, keep distance, and be careful when he buffs himself. Don\'t get greedy--Artorias controls tha pace of this fight, not you.";
        weak = List.of("None");
        resist = List.of("None");
        immune = List.of("Poison", "Toxic");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* BLACK DRAGON KALAMEET */
        name = "Black Dragon Kalameet";
        image = "http://darksouls.wikidot.com/local--files/bosses/black-dragon-kalameet-large.jpg";
        description = "Does magic and physical damage. Cut the tail. Some attacks are better blocked than dodged. His face is weak to projectiles.";
        weak = List.of("None");
        resist = List.of("None");
        immune = List.of("Poison", "Toxic");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* MANUS, FATHER OF THE ABYSS */
        name = "Manus, Father of the Abyss";
        image = "https://static.wikia.nocookie.net/darksouls/images/f/fa/Manus%2C_Father_of_the_Abyss_Ingame.jpg/revision/latest/top-crop/width/360/height/450?cb=20160702022012";
        description = "Maintain a distance that lets you react to his attacks. Note that his dark circle attack can be avoided by standing close to him. Arrows to the head stunlock.";
        weak = List.of("None");
        resist = List.of("Magic", "Lightning", "Fire");
        immune = List.of("Poison", "Toxic", "Bleed");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* SANCTUARY GAURDIAN */
        name = "Sanctuary Gaurdian";
        image = "https://static.wikia.nocookie.net/darksouls/images/3/34/Sanctuary_Guardian.png/revision/latest?cb=20130812200330";
        description = "Cut the tail. Time your dodges, and be prepared to run around. Melee builds with good reaction speeds are advised.";
        weak = List.of("Poison", "Toxic", "Bleed", "Fire");
        resist = List.of("Lightning", "Magic");
        immune = List.of("None");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));
    }

    private void saveEnemy(EEnemy enemy) {
        // enemyCrudRepository.save(enemy);
        for (EEnemyElement element : enemy.getWeaknesses()) {
            // elementCrudRepository.findById(element.getId()).ifPresent((e) -> {e.getWeakEnemies().add(enemy);});
            if (element.getWeakEnemies() instanceof PersistentBag) {
                element.setWeakEnemies(new ArrayList<EEnemy>(List.of(enemy)));
            } else {
                element.getWeakEnemies().add(enemy);
            }
        }
        for (EEnemyElement element : enemy.getResistances()) {
            // elementCrudRepository.findById(element.getId()).ifPresent((e) -> {e.getResistEnemies().add(enemy);});
            if (element.getResistEnemies() instanceof PersistentBag) {
                element.setResistEnemies(new ArrayList<EEnemy>(List.of(enemy)));
            } else {
                element.getResistEnemies().add(enemy);
            }
        }
        for (EEnemyElement element : enemy.getImmunities()) {
            // elementCrudRepository.findById(element.getId()).ifPresent((e) -> {e.getImmuneEnemies().add(enemy);});
            if (element.getResistEnemies() instanceof PersistentBag) {
                element.setImmuneEnemies(new ArrayList<EEnemy>(List.of(enemy)));
            } else {
                element.getImmuneEnemies().add(enemy);
            }
        }
        // enemyCrudRepository.save(enemy);
        
    }

    private EEnemy buildEnemy(EEnemy enemy, List<String> weak, List<String> resist, List<String> immune) {
        List<EEnemyElement> result = new ArrayList<EEnemyElement>();
        enemyCrudRepository.save(enemy);

        for (String elementName : weak) {
            result.addAll(elementCrudRepository.findByName(elementName));
            if (enemy.getWeaknesses() == null) {
                enemy.setWeaknesses(new ArrayList<EEnemyElement>(result));
                // result.get(0).setWeakEnemies(new ArrayList<EEnemy>(List.of(enemy)));
            } else {
                enemy.getWeaknesses().addAll(result);
                // result.get(0).getWeakEnemies().add(enemy);
            }
            result.clear();
        }
        

        for (String elementName : resist) {
            result.addAll(elementCrudRepository.findByName(elementName));
            if (enemy.getResistances() == null) {
                enemy.setResistances(new ArrayList<EEnemyElement>(result));
                // result.get(0).setResistEnemies(new ArrayList<EEnemy>(List.of(enemy)));
            } else {
                enemy.getResistances().addAll(result);
                // result.get(0).getResistEnemies().add(enemy);
            }
            result.clear();
        }
        
        for (String elementName : immune) {
            result.addAll(elementCrudRepository.findByName(elementName));
            if (enemy.getImmunities() == null) {
                enemy.setImmunities(new ArrayList<EEnemyElement>(result));
            } else {
                enemy.getImmunities().addAll(result);
            }
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
