package com.company;

public class Instructions {

    int a_x, b_x, q, w;
    int[] fA, fB;
    String[] command, mA, mB;

    //constructor
    public Instructions(String[] command, String[] mA, String[] mB, int[] fA, int[] fB) {
        this.command = command;
        this.mA = mA;
        this.mB = mB;
        this.fA = fA;
        this.fB = fB;
    }

    GameSimulator ObjLoopIndex = new GameSimulator();   //object for looping index

    //method for setting indexes to 0
    public void SetIndexesToZero() {
        a_x = 0;
        b_x = 0;
        q = 0;
        w = 0;
    }

    //method for finding looping indexes
    public void CalcIndexes(int index) {
        a_x = fA[index] + index;  // indirect index for A field
        a_x = ObjLoopIndex.KeepIndexWithinLimits(a_x); //keep index between min and max
        b_x = fB[index] + index;  //index using B field //indirect index for B field
        b_x = ObjLoopIndex.KeepIndexWithinLimits(b_x);
        q = a_x + fA[a_x]; //index for A field using * mode
        q = ObjLoopIndex.KeepIndexWithinLimits(q);
        w = b_x + fB[b_x];  //index for B field using @
        w = ObjLoopIndex.KeepIndexWithinLimits(w);
    }

    public void DAT(int index) {
        if(mB[index].equals("#")){
            fB[index] = fB[index];}
        else if(!"#".equals(mB[index]) && !"@".equals(mB[index])){
            fB[b_x] = fB[index];
        }else if(mB[index].equals("@")){
            fB[w] = fB[index];
        }
    }

    public void MOV(int index) {
    /*Takes instruction from address determined by A and B field and copies it to
    new address also depending on A and B field*/
        //******** keep index between 0 and 7999************/
        index = ObjLoopIndex.KeepIndexWithinLimits(index);
        SetIndexesToZero();
        CalcIndexes(index);
    //System.out.println("MOV is being called");
        if (mA[index].equals("#")) {
            fB[b_x] = fA[index];
        } else if (mA[index].equals("*") && !"@".equals(mB[index])) {
            command[b_x] = command[q];
            mA[b_x] = mA[q];
            mB[b_x] = mB[q];
            fA[b_x] = fA[q];
            fB[b_x] = fB[q];
        } else if (mB[index].equals("@") && !"*".equals(mA[index])) {
            command[w] = command[a_x];
            mA[w] = mA[a_x];
            mB[w] = mB[a_x];
            fA[w] = fA[a_x];
            fB[w] = fB[a_x];
            // works correctly!
        } else if (mB[index].equals("@") && mA[index].equals("*")) {
            command[w] = command[q];
            mA[w] = mA[q];
            mB[w] = mB[q];
            fA[w] = fA[q];
            fB[w] = fB[q];
        } else {
            command[b_x] = command[a_x];     //copying instruction
            mA[b_x] = mA[a_x];                  //copying mode A
            mB[b_x] = mB[a_x];                  //copying mode B
            fA[b_x] = fA[a_x];                  //copying field A
            fB[b_x] = fB[a_x];
        }
    }

    public void ADD(int index) {
        index = ObjLoopIndex.KeepIndexWithinLimits(index);
        SetIndexesToZero();
        CalcIndexes(index);
      //  System.out.println("ADD is being called");
        if (mA[index].equals("#") && !"@".equals(mB[index])) {
            fB[b_x] = fA[index] + fB[b_x];
        } else if (mA[index].equals("#") && mB[index].equals("@")) {
            fB[w] = fB[w] + fA[index];
        } else if (mA[index].equals("*") && !"@".equals(mB[index])) {
            fB[b_x] = fB[q] + fB[b_x];
            fA[b_x] = fA[q] + fA[b_x];
        } else if (mB[index].equals("@") && !"*".equals(mA[index])) {
            fB[w] = fB[a_x] + fB[w];
            fA[w] = fA[a_x] + fA[w];
        } else if (mB[index].equals("@") && mA[index].equals("*")) {
            fB[w] = fB[w] + fB[q];
            fA[w] = fA[w] + fA[q];
        } else {
            fB[b_x] = fB[a_x] + fB[b_x];
            fA[b_x] = fA[a_x] + fA[b_x];
        }
    }

