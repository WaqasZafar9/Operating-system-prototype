package MemoryManagment;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

class Pages implements Serializable {
    int noOfFrames;
    String[] inputStrings;
    Boolean isFIFO;
    String[] framesRecord;
    int faults;

    public Pages(int frames, String[] inputString, Boolean isFIFO) {
        this.noOfFrames = frames;
        this.inputStrings = inputString;
        this.isFIFO = isFIFO;
        faults=0;
    }
    public void setFramesRecord(String[] framesRecord){
        this.framesRecord=framesRecord;

    }


    public static void main(String[] args) {
    }
}
