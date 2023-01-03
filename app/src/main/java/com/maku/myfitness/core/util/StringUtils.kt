package com.maku.myfitness.core.util

import android.content.Context
import android.content.res.AssetManager
import java.io.IOException

fun ReplaceSpacialCharacters(string: String): String {
    return string.replace(" ", "").replace("&", "").replace("-", "").replace("'", "")
        .replace("(", "").replace(")", "")
        .replace("/", "")
}

fun getAssetItems(mContext: Context, categoryName: String): ArrayList<String>? {
    val arrayList = ArrayList<String>()
    val imgPath: Array<String>
    val assetManager = mContext.assets
    try {
        imgPath = assetManager.list(categoryName) as Array<String>
        if (imgPath != null) {
            for (anImgPath in imgPath) {
                arrayList.add("///android_asset/$categoryName/$anImgPath")
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return arrayList
}

fun getArrayOfImagesFromAsset(folderName: String): java.util.ArrayList<String>? {
    val assetManager: AssetManager? = null
    val arrayList = java.util.ArrayList<String>()
    var imgPath = arrayOfNulls<String>(0)
    try {
        imgPath = assetManager?.list(folderName)!!
        if (imgPath != null) {
            for (i in imgPath.indices) {
                arrayList.add(
                    "file:///android_asset/" + folderName.replace("(", "_")
                        .replace(")", "_") + "/" + imgPath[i]
                )
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return arrayList
}
