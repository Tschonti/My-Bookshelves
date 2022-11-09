package hu.bme.aut.android.mybookshelves

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import hu.bme.aut.android.mybookshelves.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.setOnItemSelectedListener {item ->
            if (item.itemId == R.id.booklist) {
                navController.navigate(R.id.bookListFragment)
            } else if (item.itemId == R.id.bookshelves) {
                when(navController.currentDestination?.id) {
                    R.id.bookListFragment -> navController.navigate(R.id.action_bookListFragment_to_bookshelvesFragment)
                    R.id.bookDetailsFragment -> navController.navigate(R.id.action_bookDetailsFragment_to_bookshelvesFragment)
                }

            }
            return@setOnItemSelectedListener true
        }

    }
}