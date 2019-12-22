package com.sophie.magiworldandroid.model;

public abstract class Character {
    protected String playersName;
    protected int level;
    protected int strength;
    protected int agility;
    protected int intelligence;
    protected int life;

    public Character(int level, int strength, int agility, int intelligence, String playersName) {
        this.level = level;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        this.life = level * 5;
        this.playersName = playersName;
    }


    public abstract int getStrength();
    public abstract int getAgility();
    public abstract int getIntelligence();
    public abstract int getLife();
    public abstract String getCharactersName();
    public abstract String getPlayersName();
    public abstract void basicAttack(Character rival);
    public abstract String basicAttackDamage(Character rival);
    public abstract void specialAttack(Character rival);
    public abstract String specialAttackDamage(Character rival);
    public abstract String introduction();

    public int setLifeAtZero() { return this.life = 0;}
}
