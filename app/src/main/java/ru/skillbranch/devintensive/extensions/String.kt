package ru.skillbranch.devintensive.extensions

fun String.truncate(charsNum : Int = 16) : String {
    return if (charsNum >= this.length)
        this.trim()
    else
        "${this.substring(0, charsNum).trim()}..."

}

fun String.stripHtml() : String {
    var doAdd:Boolean = true
    val escapeSequence = arrayListOf(
        '&',
        '<',
        '>',
        '\'',
        '\\',
        '"'
    )
    var newString = ""
    for (c in this) {
        if (c == '<') {
            doAdd = false
        } else if (c == '>') {
            doAdd = true
            continue
        } else if (escapeSequence.contains(c)) {
            continue
        }
    if (doAdd) newString = "$newString$c"

    }
    newString = newString.trim()
    while (newString.contains("  ")) newString = newString.replace("  ", " ")
    return newString
}


//val html : String = "<p class=\"title\">      \"Образоват&ельное      IT-сообщество\"      Skill Branch</p>"

//fun main(){
//    //html.stripHtml()
//    val string = "1234567890123456789"
//    print(string.truncate())
//}

