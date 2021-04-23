package com.yds.stickytoprecyclerview

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yds.stickytoprecycler.StickyItemDecoration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val json =
        "[{\"date\":\"2019-01\",\"children\":[{\"id\":\"30\",\"title\":\"迪拜之旅1\",\"date_time\":\"1546617600\",\"create_time\":" +
                "\"1545716408\",\"picture_count\":\"32\",\"status\":\"1\",\"date\":\"2019-01-05\"," +
                "\"cover_image\":\"http:\\/\\/chuangfen.oss-cn-hangzhou.aliyuncs.com\\/public\\/attachment\\/201812\\/25\\/13\\/5c21c2b73a93d.jpg\"}]}," +
                "{\"date\":\"2018-12\",\"children\":[{\"id\":\"31\",\"title\":\"长沙会议图集5\",\"date_time\":\"1545753600\"," +
                "\"create_time\":\"1545812893\",\"picture_count\":\"70\",\"status\":\"1\",\"date\":\"2018-12-26\"," +
                "\"cover_image\":\"http:\\/\\/chuangfen.oss-cn-hangzhou.aliyuncs.com\\/public\\/attachment\\/201812\\/26\\/16\\/5c233b9a23f5a.jpg\"}," +
                "{\"id\":\"29\",\"title\":\"长沙会议图集4\",\"date_time\":\"1545667200\",\"create_time\":\"1545710364\"," +
                "\"picture_count\":\"61\",\"status\":\"1\",\"date\":\"2018-12-25\"," +
                "\"cover_image\":\"http:\\/\\/chuangfen.oss-cn-hangzhou.aliyuncs.com\\/public\\/attachment\\/201812\\/25\\/11\\/5c21ab1b9263d.jpg\"}," +
                "{\"id\":\"24\",\"title\":\"长沙会议图集3\",\"date_time\":\"1543939200\",\"create_time\":\"1544605755\"," +
                "\"picture_count\":\"118\",\"status\":\"1\",\"date\":\"2018-12-05\"," +
                "\"cover_image\":\"http:\\/\\/chuangfen.oss-cn-hangzhou.aliyuncs.com\\/public\\/attachment\\/201812\\/12\\/17\\/5c10d0bc9ea4a.jpg\"}," +
                "{\"id\":\"23\",\"title\":\"长沙会议图集2\",\"date_time\":\"1543766400\",\"create_time\":\"1544605483\"," +
                "\"picture_count\":\"52\",\"status\":\"1\",\"date\":\"2018-12-03\"," +
                "\"cover_image\":\"http:\\/\\/chuangfen.oss-cn-hangzhou.aliyuncs.com\\/public\\/attachment\\/201812\\/12\\/17\\/5c10cf66f0d77.jpg\"}," +
                "{\"id\":\"22\",\"title\":\"长沙会议图集\",\"date_time\":\"1543593600\",\"create_time\":\"1544605082\"," +
                "\"picture_count\":\"9\",\"status\":\"1\",\"date\":\"2018-12-01\"," +
                "\"cover_image\":\"http:\\/\\/chuangfen.oss-cn-hangzhou.aliyuncs.com\\/public\\/attachment\\/201812\\/12\\/17\\/5c10ce98db254.jpg\"}]}" +
                ",{\"date\":\"2018-11\",\"children\":[{\"id\":\"10\",\"title\":\"测试10月\",\"date_time\":\"1543248000\"," +
                "\"create_time\":\"1543281219\",\"picture_count\":\"6\",\"status\":\"1\",\"date\":\"2018-11-27\"," +
                "\"cover_image\":\"http:\\/\\/chuangfen.oss-cn-hangzhou.aliyuncs.com\\/public\\/attachment\\/201811\\/27\\/09\\/5bfc9a3af1a57.jpg\"}]}," +
                "{\"date\":\"2018-10\",\"children\":[{\"id\":\"9\",\"title\":\"测试图集\",\"date_time\":\"1539100800\"," +
                "\"create_time\":\"1543222989\",\"picture_count\":\"9\",\"status\":\"1\",\"date\":\"2018-10-10\"," +
                "\"cover_image\":\"http:\\/\\/chuangfen.oss-cn-hangzhou.aliyuncs.com\\/public\\/attachment\\/201811\\/26\\/17\\/5bfbb669cd528.jpg\"}]}," +
                "{\"date\":\"2018-09\",\"children\":[{\"id\":\"12\",\"title\":\"测试9月\",\"date_time\":\"1536163200\"," +
                "\"create_time\":\"1543282534\",\"picture_count\":\"8\",\"status\":\"1\",\"date\":\"2018-09-06\"," +
                "\"cover_image\":\"http:\\/\\/chuangfen.oss-cn-hangzhou.aliyuncs.com\\/public\\/attachment\\/201811\\/27\\/09\\/5bfc9f651c4c5.jpg\"}]}," +
                "{\"date\":\"2018-08\",\"children\":[{\"id\":\"26\",\"title\":\"迪拜之旅\",\"date_time\":\"1534262400\"," +
                "\"create_time\":\"1544606585\",\"picture_count\":\"6\",\"status\":\"1\",\"date\":\"2018-08-15\"," +
                "\"cover_image\":\"http:\\/\\/chuangfen.oss-cn-hangzhou.aliyuncs.com\\/public\\/attachment\\/201812\\/12\\/17\\/5c10d378174d6.jpg\"}]}]"


    var pictureAdapter: PictureAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var stickyItemDecoration = StickyItemDecoration<BaseViewHolder>(container, PictureModel.PICTURE_TITLE)
        stickyItemDecoration.setOnStickyChangeListener({ offset ->
            container.scrollChild(offset)
            container.visibility = VISIBLE
        }, {
            container.reset()
            container.visibility = VISIBLE
        })

        container.setDataCallback{ pos ->
            var listModels = pictureAdapter?.data
            if ((listModels != null) and (listModels!!.size > pos)) {
                tv_picture_time.text = listModels[pos].date
            }
        }

        val modelList: List<PictureListModel> = Gson().fromJson<List<PictureListModel>>(
            json,
            object : TypeToken<List<PictureListModel?>?>() {}.type
        )

        val pictureModelList = arrayListOf<PictureModel>()

        for (model in modelList) {
            val title = PictureModel(PictureModel.PICTURE_TITLE)
            title.date = model.getDate()

            //先添加title
            pictureModelList.add(title)
            //再添加数据
            pictureModelList.addAll(model.children)
        }
        pictureAdapter = PictureAdapter(pictureModelList)


        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.addItemDecoration(stickyItemDecoration)
        val spaceDecoration = SpaceDecoration(dp2px(this, 10f))
        spaceDecoration.mPaddingStart = false
        recyclerView.addItemDecoration(spaceDecoration)
        recyclerView.adapter = pictureAdapter
        pictureAdapter?.bindToRecyclerView(recyclerView)
    }

    fun dp2px(context: Context, dpVal: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dpVal,
            context.resources.displayMetrics
        ).toInt()
    }
}