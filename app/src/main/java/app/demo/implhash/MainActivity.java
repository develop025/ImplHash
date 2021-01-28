package app.demo.implhash;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements IBlockRate {

    private long startMilis;

    //    ver = 2
//    prev_block = "000000000000000117c80378b8da0e33559b5997f2ad55e2f7d18ec1975b9717"
//    mrkl_root = "871714dcbae6c8193a2bb9b2a69fe1c0440399f38d94b3a0f1b447275a29978a"
//    time_ = 0x53058b35 # 2014-02-20 04:57:25
//    bits = 0x19015f53
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startMilis = System.currentTimeMillis();
        Job job = new Job(this);
        job.start();
    }

    @Override
    public void callback(int hashNumber) {
        long emdMillis = System.currentTimeMillis();
        long period = emdMillis - startMilis;
//        long check = Long.MAX_VALUE / 1000;
//        if (check > hashNumber) {
//            startMilis = System.currentTimeMillis();
//            hashNumber = 0;
//        }
        long hashRate = hashNumber / period * 1000;
        Log.d("Block", "hashRate:" + hashRate);
    }
}