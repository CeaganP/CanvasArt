
package src;

import javafx.scene.paint.Color;

/**
 *
 * @author Ceagan
 */
public class ColorValidate {
    private String outputText = "";
    private Color c;
    private int r = 0, g = 0, b = 0;
    
    public ColorValidate(int red, int green, int blue){
        r = red;
        g = green;
        b = blue;
    }

    public Color validateColor(){
        if (0 > r || r > 255){
            outputText += "Bad value for red component\n";  
            if (0 > r){
                r = 0;
            } else if (r > 255){
                r = 255;
            }
        }
        if (0 > g || g > 255){
            outputText += "Bad value for green component\n";  
            if (0 > g){
                g = 0;
            } else if (g > 255){
                g = 255;
            }
        }
        if (0 > b || b > 255){
            outputText += "Bad value for blue component\n";  
            if (0 > b){
                b = 0;
            } else if (b > 255){
                b = 255;
            }
        }
        
        c = Color.rgb(r, g, b);
        
        return c;
    }
    
    public String getError(){
        return outputText;
    }

    /**
     * @return the red
     */
    public int getRed() {
        return r;
    }

    /**
     * @return the green
     */
    public int getGreen() {
        return g;
    }

    /**
     * @return the blue
     */
    public int getBlue() {
        return b;
    }
}
