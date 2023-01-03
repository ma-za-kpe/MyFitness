package com.maku.myfitness.core.util

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import java.io.IOException

fun replaceSpacialCharacters(string: String): String {
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
        for (anImgPath in imgPath) {
            arrayList.add("///android_asset/$categoryName/$anImgPath")
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return arrayList
}

fun getArrayOfImagesFromAsset(folderName: String, mContext: Context): java.util.ArrayList<String>? {
    val assetManager: AssetManager = mContext.assets
    val arrayList = java.util.ArrayList<String>()
    var imgPath = arrayOfNulls<String>(0)
    try {
        imgPath = assetManager.list(folderName)!!
        for (i in imgPath.indices) {
            arrayList.add(
                "file:///android_asset/" + folderName.replace("(", "_")
                    .replace(")", "_") + "/" + imgPath[i]
            )
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return arrayList
}

fun allAssetsFiltered(excrciseName: String, assets: AssetManager): java.util.ArrayList<String> {
    val arrayList = java.util.ArrayList<String>()
    try {
        val imgPath: Array<String?> = assets.list(replaceSpacialCharacters(excrciseName))!!
        for (i in imgPath.indices) {
            arrayList.add(
                "file:///android_asset/" + replaceSpacialCharacters(excrciseName) +"/"+ imgPath[i]
            )
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return arrayList
}
