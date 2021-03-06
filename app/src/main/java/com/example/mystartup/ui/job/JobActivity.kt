package com.example.mystartup.ui.job

import RecyclerViewAdapter
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mystartup.R
import kotlinx.android.synthetic.main.activity_job.*

class JobActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job)

        val asyncTask = JobAsynctask(this@JobActivity)
        asyncTask.execute()


        //검색버튼
        jod_find_enter.setOnClickListener {
            job_edit.onEditorAction(EditorInfo.IME_ACTION_SEARCH)
        }
        job_edit.setOnEditorActionListener { v, actionId, event ->
            when(actionId){
                EditorInfo.IME_ACTION_SEARCH -> {
                    //클릭
                    Toast.makeText(this,"조대현 멍청이ㅎㅎ",Toast.LENGTH_SHORT).show()
                    CloseKeyboard()
                    true
                }
                else ->{
                    Toast.makeText(this,"뭐징",Toast.LENGTH_SHORT).show()
                    false
                }
            }
        }
        job_edit.setOnKeyListener { v, keyCode, event ->
            when(keyCode){
                KeyEvent.KEYCODE_ENTER->{
                    CloseKeyboard()
                    Toast.makeText(this,"조대현 멍청이ㅎㅎ",Toast.LENGTH_SHORT).show()
                    true
                }
                else->{
                    false
                }
            }
        }


    }


    fun CloseKeyboard()
    {
        var view = this.currentFocus

        if(view != null)
        {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    class JobInfoForList(
        var name:String, var info:String, var money:String,
        var place:String, var career:String) {}
}
