package demo.sunny.com.moretextview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        more.setText("汉皇重色思倾国，御宇多年求不得。\n" +
                "杨家有女初长成，养在深闺人未识。\n" +
                "天生丽质难自弃，一朝选在君王侧。\n" +
                "回眸一笑百媚生，六宫粉黛无颜色。\n" +
                "春寒赐浴华清池，温泉水滑洗凝脂。\n" +
                "侍儿扶起娇无力，始是新承恩泽时。\n" +
                "云鬓花颜金步摇，芙蓉帐暖度春宵。\n" +
                "春宵苦短日高起，从此君王不早朝。\n" +
                "承欢侍宴无闲暇，春从春游夜专夜。")
    }
}