    public void SUB(int index) {
        index = ObjLoopIndex.KeepIndexWithinLimits(index);
        SetIndexesToZero();
        CalcIndexes(index);
        if (mA[index].equals("#") && !"@".equals(mB[index])) {
            fB[b_x] = fB[b_x] - fA[index];
        } else if (mA[index].equals("#") && mB[index].equals("@")) {
            fB[w] = fB[w] - fA[index];
        } else if (mA[index].equals("*") && !"@".equals(mB[index])) {
            fB[b_x] = fB[b_x] - fB[q];
            fA[b_x] = fA[b_x] - fA[q];
        } else if (mB[index].equals("@") && !"*".equals(mA[index])) {
            fB[w] = fB[w] - fB[a_x];
            fA[w] = fA[w] - fA[a_x];
        } else if (mB[index].equals("@") && mA[index].equals("*")) {
            fB[w] = fB[q] - fB[w];
            fA[w] = fA[q] - fA[w];
        } else {
            fB[b_x] = fB[b_x] - fB[a_x];
            fA[b_x] = fA[b_x] - fA[a_x];
        }
    }

    public int JMP(int index) {
        index = ObjLoopIndex.KeepIndexWithinLimits(index);
        SetIndexesToZero();
        CalcIndexes(index);
        //ADD 4, 3      --->   keeps going back here
        //MOV 0, 2
        //JMP -2    A field only (can take *)
        if (mA[index].equals("*")) {
            return q;
        } else {
            return a_x;
        }
    }

    public int JMZ(int index) {
        index = ObjLoopIndex.KeepIndexWithinLimits(index);
        SetIndexesToZero();
        CalcIndexes(index);

        if (mB[index].equals("#") && fB[index] == 0) {
            if (mA[index].equals("*")) {
                //System.out.println("q " +q);
                return q;
            } else {
              //  System.out.println("a_x " +a_x);
                return a_x;
            }
        } else if (mB[index].equals("@")) {
            if (fB[w] == 0) {
                if (mA[index].equals("*")) {
                    return q;
                } else {
                    return a_x;
                }
            }
        }else{
            if (fB[b_x] == 0) {
                if (mA[index].equals("*")) {
                    return q;
                } else {
                    return a_x;
                }
            }
        }
        return 1000000;
    }

    public int DJN(int index) {
        index = ObjLoopIndex.KeepIndexWithinLimits(index);
        SetIndexesToZero();
        CalcIndexes(index);

        if (mB[index].equals("#") && !mB[index].equals("@")) {
            //System.out.println("we are in #");
            fB[index] = fB[index] - 1;
            if (fB[index] != 0) {
                if (mA[index].equals("*")) {
                    return q;
                } else if(!"*".equals(mA[index])) {
                    return a_x;
                }
            }
        } else if (mB[index].equals("@") && !mB[index].equals("#")) {
            //System.out.println("we are in @");
            fB[w] = fB[w] - 1;
            if (fB[w] != 0) {
                if (mA[index].equals("*")) {
                    return q;
                } else {
                    return a_x;
                }
            }
        }else if(!mB[index].equals("#") && !mB[index].equals("@")) {
            //System.out.println("we r in none");
            fB[b_x] = fB[b_x] - 1;
            //System.out.println("fB " + fB[b_x]);
            if (fB[w] != 0) {
                if (mA[index].equals("*")) {
                    return q;
                } else {
                    return a_x;
                }
        }
        }return 1000000;
    }

    public int CMP(int index) {
        if (mB[index].equals("#")) {
            if (fA[index] == fB[index]) {
                return index + 1;
            } else {
                return index;
            }
        } else if (mA[index].equals("*") && mB[index].equals("@")) {
            if(command[q]==command[w] && mA[q]==mA[w] && mB[q]==mB[w] && fA[q]==fA[w] && fB[q]==fB[w]){
                return index + 1; }
            //else {return index; }
        }else if(mA[index].equals("*") && !mB[index].equals("@")){
            if(command[q]==command[b_x] && mA[q]==mA[b_x] && mB[q]==mB[b_x] && fA[q]==fA[b_x] && fB[q]==fB[b_x]){
                return index + 1;   }
            //else { return index;   }
        }else if(!mA[index].equals("*") && mB[index].equals("@")){
            if(command[a_x]==command[w] && mA[a_x]==mA[w] && mB[a_x]==mB[w] && fA[a_x]==fA[w] && fB[a_x]==fB[w]){
            return index + 1;   }
            //else{   return index;   }
        }
        else if (!mA[index].equals("*") && !mB[index].equals("@")){
            if(command[a_x]==command[b_x] && mA[a_x]==mA[b_x] && mB[a_x]==mB[b_x] && fA[a_x]==fA[b_x] && fB[a_x]==fB[b_x]){
                return index + 1;   }
        }
          return index;
    }


}
