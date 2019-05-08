package models;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class SearchX implements Runnable {

    public List<BigInteger> fibonachi;
    public boolean isEnded;
    private int size;

    public SearchX(int x) {
        size = x;
        fibonachi = new ArrayList<BigInteger>(size);
        isEnded = false;
    }

    @Override
    public void run() {
        if (size > 2)
        {
            fibonachi.add(BigInteger.valueOf(0));
            fibonachi.add(BigInteger.valueOf(1));
        }
        else {
            isEnded=true;
            return;
        }


        for (int i = 2; i < size;i++)
        {
            BigInteger previousNumber = fibonachi.get(i-2);
            BigInteger nextNumber = fibonachi.get(i-1);

            BigInteger sum = nextNumber.add(previousNumber);
            fibonachi.add(sum);
        }
        isEnded = true;

    }

    public void saveNums(String path){
        String toSave = "";
        for (BigInteger b:fibonachi) {
            
        }
    }
}
