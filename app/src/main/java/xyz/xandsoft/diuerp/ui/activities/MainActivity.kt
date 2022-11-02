package xyz.xandsoft.diuerp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import xyz.xandsoft.diuerp.R
import xyz.xandsoft.diuerp.ui.fragments.MainFragment
import xyz.xandsoft.diuerp.ui.fragments.ScannerFragment

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var mainProductAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        supportFragmentManager.beginTransaction().replace(R.id.main_container, MainFragment())
            .commit()

        mainProductAdd.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, ScannerFragment())
                .addToBackStack("")
                .commit()
        }
    }

    private fun init() {

        mainProductAdd = findViewById(R.id.main_product_add)
    }

    override fun onClick(view: View) {

    }
}