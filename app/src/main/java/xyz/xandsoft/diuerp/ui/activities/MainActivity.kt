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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(R.id.main_container, MainFragment())
            .commit()

    }

    override fun onClick(view: View) {

    }
}