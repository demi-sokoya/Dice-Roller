package com.example.finalexam;


@SuppressWarnings({"CanBeFinal", "unused"})
public class Die{
    // member variables to store die information
    String type;
    byte dieSides;
    byte sideUp;

    /**
     A default constructor to create a d6
     Uses the roll method for a random side up
     */
    @SuppressWarnings("unused")
    public Die(){
        this.dieSides = 6;
        // the default name for dice is dX where X is the number of sides
        type = "d" + dieSides;
        roll(); // use the roll method to save writing a random value
    }

    /**
     A single arguement constrtuctor to make a die with a default name
     Uses the roll method for a random side up
     @param  dieSides    the number of sides for the die
     */
    @SuppressWarnings("unused")
    public Die(byte dieSides){
        this.dieSides = dieSides;
        // the default name for dice is dX where X is the number of sides
        type = "d" + this.dieSides;
        roll(); // use the roll method to save writing a random value
    }

    /**
     A two arguement constrtuctor to make a custom die
     Uses the roll method for a random side up
     @param  dieSides    the number of sides for the die
     @param  type        the name of the die
     */
    @SuppressWarnings("unused")
    public Die(byte dieSides, String type){
        this.dieSides = dieSides;
        this.type = type;
        roll(); // use the roll method to save writing a random value
    }

    /**
     A single arguement constrtuctor to make a die with a default name. If the argument is outside the range of a byte, it defaults to 6
     Uses the roll method for a random side up
     @param  dieSides    the number of sides for the die
     */
    public Die(int dieSides){
        if(dieSides > Byte.MAX_VALUE){
            this.dieSides = 6;
            System.out.println("Value too large. Defaulting to 6");
        }
        else this.dieSides = (byte)dieSides;
        this.type = "d" + this.dieSides;
        roll(); // use the roll method to save writing a random value
    }

    /**
     A single arguement constrtuctor to make a die with a default name. If the argument is outside the range of a byte, it defaults to 6
     Uses the roll method for a random side up
     @param  dieSides    the number of sides for the die
     @param  type        the name of the die
     */
    @SuppressWarnings("unused")
    public Die(int dieSides, String type){
        if(dieSides > Byte.MAX_VALUE){
            this.dieSides = 6;
            System.out.println("Value too large. Defaulting to 6");
        }
        else this.dieSides = (byte)dieSides;
        this.type = type;
        roll(); // use the roll method to save writing a random value
    }

    /**
     Returns the name of the die
     @return the name of the die
     */
    @SuppressWarnings("unused")
    public String getType(){ return type; }

    /**
     Returns the number of sides
     @return the number of sides
     */
    @SuppressWarnings("unused")
    public byte getdieSides(){ return dieSides; }

    /**
     Returns the current side showing on the die
     @return the side up
     */
    public byte getSideUp(){ return sideUp; }

    // set method for dieSides excluded

    /**
     Changes the name of the die
     @param  type    the new name of the die
     */
    @SuppressWarnings("unused")
    public void setType(String type){
        this.type = type;
    }

    /**
     Changes the current side showing on the die. If the side up is either below 0 or above the number of sides, the highest value is used instead
     @param  sideUp    the new side to display
     */
    public void setSideUp(byte sideUp){
        // if the parameter value isn't valid, default to the highest value
        if(sideUp > this.dieSides || sideUp < 1){
            this.sideUp = dieSides;
        }
        else this.sideUp = sideUp;
    }

    /**
     Changes the current side showing on the die. If the side up is either below 0 or above the number of sides, the highest value is used instead
     @param  sideUp    the new side to display
     */
    @SuppressWarnings("unused")
    public void setSideUp(int sideUp){
        // to save us the effor of re-writing the setSideUp method, we can just typecast the incoming value and pass it back to the method we want
        setSideUp((byte)sideUp);
    }

    /**
     Generates a new random value within the range of the die
     @see    Math
     */
    public void roll(){
        // use the setSideUp method to change the current side being displayed
        // Math.random() returns a double value 0 <= val < 1
        //  to change this to a value we can use, multiply it by the number of sides, then add 1. We also need to cast to byte to be able to pass to our method
        setSideUp((byte)((Math.random() * dieSides)+1));
    }

    @SuppressWarnings("unused")
    public void setSides(){

    }
}

