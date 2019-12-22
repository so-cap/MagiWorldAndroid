package com.sophie.magiworldandroid.model;

public class Sorcerer extends Character {
    private final int maximumLife;
    private int gainReel;

    public Sorcerer(int level, int strength, int agility, int intelligence, String playersName) {
        super(level, strength, agility, intelligence, playersName);
        maximumLife = life;
    }

    @Override
    public void basicAttack(Character rival) {
        rival.life -= this.intelligence;
    }

    @Override
    public String basicAttackDamage(Character rival) {
        return (getPlayersName() + " utilise Boule de Feu et inflige " + this.intelligence + " dommage(s)!");
    }

    @Override
    public void specialAttack(Character rival) {
        int gainBrut = (this.intelligence * 2 + this.life);

        if (gainBrut < maximumLife) {
            gainReel = this.intelligence * 2;
            this.life += (this.intelligence * 2);
        } else {
            gainReel = maximumLife - this.life;
            this.life = maximumLife;
        }
    }

    @Override
    public String specialAttackDamage(Character rival) {
        String message;

        if (this.intelligence == 0)
            message = getPlayersName() + " utilise Soin mais n'a plus d'intelligence pour gagner en vitalité !";
        else if (this.life == maximumLife)
            message = getPlayersName() + " utilise Soin et regagne toute sa vitalité !";
        else
            message = getPlayersName() + " utilise Soin et gagne " + gainReel + " en vitalité !";
        return message;
    }

    @Override
    public String introduction() {
        return ("Abracadabra je suis le Mâge !\nJe suis de niveau " + level +
                " , je possède " + life + " de vitalité, " + strength +" de force, " + agility + " d'agilité et " +
                intelligence +" d'intelligence !");
    }

    @Override
    public String getCharactersName() {return "MÂGE";}

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
