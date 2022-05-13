package com.syllabus.w12contact.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.syllabus.w12contact.R

class AvatarCard(context:Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.avatar_card_layout, this)
    }

}