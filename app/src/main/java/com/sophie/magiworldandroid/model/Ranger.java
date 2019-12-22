package com.sophie.magiworldandroid.model;

public class Ranger extends Character {
    private int gain;

    public Ranger(int level, int strength, int agility, int intelligence, String playersName) {
        super(level, strength, agility, intelligence, playersName);
    }

    @Override
    public void basicAttack(Character rival) {
        rival.life -= this.agility;
    }

    @Override
    public String basicAttackDamage(Character rival) {
        return (getPlayersName() + " utilise Tir à l'Arc et inflige " + this.agility + " dommage(s)!\n");
                //"Le joueur " + rival.playersNumber + " perd " + this.agility + " points de vie.\nIl lui reste " + rival.life + " points de vie.\n");
    }

    @Override
    public void specialAttack(Character rival) {
        gain = this.level / 2;
        this.agility += gain;
    }

    @Override
    public String specialAttackDamage(Character rival) {
        return (getPlayersName() + " utilise Concentration et gagne " + gain + " en agilité ! \n");
    }

    @Override
    public String introduction() {
        return ("Hé hé hé je suis le Rôdeur !!\nJe suis de niveau " + level +
                ", je possède " + life + " de vitalité, " + strength + " de force, " + agility + " d'agilité et "
                + intelligence + " d'intelligence !");
    }

    @Override
    public String getCharactersName(){return "RÔDEUR";}

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
