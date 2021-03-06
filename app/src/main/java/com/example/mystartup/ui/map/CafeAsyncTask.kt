package com.example.mystartup.ui.map

import android.os.AsyncTask
import android.util.Log
import kotlinx.android.synthetic.main.except_map_fragment.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class CafeAsyncTask(val cafeActivity: CafeActivity) : AsyncTask<Any?, Any?, Any?>() {
    private lateinit var buffer: String
    private val cafeList = ArrayList<CafeInfo>()
    override fun onPostExecute(result: Any?) {
        super.onPostExecute(result)
        val json = JSONObject(buffer)
        val chiefObject = json["jobCafeOpenInfo"] as JSONObject
        val resultArray = chiefObject["RESULT"] as JSONObject
        val resultCode = resultArray.get("CODE").toString()

        if (resultCode == "INFO-000") {
            val realArray = chiefObject["row"] as JSONArray
            /*카페이미지주소(33)FILE_NM
            카페명(1)CAFE_NM
            간략소개(2)SMPL_INTRO
            구군(27)GUGUN*/

            for (i in 0 until realArray.length()) {
                cafeList.add(
                    CafeInfo(
                        realArray.getJSONObject(i).get("FILE_NM").toString(),
                        realArray.getJSONObject(i).get("CAFE_NM").toString(),
                        realArray.getJSONObject(i).get("SMPL_INTRO").toString(),
                        realArray.getJSONObject(i).get("GUGUN").toString()
                    )
                )
                Log.d("request", cafeList.get(i).cafeURL)
            }
        }else{
            //통신잘안됨
        }
        with(cafeActivity.cafe_recycler_view) {
            this.adapter = CafeRecyclerAdapter(
                cafeActivity,
                android.view.LayoutInflater.from(cafeActivity),
                cafeList
            )
            this.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(cafeActivity)
        }


    }

    override fun doInBackground(vararg params: Any?): Any? {
        Log.d("requestProperty", "실행됨")
        val urlString =
            "http://openapi.seoul.go.kr:8088/75624659416a64683131306d686f4a6c/json/jobCafeOpenInfo/1/80/"

        val url = URL(urlString)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection

        connection.requestMethod = "GET"
        connection.setRequestProperty("Content-type", "application/json")
        Log.d("requestProperty1", connection.requestProperties.toString())


        Log.d("requestProperty2", connection.responseCode.toString())
        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val reader = BufferedReader(
                InputStreamReader(
                    connection.inputStream,
                    "UTF-8"
                )
            )
            buffer = reader.readLine()
            Log.d("requestProperty1", buffer)
        }
        return null
    }

}
