package com.kunall17.trellassignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var feedRv = findViewById<RecyclerView>(R.id.recyclerView)
        var adapter = ADapter(this)
        feedRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        feedRv.adapter = adapter;
        object : PagerSnapHelper() {
            override fun findTargetSnapPosition(
                layoutManager: RecyclerView.LayoutManager,
                velocityX: Int,
                velocityY: Int
            ): Int {
                val s = super.findTargetSnapPosition(layoutManager, velocityX, velocityY)
                val holder: Viddd =
                    feedRv.findViewHolderForAdapterPosition(s) as Viddd
                adapter.setPlayer(s, holder);
                return s
            }
        }.attachToRecyclerView(feedRv)

    }
}
