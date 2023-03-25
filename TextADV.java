import java.util.Scanner;

import javax.sound.sampled.SourceDataLine;

import java.util.Random;
//STARWARS BASED 
    // ideeën:
    //[x] Gebouwen: wapen shop, food shop, inventory
    //[x] functie toets E = Enter - exit store
    //[x] monsters: chicken, cow, goblin, troll
    //[x] monster difficulty
    //[x] Boss: 4 different bosses that spawn randomly and have different difficulties.
    //[x] levels: Start on level 1. level2=200xp level3=400xp and so on
    //[x] levels: experience = random per monster
    //[x] levels: higher level = higher random loot
    //[x] levels: higher level = higher random damage
    //[x] gear: add sword, shield and armor
    //[x] gear: add set effect when wielding armor and shield, when both better effect
    //[ ] if playerWeapon = 0 bareHands  1=knife        2=SwordOfPower 3=Excalibur(holyKnightBoss) 4=hammerOfThor(thor boss)
    //[ ] if playerShield = 0 noShield   1=ironShield   2=steelShield  3=knightShield              4=dragonShield(dragonboss)
    //[ ] if playerArmor  = 0 noArmor    1=leatherArmor 2=ironArmor    3=steelArmor                4=SamuraiArmor(samuraiboss)
    
    //[x] inventory: check your potions and gear
    //[x] hp function to shop current player hp
    //[x] Gameover Function
    //[ ] Name Function, Choose a name at the beginning and use it with every print.


    public class TextADV {

    // Initialize map types
    final int water         = 1;
    final int grass         = 2;
    final int rock          = 3;
    final int forest        = 4;
    final int monster       = 5;
    final int boss          = 6;
    final int blacksmith    = 7;
    final int shop          = 8;
    

    final int maxBlacksmith = 1;
    final int maxMonsters   = 20;
    final int maxBosses     = 4;

    // initialyze map max blocks
    final int mapwidth      = 20;
    final int mapheight     = 20;

    //Random
    Random Random = new Random();
    

    //player variables
    int healthPotionHealAmount  = Random.nextInt(15, 30);
    int playerHealth        = 100;
    int playerMoney         = 0;
    int numHealthPotions    = 3;
    int playerSword         = 0;
    int playerShield        = 0;
    int playerArmor         = 0;
    int playerLevel         = 1;
    int playerExperience    = 0;

    // map
    int[][] map = {
        { 1, 5, 1, 1, 1, 1, 1, 1, 1, 1 }, //1
        { 7, 2, 6, 2, 2, 2, 2, 2, 2, 1 }, //2
        { 1, 8, 2, 2, 2, 2, 2, 2, 2, 1 }, //3
        { 1, 2, 2, 2, 2, 2, 2, 2, 2, 1 }, //4
        { 1, 2, 2, 2, 2, 2, 2, 2, 2, 1 }, //5
        { 1, 2, 2, 2, 2, 2, 2, 2, 2, 1 }, //6
        { 1, 2, 2, 2, 2, 2, 2, 2, 2, 1 }, //7
        { 1, 2, 2, 2, 2, 2, 2, 2, 2, 1 }, //8
        { 1, 2, 2, 2, 2, 2, 2, 2, 2, 1 }, //9
        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }  //10
    };

    // player setup
    int playerRow; // rij waar de speler is
    int playerCol; // kolom waar de speler is

    // tools
    Scanner scanner;
    final String textSeperator = "*******************************************************************";
    
    public static void main(String args[]) // static method
    {
        TextADV ta = new TextADV();
        ta.init();
        ta.mainLoop();
    } 
    public boolean executeInput(String input) {
    // Stap omhoog
    if (input.equals("w")){
        if(playerRow > 0) {
            playerRow--;
            return true;
        }
        else{
            System.out.println("You can't go here.");
            return false;
        }
    }
    // Stap omlaag
    if (input.equals("s")){
        if(playerRow < mapheight - 1) {
            playerRow++;
            return true;
        }
        else{
            System.out.println("You can't go here.");
            return false;
        }
    }
    // Stap naar rechts
    if (input.equals("d")){
        if(playerCol < mapwidth - 1) {
            playerCol++;
            return true;
        }
        else{
            System.out.println("You can't go here.");
            return false;
        }
    }
    // Stap naar links
    if (input.equals("a")){
        if(playerCol > 0) {
            playerCol--;
            return true;
        }
        else{
            System.out.println("You can't go here.");
            return false;
        }
    }
        // print je health 
        if (input.equals("hp")) {
            System.out.println("You have "+ playerHealth +" health.");
            System.out.println(textSeperator);
        }

         // print je inventory 
         if (input.equals("i")) {
            System.out.println("You have " + playerMoney + " gold coins.");

            if (numHealthPotions < 1){
            System.out.println("You have " + numHealthPotions + " potions left.");
            }
            if (playerSword == 1){
                System.out.println("You have the Sword of Power, this gives you +100 attack damage.");
            }
            
            if (playerShield == 1 && playerArmor == 0){
                System.out.println("You have the black knight shield, you now get 20% less damage.");
            }
            
            if (playerArmor == 1 && playerShield == 0){
                System.out.println("You are currently wearing the black knight armor. You now get 20% less damage.");
            }
            
            if (playerArmor == 1 && playerShield == 1){
                System.out.println("You are currently wearing the black knight armor with the black knight shield.");
                System.out.println("This gives you a set bonus. You now get 50% less damage.");
            }
            System.out.println(textSeperator);
    }
        // print de map 
        if (input.equals("map")) {
            System.out.println(textSeperator);
            map();
            System.out.println(textSeperator);
        }

        // print de hulp opties
        if (input.equals("help")) {
            help();
            return false;
        }

        return false;
    }
    public void describeRoom() {
        // Look where the player is and what prints the tile you standing on.
         switch (map[playerRow][playerCol]) {
                    case water:
                        System.out.println("You are in the water. I hope u can swim!");
                        break;
                    case grass:
                        System.out.println("You are in the grass.");
                        break;
                    case rock:
                        System.out.println("You are in a rocky area.");
                        break;
                    case forest:
                        System.out.println("You are in a dark dense forest. Be careful!");
                        break;
                    case blacksmith:
                        blacksmith();
                        break;
                    case monster:
                        fightMonster();
                        break;
                    case boss:
                        fightBoss();
                        break;
                    case shop:
                        potionshop();
                        break;
            }
            System.out.println(textSeperator);    
        }
    public void blacksmith(){

       
            System.out.println("You have found a blacksmith, do you want to [E]nter the shop or [M]ove on?");
            System.out.println(textSeperator);
            boolean inShop2 = false; // set the flag to false to indicate that the player is not in the shop
            while (true) { // loop until the player exits the shop
                String input = scanner.nextLine();
                if (input.equals("e")) { // if the player presses "e", toggle the shop flag
                    inShop2 = !inShop2;
                    if (inShop2) {
                        System.out.println("You enter the blacksmith's shop.");
                        System.out.println(textSeperator);
                        
                        System.out.println("You can buy the Sword of Power,the black knight shield and black knight armor here.");
                        System.out.println("The prices are: Sword = 2.000 Shield = 8.000 | Armor = 16.000.");
                        System.out.println("When u buy items here they might have secret stats, you can check those in your inventory outside the shop.");
                        System.out.println("The blacksmith will buy your items for 25% less then the shop's selling price.");
                        
                        System.out.println(textSeperator);
                        System.out.println("[1] Buy the Sword of power          [4] Sell the Sword of Power.");
                        System.out.println("[2] Buy the black knight shield     [5] Sell the black knight shield.");
                        System.out.println("[3] Buy the black knight armor      [6] Sell the black knight armor.");
                        System.out.println(textSeperator);
    
                    } else {
                        System.out.println("You leave the blacksmith's shop.");
                        
    
                        break;
                    }
                    //buy sword
                } else if (inShop2 && input.equals("1")) {
                    if (playerMoney < 2000) {
                        System.out.println("You don't have enough money to buy the Sword of Power.");
                        System.out.println("You have " + playerMoney +" gold coins.");
                        System.out.println(textSeperator);
    
                    } else {
                    System.out.println("You buy the Sword of Power for 1000 gold coins.");
                    System.out.println("You now have " + playerMoney +" gold coins.");
                    System.out.println(textSeperator);
                    playerMoney -= 2000;
                    playerSword += 1;
                    }
                    // sell sword
                 } else if (inShop2 && input.equals("4")) {
                    if (playerSword == 0) {
                        System.out.println("You don't have a sword to sell.");
                        System.out.println("You have " + playerMoney +" gold coins.");;
                        System.out.println(textSeperator);
    
                    } else {
                        playerMoney += 1500;
                        playerSword -= 1;
                        System.out.println("You have sold the Sword of Power.");
                        System.out.println("You now have " + playerMoney + " gold coins.");
                        System.out.println(textSeperator);
    
                    }
                    //buy shield
                }  else if (inShop2 && input.equals("2")) {
                    if (playerMoney < 8000) {
                        System.out.println("You don't have enough money to buy the black knight shield.");
                        System.out.println("You have " + playerMoney +" gold coins.");
                        System.out.println(textSeperator);
    
                    } else {
                    System.out.println("You buy the black knights shield for 8000 gold coins.");
                    System.out.println("You now have " + playerMoney +" gold coins.");
                    System.out.println(textSeperator);
                    playerMoney -= 8000;
                    playerShield += 1;
                    }
                    //sell shield
                 } else if (inShop2 && input.equals("5")) {
                    if (playerShield == 0) {
                        System.out.println("You don't have any shield to sell.");
                        System.out.println("You have " + playerMoney +" gold coins.");
                        System.out.println(textSeperator);
    
                    } else {
                        playerMoney += 6000;
                        playerShield -= 1;
                        System.out.println("You have sold your black knight shield.");
                        System.out.println("You now have " + playerMoney + " gold coins.");
                        System.out.println(textSeperator);
    
                    }
                    //buy armor
                } else if (inShop2 && input.equals("3")) {
                    if (playerMoney < 16000) {
                        System.out.println("You don't have enough money to buy the black knight armor.");
                        System.out.println("You have " + playerMoney +" gold coins.");
                        System.out.println(textSeperator);
    
                    } else {
                    System.out.println("You buy the black knight armor for 16000 gold coins.");
                    System.out.println("You now have " + playerMoney +" gold coins.");;
                    System.out.println(textSeperator);
                    playerMoney -= 16000;
                    playerArmor += 1;
                    }
                    //sell armor
                 } else if (inShop2 && input.equals("6")) {
                    if (playerArmor == 0) {
                        System.out.println("You don't have any armor to sell.");
                        System.out.println("You have " + playerMoney +" gold coins.");
                        System.out.println(textSeperator);
    
                    } else {
                        playerMoney += 12000;
                        playerArmor -= 1;
                        System.out.println("You have sold the black knight armor.");
                        System.out.println("You now have " + playerMoney + " gold coins.");
                        System.out.println(textSeperator);
    
                    }
                
                } else if (input.equals("m")) {
                    System.out.println("You did not enter the blacksmith's shop and moved on.");
                    break;
                } 
            }
        }
    public void potionshop(){
        System.out.println("You have found a potion shop, do you want to [E]nter or [M]ove on?");
        System.out.println(textSeperator);
        boolean inShop = false; // set the flag to false to indicate that the player is not in the shop
        while (true) { // loop until the player exits the shop
            String input = scanner.nextLine();
            if (input.equals("e")) { // if the player presses "e", toggle the shop flag
                inShop = !inShop;
                if (inShop) {
                    System.out.println("You enter the potion shop.");
                    System.out.println("You can buy or sell a potion for 50 gold coins.");
                    System.out.println("You currently have " + playerMoney + " gold coins.");
                    System.out.println(textSeperator);
                    System.out.println("[B]uy potion.");
                    System.out.println("[s]ell potion.");
                    System.out.println(textSeperator);

                } else {
                    System.out.println("You leave the potion shop.");
                    

                    break;
                }
            } else if (inShop && input.equals("b")) {
                if (playerMoney < 50) {
                    System.out.println("You don't have enough money to buy a potion.");
                    System.out.println("You have " + numHealthPotions + " potions and " + playerMoney +" gold coins.");
                    System.out.println("Do you want to [E]xit or [S]ell a potion?");
                    System.out.println(textSeperator);

                } else {
                System.out.println("You buy a potion for 50 gold coins.");
                System.out.println("You now have " + numHealthPotions + " potions and " + playerMoney +" gold coins.");
                System.out.println("Do you want to [B]uy another potion, [S]ell a potion or [E]xit the shop?");
                System.out.println(textSeperator);
                playerMoney -= 50;
                numHealthPotions += 1;
                }
             } else if (inShop && input.equals("s")) {
                if (numHealthPotions == 0) {
                    System.out.println("You don't have any potions to sell.");
                    System.out.println("You have " + numHealthPotions + " potions and " + playerMoney +" gold coins.");
                    System.out.println("Do you want to [B]uy a potion or [E]xit the shop?");
                    System.out.println(textSeperator);

                } else {
                    playerMoney += 50;
                    numHealthPotions -= 1;
                    System.out.println("You have sold a health potion.");
                    System.out.println("You now have " + numHealthPotions + " potions and " + playerMoney +" gold coins.");
                    System.out.println("Do you want to [S]ell another potion, [B]uy a potion or [E]xit the shop?");
                    System.out.println(textSeperator);

                }
            } else if (input.equals("m")) {
                System.out.println("You did not enter the shop and moved on.");
                break;
            }
        }
    }   
    public class Monster {
            private String name;
            private int maxHealth;
            private int currentHealth;
            private int attackDamage;
        
            public Monster(String name, int maxHealth, int attackDamage) {
                this.name = name;
                this.maxHealth = maxHealth;
                this.currentHealth = maxHealth;
                this.attackDamage = attackDamage;
            }
        
            public String getName() {
                return name;
            }
        
            public int getMaxHealth() {
                return maxHealth;
            }
        
            public int getCurrentHealth() {
                return currentHealth;
            }
        
            public int getAttackDamage() {
                return attackDamage;
            }
        
            public void setCurrentHealth(int health) {
                currentHealth = health;
            }
        } 
    public void fightMonster() {
    // Spawn a random monster
    Monster[] monsters = {
        new Monster("chicken",  50,         5),
        new Monster("sheep",    75,         8),
        new Monster("cow",      100,        10),
        new Monster("bear",     150,        15),
        new Monster("goblin",   150,        25),
        new Monster("troll",    200,        35)
        
    };
    Monster currentMonster = monsters[Random.nextInt(monsters.length)];
       
    System.out.println("You've encountered a "+ currentMonster.getName() +  ", do you want to:");
    System.out.println("[R]un away!");
    System.out.println("[F]ight!");
    System.out.println(textSeperator);
    String input = "";

 
        while (input.equals("r") == false) {
            System.out.print(">");
            input = scanner.nextLine();
            System.out.println(textSeperator);

            if (input.equals("p")) {
                if (numHealthPotions > 0) { 
                    numHealthPotions -= 1;
                    playerHealth += healthPotionHealAmount;
                    
                    System.out.println("You drink a potion and gain " + healthPotionHealAmount + " health. You now have " + playerHealth + " Hitpoints.");
                    System.out.println("You now have " + numHealthPotions + " potions left");
                    System.out.println("Do you want to [R]un away, stay and [F]ight or drink a [P]otion?");
                    System.out.println(textSeperator);

                } else {
                    System.out.println("You don't have any potions left!");
                    System.out.println("Do you want to [R]un away or stay and [F]ight");
                    System.out.println(textSeperator);
                }
            }
            
            // fighting text and function without sword
            // fighting text and function
            if (input.equals("f")) {
                int playerAttackDamage = Random.nextInt(15, 30);
                //int enemyAttackDamage = Random.nextInt(5, 15);
                
                // 20% more damage per level
                double increasedAttackPerLevel = 0.2;
                // Increase increasedAttackPerLevel per playerLevel
                double attackDamageLevelFactor = 1.0 + (playerLevel - 1) * increasedAttackPerLevel;
                // Calculate baseAttackDamage as playerAttackDamage + 20% bonus damage
                int baseAttackDamage = (int) (playerAttackDamage * increasedAttackPerLevel);
                // Apply the attack damage level factor to the base damage
                baseAttackDamage *= attackDamageLevelFactor;
                
                playerAttackDamage = playerAttackDamage + baseAttackDamage;
                
                if (playerSword == 1) {
                    playerAttackDamage += 100;
                }

                //Less damage when a player owns a shield
                int damageMultiplier = 1;
                if (playerShield == 1) {
                    damageMultiplier *= 0.8; // Reduce damage by 20%
                }

                //Less damage and a HP boost when a player owns an armor
                if (playerArmor == 1) {
                    damageMultiplier *= 0.8; // Reduce damage by 20%
                }

                //60% less damage when a player owns both a shield and an armor
                if (playerShield == 1 && playerArmor == 1) {
                    damageMultiplier *= 0.5; // Reduce damage by 50%
                }
                int finalEnemyDamage = currentMonster.getAttackDamage() * damageMultiplier;
                if (playerSword == 1) {
                    System.out.println("You swing your sword against the " + currentMonster.getName() + " and deal " + playerAttackDamage + " damage!");
                } else {
                    System.out.println("You attack the " + currentMonster.getName() + " and deal " + playerAttackDamage + " damage!");
                }
                
                currentMonster.setCurrentHealth (currentMonster.getCurrentHealth() - playerAttackDamage);

                if (currentMonster.getCurrentHealth() <= 0) {
                    currentMonster.setCurrentHealth(0);
                    System.out.println("You have defeated the " + currentMonster.getName() + " ,congratulations!");
                    int loot = Random.nextInt(10, 50);
                    loot = (int) (loot * Math.pow(1.25, playerLevel - 1));
                    int xp = Random.nextInt(50,150);
                    System.out.println("You found " + loot + " golden coins and gained " + xp +" experience.");
                    playerMoney += loot;
                    playerExperience += xp;
                    while (playerExperience >= 100 * Math.pow(2, playerLevel - 1)) {
                        playerLevel++;
                        if (playerLevel % 2 == 0) {
                            System.out.println("You leveled up and reached level " + playerLevel + ".");
                            playerHealth += 100;
                            System.out.println("You gained 100 extra health and now have " + playerHealth + " health.");
                        }
                    }

                    break; 

                } else {
                    playerHealth -= finalEnemyDamage;
                    System.out.println("The "+ currentMonster.getName() +" attacked back and did " + finalEnemyDamage + " damage to you.");
                    System.out.println(textSeperator);
                    System.out.println("Your    HP: " + playerHealth);
                    System.out.println("Monster HP: " + currentMonster.getCurrentHealth());
                    System.out.println("Do you want to [R]un away, stay and [F]ight or drink a [P]otion?");
                    System.out.println(textSeperator);
                }
            
                // if the playerHealth 0 the game stops
                if (playerHealth < 1) {
                    playerHealth = 0;
                    System.out.println(textSeperator);
                    System.out.println("Game over!");
                    System.out.println(textSeperator);
                    System.exit(0);      
                }           
            }            

            //Run away text
            if (input.equals("r")) {
                System.out.println("You have run away successfully!");
                
                break;
            }
        }
    }
    public class Boss {
        private String name;
        private int maxHealth;
        private int currentHealth;
        private int attackDamage;
    
        public Boss(String name, int maxHealth, int attackDamage) {
            this.name = name;
            this.maxHealth = maxHealth;
            this.currentHealth = maxHealth;
            this.attackDamage = attackDamage;
        }
    
        public String getName() {
            return name;
        }
    
        public int getMaxHealth() {
            return maxHealth;
        }
    
        public int getCurrentHealth() {
            return currentHealth;
        }
    
        public int getAttackDamage() {
            return attackDamage;
        }
    
        public void setCurrentHealth(int health) {
            currentHealth = health;
        }
    }    
    public void fightBoss() {
        // Spawn a random Boss
        Boss[] boss = {
            new Boss("Holy Knight",             150000,     	     100),
            new Boss("Undead Viking",           1500000,          150),
            new Boss("Mongol Warrior King",     15000000,         250),
            new Boss("Mighty god Thor",         15000000,         1500)
        };
        Boss currentBoss = boss[Random.nextInt(boss.length)];
        //Player standard damage
        
        System.out.println("You have found the "+ currentBoss.getName() +  "! This is a boss.");
        System.out.println("Bosses are very strong enemies with high damage, be sure that you are ready to face the boss.");
        System.out.println("What do you want to do?");
        System.out.println("[R]un away!");
        System.out.println("[F]ight!");
        System.out.println(textSeperator);
        String input = "";
    
     
            while (input.equals("r") == false) {
                System.out.print(">");
                input = scanner.nextLine();
                System.out.println(textSeperator);
    
                if (input.equals("p")) {
                    if (numHealthPotions > 0) { 
                        numHealthPotions -= 1;
                        playerHealth += healthPotionHealAmount;
                        
                        System.out.println("You drink a potion and gain " + healthPotionHealAmount + " health. You now have " + playerHealth + " Hitpoints.");
                        System.out.println("You now have " + numHealthPotions + " potions left");
                        System.out.println("Do you want to [R]un away, stay and [F]ight or drink a [P]otion?");
                        System.out.println(textSeperator);
    
                    } else {
                        System.out.println("You don't have any potions left!");
                        System.out.println("Do you want to [R]un away or stay and [F]ight");
                        System.out.println(textSeperator);
                    }
                }
                
                
                // fighting text and function with damage multipliers for sword and gear
                if (input.equals("f")) {
                    int playerAttackDamage = Random.nextInt(15, 30);
                    //int enemyAttackDamage = Random.nextInt(5, 15);
                    
                    // 20% more damage per level
                    double increasedAttackPerLevel = 0.2;
                    // Increase increasedAttackPerLevel per playerLevel
                    double attackDamageLevelFactor = 1.0 + (playerLevel - 1) * increasedAttackPerLevel;
                    // Calculate baseAttackDamage as playerAttackDamage + 20% bonus damage
                    int baseAttackDamage = (int) (playerAttackDamage * increasedAttackPerLevel);
                    // Apply the attack damage level factor to the base damage
                    baseAttackDamage *= attackDamageLevelFactor;
                    
                    playerAttackDamage = playerAttackDamage + baseAttackDamage;
                    
                    if (playerSword == 1) {
                        playerAttackDamage += 100;
                    }
    
                    //Less damage when a player owns a shield
                    int damageMultiplier = 1;
                    if (playerShield == 1) {
                        damageMultiplier *= 0.8; // Reduce damage by 20%
                    }
    
                    //Less damage and a HP boost when a player owns an armor
                    if (playerArmor == 1) {
                        damageMultiplier *= 0.8; // Reduce damage by 20%
                    }
    
                    //60% less damage when a player owns both a shield and an armor
                    if (playerShield == 1 && playerArmor == 1) {
                        damageMultiplier *= 0.5; // Reduce damage by 50%
                    }
                    int finalEnemyDamage = currentBoss.getAttackDamage() * damageMultiplier;
                    if (playerSword == 1) {
                        System.out.println("You swing your sword against the " + currentBoss.getName() + " and deal " + playerAttackDamage + " damage!");
                    } else {
                        System.out.println("You attack the " + currentBoss.getName() + " and deal " + playerAttackDamage + " damage!");
                    }
                    
                    currentBoss.setCurrentHealth (currentBoss.getCurrentHealth() - playerAttackDamage);
    
                    
                    // if the playerHealth 0 the game stops
                    if (playerHealth < 1) {
                        playerHealth = 0;
                        System.out.println(textSeperator);
                        System.out.println("Game over!");
                        System.out.println(textSeperator);
                        System.exit(0);      
                    }    
                    if (currentBoss.getCurrentHealth() <= 0) {
                        currentBoss.setCurrentHealth(0);
                        System.out.println("You have defeated the " + currentBoss.getName() + " boss, congratulations!");
                        int loot = Random.nextInt(1000, 500000);
                        loot = (int) (loot * Math.pow(1.50, playerLevel - 1));
                        int xp = Random.nextInt(50000,150000);
                        System.out.println("You found " + loot + " golden coins and gained " + xp +" experience.");
                        playerMoney += loot;
                        playerExperience += xp;
                        while (playerExperience >= 100 * Math.pow(2, playerLevel - 1)) {
                            playerLevel++;
                            if (playerLevel % 2 == 0) {
                                System.out.println("You leveled up and reached level " + playerLevel + ".");
                                playerHealth += 100;
                                System.out.println("You gained 100 extra health and now have " + playerHealth + " health.");
                            }
                        }
    
                        break; 
    
                    } else {
                        playerHealth -= finalEnemyDamage;
                        Random rand = new Random();
                        int randomNum = rand.nextInt(5);
                        
                        switch(randomNum) {
                            case 0:
                                System.out.println("The " + currentBoss.getName() + " pounced at you, landing a powerful blow for " + finalEnemyDamage + " damage.");
                                break;
                            case 1:
                                System.out.println("The " + currentBoss.getName() + " unleashes a vicious attack, causing " + finalEnemyDamage + " damage to you.");
                                break;
                            case 2:
                                System.out.println("You feel the force of the " + currentBoss.getName() + "'s attack as it strikes you for " + finalEnemyDamage + " damage.");
                                break;
                            case 3:
                                System.out.println("The " + currentBoss.getName() + " charges forward, slamming into you and dealing " + finalEnemyDamage + " damage.");
                                break;
                            case 4:
                                System.out.println("With lightning speed, the " + currentBoss.getName() + " strikes you for " + finalEnemyDamage + " damage.");
                                break;
                            default:
                                System.out.println("An unknown force stirs within you, granting you a sudden burst of strength.");
                                break;
                        }
                        System.out.println(textSeperator);
                        System.out.println("Your    HP: " + playerHealth);
                        System.out.println("Monster HP: " + currentBoss.getCurrentHealth());
                        System.out.println("Do you want to [R]un away, stay and [F]ight or drink a [P]otion?");
                        System.out.println(textSeperator);
                    }
                       
                }            
    
                //Run away text
                if (input.equals("r")) {
                    System.out.println("You have run away successfully!");
                    
                    break;
                }
            }
        }
    public void mainLoop() {

        String input = "";
        Boolean moved = true;

        while (input.equals("q") == false) {

            if (moved) {
                describeRoom();
            }
            System.out.print(">");
            input = scanner.nextLine();
            moved = executeInput(input);

        }
    }
    public void help() {
        System.out.println(textSeperator);
        System.out.println("You can move with the keys: W,A,S,D.");
        System.out.println("F = Fight, R = Run away, P = Potion");
        System.out.println("HP = Health, I = Inventory, ");
        System.out.println("Q   = Quit");
        System.out.println("map = Prints out the map and your current position.");
        System.out.println(textSeperator);
    }  
    public void map() {
        for (int i = 0; i < mapheight; i++) {
            for (int j = 0; j < mapwidth; j++) {
                if (i == playerRow && j == playerCol) {
                    System.out.print("P ");
                } else {
                    System.out.print(map[i][j] + " ");
                }  
            }
            System.out.println();
        }
    }
    public void init() {
        scanner = new Scanner(System.in);
        System.out.println(textSeperator);
        System.out.println("Welcome to the adventure text game! Type help for all the commands");
        System.out.println(textSeperator);
        playerRow = 1; // mapheight -1;
        playerCol = 1; // mapwidth / 1;

    }
}
