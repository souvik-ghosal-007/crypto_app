package com.souvik.cryptoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.souvik.cryptoapp.databinding.ActivityMainBinding
import com.souvik.cryptoapp.fragments.HomeFragment
import com.souvik.cryptoapp.fragments.MarketsFragment
import com.souvik.cryptoapp.fragments.WatchlistFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replace(HomeFragment())
        binding.bottomBar.setOnItemSelectedListener {
            when(it) {
                0 -> replace(HomeFragment())
                1 -> replace(MarketsFragment())
                2 -> replace(WatchlistFragment())
            }
        }
    }

    private fun replace(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()
    }
}