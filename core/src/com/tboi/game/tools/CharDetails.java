package com.tboi.game.tools;

import com.badlogic.gdx.scenes.scene2d.Touchable;

public class CharDetails {

    String score;

    Touchable level2;
    Touchable level3;
    Touchable level4;
    Touchable level5;
    Touchable level6;
    Touchable level7;
    Touchable level8;
    Touchable level9;
    String count;
    String level;

    public Touchable getLevel2() {
        return level2;
    }

    public void setLevel2(Touchable level2) {
        this.level2 = level2;
    }

    public Touchable getLevel3() {
        return level3;
    }

    public void setLevel3(Touchable level3) {
        this.level3 = level3;
    }

    public Touchable getLevel4() {
        return level4;
    }

    public void setLevel4(Touchable level4) {
        this.level4 = level4;
    }

    public Touchable getLevel5() {
        return level5;
    }

    public void setLevel5(Touchable level5) {
        this.level5 = level5;
    }

    public Touchable getLevel6() {
        return level6;
    }

    public void setLevel6(Touchable level6) {
        this.level6 = level6;
    }

    public Touchable getLevel7() {
        return level7;
    }

    public void setLevel7(Touchable level7) {
        this.level7 = level7;
    }

    public Touchable getLevel8() {
        return level8;
    }

    public void setLevel8(Touchable level8) {
        this.level8 = level8;
    }

    public Touchable getLevel9() {
        return level9;
    }

    public void setLevel9(Touchable level9) {
        this.level9 = level9;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScore(){
        return score;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
