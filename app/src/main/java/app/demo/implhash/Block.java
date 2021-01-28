package app.demo.implhash;

import android.util.Log;

import java.util.Date;


public class Block {

    private final IBlockRate rate;
    public String hash;
    public String previousHash;
    private String data; //our data will be a simple message.
    private long timeStamp; //as number of milliseconds since 1/1/1970.
    private int nonce;
    private int hashNumber;

    //Block Constructor.
    public Block(String data, String previousHash, IBlockRate rate) {
        this.rate = rate;
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash(); //Making sure we do this after we set the other values.
    }

    public String calculateHash() {
        String calculatedHash = StringUtil.applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        data
        );
        hashNumber++;
        if (hashNumber % 10000 == 0)
            rate.callback(hashNumber);
        return calculatedHash;
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0"
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        Log.d("Block", "Block Mined!!! : " + hash);
    }

    public int getHashNumber() {
        return hashNumber;
    }

    public void setHashNumber(int hashNumber) {
        this.hashNumber = hashNumber;
    }
}