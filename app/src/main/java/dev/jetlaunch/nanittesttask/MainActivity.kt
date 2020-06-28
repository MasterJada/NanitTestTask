package dev.jetlaunch.nanittesttask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import dev.jetlaunch.nanittesttask.ui.DetailsFragment

class MainActivity : AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null)
            supportFragmentManager.commit {
                replace(R.id.container, DetailsFragment.newInstance)
            }
    }

}