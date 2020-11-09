package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard(){
    val focus = this.currentFocus
    focus?.let {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.let {
            it.hideSoftInputFromWindow(focus.windowToken, 0)
        }
    }
}

fun Activity.isKeyboardOpen(): Boolean{
    val rootView = findViewById<View>(android.R.id.content)
    val visibleBounds = Rect()
    rootView.getWindowVisibleDisplayFrame(visibleBounds)
    val heightDiff = rootView.height - visibleBounds.height()
    return heightDiff > 0.25 * rootView.height
}

fun Activity.isKeyboardClosed(): Boolean {
    return this.isKeyboardOpen().not()
}