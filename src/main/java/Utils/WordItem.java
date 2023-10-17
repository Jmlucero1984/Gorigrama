
package Utils;

/* @author jmlucero */
public class WordItem {
     String word;
    int initX;
    int initY;
    int endX;
    int endY;
    Direction dir;

    public WordItem(){};
    public WordItem(String word, int initX, int initY, int endX, int endY, Direction dir) {
        this.word = word;
        this.initX = initX;
        this.initY = initY;
        this.endX = endX;
        this.endY = endY;
        this.dir = dir;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getInitX() {
        return initX;
    }

    public void setInitX(int initX) {
        this.initX = initX;
    }

    public int getInitY() {
        return initY;
    }

    public void setInitY(int initY) {
        this.initY = initY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }
   
    

}
