package app.demo.implhash;

import android.util.Log;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class Job {

    private final IBlockRate rate;

    Job(IBlockRate rate) {
        this.rate = rate;
    }

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 5;

    void start() {
        Thread jobThread = new Thread(() -> {

            Block block = new Block("Hi im the first block", "0", rate);
            blockchain.add(block);
            Log.d("Block", "Trying to Mine block 1... ");
            blockchain.get(0).mineBlock(difficulty);

            Log.d("Block", "Blockchain is Valid: " + isChainValid());

            String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
            Log.d("Block", "The block chain: ");
            Log.d("Block", blockchainJson);

        });
        jobThread.start();
    }


    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;

        //loop through blockchain to check hashes:
        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);
            //compare registered hash and calculated hash:
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current Hashes not equal");
                return false;
            }
            //compare previous hash and registered previous hash
            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
        }
        return true;
    }
}
