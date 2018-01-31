package yxdroid.droidfm.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtil {


    public static Bitmap decode(Context context, int resId, int reqWidth, int reqHeight) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(context.getResources(), resId, options);

        options.inSampleSize = caculateSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(context.getResources(), resId, options);
    }

    private static int caculateSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        int width = options.outWidth;
        int height = options.outHeight;

        int simpleSize = 1;

        if (width > reqWidth || height > reqHeight) {
            int halfWidth = reqWidth / 2;
            int halfHeight = reqHeight / 2;

            while ((halfWidth / simpleSize) > reqWidth
                    && (halfHeight / simpleSize) > reqHeight) {
                simpleSize *= 2;
            }

        }

        return simpleSize;
    }
}
