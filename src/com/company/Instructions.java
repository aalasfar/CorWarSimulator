package com.company;

public class Instructions {

    int a_x, b_x, q, w;
    int[] fA, fB;
    String[] command, mA, mB;

    //constructor
    public Instructions(String[] command, String[] mA, String[] mB, int[] fA, int[] fB){
        this.command = command; this.mA = mA; this.mB = mB; this.fA = fA; this.fB = fB;
    }

    GameSimulator ObjLoopIndex = new GameSimulator();   //object for looping index

    //method for setting indexes to 0
    public void SetIndexesToZero(){
        a_x = 0; b_x = 0; q = 0; w = 0;
    }

    //method for finding looping indexes
    public void CalcIndexes(int index){
        a_x = fA[index]+index;  // indirect index for A field
        a_x = ObjLoopIndex.KeepIndexWithinLimits(a_x); //keep index between min and max
        b_x = fB[index]+index;  //index using B field //indirect index for B field
        b_x = ObjLoopIndex.KeepIndexWithinLimits(b_x);
        q = a_x + fA[a_x]; //index for A field using * mode
        q = ObjLoopIndex.KeepIndexWithinLimits(q);
        w = b_x + fB[b_x];  //index for B field using @
        w = ObjLoopIndex.KeepIndexWithinLimits(w);
    }

    public void DAT(int index){
    /*only keeps a value in the b field but if executed player loses*/
        command[index] = "DAT";
    }

    public void MOV(int index) {
    /*Takes instruction from address determined by A and B field and copies it to
    new address also depending on A and B field*/
        //******** keep index between 0 and 7999************/
        index = ObjLoopIndex.KeepIndexWithinLimits(index);
        SetIndexesToZero();
        CalcIndexes(index);

        if (mA[index].equals("#")) {
            //puts A field value in B field at the B field target
            // MOV #2, 1    ------->    MOV #2, 1
            // ADD 2, @3                ADD 2, @2
            fB[b_x] = fA[index];
        } else if (mA[index].equals("*") && !"@".equals(mB[index])) {
            // 300: MOV *1, 1    ------->    MOV *1, 1
            // 301: ADD 1, @2                DAT 0, 3
            // 302: DAT 0, 3                 DAT 0, 3
            command[b_x] = command[q];
            mA[b_x] = mA[q];
            mB[b_x] = mB[q];
            fA[b_x] = fA[q];
            fB[b_x] = fB[q];
            //System.out.println("*");
            //works correctly!
        } else if(mB[index].equals("@") && !"*".equals(mA[index])){
            // 300: MOV 2, @1    ------->    MOV 2, @1
            // 301: DAT 0, 2                 DAT 0, 2
            // 302: ADD 4, 3                 ADD 4, 3
            // 303: ADD 3, 2                 ADD 4, 3
            command[w] = command[a_x];
            mA[w] = mA[a_x];
            mB[w] = mB[a_x];
            fA[w] = fA[a_x];
            fB[w] = fB[a_x];
            // works correctly!
        }
        else if(mB[index].equals("@") && mA[index].equals("*")){
//            MOV *1, @3
//            MOV 1, 2
//            MOV 2, 1
//            DAT 1
//            DAT 3        -----> MOV 2, 1
            System.out.println("w " + w);
            command[w] = command[q];
            mA[w] = mA[q];
            mB[w] = mB[q];
            fA[w] = fA[q];
            fB[w] = fB[q];

        }else {
            //say index is 300
            //300: MOV 1, 2
            //301: ADD #4, @1    Afield = 4, Bfield = 1
            //302: ADD 2, 3      Afield = 2, Bfield = 3
            //System.out.println("Are we here!?");
            command[b_x] = command[a_x];     //copying instruction
            mA[b_x] = mA[a_x];                  //copying mode A
            mB[b_x] = mB[a_x];                  //copying mode B
            fA[b_x] = fA[a_x];                  //copying field A
            fB[b_x] = fB[a_x];
            // this worked !!
        }
    }

    public void ADD(int index) {
        index = ObjLoopIndex.KeepIndexWithinLimits(index);
        SetIndexesToZero();
        CalcIndexes(index);
        if (mA[index].equals("#") && !"@".equals(mB[index])) {
            fB[b_x] = fA[index] + fB[b_x];
        }else if(mA[index].equals("#") && mB[index].equals("@")){
            fB[w] = fB[w] + fA[index];
        } else if (mA[index].equals("*") && !"@".equals(mB[index])) {
            fB[b_x] = fB[q] + fB[b_x];
            fA[b_x] = fA[q] + fA[b_x];
        }else if(mB[index].equals("@") &&  !"*".equals(mA[index])) {
            fB[w] = fB[a_x] + fB[w];
            fA[w] = fA[a_x] + fA[w];
        }else if(mB[index].equals("@") && mA[index].equals("*")){
            fB[w] = fB[w] + fB[q];
            fA[w] = fA[w] + fA[q];
        } else{
            fB[b_x] = fB[a_x] + fB[b_x];
            fA[b_x] = fA[a_x] + fA[b_x];
        }
    }
    public void SUB(int index){
        index = ObjLoopIndex.KeepIndexWithinLimits(index);
        SetIndexesToZero();
        CalcIndexes(index);
        if (mA[index].equals("#") && !"@".equals(mB[index])) {
            fB[b_x] = fB[b_x] - fA[index];
        }else if(mA[index].equals("#") && mB[index].equals("@")){
            fB[w] = fB[w] - fA[index];
        } else if (mA[index].equals("*") && !"@".equals(mB[index])) {
            fB[b_x] = fB[b_x] - fB[q];
            fA[b_x] = fA[b_x] - fA[q];
        }else if(mB[index].equals("@") &&  !"*".equals(mA[index])) {
            fB[w] = fB[w] - fB[a_x];
            fA[w] = fA[w] - fA[a_x];
        }else if(mB[index].equals("@") && mA[index].equals("*")){
            fB[w] = fB[q] - fB[w];
            fA[w] = fA[q] - fA[w];
        } else{
            fB[b_x] = fB[b_x] - fB[a_x];
            fA[b_x] = fA[b_x] - fA[a_x];
        }
    }

    public void JMP(int index){
        
    }

    public class JMZ{

    }

    public class DJN{

    }

    public class CMP{

    }

    public class SPL{

    }

}
