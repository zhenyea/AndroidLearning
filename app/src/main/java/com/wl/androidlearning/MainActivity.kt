package com.wl.androidlearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wl.androidlearning.designpattern.adapter.AudioPlayer
import com.wl.androidlearning.designpattern.bridge.GreenCircle
import com.wl.androidlearning.designpattern.bridge.RedCircle
import com.wl.androidlearning.designpattern.builder.MealBuilder
import com.wl.androidlearning.designpattern.composite.Employee
import com.wl.androidlearning.designpattern.decorator.RedShapeDectorator
import com.wl.androidlearning.designpattern.facade.ShapeMaker
import com.wl.androidlearning.designpattern.factory.FactoryProducer
import com.wl.androidlearning.designpattern.factory.ShapeFactory
import com.wl.androidlearning.designpattern.prototype.Circle
import com.wl.androidlearning.designpattern.prototype.Rectangle
import com.wl.androidlearning.designpattern.prototype.ShapeCache
import com.wl.androidlearning.designpattern.prototype.Square
import com.wl.androidlearning.designpattern.proxy.ProxyImage
import com.wl.androidlearning.designpattern.singleton.SingleObject
import com.wl.androidlearning.designpattern.singleton.Singleton
import com.wl.androidlearning.designpattern.singleton.SingletonEnum
import com.wl.androidlearning.fragment.HomeFragment
import com.wl.androidlearning.hilt.HiltSample
import com.wl.androidlearning.hilt.Truck
import com.wl.androidlearning.utils.LogUtils
import com.wl.androidlearning.viewmodel.FoodTasteViewModel
import com.wl.androidlearning.viewmodel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var hiltSample:HiltSample
    @Inject
    lateinit var truck: Truck

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var retrofit: Retrofit

    private val viewModel: MyViewModel by lazy { ViewModelProvider(this).get(MyViewModel::class.java) }

    private val foodTasteViewModel:FoodTasteViewModel by lazy { ViewModelProvider(this).get(FoodTasteViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        truck.deliver()

        LogUtils.d("okhttpclient",okHttpClient.toString())
        LogUtils.d("retrofit",retrofit.toString())
        //????????????

        var circle = ShapeFactory.createShape("circle")
        var rectangle = ShapeFactory.createShape("rectangle")
        var square = ShapeFactory.createShape("square")

        circle?.draw()
        rectangle?.draw()
        square?.draw()


        hiltSample.doSomeThing()

        bt_get_province.setOnClickListener {

//            val apiService = retrofit.create(RetrofitService::class.java)
//            lifecycleScope.launch(Dispatchers.IO) {
//                val foodTasteBeanItems = apiService.getFoodTaste()
//                LogUtils.d("mair","onclick-2-${Gson().toJson(foodTasteBeanItems)}")
//            }

            foodTasteViewModel.getFoodTaste()


        }


        foodTasteViewModel.apply {
            foodTastesLiveData.observe(this@MainActivity, Observer {

                it.data?.forEach {
                    LogUtils.d("testlviewmodel","taste:${it.name}")
                }
            })

            queryStatusLiveData.observe(this@MainActivity, Observer {
                LogUtils.d("testlviewmodel","status:${it}")
            })

            errorMsgLiveData.observe(this@MainActivity, Observer {
                LogUtils.e("testlviewmodel","error:${it}")
            })

        }



        bt_do_work.setOnClickListener {
            viewModel.doWork()
        }

        supportFragmentManager.beginTransaction().replace(R.id.cons_root,HomeFragment::class.java,null).commit()


        patternLearning()
    }

    private fun patternLearning() {
        //????????????

//        var circle = ShapeFactory.createShape("circle")
//        var rectangle = ShapeFactory.createShape("rectangle")
//        var square = ShapeFactory.createShape("square")
//
//        circle?.draw()
//        rectangle?.draw()
//        square?.draw()

        //??????????????????
        var shapeFactory = FactoryProducer.createFactory("shapeFactory")
        var circle = shapeFactory?.createShape("circle")
        var rectangle = shapeFactory?.createShape("rectangle")
        var square = shapeFactory?.createShape("square")

        circle?.draw()
        rectangle?.draw()
        square?.draw()

        var colorFactory = FactoryProducer.createFactory("colorFactory")
        var red = colorFactory?.createColor("red")
        var green = colorFactory?.createColor("green")
        var blue = colorFactory?.createColor("blue")

        red?.fill()
        green?.fill()
        blue?.fill()


        var singleObject = SingleObject.getInstance()
        singleObject.showMessage()

        var singleton = Singleton.getInstance()
        singleton.showMessage()

        var singletonEnum = SingletonEnum.INSTANCE
        singletonEnum.showMessage()


        Log.d("test","newfdsfasdftest")

        var mealBuilder = MealBuilder()
        var meal = mealBuilder.createNoVegMeal()
        meal.cost
        meal.showItems()

        var mealB =  mealBuilder.createVegMeal()
        mealB.cost
        mealB.showItems()

        //????????????
        ShapeCache.loadCatch()


        var circleProto =  ShapeCache.getShape("1") as Circle
        var rectangleProto =  ShapeCache.getShape("2") as Rectangle
        var squareProto =  ShapeCache.getShape("3") as Square
        Log.d("proto","circle:${circleProto.type}--rect:${rectangleProto.type}--suqare:${squareProto.type}")


        //???????????????
        var audioPlayer = AudioPlayer()
        audioPlayer.play("mp3","????????????")
        audioPlayer.play("mp4","????????????")
        audioPlayer.play("vlc","???????????????")
        audioPlayer.play("mkv","???????????????")

        //????????????
        var redCircle = com.wl.androidlearning.designpattern.bridge.Circle(RedCircle(),5,40,40)
        redCircle.draw()

        var greenCircle = com.wl.androidlearning.designpattern.bridge.Circle(GreenCircle(),5,40,40);
        greenCircle.draw()

        //????????????
        var xuLiang = Employee("??????",100000,"?????????")
        var liWei = Employee("??????",10000,"IT???")
        var xiuLiNa = Employee("?????????",7000,"?????????")
        var wangLei = Employee("??????",6000,"IT???")
        var hanHang = Employee("??????",6000,"IT???")
        var zhangRuiQiang = Employee("?????????",5000,"?????????")

        xuLiang.addEmployee(liWei)
        xuLiang.addEmployee(xiuLiNa)
        liWei.addEmployee(wangLei)
        liWei.addEmployee(hanHang)
        xiuLiNa.addEmployee(zhangRuiQiang)

        LogUtils.d("composite","name:${xuLiang}")
        xuLiang.subordinates.forEach {
        LogUtils.d("composite","name:${it}")
            it.subordinates.forEach{
                LogUtils.d("composite","name:${it}")
            }
        }

        //???????????????

        var redCircleShapeDectorator = RedShapeDectorator(com.wl.androidlearning.designpattern.decorator.Circle())

        var redRectangleDectorator = RedShapeDectorator(com.wl.androidlearning.designpattern.decorator.Rectangle())

        redCircleShapeDectorator.draw()
        redRectangleDectorator.draw()


        //????????????
        ShapeMaker.drawCircle()
        ShapeMaker.drawRectangle()
        ShapeMaker.drawSquare()

        //????????????
        var colors = arrayOf("yello","red","green","blue","pink","orange")


        for(i in 1..50){
          var circle = com.wl.androidlearning.designpattern.flyweight.Circle(colors.get(Random.nextInt(0,5)))
            circle.radius = Random.nextInt(5,40)
            circle.x = Random.nextInt(10,60)
            circle.y = Random.nextInt(20,70)
            var createCircle = com.wl.androidlearning.designpattern.flyweight.ShapeFactory.createCircle(circle)
            createCircle.draw()

        }

        //????????????
        var proxyImage = ProxyImage("????????????")
        proxyImage.display()

//


    }
}