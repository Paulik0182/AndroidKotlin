package com.android.androidjavakotlin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.util.*

@Suppress("DEPRECATION")
class RemarksList : Fragment(){
    private val CURRENT_REMARK = "CurrentRemark"
    private var currentRemark: Remark? = null
    private var isLandscape = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                     savedInstanceState: Bundle?): View? {
//        View view = inflater.inflate(R.layout.fragment_remarks_list, container, false);
//        return view;
        return inflater.inflate(R.layout.fragment_remarks_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList(view)
    }

    @SuppressLint("NonConstantResourceId")
    private fun initList(view: View) {
        val layoutView = view as LinearLayout
        val dates: Array<String> = getResources().getStringArray(R.array.dates)
        val remarks: Array<String> = getResources().getStringArray(R.array.remarks)
        for (i in remarks.indices) {
            val remark = remarks[i]
            val date = dates[i]
            @SuppressLint("UseRequireInsteadOfGet") val subLayoutView = LinearLayoutCompat(Objects.requireNonNull(getContext()))
            subLayoutView.orientation = LinearLayoutCompat.VERTICAL
            val textviewName = TextView(getContext())
            val textviewDate = TextView(getContext())
            val subLayoutViewId = subLayoutView.id

            // оформление view
            val remarkColor = Color.parseColor("#FFFAF096")
            textviewDate.text = date
            textviewDate.setTextColor(Color.GRAY)
            textviewDate.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            textviewDate.setBackgroundColor(remarkColor)
            textviewName.text = remark
            textviewName.textSize = 25f
            textviewName.setTextColor(Color.BLACK)
            textviewName.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            textviewName.setBackgroundColor(remarkColor)

            // добавление view в layouts
            subLayoutView.addView(textviewName)
            subLayoutView.addView(textviewDate)
            layoutView.addView(subLayoutView)

            // вызов menu
            subLayoutView.setOnLongClickListener { v: View? ->
                val activity: Activity = requireActivity()
                val popupMenu = PopupMenu(activity, v!!)
                val menu = popupMenu.menu
                activity.menuInflater.inflate(R.menu.popup, menu)
                popupMenu.setOnMenuItemClickListener { item: MenuItem ->
                    val id = item.itemId
                    when (id) {
                        R.id.favorite_popup -> {
                            Toast.makeText(getContext(), "В Избранное", Toast.LENGTH_SHORT).show()
                            return@setOnMenuItemClickListener true
                        }
                        R.id.delete_popup -> {
                            Toast.makeText(getContext(), "Удалить", Toast.LENGTH_SHORT).show()
                            return@setOnMenuItemClickListener true
                        }
                    }
                    true
                }
                popupMenu.show()
                true
            }

            // обработка нажатия на заметку
            val index: Int = i
            subLayoutView.setOnClickListener { v: View? ->
                currentRemark = Remark(getResources().getStringArray(R.array.remarks).get(index), getResources().getStringArray(R.array.descriptions).get(index), getResources().getStringArray(R.array.dates).get(index))
                showRemark(currentRemark!!)
            }
        }
    }

    // метод вызывает один из двух методов в зависимости от ориентации экрана
    private fun showRemark(remark: Remark) {
        if (isLandscape) {
            showRemarkLandscape(remark)
        } else {
            showRemarkPortrait(remark)
        }
    }

    private fun showRemarkPortrait(remark: Remark) {
        // создаём новый фрагмент
        val notesDetailed: RemarksDetailedFragment = RemarksDetailedFragment.newInstance(remark)
        // выполняем транзакцию по замене фрагмента
        val fragmentManager: FragmentManager = requireActivity().getSupportFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.remarkDetailed, notesDetailed)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.commit()

        // переходим на RemarkssDetailedActivity, т.к. к ней привязан фрагмент с деталями заметки
        val intent = Intent()
        intent.setClass(getActivity(), RemarksDetailedActivity::class.java)
        // передаем с интентом экземпляр заметки, по которой было нажатие
        intent.putExtra(RemarksDetailedFragment.ARG_REMARK, remark)
//        startActivity(intent);
    }

    private fun showRemarkLandscape(remark: Remark?) {
        // создаём новый фрагмент с текущей позицией
        val remarksDetailed: RemarksDetailedFragment = RemarksDetailedFragment.newInstance(remark)
        // выполняем транзакцию по замене фрагмента
        val fragmentManager: FragmentManager = requireActivity().getSupportFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.remarkDetailed, remarksDetailed)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.commit()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE

        //  показываем сохраненную заметку
        currentRemark = if (savedInstanceState != null) {
            savedInstanceState.getParcelable(CURRENT_REMARK)
        } else {
            // если не появлась заметка - показываем самую первую
            Remark(getResources().getStringArray(R.array.remarks).get(0), getResources().getStringArray(R.array.descriptions).get(0), getResources().getStringArray(R.array.dates).get(0))
        }
        if (isLandscape) {
            showRemarkLandscape(currentRemark)
        }
    }

    // сохраняем текущую отображаемую заметку
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(CURRENT_REMARK, currentRemark)
        super.onSaveInstanceState(outState)
    }
}