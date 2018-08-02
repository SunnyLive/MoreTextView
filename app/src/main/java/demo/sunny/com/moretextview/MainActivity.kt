package demo.sunny.com.moretextview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        more.setText("aaaaaaaaaaaaaaaaaaaaa" +
                     "aaaaaaaaaaaaaaaaaaaaa" +
                     "aaaaaaaaaaaaaaaaaaaaa" +
                     "aaaaaaaaaaaaaaaaaaaaa" +
                     "aaaaaaaaaaaaaaaaaaaaaaa")
    }
}
