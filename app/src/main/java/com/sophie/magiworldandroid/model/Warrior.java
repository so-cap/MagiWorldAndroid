package com.sophie.magiworldandroid.model;

public class Warrior extends Character {

    public Warrior(int level, int strength, int agility, int intelligence, String playersName) {
        super(level, strength, agility, intelligence, playersName);
    }

    @Override
    public void basicAttack(Character rival) { rival.life -= this.strength;}

    @Override
    public String basicAttackDamage(Character rival) {
        return (getPlayersName()+ " utilise Coup d'Épée et inflige " +this.strength+ " dommage(s)!\n");
    }

    @Override
    public void specialAttack(Character rival) {
        rival.life -= (this.strength * 2);
        this.life -= this.strength / 2;
    }

    @Override
    public String specialAttackDamage(Character rival) {
        return (getPlayersName() + " utilise Coup de Rage et inflige " + (this.strength * 2) + " dommage(s) !\n" +
                getPlayersName() + " perd " + this.strength / 2 + " point(s) de vie suite à l'attaq.\n");
    }

    @Override
    public String introduction() {
        return "Woaarg je suis le Guerrier !\nJe suis de niveau " + level +
                " , je possède " + life + " de vitalité, " + strength +" de force, " + agility + " d'agilité et "
                + intelligence + " d'intelligence !";
    }

    @Override
    public String getCharactersName(){return "GUERRIER";}

    @Override
    public String getPlayersName(){return playersName;}

    @Override
    public int getLife() {
        return life;
    }

    @Override
    public int getStrength() {return this.strength;}

    @Override
    public int getAgility() {return this.agility;}

    @Override
    public int getIntelligence() {return this.intelligence;}
}
